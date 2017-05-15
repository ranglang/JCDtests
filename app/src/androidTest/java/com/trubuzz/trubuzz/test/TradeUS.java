package com.trubuzz.trubuzz.test;

import android.support.test.rule.ActivityTestRule;
import android.util.Log;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.elements.AQuotes;
import com.trubuzz.trubuzz.elements.Global;
import com.trubuzz.trubuzz.idlingResource.ViewIdlingResource;
import com.trubuzz.trubuzz.utils.God;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static com.trubuzz.trubuzz.feature.custom.ViewInteractionHandler.getView;
import static com.trubuzz.trubuzz.shell.Park.given;
import static com.trubuzz.trubuzz.utils.DoIt.regIdlingResource;

/**
 * Created by king on 17/5/15.
 */
@RunWith(JUnitParamsRunner.class)
public class TradeUS extends BaseTest{
    private final String TAG = "jcd_" + this.getClass().getSimpleName();
    private final AQuotes aq = new AQuotes();
    private final AQuotes.details ad = new AQuotes.details();

    @Rule
    public ActivityTestRule<?> matr = new ActivityTestRule(God.getFixedClass(AName.MAIN));


    private Object[] fixedUS(){
        return new Object[]{
                new Object[]{"AAPL"},
                new Object[]{"BABA"}
        };
    }

    @Before
    public void wishLogin(){
        //使用开户用户登录 ; 进入行情板块
        Wish.wantBrokerLogin();
        given(Global.quotes_radio).perform(click());
    }

    @Test
    @Parameters(method = "fixedUS")
    public void buyFixedUS(String fixed_symbol){
        given(aq.search_icon).perform(click());
        given(aq.search_input).perform(replaceText(fixed_symbol));
        regIdlingResource(new ViewIdlingResource(getView(aq.stocks_recycler)));
        Log.i(TAG, String.format("add_index_stock: search %s RecyclerView has shown .",
                fixed_symbol));
        given(aq.search_symbol.setText(fixed_symbol)).perform(click()); //选择指定记录进入详情

        given(ad.buy_button).perform(click());  //点击"买"

    }
    /**
     * 1. 随机下单  --> 行情
     * 2. 搜索指定股票下单(指定的股票具有特殊性或代表性)  --> 搜索
     */
}
