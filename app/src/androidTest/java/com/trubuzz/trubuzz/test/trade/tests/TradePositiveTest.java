package com.trubuzz.trubuzz.test.trade.tests;

import android.support.test.rule.ActivityTestRule;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.constant.Config;
import com.trubuzz.trubuzz.constant.enumerate.Commissioned;
import com.trubuzz.trubuzz.constant.enumerate.OrderType;
import com.trubuzz.trubuzz.constant.enumerate.Position;
import com.trubuzz.trubuzz.constant.enumerate.StockType;
import com.trubuzz.trubuzz.constant.enumerate.TimeInForce;
import com.trubuzz.trubuzz.shell.Var;
import com.trubuzz.trubuzz.test.BaseTest;
import com.trubuzz.trubuzz.test.Wish;
import com.trubuzz.trubuzz.test.quote.QuoteAction;
import com.trubuzz.trubuzz.test.trade.TradeAction;
import com.trubuzz.trubuzz.test.trade.TradeService;
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
import static com.trubuzz.trubuzz.constant.enumerate.Commissioned.market;
import static com.trubuzz.trubuzz.constant.enumerate.OrderType.amount;
import static com.trubuzz.trubuzz.constant.enumerate.OrderType.volume;
import static com.trubuzz.trubuzz.constant.enumerate.Position.BEAR;
import static com.trubuzz.trubuzz.constant.enumerate.Position.BULL;
import static com.trubuzz.trubuzz.constant.enumerate.StockType.HK;
import static com.trubuzz.trubuzz.constant.enumerate.StockType.US;
import static com.trubuzz.trubuzz.constant.enumerate.TimeInForce.GFD;
import static com.trubuzz.trubuzz.constant.enumerate.TimeInForce.IOC;

/**
 * Created by king on 17/5/15.
 */
@RunWith(JUnitParamsRunner.class)
public class TradePositiveTest extends BaseTest {
    private final String TAG = "jcd_" + this.getClass().getSimpleName();
    private final TradeService ta = new TradeAction();
    private final QuoteAction qa = new QuoteAction();

    @Rule
    public ActivityTestRule<?> matr = new ActivityTestRule(God.getFixedClass(AName.MAIN));


    private Object[] verify_order_default_show_data(){
        return new Object[]{
                create_verify_order_default_show_data("AAPL",US ,BULL),
                create_verify_order_default_show_data("00939",HK ,BEAR)
        };
    }
    private Object[] search_stock_ordering_data(){
        return new Object[]{
                // 美股 限价买入 金额成交 ,当日有效
                this.create_search_stock_ordering_data("AMZN" ,limit , BULL ,"2000" ,GFD ,"109" , US ,amount),
                // 美股 市价卖出 金额成交 ,IOC
                this.create_search_stock_ordering_data("BORN" ,market ,BEAR ,"2000" ,IOC ,null , US , amount),
                // 美股 限价卖出 股数成交 ICO
                this.create_search_stock_ordering_data("BORN" ,market , BEAR ,"10" ,IOC ,"1.5" , US ,volume),
                // 美股 市价买入 股数成交 当日有效
                this.create_search_stock_ordering_data("BABA" ,market , BULL ,"20" ,GFD ,null , US , volume),
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
     * @param symbol
     * @param stockType 股票类型
     * @param position 交易方向 [ 买/ 卖]
     */
    @Test
    @Parameters(method = "verify_order_default_show_data")
    public void verify_order_default_show(@Var("symbol") String symbol ,@Var("stockType") StockType stockType ,
                                          @Var("position") Position position){
        ta.into_ordering_page(symbol , position);
        ta.check_order_default_show();
        ta.check_commission_default_show(position, limit, stockType);    // 默认展示限价
        ta.check_time_in_force_default_show(stockType);

        // 切换至市价检查
        ta.select_commission_way(market);
        ta.check_commission_default_show(position, market, stockType);
        ta.check_time_in_force_default_show(stockType);
    }
    private Object[] create_verify_order_default_show_data(String symbol, StockType stockType, Position position) {
        return new Object[]{symbol ,stockType ,position};
    }

    /**
     * 交易美股 ,金额成交
     * @param symbol 股票代码
     * @param limitOrMarket 委托方式
     * @param position 交易方向
     * @param amount 交易金额/股数
     * @param timeInForce 时效指令
     * @param price 委托价格(市价单时可为null)
     * @param stockType 美股 / 港股
     * @param cashOrShares 成交方式
     */
    @Test
    @Parameters(method = "search_stock_ordering_data")
    public void search_stock_ordering(@Var("symbol") String symbol , @Var("limitOrMarket")Commissioned limitOrMarket , @Var("position")Position position ,
                                      @Var("amount")String amount , @Var("timeInForce")TimeInForce timeInForce , @Var("price")String price,
                                      @Var("stockType") StockType stockType , @Var("cashOrShares") OrderType cashOrShares) {
        ta.into_ordering_page(symbol, position);
        ta.check_order_default_show();

        ta.select_commission_way(limitOrMarket);
        ta.check_commission_default_show(position ,limitOrMarket ,stockType );
        ta.check_time_in_force_default_show(stockType);

        if (limitOrMarket == limit) {
            checkNotNull(price);
            ta.type_price(price);
        }

        ta.change_deal_type(cashOrShares);

        ta.type_amount(amount);
        String shareAmount = ta.check_forecast_show(cashOrShares ,position ,limitOrMarket ,amount ,stockType);

        ta.select_time_in_force(timeInForce);

//        ta.click_submit_button();
        ta.click_keyboard_submit();

        ta.type_trade_password(Config.tradePwd);

        ta.confirm_trade_pwd();
        ta.check_trade_succeed();

        ta.check_assets_order_list_show(symbol, new Date(), price, shareAmount, position);

    }
    private Object[] create_search_stock_ordering_data(String symbol , Commissioned limitOrMarket , Position position ,
                                                       String amount , TimeInForce timeInForce , String price , StockType stockType , OrderType orderType) {
        return new Object[]{symbol, limitOrMarket, position, amount, timeInForce, price ,stockType , orderType};
    }










    /**
     * 1. 随机下单  --> 行情
     * 2. 搜索指定股票下单(指定的股票具有特殊性或代表性)  --> 搜索
     */
}
