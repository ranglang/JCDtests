package com.trubuzz.trubuzz.test.trade.tests;

import android.support.test.espresso.core.deps.guava.base.Strings;
import android.support.test.rule.ActivityTestRule;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.constant.enumerate.Commissioned;
import com.trubuzz.trubuzz.constant.enumerate.OrderType;
import com.trubuzz.trubuzz.constant.enumerate.Position;
import com.trubuzz.trubuzz.constant.enumerate.StockType;
import com.trubuzz.trubuzz.test.BaseTest;
import com.trubuzz.trubuzz.test.Wish;
import com.trubuzz.trubuzz.test.common.CommonAction;
import com.trubuzz.trubuzz.test.quote.QuoteAction;
import com.trubuzz.trubuzz.test.trade.TradeAction;
import com.trubuzz.trubuzz.test.trade.TradeService;
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
import static com.trubuzz.trubuzz.constant.enumerate.OrderType.amount;
import static com.trubuzz.trubuzz.constant.enumerate.OrderType.volume;
import static com.trubuzz.trubuzz.constant.enumerate.Position.BULL;
import static com.trubuzz.trubuzz.constant.enumerate.StockType.HK;
import static com.trubuzz.trubuzz.constant.enumerate.StockType.US;

/**
 * Created by king on 17/6/9.
 */
@RunWith(JUnitParamsRunner.class)
public class TradeReverseTest extends BaseTest {
    TradeService ta = new TradeAction();
    QuoteAction qa = new QuoteAction();

    @Rule
    public ActivityTestRule<?> matr = new ActivityTestRule(God.getFixedClass(AName.MAIN));


    private Object[] trade_small_hk_stocks_data(){
        return new Object[]{
                create_trade_small_hk_stocks_data("00939" ,BULL ,limit ,"9.03" ,"1000" ,volume)
        };
    }
    private Object[] not_enough_to_trade_one_data() {
        return new Object[]{
                create_not_enough_to_trade_one_data("BABA", BULL ,market ,US ,null)
        };
    }

    @Before
    public void wishLogin(){
        //使用开户用户登录 ; 进入行情板块
        Wish.wantBrokerLogin();
        qa.into_quote();
    }

    /**
     * 交易小于1手的港股 , 股数成交
     * @param symbol
     * @param position
     * @param limitOrMarket
     * @param price
     * @param amount
     */
    @Test
    @Parameters(method = "trade_small_hk_stocks_data")
    public void trade_small_hk_stocks(String symbol , Position position , Commissioned limitOrMarket ,String price ,String amount ,OrderType orderType) {
        ta.into_ordering_page(symbol ,position);
        ta.check_order_default_show();

        ta.select_commission_way(limitOrMarket);
        ta.check_commission_default_show(position ,limitOrMarket , HK );
        ta.check_time_in_force_default_show(HK);

        if (limitOrMarket == limit) {
            checkNotNull(price);
            ta.type_price(price);
        }

        ta.change_deal_type(orderType);

        ta.type_amount(amount);

        ta.click_keyboard_submit();
        CommonAction.check_current_activity(AName.ORDER );
        ta.check_lotsize_error_msg_toast();

    }

    private Object[] create_trade_small_hk_stocks_data(String symbol , Position position ,
                       Commissioned limitOrMarket ,String price ,String amount ,OrderType orderType) {
        return new Object[]{symbol ,position ,limitOrMarket ,price ,amount ,orderType};
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
    @Parameters(method = "")
    public void not_enough_to_trade_one(String symbol , Position position , Commissioned limitOrMarket ,
                                        StockType stockType ,String price) {
        ta.into_ordering_page(symbol ,position);
        ta.check_order_default_show();

        ta.select_commission_way(limitOrMarket);
        ta.check_commission_default_show(position ,limitOrMarket , stockType );
        ta.check_time_in_force_default_show(stockType);

        if (limitOrMarket == limit) {
            checkNotNull(price);
            ta.type_price(price);
        }

        ta.change_deal_type(amount);

        ta.click_keyboard_submit();
        CommonAction.check_current_activity(AName.ORDER );
        ta.check_lotsize_error_msg_toast();
    }

    private Object[] create_not_enough_to_trade_one_data(String symbol, Position position, Commissioned limitOrMarket,
                                                         StockType stockType, String price) {
        return new Object[]{symbol, position, limitOrMarket, stockType, price};
    }
}