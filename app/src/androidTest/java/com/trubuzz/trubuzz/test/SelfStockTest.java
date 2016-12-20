package com.trubuzz.trubuzz.test;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.elements.AQuotes;
import com.trubuzz.trubuzz.elements.Global;
import com.trubuzz.trubuzz.feature.custom.ViewInteractionHandler;
import com.trubuzz.trubuzz.idlingResource.ViewIdlingResource;
import com.trubuzz.trubuzz.shell.beautify.RecyclerViewItemElement;
import com.trubuzz.trubuzz.utils.God;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.CursorMatchers.withRowString;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.trubuzz.trubuzz.constant.Config.watchlistKey;
import static com.trubuzz.trubuzz.feature.custom.CustomRecyclerViewActions.scrollToRecyclerPosition;
import static com.trubuzz.trubuzz.feature.custom.ViewInteractionHandler.getDescendant;
import static com.trubuzz.trubuzz.feature.custom.ViewInteractionHandler.getRecyclerViewItem;
import static com.trubuzz.trubuzz.feature.custom.ViewInteractionHandler.getRecyclerViewItemCount;
import static com.trubuzz.trubuzz.feature.custom.ViewInteractionHandler.getText;
import static com.trubuzz.trubuzz.feature.custom.ViewInteractionHandler.getView;
import static com.trubuzz.trubuzz.shell.Park.given;
import static com.trubuzz.trubuzz.shell.beautify.ElementHandle.getElementText;
import static com.trubuzz.trubuzz.utils.DoIt.regIdlingResource;
import static com.trubuzz.trubuzz.utils.DoIt.sleep;
import static com.trubuzz.trubuzz.utils.DoIt.toHexUpString;
import static com.trubuzz.trubuzz.utils.Judge.checkNotZero;
import static com.trubuzz.trubuzz.utils.Judge.isExistRecyclerViewData;
import static junit.framework.Assert.fail;
import static org.hamcrest.Matchers.not;

/**
 * Created by king on 16/12/15.
 */
@RunWith(JUnitParamsRunner.class)
public class SelfStockTest extends BaseTest{
    private final String TAG = "jcd_" + this.getClass().getSimpleName();
    private static boolean initialized = false;
    private final AQuotes aq = new AQuotes();
    private final AQuotes.details details = new AQuotes.details();

    @Rule
    public ActivityTestRule<?> matr = new ActivityTestRule(God.getFixedClass(AName.MAIN));


    private Object[] index_data(){
        return new Object[]{
                new Object[]{"HSI"},        //香港恒生指数
                new Object[]{"HSCCI"},      //恒生香港中资企业指数(红筹指数)
                new Object[]{"HSCEI"},      //恒生中国企业指数
                new Object[]{"SPX"},        //美国标准普尔500指数 (SPX)
                new Object[]{"NASX"},       //纳斯达克综合指数 (IXIC)
                new Object[]{"DJI"},        //道琼斯工业平均指数 (DJI)
                new Object[]{"SH000001"},        //上证指数(SH:000001)
                new Object[]{"SZ399001"},        //深证成指(SZ:399001)
                new Object[]{"SZ399006"},        //创业板指(SZ:399006)
                new Object[]{"SZ399007"},        //深证300(SZ:399007)
        };
    }
    @Before
    public void into_quotes() {
        Wish.wantLogin();
        given(Global.quotes_radio).perform(click());
        if(!initialized){
            initialized = init();
        }
    }
    private boolean init(){
        try {
        /* 1. 删除全部自选列表 */
            WatchlistTest wt = new WatchlistTest();
            wt.b_into_watchlist();
            wt.b_delete_remain_one();
        /* 2. 添加3个列表 */
            for (int i = 1; i <= 3; i++) {
                wt.c_add_watchlist_item(toHexUpString(new Date().getTime()), true);
            }
        } catch (Exception e) {
            Log.e(TAG, "init: error", e);
            return false;
        }
        return true;
    }
    /**
     * 添加 美股
     */
    @Test
    public void add_us_stock(){
        given(AQuotes.us_fence).perform(click());

        add_stock();
    }
    /**
     * 添加 港股
     */
    @Test
    public void add_hk_stock(){
        given(AQuotes.hk_fence).perform(click());

        add_stock();
    }

    /**
     * 添加 沪深
     */
    @Test
    public void add_cn_stock(){
        given(AQuotes.cn_fence).perform(click());

        add_stock();
    }

