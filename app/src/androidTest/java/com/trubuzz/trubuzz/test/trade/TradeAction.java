package com.trubuzz.trubuzz.test.trade;

import android.util.Log;

import com.trubuzz.trubuzz.constant.enumerate.Deal;
import com.trubuzz.trubuzz.constant.enumerate.Direction;
import com.trubuzz.trubuzz.constant.enumerate.Position;
import com.trubuzz.trubuzz.constant.enumerate.StockType;
import com.trubuzz.trubuzz.idlingResource.ViewIdlingResource;
import com.trubuzz.trubuzz.test.quote.QuoteAction;
import com.trubuzz.trubuzz.test.quote.QuoteView;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.trubuzz.trubuzz.feature.custom.CustomMatcher.isPassword;
import static com.trubuzz.trubuzz.feature.custom.CustomMatcher.withWildcardText;
import static com.trubuzz.trubuzz.feature.custom.CustomViewAction.swipeToVisible;
import static com.trubuzz.trubuzz.feature.custom.ViewInteractionHandler.getText;
import static com.trubuzz.trubuzz.feature.custom.ViewInteractionHandler.getView;
import static com.trubuzz.trubuzz.shell.Park.given;
import static com.trubuzz.trubuzz.test.R.string.buy;
import static com.trubuzz.trubuzz.test.R.string.forecast_shares_number;
import static com.trubuzz.trubuzz.test.R.string.order_by_cash;
import static com.trubuzz.trubuzz.test.R.string.order_by_cash_hkd;
import static com.trubuzz.trubuzz.test.R.string.order_by_cash_shares_number;
import static com.trubuzz.trubuzz.test.R.string.order_by_cash_usd;
import static com.trubuzz.trubuzz.test.R.string.order_by_shares;
import static com.trubuzz.trubuzz.test.R.string.order_by_shares_unit;
import static com.trubuzz.trubuzz.test.R.string.order_limit_buy;
import static com.trubuzz.trubuzz.test.R.string.order_market_buy;
import static com.trubuzz.trubuzz.test.R.string.sell;
import static com.trubuzz.trubuzz.utils.DoIt.regIdlingResource;
import static com.trubuzz.trubuzz.utils.DoIt.unRegIdlingResource;
import static com.trubuzz.trubuzz.utils.God.getString;
import static junit.framework.Assert.fail;
import static org.hamcrest.Matchers.not;

/**
 * Created by king on 17/5/16.
 */

