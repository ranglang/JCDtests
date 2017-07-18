package com.trubuzz.trubuzz.test;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.PerformException;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;
import android.view.View;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.test.quote.QuoteView;
import com.trubuzz.trubuzz.elements.AWatchlist;
import com.trubuzz.trubuzz.test.common.GlobalView;
import com.trubuzz.trubuzz.feature.custom.handlers.ViewsFinder;
import com.trubuzz.trubuzz.idlingResource.HasViewIdlingResource;
import com.trubuzz.trubuzz.idlingResource.ViewMatcherIdlingResource;
import com.trubuzz.trubuzz.shell.Var;
import com.trubuzz.trubuzz.shell.beautify.ActivityElement;
import com.trubuzz.trubuzz.shell.beautify.RecyclerViewItemElement;
import com.trubuzz.trubuzz.utils.DoIt;
import com.trubuzz.trubuzz.utils.God;
import com.trubuzz.trubuzz.utils.Judge;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.Date;
import java.util.List;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.CursorMatchers.withRowString;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.trubuzz.trubuzz.constant.Env.watchlistKey;
import static com.trubuzz.trubuzz.constant.ToastInfo.watchlist_existing_toast;
import static com.trubuzz.trubuzz.feature.custom.matchers.CustomMatcher.hasChildrenCount;
import static com.trubuzz.trubuzz.feature.custom.matchers.CustomMatcher.hasMoreChildren;
import static com.trubuzz.trubuzz.feature.custom.matchers.CustomMatcher.withView;
import static com.trubuzz.trubuzz.feature.custom.actions.CustomRecyclerViewActions.scrollToRecyclerPosition;
import static com.trubuzz.trubuzz.feature.custom.actions.CustomViewAction.nothing;
import static com.trubuzz.trubuzz.feature.custom.handlers.ViewInteractionHandler.getRecyclerViewItemCount;
import static com.trubuzz.trubuzz.feature.custom.handlers.ViewInteractionHandler.getText;
import static com.trubuzz.trubuzz.feature.custom.handlers.ViewInteractionHandler.getView;
import static com.trubuzz.trubuzz.shell.Park.given;
import static com.trubuzz.trubuzz.utils.DoIt.sleep;
import static com.trubuzz.trubuzz.utils.DoIt.toHexUpString;
import static junit.framework.TestCase.fail;

/**
 * Created by king on 16/12/6.
 */