    /**
     * 添加 指数
     */
    @Test
    @Parameters(method = "index_data")
    public void add_index_stock(String index_symbol){
        given(aq.search_icon).perform(click());
        given(aq.search_input).perform(replaceText(index_symbol));
        regIdlingResource(new ViewIdlingResource(getView(aq.stocks_recycler)));
        Log.i(TAG, String.format("add_index_stock: search %s RecyclerView has shown .",
                index_symbol));
        given(aq.search_symbol.setText(index_symbol)).perform(click()); //选择指定记录进入详情

        given(details.add_self_stock).perform(click(pressBack()));  //点击添加自选按钮
        sleep(2000);
        List<ViewInteractionHandler.ViewPosition> not_in_group_views =
                getRecyclerViewItem(aq.stocks_recycler, not(hasDescendant(details.in_group_yet.way())));
        String group_text = "";

        // 若所选指数已存在于列表则跳过
        if(not_in_group_views.size() > 0) {
            int random_group = God.getRandomInt(not_in_group_views.size() - 1, 0);
            ViewInteractionHandler.ViewPosition groupPosition = not_in_group_views.get(random_group);   //取出随机选取的view 位置信息
            //随机选取未在分类的view
            given(aq.stocks_recycler).perform(scrollToRecyclerPosition(groupPosition.position));    //先滑动到view至可见
            RecyclerViewItemElement self_group = new RecyclerViewItemElement(aq.stocks_recycler).setPosition(groupPosition.position);
            group_text = getText(details.group_name.setAncestor(self_group));      //保存自选列表名
            Log.i(TAG, String.format("add_us_stock: select watchlist '%s'", group_text));

            given(self_group).perform(click());
            sleep(2000);
            given(self_group).check(matches(hasDescendant(details.in_group_yet.way())));
        }else{
            //这里只为随机获取一个Group name 方便check.
            List<ViewInteractionHandler.ViewPosition> in_group_views =
                    getRecyclerViewItem(aq.stocks_recycler, hasDescendant(details.in_group_yet.way()));
            int random_group = God.getRandomInt(in_group_views.size() - 1, 0);
            group_text = getText(getDescendant(in_group_views.get(random_group).view ,details.group_name));///////
        }
        this.back_to_main();
        this.check_group(index_symbol ,group_text,true);
    }
    /**
     * 同一只股票 / 指数可添加至多个Watchlist
     * @deprecated 后续重构逻辑
     */
    @Test
    public void add_more_group(){
        given(AQuotes.us_fence).perform(click());

        this.into_all_stocks();
        String symbol ="";
        List<ViewInteractionHandler.ViewPosition> not_in_group_views = null;

        for(int i=1;i<=3;i++) {
            symbol = getSymbol ();   //选择股票并返回 Symbol
            sleep(2000);
            not_in_group_views = getRecyclerViewItem(aq.stocks_recycler, not(hasDescendant(details.in_group_yet.way())));
            Log.i(TAG, String.format("add_more_group: %s 为在分类列表数为 : %s .",symbol ,not_in_group_views.size() ));
            if (not_in_group_views.size() >= 2) {
                break;
            }
            Log.i(TAG, String.format("add_more_group: 第 %s 次空闲的自选列表少于2 .",i ));
            if (i == 3) {
                fail(String.format("%s 次随机选择空闲自选列表少于2 ,请初始化自选 .",i));
            }
            this.back_loop(2);
        }
        List<String> group_texts = new ArrayList<>();
        for(ViewInteractionHandler.ViewPosition groupPosition : not_in_group_views){
            given(aq.stocks_recycler).perform(scrollToRecyclerPosition(groupPosition.position));    //先滑动到view至可见
            RecyclerViewItemElement self_group = new RecyclerViewItemElement(aq.stocks_recycler).setPosition(groupPosition.position);
            given(self_group).perform(click());
            sleep(2000);
            given(self_group).check(matches(hasDescendant(details.in_group_yet.way())));

            String group_text = getText(details.group_name.setAncestor(self_group));      //保存自选列表名
            Log.i(TAG, String.format("add_more_group: add to watchlist '%s'", group_text));
            group_texts.add(group_text);
        }
        this.back_to_main();
        for (String text : group_texts) {
            this.check_group(symbol ,text,true);
            this.back_to_main();
        }
    }

