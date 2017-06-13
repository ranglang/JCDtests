package com.trubuzz.trubuzz.test.trade.tests;

import android.support.test.rule.ActivityTestRule;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.constant.enumerate.Commissioned;
import com.trubuzz.trubuzz.constant.enumerate.OrderType;
import com.trubuzz.trubuzz.constant.enumerate.Position;
import com.trubuzz.trubuzz.test.BaseTest;
import com.trubuzz.trubuzz.test.common.CommonAction;
import com.trubuzz.trubuzz.test.trade.TradeAction;
import com.trubuzz.trubuzz.test.trade.TradeService;
import com.trubuzz.trubuzz.utils.God;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static android.support.test.espresso.intent.Checks.checkNotNull;
import static com.trubuzz.trubuzz.constant.enumerate.Commissioned.limit;
import static com.trubuzz.trubuzz.constant.enumerate.StockType.HK;

/**
 * Created by king on 17/6/9.
 */
@RunWith(JUnitParamsRunner.class)
public class TradeReverseTest extends BaseTest {
    TradeService ta = new TradeAction();

    @Rule
    public ActivityTestRule<?> matr = new ActivityTestRule(God.getFixedClass(AName.MAIN));

    private Object[] trade_small_hk_stocks_data(){
        return new Object[]{
                create_trade_small_hk_stocks_data("00939" ,Position.BULL ,limit ,"9.03" ,"1000")
        };
    }

    @Test
    @Parameters(method = "trade_small_hk_stocks_data")
    public void trade_small_hk_stocks(String symbol , Position position , Commissioned limitOrMarket ,String price ,String amount) {
        ta.into_ordering_page(symbol ,position);
        ta.check_order_default_show();

        ta.select_commission_way(limitOrMarket);
        ta.check_commission_default_show(position ,limitOrMarket , HK );
        ta.check_time_in_force_default_show(HK);

        if (limitOrMarket == limit) {
            checkNotNull(price);
            ta.type_price(price);
        }

        ta.change_deal_type(OrderType.volume);

        ta.type_amount(amount);

        ta.click_keyboard_submit();
        CommonAction.check_current_activity(AName.ORDER );

    }

    private Object[] create_trade_small_hk_stocks_data(String symbol , Position position , Commissioned limitOrMarket ,String price ,String amount) {
        return new Object[]{symbol ,position ,limitOrMarket ,price ,amount};
    }
}
