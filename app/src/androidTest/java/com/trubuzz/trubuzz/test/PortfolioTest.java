package com.trubuzz.trubuzz.test;

import android.support.test.rule.ActivityTestRule;
import android.util.Log;
import android.view.View;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.elements.AWealth;
import com.trubuzz.trubuzz.test.common.GlobalView;
import com.trubuzz.trubuzz.feature.custom.handlers.ViewInteractionHandler;
import com.trubuzz.trubuzz.shell.Element;
import com.trubuzz.trubuzz.shell.Uncalibrated;
import com.trubuzz.trubuzz.shell.Var;
import com.trubuzz.trubuzz.shell.beautify.RecyclerViewItemElement;
import com.trubuzz.trubuzz.utils.God;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.trubuzz.trubuzz.feature.custom.actions.CustomRecyclerViewActions.scrollToRecyclerPosition;
import static com.trubuzz.trubuzz.feature.custom.handlers.ViewInteractionHandler.getRecyclerViewItem;
import static com.trubuzz.trubuzz.feature.custom.handlers.ViewInteractionHandler.getRecyclerViewItemCount;
import static com.trubuzz.trubuzz.feature.custom.handlers.ViewInteractionHandler.getText;
import static com.trubuzz.trubuzz.shell.Park.given;
import static com.trubuzz.trubuzz.test.Wish.wantBrokerLogin;
import static com.trubuzz.trubuzz.test.Wish.wantNotBrokerLogin;
import static com.trubuzz.trubuzz.utils.DoIt.sleep;

/**
 * Created by king on 16/12/21.
 */
@RunWith(JUnitParamsRunner.class)
public class PortfolioTest extends BaseTest {
    private String TAG = "jcd_"+this.getClass().getSimpleName();
    private AWealth aw = new AWealth();

    @Rule
    public ActivityTestRule<?> matr = new ActivityTestRule(God.getFixedClass(AName.MAIN));

    private Object[] has_broker_data(){
        return new Object[]{
                new Object[]{true},
                new Object[]{false}
        };
    }
    /**
     * 未/已开户用户订阅 / 取消订阅 (外部取消/详情内取消)
     * 详情截图
     */
    @Test
    @Parameters(method = "has_broker_data")
    @Uncalibrated
    public void outside_subscribe_test(@Var("has_broker") boolean has_broker){
        this.into_portfolio(has_broker);     //使用已开户 / 未开户 用户登录

        //在外部订阅
        this.subscribe_and_unsubscribe(true ,true);
        this.back_to_portfolio_list();  //回到列表页

        //内部订阅
        this.subscribe_and_unsubscribe(false ,true);
        this.back_to_portfolio_list();  //回到列表页

        //外部取消订阅
        this.subscribe_and_unsubscribe(true ,false);
        this.back_to_portfolio_list();  //回到列表页

        //内部取消订阅
        this.subscribe_and_unsubscribe(false ,false);
        this.back_to_portfolio_list();  //回到列表页

        //内外部内容检查
        int viewItemCount = getRecyclerViewItemCount(aw.recycler);
        int randomInt = God.getRandomInt(viewItemCount - 1, 0);
        given(aw.recycler).perform(scrollToRecyclerPosition(randomInt));
        RecyclerViewItemElement item = new RecyclerViewItemElement(aw.recycler).setPosition(randomInt);
        this.inside_and_outside_check(item);
    }


/*********************** 辅助方法 *********************/

    /**
     * @param isOutside 是否外部订阅/取消订阅
     * @param wantSubscribe 希望订阅( 按钮当为"订阅" )/取消订阅( 按钮当为"已订阅" )
     */
    private void subscribe_and_unsubscribe(boolean isOutside ,boolean wantSubscribe){
        List<ViewInteractionHandler.ViewPosition> subscribe;
        if (wantSubscribe) {
            subscribe = getRecyclerViewItem(aw.recycler, hasDescendant(aw.subscribe_button.way())); //"订阅"按钮个数
            if (subscribe.size() > 0) {
                //订阅
                if (isOutside) this.outside_subscribe(subscribe);
                else this.details_subscribe(subscribe);
            } else {
                //取消订阅
                subscribe = getRecyclerViewItem(aw.recycler, hasDescendant(aw.subscribed_button.way()));    //更换成"已订阅"
                if (isOutside) this.outside_unsubscribe(subscribe);
                else this.details_unsubscribe(subscribe);
            }
        } else {
            subscribe = getRecyclerViewItem(aw.recycler, hasDescendant(aw.subscribed_button.way())); //"已订阅"按钮个数
            if (subscribe.size() > 0) {
                //取消订阅
                if (isOutside) this.outside_unsubscribe(subscribe);
                else this.details_unsubscribe(subscribe);
            } else {
                //订阅
                subscribe = getRecyclerViewItem(aw.recycler, hasDescendant(aw.subscribe_button.way()));    //更换成"订阅"
                if (isOutside) this.outside_subscribe(subscribe);
                else this.details_subscribe(subscribe);
            }
        }
    }

