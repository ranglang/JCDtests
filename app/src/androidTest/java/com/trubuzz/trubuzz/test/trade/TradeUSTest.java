package com.trubuzz.trubuzz.test.trade;

import android.support.test.rule.ActivityTestRule;
import android.util.Log;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.test.BaseTest;
import com.trubuzz.trubuzz.test.Wish;
import com.trubuzz.trubuzz.test.quote.QuoteAction;
import com.trubuzz.trubuzz.utils.God;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

/**
 * Created by king on 17/5/15.
 */
@RunWith(JUnitParamsRunner.class)
public class TradeUSTest extends BaseTest {
    private final String TAG = "jcd_" + this.getClass().getSimpleName();
    private final TradeAction ta = new TradeAction();
    private final QuoteAction qa = new QuoteAction();

    @Rule
    public ActivityTestRule<?> matr = new ActivityTestRule(God.getFixedClass(AName.MAIN));


    private Object[] fixedUS(){
        return new Object[]{
//                new Object[]{"AAPL"},
                new Object[]{"BABA"}
        };
    }

    @Before
    public void wishLogin(){
        //使用开户用户登录 ; 进入行情板块
        Wish.wantBrokerLogin();
        qa.into_quote();
    }

    @Test
    @Parameters(method = "fixedUS")
    public void buyFixedUS(String fixed_symbol){
        qa.search_stock(fixed_symbol);
        qa.waiting_search_result();
        Log.i(TAG, String.format("buyFixedUS: search %s RecyclerView has shown .",
                fixed_symbol));
        qa.into_search_stock_quote(fixed_symbol);

        ta.click_buy_button();
        ta.limit_buy_default_show();
        ta.decrease_price();
        ta.change_deal_type(TradeAction.Deal.amount);
        ta.input_amount("3000");
        ta.click_submit_button();

    }
    /**
     * 1. 随机下单  --> 行情
     * 2. 搜索指定股票下单(指定的股票具有特殊性或代表性)  --> 搜索
     */
}
