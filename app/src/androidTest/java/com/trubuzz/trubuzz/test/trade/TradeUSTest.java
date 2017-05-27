package com.trubuzz.trubuzz.test.trade;

import android.support.test.rule.ActivityTestRule;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.constant.Config;
import com.trubuzz.trubuzz.constant.enumerate.Deal;
import com.trubuzz.trubuzz.constant.enumerate.Position;
import com.trubuzz.trubuzz.constant.enumerate.StockType;
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
    private final TradeServer ta = new TradeAction();
    private final QuoteAction qa = new QuoteAction();
    private final String limit = "limit";
    private final String market = "market";
    private final String GFD = "GFD";
    private final String IOC = "IOC";

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

    /**
     * 验证买单页面默认展示
     * @param symbol us 和 hk 各一只
     */
    @Test
    @Parameters(method = "")
    public void verify_buy_order_default_show(String symbol){
        ta.into_ordering_page(symbol , Position.BULL);
        ta.check_buy_order_default_show();
        ta.check_limit_buy_default_show(StockType.US);  // 默认展示限价
    }

    /**
     * 验证买单市价默认展示
     * @param symbol
     */
    public void verify_buy_order_market_default_show(String symbol) {
        ta.into_ordering_page(symbol ,Position.BULL);
        ta.click_market_buy_tab();
        ta.check_market_buy_default_show(StockType.US);
    }

    /**
     * 美股买入 ,金额成交
     * @param symbol
     */
    public void us_cash_buy_ordering(String symbol ,String limitOrMarket ,String timeForce) {

    }

    /**
     * 美股市价买入 ,金额成交 ,当日有效
     * @param symbol
     */
    public void us_market_cash_buy_ordering_GFD(String symbol) {

    }

    /**
     * 美股限价买入 ,金额成交 ,IOC
     * @param symbol
     */
    public void us_limit_cash_buy_ordering_IOC(String symbol) {

    }

    /**
     * 美股市价买入 ,金额成交 ,IOC
     * @param symbol
     */
    public void us_market_cash_buy_ordering_IOC(String symbol) {

    }









    @Test
    @Parameters(method = "fixedUS")
    public void buyFixedUS(String fixed_symbol){


        ta.check_limit_buy_default_show(StockType.US);
        ta.decrease_price();
        ta.change_deal_type(Deal.amount);
        ta.type_cash_amount("3000");
        ta.click_submit_button();
        ta.type_trade_password(Config.tradePwd);
        ta.confirm_trade_pwd();
        ta.check_trade_succeed();
    }
    /**
     * 1. 随机下单  --> 行情
     * 2. 搜索指定股票下单(指定的股票具有特殊性或代表性)  --> 搜索
     */
}