class TradeAction implements TradeServer {
    public void setStockType(StockType stockType) {
        this.stockType = stockType;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    private final String TAG = "jcd_" + this.getClass().getSimpleName();
    private StockType stockType = StockType.US;
    private Position position = Position.BULL;
    private String limitOrMarket = "limit";
    private QuoteView qv = new QuoteView();
    private TradeView tv = new TradeView();
    private final QuoteAction qa = new QuoteAction();
    private String limitBuyTab = getString("限价买入",order_limit_buy);
    private String marketBuyTab = getString("市价买入", order_market_buy);
    private String orderByShares = getString("股数成交", order_by_shares);
    private String orderByCash = getString("金额成交", order_by_cash);
    private String usCurrency = getString("USD", order_by_cash_usd);
    private String hkCurrency = getString("HKD", order_by_cash_hkd);
    private String shares = getString("股", order_by_shares_unit);
    private String forecastSharesNumber = getString("可买*股", forecast_shares_number);
    private String limit = "limit";
    private String market = "market";

    /**
     * 根据交易方向, 点击买/卖按钮
     * @param position
     */
    private void click_order_button_with(Position position){
        String stockName = getText(qv.stock_name);
        switch (position) {
            case BULL:
                given(qv.buy_button).perform(click());
                break;
            case BEAR:
                given(qv.sell_button).perform(click());
                break;
        }
        // 验证股票名称与行情中一致
        given(tv.stockName).check(matches(withText(stockName)));
        // 验证股票价格展示不为空
        given(tv.stockPrice).check(matches(not(withText(""))));
    }
    /**
     * 金额/股数成交这块的默认展示
     */
    private void amount_default_show(){
        String type = getText(tv.orderType);
        if (orderByCash.equals(type)) {
            switch (stockType) {
                case US:
                    given(tv.orderUnit).check(matches(withText(usCurrency)));
                    break;
                case HK:
                    given(tv.orderUnit).check(matches(withText(hkCurrency)));
                    break;
            }
        } else if (orderByShares.equals(type)) {
            given(tv.orderUnit).check(matches(withText(shares)));
        }
        given(tv.orderBySharesNumber).check(matches(withWildcardText(forecastSharesNumber)));
    }

    /**
     * 检查订单时效默认选择
     * @param type us / hk
     */
    private void check_time_in_force_default_show(StockType type) {
        given(tv.restOfDay).check(matches(isChecked()));
        switch (type) {
            case US:
                given(tv.ioc).check(matches(not(isChecked())));
                break;
            case HK:
                // 港股没有ioc模式
                try {
                    given(tv.ioc).check(matches(isDisplayed()));
                } catch (Exception e) {
                    return;
                }
                fail("港股下单页面出现 ioc 模式");
                break;
        }
    }

    @Override
    public void check_limit_buy_default_show(StockType type){
        given(tv.limit).check(matches(withText(limitBuyTab)));
        given(tv.limitSelected).check(matches(isDisplayed()));
        given(tv.limitPriceInput).check(matches(isDisplayed()));
        amount_default_show();
        check_time_in_force_default_show(type);

        // 价格微调 + - 出现太慢 , 10秒都不一定能出来
//        given(tv.priceDecrease).check(10 , matches(allOf(isDisplayed() ,isEnabled())));
//        given(tv.priceIncrease).check(10 , matches(allOf(isDisplayed() ,isEnabled())));
    }

    @Override
    public void check_market_buy_default_show(StockType type){
        given(tv.market).check(matches(withText(marketBuyTab)));
        given(tv.marketSelected).check(matches(isDisplayed()));
        // 展示市价交易提示 ( 以最优五档价成交 )
        given(tv.marketReminder).check(matches(isDisplayed()));
        amount_default_show();
        check_time_in_force_default_show(type);
    }



    @Override
    public void click_limit_buy_tab(){
        given(tv.limit).check(matches(withText(limitBuyTab)))
                .perform(click());
        // 验证展示
        check_limit_buy_default_show(StockType.US);
        this.limitOrMarket = limit;
    }

    @Override
    public void click_market_buy_tab(){
        given(tv.market).check(matches(withText(marketBuyTab)))
                .perform(click());
        this.limitOrMarket = market;
    }

    @Override
    public void type_price(String price) {
        given(tv.limitPriceInput).perform(replaceText(price));
    }

    @Override
    public void type_cash_amount(String amount) { ////
        given(tv.amountInput).perform(typeText(amount))
                .check(matches(withText(amount)));
        float fAmount = Float.parseFloat(amount);
        float price = 0;
        if (this.limitOrMarket.equals(limit)) {
            price = Float.parseFloat(getText(tv.limitPriceInput));
        }
        if (this.limitOrMarket.equals(market)) {
            price = Float.parseFloat(getText(tv.marketPrice));
        }
        int can_trade = 0;
        if(price == 0) can_trade = 0;
        can_trade = (int) (fAmount / price);
        String s1 ="";
        switch (position) {
            case BULL:
                s1 = getString("买", buy);
                break;
            case BEAR:
                s1 = getString("卖", sell);
                break;
        }
        String ms = String.format(getString(order_by_cash_shares_number), s1, String.valueOf(can_trade));
        given(tv.forecast_amount).check(matches(withText(ms)));
    }

    @Override
    public void type_shares_amount(String amount) {
        given(tv.amountInput).perform(typeText(amount))
                .check(matches(withText(amount)));
        if (this.limitOrMarket.equals(limit)) {

            return;
        }
        if (this.limitOrMarket.equals(market)) {

            return;
        }
    }


    /**
     * 输入总金额 / 总股数
     * @param amount 金额/股数
     */
    private void input_amount(String amount, Deal deal) {
        // 1. 输入amount
        given(tv.amountInput).perform(typeText(amount));
        // 2. 获得成交方式
        String type = getText(tv.orderType);
        // 3. 获得委托方式[市价/限价]

        // 4. 获得委托价格

        // 5. 计算预估手续费

        // 6. 计算预估股数/金额
    }

    @Override
    public void change_deal_type(Deal deal){
        given(tv.orderTypeSwitch).perform(click());
        given(tv.orderTypeSelectDialog).check(matches(isDisplayed()));
        switch (deal) {
            case amount:
                given(tv.amountRadio).perform(click());
                given(tv.orderType).check(matches(withText(orderByCash)));
                break;
            case volume:
                given(tv.volumeRadio).perform(click());
                given(tv.orderType).check(matches(withText(orderByShares)));
                break;
        }
    }

    @Override
    public void decrease_price(){
        // 等待减号可用
        regIdlingResource(new ViewIdlingResource(getView(tv.priceDecrease),isEnabled()));
        given(tv.priceDecrease).check(matches(isEnabled()));
        unRegIdlingResource();

        final float price = Float.parseFloat(getText(tv.limitPriceInput));
        // 由于不能取到各股的价格最小变动 , 这里只能以最大变动 ( 5 HKD )做校验了
        given(tv.priceDecrease).perform(click());
        float priceAfter = Float.parseFloat(getText(tv.limitPriceInput));
        assertThat(priceAfter, new TypeSafeMatcher<Float>() {
            @Override
            protected boolean matchesSafely(Float item) {
                return item >= 0.001f && 5.00f >= price - item && price - item >= 0.001f ;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("价格微减结果检查:"+ price);
            }
        });
    }

    @Override
    public void increase_price(){
        // 等待加号可用
        regIdlingResource(new ViewIdlingResource(getView(tv.priceIncrease)));
        given(tv.priceIncrease).check(matches(isEnabled()));
        unRegIdlingResource();

        final float price = Float.parseFloat(getText(tv.limitPriceInput));
        // 由于不能取到各股的价格最小变动 , 这里只能以最大变动 ( 5 HKD )做校验了
        given(tv.priceIncrease).perform(click());
        float priceAfter = Float.parseFloat(getText(tv.limitPriceInput));
        assertThat(priceAfter, new TypeSafeMatcher<Float>() {
            @Override
            protected boolean matchesSafely(Float item) {
                return item >= 0.001f && 5.00f >= item - price && item - price >= 0.001f ;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("价格微减结果检查.");
            }
        });
    }
    @Override
    public void click_submit_button(){
        given(tv.ordering).perform(swipeToVisible(Direction.CENTER_UP ));
        given(tv.ordering).perform(click());
    }

    @Override
    public void type_trade_password(String pwd) {
        given(tv.tradePwdInput).perform(replaceText(pwd))
                .check(matches(isPassword()));
    }

    @Override
    public void confirm_trade_pwd(){
        given(tv.confirmTrade).perform(click());
    }

    @Override
    public void check_trade_password_error(){
        given(tv.trade_pwd_error_toast).check(matches(isDisplayed()));
    }

    @Override
    public void check_trade_succeed(){
        given(tv.order_place_success_toast).check(matches(isDisplayed()));
    }

    @Override
    public void into_ordering_page(String symbol, Position position) {
        qa.search_stock(symbol);
        qa.waiting_search_result();
        qa.into_search_stock_quote(symbol);
        this.click_order_button_with(position);
        Log.i(TAG, String.format("into_ordering_page: has entered %s ordering page .", symbol));
    }

    @Override
    public void check_buy_order_default_show() {
        given(tv.bullBearBar).check(matches(isDisplayed()));
        given(tv.supportPrice).check(matches(isDisplayed()));
        given(tv.resistancePrice).check(matches(isDisplayed()));
        given(tv.guessRising).check(matches(isDisplayed()));
        given(tv.guessFalling).check(matches(isDisplayed()));
        // 上述是一些宽松的验证, 尚未进一步做严格验证
    }
}
