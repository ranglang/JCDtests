package com.trubuzz.trubuzz.test.trade;

import com.trubuzz.trubuzz.constant.Direction;
import com.trubuzz.trubuzz.idlingResource.ViewIdlingResource;
import com.trubuzz.trubuzz.test.common.Actions;
import com.trubuzz.trubuzz.test.quote.QuoteView;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.trubuzz.trubuzz.feature.custom.CustomMatcher.isPassword;
import static com.trubuzz.trubuzz.feature.custom.CustomViewAction.swipeToVisible;
import static com.trubuzz.trubuzz.feature.custom.ViewInteractionHandler.getText;
import static com.trubuzz.trubuzz.feature.custom.ViewInteractionHandler.getView;
import static com.trubuzz.trubuzz.shell.Park.given;
import static com.trubuzz.trubuzz.test.R.string.order_by_cash;
import static com.trubuzz.trubuzz.test.R.string.order_by_shares;
import static com.trubuzz.trubuzz.test.R.string.order_limit_buy;
import static com.trubuzz.trubuzz.test.R.string.order_market_buy;
import static com.trubuzz.trubuzz.utils.DoIt.regIdlingResource;
import static com.trubuzz.trubuzz.utils.DoIt.unRegIdlingResource;
import static com.trubuzz.trubuzz.utils.God.getString;
import static org.hamcrest.Matchers.not;

/**
 * Created by king on 17/5/16.
 */

class TradeAction extends Actions {
    private QuoteView qv = new QuoteView();
    private TradeView tv = new TradeView();
    private String limitBuyTab = getString("限价买入",order_limit_buy);
    private String marketBuyTab = getString("市价买入", order_market_buy);
    private String orderByShares = getString("股数成交", order_by_shares);
    private String orderByCash = getString("金额成交", order_by_cash);


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

        // 价格微调 + - 出现太慢 , 10秒都不一定能出来
//        given(tv.priceDecrease).check(10 , matches(allOf(isDisplayed() ,isEnabled())));
//        given(tv.priceIncrease).check(10 , matches(allOf(isDisplayed() ,isEnabled())));
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
     * 更改成交方式 ( 金额成交 / 股数成交 )
     * @param deal 已定义的成交方式
     */
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
    public enum Deal{
        amount ,volume
    }

    /**
     * 点击价格微减
     * 这里只能做模糊校验 , 具体还需人工测试
     */
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

    /**
     * 点击价格微加
     * 这里只能做模糊校验 , 具体还需人工测试
     */
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
    /**
     * 点击下单按钮
     */
    public void click_submit_button(){
        given(tv.ordering).perform(swipeToVisible(Direction.CENTER_UP ));
        given(tv.ordering).perform(click());
    }

    /**
     * 输入交易密码
     * @param pwd
     */
    public void type_trade_password(String pwd) {
        given(tv.tradePwdInput).perform(replaceText(pwd))
                .check(matches(isPassword()));
    }

    /**
     * 确认交易密码后下单
     */
    public void confirm_trade_pwd(){
        given(tv.confirmTrade).perform(click());
    }

    /**
     * 检查交易成功
     */
    public void check_trade_succeed(){
        given(tv.order_place_success_toast).check(matches(isDisplayed()));
    }
}
