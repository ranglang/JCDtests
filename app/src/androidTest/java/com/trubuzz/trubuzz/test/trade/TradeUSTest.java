package com.trubuzz.trubuzz.test.trade;

import android.support.test.rule.ActivityTestRule;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.constant.Config;
import com.trubuzz.trubuzz.constant.enumerate.Commissioned;
import com.trubuzz.trubuzz.constant.enumerate.Deal;
import com.trubuzz.trubuzz.constant.enumerate.Position;
import com.trubuzz.trubuzz.constant.enumerate.StockType;
import com.trubuzz.trubuzz.constant.enumerate.TimeInForce;
import com.trubuzz.trubuzz.shell.Var;
import com.trubuzz.trubuzz.test.BaseTest;
import com.trubuzz.trubuzz.test.Wish;
import com.trubuzz.trubuzz.test.quote.QuoteAction;
import com.trubuzz.trubuzz.utils.God;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static android.support.test.espresso.intent.Checks.checkNotNull;
import static com.trubuzz.trubuzz.constant.enumerate.Commissioned.limit;
import static com.trubuzz.trubuzz.constant.enumerate.TimeInForce.GFD;

/**
 * Created by king on 17/5/15.
 */
@RunWith(JUnitParamsRunner.class)
public class TradeUSTest extends BaseTest {
    private final String TAG = "jcd_" + this.getClass().getSimpleName();
    private final TradeServer ta = new TradeAction();
    private final QuoteAction qa = new QuoteAction();

    @Rule
    public ActivityTestRule<?> matr = new ActivityTestRule(God.getFixedClass(AName.MAIN));


    private Object[] us_cash_ordering_data(){
        return new Object[]{
                this.create_us_cash_ordering_data("AMZN" ,limit ,Position.BULL ,"2000" ,GFD ,"109")
        };
    }

    @Before
    public void wishLogin(){
        //使用开户用户登录 ; 进入行情板块
        Wish.wantBrokerLogin();
        qa.into_quote();
    }

    /**
     * 验证下单页面默认展示
     * @param symbol us 和 hk 各一只
     * @param stockType 股票类型
     * @param position 交易方向 [ 买/ 卖]
     */
//    @Test
    @Parameters(method = "")
    public void verify_order_default_show(String symbol ,StockType stockType , Position position){
        ta.into_ordering_page(symbol , position);
        ta.check_order_default_show();
        ta.check_commission_default_show(position, limit, StockType.US, Deal.amount);    // 默认展示限价
        ta.check_time_in_force_default_show(stockType);

        // 切换至市价检查
        ta.select_commission_way(Commissioned.market);
        ta.check_commission_default_show(position, Commissioned.market, StockType.US, Deal.amount);
        ta.check_time_in_force_default_show(stockType);
    }

    /**
     * 交易美股 ,金额成交
     * @param symbol 股票代码
     * @param limitOrMarket 委托方式
     * @param position 交易方向
     * @param amount 交易金额/股数
     * @param timeInForce 时效指令
     * @param price 委托价格(市价单时可为null)
     */
    @Test
    @Parameters(method = "us_cash_ordering_data")
    public void us_cash_ordering(@Var("symbol") String symbol , @Var("limitOrMarket")Commissioned limitOrMarket , @Var("position")Position position ,
                                 @Var("amount")String amount , @Var("timeInForce")TimeInForce timeInForce , @Var("price")String price) {
        ta.into_ordering_page(symbol, position);
        ta.check_order_default_show();

        ta.select_commission_way(limitOrMarket);
        ta.check_commission_default_show(position ,limitOrMarket ,StockType.US ,Deal.amount);
        ta.check_time_in_force_default_show(StockType.US);

        if (limitOrMarket == limit) {
            checkNotNull(price);
            ta.type_price(price);
        }

        ta.change_deal_type(Deal.amount);

        ta.type_amount(amount);
        String shareAmount = ta.check_forecast_show(Deal.amount ,position ,limitOrMarket ,amount ,StockType.US);

        ta.select_time_in_force(timeInForce);

        ta.click_submit_button();

        ta.type_trade_password(Config.tradePwd);

        ta.confirm_trade_pwd();
        ta.check_trade_succeed();

        ta.check_assets_order_list_show(symbol, new Date(), price, shareAmount, position);

    }
    private Object[] create_us_cash_ordering_data(String symbol , Commissioned limitOrMarket , Position position ,
                                                  String amount , TimeInForce timeInForce , String price) {
        return new Object[]{symbol, limitOrMarket, position, amount, timeInForce, price};
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








    /**
     * 1. 随机下单  --> 行情
     * 2. 搜索指定股票下单(指定的股票具有特殊性或代表性)  --> 搜索
     */
}
