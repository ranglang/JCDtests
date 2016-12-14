package com.trubuzz.trubuzz.test;

import android.support.test.espresso.PerformException;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;
import android.view.View;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.elements.AQuotes;
import com.trubuzz.trubuzz.elements.AWatchlist;
import com.trubuzz.trubuzz.elements.Global;
import com.trubuzz.trubuzz.feature.custom.ViewsFinder;
import com.trubuzz.trubuzz.idlingResource.ViewMatcherIdlingResource;
import com.trubuzz.trubuzz.shell.Var;
import com.trubuzz.trubuzz.shell.beautify.ActivityElement;
import com.trubuzz.trubuzz.shell.beautify.RecyclerViewItemElement;
import com.trubuzz.trubuzz.utils.DoIt;
import com.trubuzz.trubuzz.utils.God;
import com.trubuzz.trubuzz.utils.Judge;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

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
import static com.trubuzz.trubuzz.constant.ToastInfo.watchlist_existing_toast;
import static com.trubuzz.trubuzz.feature.custom.CustomMatcher.hasMoreChildren;
import static com.trubuzz.trubuzz.feature.custom.CustomMatcher.withView;
import static com.trubuzz.trubuzz.feature.custom.CustomViewAction.nothing;
import static com.trubuzz.trubuzz.feature.custom.ViewInteractionHandler.getRecyclerViewChildrenCount;
import static com.trubuzz.trubuzz.feature.custom.ViewInteractionHandler.getText;
import static com.trubuzz.trubuzz.feature.custom.ViewInteractionHandler.getView;
import static com.trubuzz.trubuzz.shell.Park.given;
import static com.trubuzz.trubuzz.utils.DoIt.sleep;
import static junit.framework.TestCase.fail;

/**
 * Created by king on 16/12/6.
 */
@RunWith(JUnitParamsRunner.class)
public class WatchlistTest extends BaseTest{
    private final String TAG = "jcd_" + this.getClass().getSimpleName();
    private int itemCount;
    private AWatchlist aw = new AWatchlist();
    @Rule
    public ActivityTestRule<?> matr = new ActivityTestRule(God.getFixedClass(AName.MAIN));

    private Object[] add_watchlist_item_data(){
        return new Object[]{
//                new Object[]{"t001", true},
                new Object[]{"t001", false}
        };
    }

    @Before
    public void into_quotes(){
        Wish.wantLogin();
        given(Global.quotes_radio).perform(click());
        given(AQuotes.edit_watchlist).perform(click());
        DoIt.regIdlingResource(new ViewMatcherIdlingResource(
                getView(aw.watchlist_recycler), hasMoreChildren(2)));

        itemCount = getRecyclerViewChildrenCount(aw.watchlist_recycler);
        Log.i(TAG, String.format("into_quotes: RecyclerView children count = %s" , itemCount));

        DoIt.unAllRegIdlingResource();
    }

//    @Test
    public void default_show_test(){
        Wish.allDisplayed(
                Global.back_up,
                aw.add_icon,
                aw.del ,
                aw.alter
        );

        String watchlist = getText(aw.default_item_text);
        given(Global.back_up).perform(click());

        given(AQuotes.watchlist_default_item).check(matches(isDisplayed()))
                .check(matches(withText(watchlist)));
    }

    //@Test
    /**
     * 删除列表 , 1.随机删除 , 2.全部删除 3.没有可删除的项则调用添加列表的方法 .4.默认列表不可删除
     */
    public void delete_watchlist_item(){

    }

    /**
     * 修改, 1.正常修改 2.重名修改 3.大小写修改 4.默认列表不可修改
     */
    @Test
    public void alter_watchlist_item(){
        given(aw.alter).perform(click());

        List<View> views = new ViewsFinder().getViews(aw.alter_icon.interactionWay());
        Log.i(TAG, String.format("alter_watchlist_item: has %s views", views.size()));
        for (View view : views) {
            given(withView(view)).check(matches(isDisplayed()));
        }
        int randomItem = God.getRandomInt(this.itemCount - 1, 0);
        Log.i(TAG, String.format("alter_watchlist_item: random perform on position: %s", randomItem));
        given(aw.alter_icon.setParent(
                new ActivityElement().setMatchers(new RecyclerViewItemElement(aw.watchlist_recycler).setPosition(randomItem).interactionWay()
                ))
        ).perform(click());
//        given(aw.watchlist_recycler).perform(RecyclerViewActions.actionOnItem(allOf(aw.alter_icon.interactionWay(), withIndex(randomItem)), click()));
        sleep(2000);
    }

    /**
     * 添加 ,1.正常添加 2.重名添加 3.各种字符
     */
//    @Test
    @Parameters(method = "add_watchlist_item_data")
    public void add_watchlist_item(@Var("listName") String listName , @Var("confirm") boolean confirm){
        ActivityElement tmpEle = new ActivityElement().setText(listName);
        boolean exist = Judge.isExistData(aw.watchlist_recycler, hasDescendant(withText(listName)));

        Log.i(TAG, String.format("add_watchlist_item: %s exist = %s",listName ,exist));
        given(aw.add_icon).perform(click());
//        DoIt.unAllRegIdlingResource();

        given(aw.add_frame).check(matches(isDisplayed()));
        given(aw.add_input).perform(replaceText(listName));
        if(confirm){
            given(aw.add_ok).perform(click());
            sleep(2000);
            if(exist){
                given(watchlist_existing_toast).check(matches(isDisplayed()));
                given(aw.add_cancel).perform(click());
            }

            given(tmpEle).check(matches(isDisplayed()));
            given(Global.back_up).perform(click());
            given(AQuotes.watchlist_default_item).perform(click());
            onData(withRowString("name", listName)).check(matches(isDisplayed()));
        }else{
            given(aw.add_cancel).perform(click());
            sleep(2000);
            //如果本来就存在  , 存在则return
            if (exist) {
                return;
            }
            given(new ActivityElement().setText(listName)).check(doesNotExist());
            given(Global.back_up).perform(click());
            given(AQuotes.watchlist_default_item).perform(click());

            //如本来不存在则 try perform一下, 若不抛异常则证明在列表存在了 , 这时当fail
            try {
                onData(withRowString("name", listName)).perform(nothing());
                fail(String.format("Hope not found %s ,but found it ." +
                        "Should have thrown PerformException",listName));
            } catch (PerformException ignored) { }

        }

    }

    /**
     * 思考, 已添加价格预警的自选  , 删除列表
     */
}