    /**
     * 从默认列表中删除自选
     */
    @Test
    public void del_with_default_group(){
        given(AQuotes.watchlist_fence).perform(click());
        given(AQuotes.watchlist_spinner).perform(click());
        given(aq.watchlist_default_item).perform(click());

        int recyclerViewItemCount = getRecyclerViewItemCount(aq.stocks_recycler);
        int randomItem = God.getRandomInt(recyclerViewItemCount - 1, 0);
        // 随机选择一只股票
        RecyclerViewItemElement recyclerViewItem = new RecyclerViewItemElement(aq.stocks_recycler).setPosition(randomItem);
        given(aq.stocks_recycler).perform(scrollToRecyclerPosition(randomItem));    //滚动到位置

        String symbol = getText(AQuotes.symbol_text.setAncestor(recyclerViewItem)); //获取该股票的Symbol
        given(recyclerViewItem).perform(click());
        given(details.add_self_stock).perform(click(pressBack()));
        sleep(2000);

        given(aq.watchlist_default_item)
                .check(matches(hasSibling(details.in_group_yet.way())))
                .perform(click());

        this.back_to_main();
        sleep(1000);
        this.check_group(symbol ,getElementText(aq.watchlist_default_item) ,false); //检查列表中已消失

    }


/***************************** 私有辅助方法 *******************************/
    private void add_stock(){
        this.into_all_stocks();
        String symbol ="";
        List<ViewInteractionHandler.ViewPosition> not_in_group_views = null;

        for(int i=1;i<=3;i++) {
            symbol = getSymbol ();   //选择股票并返回 Symbol
            sleep(2000);
            not_in_group_views = getRecyclerViewItem(aq.stocks_recycler, not(hasDescendant(details.in_group_yet.way())));
            Log.i(TAG, String.format("add_us_stock: %s 为在分类列表数为 : %s .",symbol ,not_in_group_views.size() ));
            if (not_in_group_views.size() != 0) {
                break;
            }
            Log.i(TAG, String.format("add_us_stock: 第 %s 次没有空闲的自选列表 .",i ));
            if (i == 3) {
                fail(String.format("%s 次随机选择没有空闲自选列表 ,请初始化自选 .",i));
            }
            this.back_loop(2);
        }

        int random_group = God.getRandomInt(not_in_group_views.size() - 1, 0);
        ViewInteractionHandler.ViewPosition groupPosition = not_in_group_views.get(random_group);   //取出随机选取的view 位置信息
        //随机选取未在分类的view
        given(aq.stocks_recycler).perform(scrollToRecyclerPosition(groupPosition.position));    //先滑动到view至可见
        RecyclerViewItemElement self_group = new RecyclerViewItemElement(aq.stocks_recycler).setPosition(groupPosition.position);
        String group_text = getText(details.group_name.setAncestor(self_group));      //保存自选列表名
        Log.i(TAG, String.format("add_us_stock: select watchlist '%s'",group_text ));

        given(self_group).perform(click());
        sleep(2000);
        given(self_group).check(matches(hasDescendant(details.in_group_yet.way())));

        this.back_to_main();

        this.check_group(symbol, group_text ,true);   //检查是否在自选中存在
    }

    /**
     * 进入类别全部
     */
    private void into_all_stocks(){
        List<ViewInteractionHandler.ViewPosition> kind_all_view = getRecyclerViewItem(aq.stocks_recycler, aq.kind_all.way());
        Log.i(TAG, String.format("add_us_stock: kind_all_view size: %s",kind_all_view.size() ));
        int randomGroup = God.getRandomInt(checkNotZero(kind_all_view.size()) - 1, 0);      //生成类别随机数
//        randomGroup=3; ///---
        ViewInteractionHandler.ViewPosition view = kind_all_view.get(randomGroup);
        //随机点击一个类别的查看全部
        given(aq.stocks_recycler).perform(RecyclerViewActions.actionOnItemAtPosition(view.position, click()));
    }
    private void check_group(String symbol , String group_text , boolean exist){
        given(AQuotes.watchlist_fence).perform(click());
        given(AQuotes.watchlist_spinner).perform(click());
        onData(withRowString(watchlistKey, group_text)).perform(click());
        Log.d(TAG, String.format("check_group: 展开自选列表 '%s'",group_text));
        sleep(1000);
        boolean existRecyclerViewData = isExistRecyclerViewData(aq.stocks_recycler, hasDescendant(withText(symbol))); ////
        existRecyclerViewData = ! exist ^ existRecyclerViewData;
        String log1 = exist ? "is exist" : "not exist";
        String log2 = ! exist ? "is exist" : "not exist";
        if(!existRecyclerViewData){
            fail(String.format("want symbol %s %s in %s ,but it %s .",symbol ,log1 ,group_text ,log2));
        }
    }
    private String getSymbol (){
        int count = getRecyclerViewItemCount(aq.stocks_recycler);       //获得某分类中全部股票个数
        int random = God.getRandomInt(checkNotZero(count) - 1, 0);                      //随机取一个
//        random = 3;///---
        given(aq.stocks_recycler).perform(scrollToRecyclerPosition(random));    //先滑动到view至可见
        RecyclerViewItemElement itemElement = new RecyclerViewItemElement(aq.stocks_recycler).setPosition(random);
        String symbol = getText(AQuotes.symbol_text.setAncestor(itemElement));      //保存股票代码
        Log.i(TAG, String.format("add_us_stock: select symbol '%s'",symbol ));

        given(itemElement).perform(click());
        given(details.add_self_stock).perform(click(pressBack()));

        return symbol;
    }


}
