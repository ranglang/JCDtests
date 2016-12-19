package com.trubuzz.trubuzz.test;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.elements.AQuotes;
import com.trubuzz.trubuzz.elements.Global;
import com.trubuzz.trubuzz.feature.custom.ViewInteractionHandler;
import com.trubuzz.trubuzz.shell.beautify.RecyclerViewItemElement;
import com.trubuzz.trubuzz.utils.God;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.CursorMatchers.withRowString;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.trubuzz.trubuzz.constant.Config.watchlistKey;
import static com.trubuzz.trubuzz.feature.custom.CustomRecyclerViewActions.scrollToRecyclerPosition;
import static com.trubuzz.trubuzz.feature.custom.ViewInteractionHandler.getRecyclerViewItem;
import static com.trubuzz.trubuzz.feature.custom.ViewInteractionHandler.getRecyclerViewItemCount;
import static com.trubuzz.trubuzz.feature.custom.ViewInteractionHandler.getText;
import static com.trubuzz.trubuzz.shell.Park.given;
import static com.trubuzz.trubuzz.utils.DoIt.sleep;
import static com.trubuzz.trubuzz.utils.Judge.checkNotZero;
import static com.trubuzz.trubuzz.utils.Judge.isExistRecyclerViewData;
import static junit.framework.Assert.fail;
import static org.hamcrest.Matchers.not;

/**
 * Created by king on 16/12/15.
 */

public class SelfStockTest extends BaseTest{
    private final String TAG = "jcd_" + this.getClass().getSimpleName();
    private final AQuotes aq = new AQuotes();
    private final AQuotes.details details = new AQuotes.details();
    @Rule
    public ActivityTestRule<?> matr = new ActivityTestRule(God.getFixedClass(AName.MAIN));


    @Before
    public void into_quotes() {
        Wish.wantLogin();
        given(Global.quotes_radio).perform(click());
    }
    /**
     * 添加 / 删除 美股
     */
    @Test
    public void add_us_stock(){
        given(AQuotes.us_fence).perform(click());

        List<ViewInteractionHandler.ViewPosition> kind_all_view = getRecyclerViewItem(aq.stocks_recycler, aq.kind_all.interactionWay());
        Log.i(TAG, String.format("add_us_stock: kind_all_view size: %s",kind_all_view.size() ));
        int randomGroup = God.getRandomInt(checkNotZero(kind_all_view.size()) - 1, 0);      //生成类别随机数
//        randomGroup=3; ///---
        ViewInteractionHandler.ViewPosition view = kind_all_view.get(randomGroup);
        //随机点击一个类别的查看全部
        given(aq.stocks_recycler).perform(RecyclerViewActions.actionOnItemAtPosition(view.position, click()));
        String symbol ="";

        List<ViewInteractionHandler.ViewPosition> not_in_group_views = null;
        for(int i=1;i<=3;i++) {
            symbol = getSymbol ();   //选择股票并返回 Symbol
            sleep(2000);
            not_in_group_views = getRecyclerViewItem(aq.stocks_recycler, not(hasDescendant(details.in_group_yet.interactionWay())));
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
        given(self_group).check(matches(hasDescendant(details.in_group_yet.interactionWay())));

        this.back_to_main();
        given(AQuotes.watchlist_fence).perform(click());
        given(AQuotes.watchlist_default_item).perform(click());
        onData(withRowString(watchlistKey, group_text)).perform(click());
        Log.d(TAG, String.format("add_us_stock: 展开自选列表 '%s'",group_text));
        sleep(1000);
        boolean existRecyclerViewData = isExistRecyclerViewData(aq.stocks_recycler, hasDescendant(withText(symbol))); ////
        if(!existRecyclerViewData){
            fail(String.format("symbol '%s' not in '%s' watchlist .",symbol ,group_text));
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
        given(details.add_self_stock).perform(click());

        return symbol;
    }

    /**
     * 添加 / 删除 港股
     */


    /**
     * 添加 / 删除 沪深
     */

    /**
     * 添加 / 删除 指数
     */

    /**
     * 同一只股票 / 指数可添加至多个Watchlist
     */
}
