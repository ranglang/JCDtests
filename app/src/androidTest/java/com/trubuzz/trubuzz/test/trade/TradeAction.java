package com.trubuzz.trubuzz.test.trade;

import com.trubuzz.trubuzz.test.common.Actions;
import com.trubuzz.trubuzz.test.quote.QuoteView;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.trubuzz.trubuzz.feature.custom.ViewInteractionHandler.getText;
import static com.trubuzz.trubuzz.shell.Park.given;
import static com.trubuzz.trubuzz.test.R.string.order_limit_buy;
import static com.trubuzz.trubuzz.test.R.string.order_market_buy;
import static com.trubuzz.trubuzz.utils.God.getString;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

/**
 * Created by king on 17/5/16.
 */

class TradeAction extends Actions {
    private QuoteView qv = new QuoteView();
    private TradeView tv = new TradeView();
    private String limitBuyTab = getString("限价买入",order_limit_buy);
    private String marketBuyTab = getString("市价买入", order_market_buy);


    /**
     *  买 ( 进入买入下单页面 )
     */
    public void click_buy_button(){
        String stockName = getText(qv.stock_name);
        given(qv.buy_button).perform(click());
        // 验证股票名称与行情中一致
        given(tv.stockName).check(matches(withText(stockName)));
        // 验证股票价格展示不为空
        given(tv.stockPrice).check(matches(not(withText(""))));
        // 默认展示限价买入
        limit_buy_default_show();
    }

    /**
     * 限价买入默认展示
     */
    public void limit_buy_default_show(){
        given(tv.limit).check(matches(withText(limitBuyTab)));
        given(tv.limitSelected).check(matches(isDisplayed()));
        given(tv.limitPriceInput).check(matches(isDisplayed()));
        given(tv.priceDecrease).check(matches(allOf(isDisplayed() ,isEnabled())));
        given(tv.priceIncrease).check(matches(allOf(isDisplayed() ,isEnabled())));
    }

    /**
     * 市价默认买入默认展示
     */
    public void market_buy_default_show(){
        given(tv.market).check(matches(withText(marketBuyTab)));
        given(tv.marketSelected).check(matches(isDisplayed()));
        // 展示市价交易提示 ( 以最优五档价成交 )
        given(tv.marketReminder).check(matches(isDisplayed()));
    }

    /**
     * 点击限价买入
     */
    public void click_limit_buy_tab(){
        given(tv.limit).check(matches(withText(limitBuyTab)))
                .perform(click());
        // 验证展示
        limit_buy_default_show();
    }

    /**
     * 点击市价买入
     */
    public void click_market_buy_tab(){
        given(tv.market).check(matches(withText(marketBuyTab)))
                .perform(click());
        // 验证展示
        market_buy_default_show();
    }

    /**
     * 输入交易价格
     * @param price 单价
     */
    public void input_price(String price){
        given(tv.limitPriceInput).perform(replaceText(price));
    }

    /**
     * 输入总金额 / 总股数
     * @param amount 金额/股数
     */
    public void input_amount(String amount) {
        given(tv.amountInput).perform(typeText(amount));
    }

    /**
     * 点击下单按钮
     */
    public void click_submit_button(){
        given(tv.ordering).perform(click());
    }

}
