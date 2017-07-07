package com.trubuzz.trubuzz.test.trade.tests;

import android.support.test.rule.ActivityTestRule;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.constant.enumerate.Commissioned;
import com.trubuzz.trubuzz.constant.enumerate.OrderType;
import com.trubuzz.trubuzz.constant.enumerate.Position;
import com.trubuzz.trubuzz.constant.enumerate.StockType;
import com.trubuzz.trubuzz.shell.Var;
import com.trubuzz.trubuzz.test.BaseTest;
import com.trubuzz.trubuzz.test.Wish;
import com.trubuzz.trubuzz.test.common.CommonAction;
import com.trubuzz.trubuzz.test.quote.QuoteAction;
import com.trubuzz.trubuzz.test.trade.TradeAction;
import com.trubuzz.trubuzz.test.trade.TradeService;
import com.trubuzz.trubuzz.test.trade.TradeView;
import com.trubuzz.trubuzz.utils.God;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static android.support.test.espresso.intent.Checks.checkNotNull;
import static com.trubuzz.trubuzz.constant.enumerate.Commissioned.limit;
import static com.trubuzz.trubuzz.constant.enumerate.Commissioned.market;
import static com.trubuzz.trubuzz.constant.enumerate.OrderType.CASH;
import static com.trubuzz.trubuzz.constant.enumerate.OrderType.SHARES;
import static com.trubuzz.trubuzz.constant.enumerate.Position.BULL;
import static com.trubuzz.trubuzz.constant.enumerate.StockType.HK;
import static com.trubuzz.trubuzz.constant.enumerate.StockType.US;
import static com.trubuzz.trubuzz.test.common.CommonAction.check_toast_msg;

/**
 * Created by king on 17/6/9.
 */
@RunWith(JUnitParamsRunner.class)
public class TradeReverseTest extends BaseTest {
    private final TradeService ta = new TradeAction();
    private final QuoteAction qa = new QuoteAction();
    private final TradeView.Toast vt = new TradeView.Toast();

    @Rule
    public ActivityTestRule<?> matr = new ActivityTestRule(God.getFixedClass(AName.MAIN));


    @Before
    public void wishLogin() {
        //使用开户用户登录 ; 进入行情板块
        Wish.wantBrokerLogin();
        qa.into_quote();
    }

    /**
     * 交易小于1手的港股 , 股数成交
     *
     * @param symbol
     * @param position
     * @param limitOrMarket
     * @param price
     * @param amount
     */
    @Test
    @Parameters({
            "00939, BULL, limit, 9.03, 1000, SHARES",
    })
    public void trade_small_hk_stocks(@Var("symbol") String symbol, @Var("position") Position position,
                                      @Var("limitOrMarket") Commissioned limitOrMarket,@Var("price") String price,
                                      @Var("amount") String amount, @Var("orderType") OrderType orderType) {
        ta.into_ordering_page(symbol, position);
        ta.check_order_default_show();

        ta.select_commission_way(limitOrMarket);
        ta.check_commission_default_show(position, limitOrMarket, HK);
        ta.check_time_in_force_default_show(HK);

        if (limitOrMarket == limit) {
            checkNotNull(price);
            ta.type_price(price);
        }

        ta.change_deal_type(orderType);

        ta.type_amount(amount);

        ta.click_keyboard_submit();
        CommonAction.check_current_activity(AName.ORDER);
        check_toast_msg(vt.order_lotsize_limit_toast);

    }

    /**
     * 金额成交不够交易1股
     * @param symbol
     * @param position
     * @param limitOrMarket
     * @param stockType
     * @param price
     */
    @Test
    @Parameters({
            "BABA, BULL, market, US, null, 50",
    })
    public void not_enough_to_trade_one(@Var("symbol") String symbol, @Var("position") Position position,
                                        @Var("limitOrMarket") Commissioned limitOrMarket,@Var("stockType") StockType stockType,
                                        @Var("price") String price,@Var("amount") String amount) {
        ta.into_ordering_page(symbol, position);
        ta.check_order_default_show();

        ta.select_commission_way(limitOrMarket);
        ta.check_commission_default_show(position, limitOrMarket, stockType);
        ta.check_time_in_force_default_show(stockType);

        if (limitOrMarket == limit) {
            checkNotNull(price);
            ta.type_price(price);
        }

        ta.change_deal_type(CASH);

        ta.type_amount(amount);

        ta.click_keyboard_submit();
        CommonAction.check_current_activity(AName.ORDER);
        check_toast_msg(vt.order_lotsize_limit_toast);
    }

    /**
     * 使用无效的委托价格交易
     * @param symbol
     * @param position
     * @param stockType
     * @param price  空价格 , 价格为0.00 , 小于最小波动[美股 0.01 , 小于1$ 为 0.0001 ;  港股则异同①]
     * @param orderType
     * @param amount
     */
    @Test
    @Parameters({
            "AAPL, BULL, US, , SHARES, 1",
    })
    public void invalid_entrustment_price_trade(@Var("symbol") String symbol, @Var("position") Position position,
                                                @Var("stockType") StockType stockType,@Var("price") String price,
                                                @Var("orderType") OrderType orderType, @Var("amount") String amount) {
        ta.into_ordering_page(symbol, position);
        ta.check_order_default_show();

        ta.select_commission_way(limit);
        ta.check_commission_default_show(position, limit, stockType);
        ta.check_time_in_force_default_show(stockType);

        checkNotNull(price);
        ta.type_price(price);

        ta.change_deal_type(orderType);

        ta.type_amount(amount);
        ta.check_invalid_price_or_amount();

        ta.click_keyboard_submit();
        CommonAction.check_current_activity(AName.ORDER);
        check_toast_msg(vt.order_invalid_price_toast);
    }


    /**
     * 无效的股数/总金额交易
     * @param symbol
     * @param position
     * @param limitOrMarket
     * @param stockType
     * @param price
     * @param orderType
     * @param amount  空股数/总金额 , 0股数/总金额
     */
    @Test
    @Parameters({
            "AAPL, BULL, limit, US, 191, SHARES, ",
    })
    public void invalid_amount_trade(@Var("symbol") String symbol ,@Var("position") Position position ,
                                     @Var("limitOrMarket") Commissioned limitOrMarket ,@Var("stockType") StockType stockType ,
                                     @Var("price") String price , @Var("orderType") OrderType orderType ,@Var("amount") String amount){
        ta.into_ordering_page(symbol, position);
        ta.check_order_default_show();

        ta.select_commission_way(limitOrMarket);
        ta.check_commission_default_show(position, limitOrMarket, stockType);
        ta.check_time_in_force_default_show(stockType);

        checkNotNull(price);
        ta.type_price(price);

        ta.change_deal_type(orderType);

        ta.type_amount(amount);
        ta.check_invalid_price_or_amount();

        ta.click_keyboard_submit();
        CommonAction.check_current_activity(AName.ORDER);
        check_toast_msg(vt.order_lotsize_limit_toast);
    }
}