    /**
     * 初始化( 进入投资组合列表 )
     * @param wantBroker
     */
    private void into_portfolio(boolean wantBroker){
        if(wantBroker) wantBrokerLogin();
        else wantNotBrokerLogin();

        given(GlobalView.wealth_radio).perform(click());
        given(aw.wealth).perform(click());
        given(aw.recycler).check(matches(isDisplayed()));
    }

    /**
     * 订阅
     */
    private void outside_subscribe(List<ViewInteractionHandler.ViewPosition> items){
        int randomInt = God.getRandomInt(items.size() - 1, 0);
        ViewInteractionHandler.ViewPosition viewPosition = items.get(randomInt);

        given(aw.recycler).perform(scrollToRecyclerPosition(viewPosition.position));
        RecyclerViewItemElement item = new RecyclerViewItemElement(aw.recycler).setPosition(viewPosition.position);

        given(item).check(matches(hasDescendant(aw.subscribe_button.way())));
        given(aw.subscribe_button.setAncestor(item)).perform(click());      //点击指定item的订阅按钮
        given(item).check(matches(hasDescendant(aw.subscribed_button.way())));      //检查 订阅按钮变化为已订阅

        given(item).perform(click());   //进入详情
        given(aw.subscribed_button).check(matches(isDisplayed()));
    }

    private void details_subscribe(List<ViewInteractionHandler.ViewPosition> items) {
        int randomInt = God.getRandomInt(items.size() - 1, 0);
        ViewInteractionHandler.ViewPosition viewPosition = items.get(randomInt);

        given(aw.recycler).perform(scrollToRecyclerPosition(viewPosition.position));
        RecyclerViewItemElement item = new RecyclerViewItemElement(aw.recycler).setPosition(viewPosition.position);

        given(item).perform(click());   //进入详情
        given(aw.subscribe_button).perform(click());
        given(aw.subscribed_button).check(matches(isDisplayed()));

        given(aw.back_up).perform(click());
        given(item).check(matches(hasDescendant(aw.subscribed_button.way())));
    }

    /**
     * 取消订阅
     */
    private void outside_unsubscribe(List<ViewInteractionHandler.ViewPosition> items) {
        int randomInt = God.getRandomInt(items.size() - 1, 0);
        ViewInteractionHandler.ViewPosition viewPosition = items.get(randomInt);

        given(aw.recycler).perform(scrollToRecyclerPosition(viewPosition.position));
        RecyclerViewItemElement item = new RecyclerViewItemElement(aw.recycler).setPosition(viewPosition.position);

        given(item).check(matches(hasDescendant(aw.subscribed_button.way())));
        given(aw.subscribed_button.setAncestor(item)).perform(click());      //点击指定item的已订阅按钮
        given(item).check(matches(hasDescendant(aw.subscribe_button.way())));      //检查 已订阅按钮变化为订阅

        given(item).perform(click());   //进入详情
        given(aw.subscribe_button).check(matches(isDisplayed()));
    }
    private void details_unsubscribe(List<ViewInteractionHandler.ViewPosition> items) {
        int randomInt = God.getRandomInt(items.size() - 1, 0);
        ViewInteractionHandler.ViewPosition viewPosition = items.get(randomInt);

        given(aw.recycler).perform(scrollToRecyclerPosition(viewPosition.position));
        RecyclerViewItemElement item = new RecyclerViewItemElement(aw.recycler).setPosition(viewPosition.position);

        given(item).perform(click());   //进入详情
        given(aw.subscribed_button).perform(click());
        given(aw.subscribe_button).check(matches(isDisplayed()));

        given(aw.back_up).perform(click());
        given(item).check(matches(hasDescendant(aw.subscribe_button.way())));
    }

    /**
     * 详情内外展示
     */
    private void inside_and_outside_check(Element<Matcher<View>> item) {
        String subscribe = getText(aw.subscribe_status.setAncestor(item));
        String title = getText(aw.title_text.setAncestor(item));
//        String total_assets = getText(aw.total_assets_value.setAncestor(item));     //目前详情中没有该项的展示
        String total_gains = getText(aw.total_gains_value.setAncestor(item));

        given(item).perform(click());
        given(aw.title_text).check(matches(withText(title)));
        given(aw.subscribe_status).check(matches(withText(subscribe)));
        given(aw.total_gains_value).check(matches(withText(total_gains)));

        this.compareTakeScreenshot("portfolio details routine checks");     //截图详情
    }

    private void back_to_portfolio_list(){
        sleep(1000);
        String topActivityName = God.getTopActivityName(this.matr.getActivity());
        Log.i(TAG, String.format("back_to_portfolio_list: top activity name =  %s", topActivityName));
        if (AName.PORTFOLIO_LIST.equals(topActivityName))   return;
        if (AName.PORTFOLIO_DETAIL.equals(topActivityName)) {
            given(aw.back_up).perform(click());
        }
    }
}
