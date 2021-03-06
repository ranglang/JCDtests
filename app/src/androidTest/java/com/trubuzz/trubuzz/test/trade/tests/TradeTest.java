package com.trubuzz.trubuzz.test.trade.tests;

import android.support.test.rule.ActivityTestRule;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.constant.enumerate.Commissioned;
import com.trubuzz.trubuzz.constant.enumerate.OrderType;
import com.trubuzz.trubuzz.constant.enumerate.Position;
import com.trubuzz.trubuzz.constant.enumerate.StockType;
import com.trubuzz.trubuzz.constant.enumerate.TimeInForce;
import com.trubuzz.trubuzz.feature.custom.parameters.GenreParameter;
import com.trubuzz.trubuzz.shell.Var;
import com.trubuzz.trubuzz.test.BaseTest;
import com.trubuzz.trubuzz.test.Wish;
import com.trubuzz.trubuzz.test.quote.QuoteAction;
import com.trubuzz.trubuzz.test.trade.TradeAction;
import com.trubuzz.trubuzz.test.trade.TradeService;
import com.trubuzz.trubuzz.test.trade.TradeView;
import com.trubuzz.trubuzz.utils.God;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static android.support.test.espresso.intent.Checks.checkNotNull;
import static com.trubuzz.trubuzz.constant.Config.tradePwd;
import static com.trubuzz.trubuzz.constant.enumerate.Commissioned.limit;
import static com.trubuzz.trubuzz.constant.enumerate.Commissioned.market;
import static com.trubuzz.trubuzz.constant.enumerate.OrderType.CASH;
import static com.trubuzz.trubuzz.constant.enumerate.OrderType.SHARES;
import static com.trubuzz.trubuzz.constant.enumerate.Position.BEAR;
import static com.trubuzz.trubuzz.constant.enumerate.Position.BULL;
import static com.trubuzz.trubuzz.constant.enumerate.StockType.HK;
import static com.trubuzz.trubuzz.constant.enumerate.StockType.US;
import static com.trubuzz.trubuzz.constant.enumerate.TimeInForce.GFD;
import static com.trubuzz.trubuzz.constant.enumerate.TimeInForce.IOC;
import static com.trubuzz.trubuzz.test.common.CommonAction.check_toast_msg;

/**
 * Created by king on 17/5/15.
 */
@RunWith(JUnitParamsRunner.class)
public class TradeTest extends BaseTest {
    private final TradeService ta = new TradeAction();
    private final QuoteAction qa = new QuoteAction();
    private final TradeView.Toast vt = new TradeView.Toast();

    @Rule
    public ActivityTestRule<?> matr = new ActivityTestRule(God.getFixedClass(AName.MAIN));


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
    @GenreParameter({
            "AAPL,US ,BULL" ,
            "00939,HK ,BEAR"
    })
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
    @GenreParameter({
            // 美股 限价买入 金额成交 ,当日有效
            "AMZN ,limit , BULL ,2000 ,GFD ,10 , US , CASH",
            // 美股 市价卖出 金额成交 ,IOC
            "BORN ,market ,BEAR ,2000 ,IOC ,null , US , CASH" ,
            // 美股 限价卖出 股数成交 ICO
            "BORN ,market , BEAR ,10 ,IOC ,1.5 , US , SHARES" ,
            // 美股 市价买入 股数成交 当日有效
            "BABA ,market , BULL ,20 ,GFD ,null , US , SHARES"
    })
    public void search_stock_ordering(@Var("symbol") String symbol , @Var("limitOrMarket")Commissioned limitOrMarket , @Var("position")Position position ,
                                      @Var("CASH")String amount , @Var("timeInForce")TimeInForce timeInForce , @Var("price")String price,
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

        ta.type_trade_password(tradePwd);

        ta.confirm_trade_pwd();
        check_toast_msg(vt.order_place_success_toast);

        ta.check_assets_order_list_show(symbol, new Date(), price, shareAmount, position);

    }





    /**
     * 1. 随机下单  --> 行情
     * 2. 搜索指定股票下单(指定的股票具有特殊性或代表性)  --> 搜索
     */
}