@RunWith(JUnitParamsRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WatchlistTest extends BaseTest{
    private final String TAG = "jcd_" + this.getClass().getSimpleName();
    private int itemCount;
    private AWatchlist aw = new AWatchlist();
    @Rule
    public ActivityTestRule<?> matr = new ActivityTestRule(God.getFixedClass(AName.MAIN));

    private Object[] add_watchlist_item_data(){
        return new Object[]{
//                new Object[]{"t001", true},
                new Object[]{"t001222", false}
        };
    }
    private Object[] alter_watchlist_item_data(){
        return new Object[]{
                //最小值用 0 ; 最大值用 -1 ;随机值可用任意数字 ,这里用9 .
//                new Object[]{0 ,"t003", true},
//                new Object[]{-1 ,"t002", false},
                new Object[]{9 ,"t0023", true}
        };
    }
    private Object[] delete_watchlist_item_data(){
        return new Object[]{
                new Object[]{true},
                new Object[]{false}
        };
    }

    @Before
    public void b_into_watchlist(){
        Wish.wantLogin();
        given(GlobalView.quotes_radio).perform(click());
        given(QuoteView.edit_watchlist).perform(click());
        DoIt.regIdlingResource(new ViewMatcherIdlingResource(
                getView(aw.watchlist_recycler), hasMoreChildren(2)));

        itemCount = getRecyclerViewItemCount(aw.watchlist_recycler);
        Log.i(TAG, String.format("into_quotes: RecyclerView children count = %s" , itemCount));

        DoIt.unAllRegIdlingResource();


    }
    @Before
    public void a_check_item_count(){
        // 如果小于5项则添加9个item
        if(itemCount < 5){
            for(int i=0;i<=8;i++) {
                c_add_watchlist_item(toHexUpString(new Date().getTime()),true);
            }
            itemCount = getRecyclerViewItemCount(aw.watchlist_recycler);    //重新计算
        }
    }

    @Test
    public void a_default_show_test(){
        Wish.allDisplayed(
                GlobalView.back_up,
                aw.add_icon,
                aw.del ,
                aw.alter
        );

        String watchlist = getText(aw.default_item_text);
        given(GlobalView.back_up).perform(click());

        given(QuoteView.watchlist_spinner).check(matches(isDisplayed()))
                .check(matches(withText(watchlist)));
    }

    /**
     * 删除列表 , 1.随机删除 , 2.全部删除 3.没有可删除的项则调用添加列表的方法 .4.默认列表不可删除
     */
    @Test
    @Parameters(method = "delete_watchlist_item_data")
    public void e_delete_watchlist_item(@Var("confirm") boolean confirm){
        given(aw.del).perform(click());

        int randomItem = God.getRandomInt(this.itemCount - 1, 1);       //生成从 1~ 最大个数的随机数
        RecyclerViewItemElement recyclerViewItem = new RecyclerViewItemElement(aw.watchlist_recycler).setPosition(randomItem);
        given(aw.watchlist_recycler).perform(scrollToRecyclerPosition(randomItem));
        String name = getText(aw.watchlist_name.setParent(recyclerViewItem));

        given(aw.del_icon.setParent(recyclerViewItem)) .perform(click());
        given(aw.del_title).check(matches(isDisplayed()));
        if (confirm) {
            given(aw.edit_ok).perform(click());

            Espresso.pressBack();
            given(QuoteView.watchlist_spinner).perform(click());
            try {
                onData(withRowString(watchlistKey, name)).check(matches(isDisplayed()));
                fail(String.format("the item \"%s\" has been deleted .", name));
            } catch (Exception e) {}
        }else{
            given(aw.edit_cancel).perform(click());

            Espresso.pressBack();
            given(QuoteView.watchlist_spinner).perform(click());
            onData(withRowString(watchlistKey, name)).check(matches(isDisplayed()));
        }
    }
    /**
     * 删除列表后可以创建一个同删除的名称相同的项目
     */
    @Test
    public void f_alter_after_delete(){
        given(aw.del).perform(click());

        String name = randomDelOne();

        DoIt.regIdlingResource(new HasViewIdlingResource(false ,withText(name)));
        Log.i(TAG, String.format("f_alter_after_delete: item '%s' been deleted .",name ));
        DoIt.unRegIdlingResource();

        d_alter_watchlist_item(9, name, true);

    }

    /**
     * 删除后可以添加一个同名的项目
     */
    @Test
    public void g_add_after_delete(){
        given(aw.del).perform(click());

        String name = randomDelOne();

        DoIt.regIdlingResource(new HasViewIdlingResource(false ,withText(name)));
        Log.i(TAG, String.format("f_alter_after_delete: item '%s' been deleted .",name ));
        DoIt.unRegIdlingResource();

        c_add_watchlist_item(name, true);
    }

    private String randomDelOne(){
        int randomItem = God.getRandomInt(this.itemCount - 1, 1);       //生成从 1~ 最大个数的随机数
        RecyclerViewItemElement recyclerViewItem = new RecyclerViewItemElement(aw.watchlist_recycler).setPosition(randomItem);
        given(aw.watchlist_recycler).perform(scrollToRecyclerPosition(randomItem));
        String name = getText(aw.watchlist_name.setParent(recyclerViewItem));

        given(aw.del_icon.setParent(recyclerViewItem)) .perform(click());
        given(aw.del_title).check(matches(isDisplayed()));

        given(aw.edit_ok).perform(click());

        return name;
    }
    /**
     * 修改, 1.正常修改 2.重名修改 3.大小写修改 4.默认列表不可修改
     */
//    @Test
    @Parameters(method = "alter_watchlist_item_data")
    public void d_alter_watchlist_item(@Var("position") int position ,
                                     @Var("alterString") String alterString ,
                                     @Var("confirm") boolean confirm){


        given(aw.alter).perform(click());

        List<View> views = new ViewsFinder().getViews(aw.alter_icon.way());
        Log.i(TAG, String.format("alter_watchlist_item: has %s views", views.size()));
        for (View view : views) {
            given(withView(view)).check(matches(isDisplayed()));
        }
        //检查是否存在相同的名称
        boolean exist = Judge.isExistData(aw.watchlist_recycler, hasDescendant(withText(alterString)));
        Log.i(TAG, String.format("alter_watchlist_item:  %s exist = %s",alterString ,exist ));

        int randomItem = God.getRandomInt(this.itemCount - 1, 1);       //生成从 1~ 最大个数的随机数

        randomItem = position == 0 ? position : randomItem;
        randomItem = position == -1 ? itemCount-1 : randomItem;
        Log.i(TAG, String.format("alter_watchlist_item: perform on position: %s", randomItem));

        RecyclerViewItemElement recyclerViewItem = new RecyclerViewItemElement(aw.watchlist_recycler).setPosition(randomItem);
        given(aw.watchlist_recycler).perform(scrollToRecyclerPosition(randomItem));
        String name = getText(aw.watchlist_name.setParent(recyclerViewItem));
        if (position == 0) {
            try{
                given(aw.alter_icon.setParent(recyclerViewItem)).perform(click());
                fail(String.format("%s item cannot do alter" ,name));
            } catch (NoMatchingViewException e){
                return;
            }
        }else {
            given(aw.alter_icon.setParent(recyclerViewItem)) .perform(click());
        }

        given(aw.alter_frame).check(matches(isDisplayed()));
        given(aw.edit_input).check(matches(withText(name)))
            .perform(replaceText(alterString));
        if (confirm) {
            given(aw.edit_ok).perform(click());
            if (exist) {
                given(watchlist_existing_toast).check(matches(isDisplayed()));
                given(aw.edit_cancel).perform(click());
                return;
            }
            sleep(2000);
            given(recyclerViewItem).check(matches(hasDescendant(withText(alterString))));   //这里比较容易失败, 原因:网络延迟严重
        }else{
            given(aw.edit_cancel).perform(click());
            if (exist)  return;
            given(new ActivityElement().setText(alterString)).check(doesNotExist());
        }
    }


    /**
     * 添加 ,1.正常添加 2.重名添加 3.各种字符
     */
//    @Test
    @Parameters(method = "add_watchlist_item_data")
    public void c_add_watchlist_item(@Var("listName") String listName , @Var("confirm") boolean confirm){
        ActivityElement tmpEle = new ActivityElement().setText(listName);
        boolean exist = Judge.isExistData(aw.watchlist_recycler, hasDescendant(withText(listName)));

        Log.i(TAG, String.format("add_watchlist_item: %s exist = %s",listName ,exist));
        given(aw.add_icon).perform(click());
//        DoIt.unAllRegIdlingResource();

        given(aw.add_frame).check(matches(isDisplayed()));
        given(aw.edit_input).perform(replaceText(listName));
        if(confirm){
            given(aw.edit_ok).perform(click());
            sleep(2000);
            if(exist){
                given(watchlist_existing_toast).check(matches(isDisplayed()));
                given(aw.edit_cancel).perform(click());
            }

            given(tmpEle).check(matches(isDisplayed()));
            given(GlobalView.back_up).perform(click());
            given(QuoteView.watchlist_spinner).perform(click());
            onData(withRowString(watchlistKey, listName)).check(matches(isDisplayed()));
        }else{
            given(aw.edit_cancel).perform(click());
            sleep(2000);
            //如果本来就存在  , 存在则return
            if (exist) {
                return;
            }
            given(new ActivityElement().setText(listName)).check(doesNotExist());
            given(GlobalView.back_up).perform(click());
            given(QuoteView.watchlist_spinner).perform(click());

            //如本来不存在则 try perform一下, 若不抛异常则证明在列表存在了 , 这时当fail
            try {
                onData(withRowString(watchlistKey, listName)).perform(nothing());
                fail(String.format("Hope not found %s ,but found it ." +
                        "Should have thrown PerformException",listName));
            } catch (PerformException ignored) { }

        }

    }

    /**
     * 思考, 已添加价格预警的自选  , 删除列表
     */
    public void think() {

    }

    /**
     * 删除所有项目, 只留默认列表
     */
    @Test
    public void b_delete_remain_one(){
        given(aw.del).perform(click());
        List<View> views = new ViewsFinder().getViews(aw.del_icon.way());
        Log.i(TAG, String.format("alter_watchlist_item: has %s views", views.size()));
        for (View view : views) {
            given(withView(view)).check(matches(isDisplayed()));
        }

        int childCount = itemCount;
        for (int item = 1; item < childCount; item++) {
            Log.i(TAG, String.format("delete_remain_one: start delete item %s view", item));
            RecyclerViewItemElement recyclerViewItem = new RecyclerViewItemElement(aw.watchlist_recycler).setPosition(item);
            given(aw.watchlist_recycler).perform(scrollToRecyclerPosition(item));

            given(aw.del_icon.setParent(recyclerViewItem)).perform(click());
            given(aw.del_title).check(matches(isDisplayed()));
            given(aw.del_content).check(matches(hasDescendant(aw.del_reminder.way())));

            given(aw.edit_ok).perform(click());

            /* 避免网络延迟 , 若对话框还在 , 则在点击一次 , 正常测试时当注释掉 */
            sleep(500);
            if(Wish.isVisible(aw.del_title)){
                given(aw.edit_ok).perform(click());
            }
            //重新计算总数
            childCount = getRecyclerViewItemCount(aw.watchlist_recycler);
            if(childCount != 1 && item >= childCount){
                item = 1;
            }
        }

        Espresso.pressBack();
        given(QuoteView.watchlist_spinner).perform(click());
        given(QuoteView.watchlist_ListView).check(matches(hasChildrenCount(1)));

    }
}
