package com.trubuzz.trubuzz.test.trade;

import android.util.Log;
import android.view.View;

import com.trubuzz.trubuzz.constant.enumerate.Commissioned;
import com.trubuzz.trubuzz.constant.enumerate.Direction;
import com.trubuzz.trubuzz.constant.enumerate.OrderType;
import com.trubuzz.trubuzz.constant.enumerate.Position;
import com.trubuzz.trubuzz.constant.enumerate.StockType;
import com.trubuzz.trubuzz.constant.enumerate.TimeInForce;
import com.trubuzz.trubuzz.idlingResource.ViewIdlingResource;
import com.trubuzz.trubuzz.test.quote.QuoteAction;
import com.trubuzz.trubuzz.test.quote.QuoteView;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Date;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.trubuzz.trubuzz.constant.Config.hkMinFee;
import static com.trubuzz.trubuzz.constant.Config.hkPerFee;
import static com.trubuzz.trubuzz.constant.Config.usMaxFee;
import static com.trubuzz.trubuzz.constant.Config.usMinFee;
import static com.trubuzz.trubuzz.constant.Config.usPerFee;
import static com.trubuzz.trubuzz.constant.enumerate.Condition.GLOBAL;
import static com.trubuzz.trubuzz.constant.enumerate.Position.BEAR;
import static com.trubuzz.trubuzz.constant.enumerate.Position.BULL;
import static com.trubuzz.trubuzz.feature.custom.matchers.CustomMatcher.isPassword;
import static com.trubuzz.trubuzz.feature.custom.matchers.CustomMatcher.withWildcardText;
import static com.trubuzz.trubuzz.feature.custom.actions.CustomViewAction.clickXY;
import static com.trubuzz.trubuzz.feature.custom.actions.CustomViewAction.swipeToVisible;
import static com.trubuzz.trubuzz.feature.custom.handlers.ViewInteractionHandler.getText;
import static com.trubuzz.trubuzz.feature.custom.handlers.ViewInteractionHandler.getView;
import static com.trubuzz.trubuzz.shell.Park.given;
import static com.trubuzz.trubuzz.test.R.string.buy;
import static com.trubuzz.trubuzz.test.R.string.order_available_shares;
import static com.trubuzz.trubuzz.test.R.string.order_by_cash_hkd;
import static com.trubuzz.trubuzz.test.R.string.order_by_cash_shares_number;
import static com.trubuzz.trubuzz.test.R.string.order_by_cash_usd;
import static com.trubuzz.trubuzz.test.R.string.order_by_shares_cash_number;
import static com.trubuzz.trubuzz.test.R.string.order_by_shares_fee;
import static com.trubuzz.trubuzz.test.R.string.order_by_shares_unit;
import static com.trubuzz.trubuzz.test.R.string.order_limit_buy;
import static com.trubuzz.trubuzz.test.R.string.order_limit_sell;
import static com.trubuzz.trubuzz.test.R.string.order_market_buy;
import static com.trubuzz.trubuzz.test.R.string.order_market_sell;
import static com.trubuzz.trubuzz.test.R.string.sell;
import static com.trubuzz.trubuzz.utils.DoIt.regIdlingResource;
import static com.trubuzz.trubuzz.utils.DoIt.unRegIdlingResource;
import static com.trubuzz.trubuzz.utils.God.getString;
import static org.hamcrest.Matchers.not;

/**
 * Created by king on 17/5/16.
 */

public class TradeAction implements TradeService {
    private final String TAG = "jcd_" + this.getClass().getSimpleName();
    private QuoteView qv = new QuoteView();
    private TradeView tv = new TradeView();
    private final QuoteAction qa = new QuoteAction();
    private String limitBuyTab = getString("限价买入",order_limit_buy);
    private String limitSellTab = getString("限价卖出",order_limit_sell);
    private String marketBuyTab = getString("市价买入", order_market_buy);
    private String marketSellTab = getString("市价卖出", order_market_sell);
    private String usCurrency = getString("USD", order_by_cash_usd);
    private String hkCurrency = getString("HKD", order_by_cash_hkd);
    private String shares = getString("股", order_by_shares_unit);
    private String forecastSharesNumber = getString("可%s*股", order_available_shares);
    private String forecastFee = getString("预估手续费:%1$s%2$s", order_by_shares_fee);
    private String fundString = getString("资金:%1$s%2$s", order_by_shares_cash_number);
    private String canOrderSharesString = getString("預估可%1$s入股數:%2$s股", order_by_cash_shares_number);
    private String format = ",###.######";


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
//        given(tv.stockPrice).check(matches(not(withText("")))); ////--暂时注释
    }
    /**
     * 金额/股数成交这块的默认展示
     */
    private void amount_default_show(Position position ,StockType stockType){
        // 获取当前成交方式
        String ot = getText(tv.orderType);
        if (OrderType.CASH.getValue().equals(ot)) {
            given(tv.orderUnit).check(matches(withText(getCurrencyString(stockType))));
        } else if (OrderType.SHARES.getValue().equals(ot)) {
            given(tv.orderUnit).check(matches(withText(shares)));
        }
        forecastSharesNumber = String.format(forecastSharesNumber ,getPositionString(position) ,"*");

        given(tv.orderBySharesNumber).check(matches(withWildcardText(forecastSharesNumber)));
    }

    /**
     * 根据股票类型获取货币字符串
     * @param stockType
     * @return
     */
    private String getCurrencyString(StockType stockType) {
        switch (stockType) {
            case US:
                return usCurrency;
            case HK:
                return hkCurrency;
        }
        return "";
    }

    /**
     * 按交易方向获得[买/卖]字符串
     * @param position
     * @return
     */
    private String getPositionString(Position position) {
        switch (position) {
            case BULL:
                return getString("买", buy);
            case BEAR:
                return getString("卖", sell);
        }
        return "";
    }

    /**
     * 获得当前使用的委托价格
     * @param limitOrMarket
     * @return
     */
    private String getPrice(Commissioned limitOrMarket) {
        switch (limitOrMarket) {
            case limit:
                return getText(tv.limitPriceInput);
            case market:
                return getText(tv.marketPrice);
            default:
                return String.valueOf(0);
        }
    }

    /**
     * 计算预估手续费 ( global版本需在{@link com.trubuzz.trubuzz.constant.Env#condition}中配置 )
     * 美股global版本最小手续费不样
     * @param shares
     * @param fPrice
     * @param stockType
     * @return
     */
    private String getForecastFee(String shares , String fPrice ,StockType stockType){
        BigDecimal fShares = new BigDecimal(shares);
        // 美股手续费预估
        if(stockType == StockType.US) {
            BigDecimal maxFee = fShares.multiply(new BigDecimal(fPrice)).multiply(usMaxFee);
            BigDecimal nowFee = fShares.multiply(usPerFee);

//            if (GLOBAL == Env.condition) {
//                if (usMinFee_G .compareTo(nowFee) == 1) return new DecimalFormat(format).format(usMinFee_G);
//            }
            // global 的最小手续费将在配置文件中设置 , 此处不单独设置
            if (usMinFee.compareTo(nowFee) == 1) return new DecimalFormat(format).format(usMinFee);
            if (nowFee.compareTo(maxFee) == 1) new DecimalFormat(format).format(maxFee);
            return new DecimalFormat(format).format(nowFee);
        }
        // 港股手续费预估
        if (stockType == StockType.HK) {
            BigDecimal nowFee = fShares.multiply(new BigDecimal(fPrice).multiply(hkPerFee));

            if (hkMinFee.compareTo(nowFee) == 1) return new DecimalFormat(format).format(usMinFee);
            return new DecimalFormat(format).format(nowFee);
        }
        return new DecimalFormat(format).format(0);
    }

    /**
     * 计算预估可购买股数
     * 股数必须为整数
     * @param amount
     * @param price
     * @return
     */
    private String getForecastShares(String amount , String price){
        BigDecimal fAmount = new BigDecimal(amount);
        BigDecimal can_trade = new BigDecimal("0");
        BigDecimal fPrice = new BigDecimal(price);

        // 判断委托价格不为0
        if(fPrice.compareTo(can_trade) != 0)
            // 舍尾取整
            can_trade = fAmount.divide(fPrice ,0 ,BigDecimal.ROUND_DOWN);

        return new DecimalFormat(format).format(can_trade);
    }
    /**
     * 计算预估金额
     * @param shares
     * @param fPrice
     * @return
     */
    private String getForecastAmount(String shares , String fPrice){
        BigDecimal fAmount = new BigDecimal(shares);
        // 这里由于股数为整数,故不用考虑精度
        BigDecimal needAmount = fAmount.multiply(new BigDecimal(fPrice));

        return new DecimalFormat(format).format(needAmount);
    }

    /**
     * 获取自定义键盘submit点击点的绝对位置
     * @return
     */
    private int[] getSubmitXY(){
        View view = getView(tv.keyboard);
        final int[] screenPos = new int[2];
        view.getLocationOnScreen(screenPos);
        Log.i(TAG, String.format("getSubmitXY: screenPos before : %s", Arrays.toString(screenPos)));
        screenPos[0] = screenPos[0] + view.getWidth() - 10;
        screenPos[1] = screenPos[1] + view.getHeight() -10;
        Log.i(TAG, String.format("getSubmitXY: screenPos after : %s", Arrays.toString(screenPos)));
        return screenPos;

    }

    @Override
    public void check_commission_default_show(Position position, Commissioned limitOrMarket, StockType stockType) {
        switch (limitOrMarket) {
            case limit:
                if (position == BULL) {
                    given(tv.limit).check(matches(withText(limitBuyTab)));
                } else if (position == BEAR) {
                    given(tv.limit).check(matches(withText(limitSellTab)));
                }
                given(tv.limitSelected).check(matches(isDisplayed()));
                given(tv.limitPriceInput).check(matches(isDisplayed()));
                // 价格微调 + - 出现太慢 , 10秒都不一定能出来
//              given(tv.priceDecrease).check(10 , matches(allOf(isDisplayed() ,isEnabled())));
//              given(tv.priceIncrease).check(10 , matches(allOf(isDisplayed() ,isEnabled())));
                break;
            case market:
                if (position == BULL) {
                    given(tv.market).check(matches(withText(marketBuyTab)));
                } else if (position == BEAR) {
                    given(tv.market).check(matches(withText(marketSellTab)));
                }
                given(tv.marketSelected).check(matches(isDisplayed()));
                // 展示市价交易提示 ( 以最优五档价成交 )
                given(tv.marketReminder).check(matches(isDisplayed()));
        }
//        amount_default_show(position, stockType); ////-- 暂时注释
    }


    @Override
    public void type_price(String price) {
        given(tv.limitPriceInput).perform(replaceText(price));
    }

    @Override
    public void type_amount(String amount) {
        given(tv.amountInput).perform(typeText(amount))
                .check(matches(withText(amount)));
    }

    @Override
    public void check_invalid_price_or_amount(){
        given(tv.forecast_amount).check(doesNotExist());
        given(tv.orderBySharesFee).check(doesNotExist());
    }
    @Override
    public String check_forecast_show(OrderType orderType, Position position, Commissioned limitOrMarket, String amount, StockType stockType) {
        String price = this.getPrice(limitOrMarket);
        String shares = "";
        String needAmount = "";

        String text = "";
        String sm_s1 = getPositionString(position);
        String sm_s2 = "";
        // 金额成交 : 验证提示预计可购买股数
        if(orderType == OrderType.CASH) {
            // 计算可购股数
            shares = this.getForecastShares(amount, price);
            // 格式化字符串
            text = String.format(canOrderSharesString, sm_s1, shares);
            Log.d(TAG, "check_forecast_show: 预计可购买股数提示: " + text);
        }
        // 股数成交: 验证预估资金
        else if(orderType == OrderType.SHARES)   {
            // 计算预估资金
            shares = amount;
            needAmount = this.getForecastAmount(shares, price);
            // 格式化字符串
            text = String.format(fundString, needAmount ,getCurrencyString(stockType));
            Log.d(TAG, "check_forecast_show: 预估资金提示: " + text);
        }
        // 验证预估股数/资金 提示
        given(tv.forecast_amount).check(matches(withText(text)));

        // 验证预估手续费
        String s2 = getCurrencyString(stockType);

        String ms = String.format(forecastFee, this.getForecastFee(shares, price, stockType), s2);
        given(tv.orderBySharesFee).check(matches(withText(ms)));

        // 最终将交易股数返回
        return shares;
    }

    @Override
    public void change_deal_type(OrderType orderType){
        given(tv.orderTypeSwitch).perform(click());
        given(tv.orderTypeSelectDialog).check(matches(isDisplayed()));
        switch (orderType) {
            case CASH:
                given(tv.amountRadio).perform(click());
                break;
            case SHARES:
                given(tv.volumeRadio).perform(click());
                break;
        }
        given(tv.orderType).check(matches(withText(orderType.getValue())));
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
    public void check_time_in_force_default_show(StockType type) {
        given(tv.restOfDay).check(matches(isChecked()));
        switch (type) {
            case US:
                given(tv.ioc).check(matches(not(isChecked())));
                break;
            case HK:
                // 港股没有ioc模式
                given(tv.ioc).check(doesNotExist());
                break;
        }
    }

    @Override
    public void select_time_in_force(TimeInForce timeInForce) {
        switch (timeInForce) {
            case GFD:
                given(tv.restOfDay).perform(click())
                        .check(matches(isChecked()));
                break;
            case IOC:
                given(tv.ioc).perform(click())
                        .check(matches(isChecked()));
                break;
            default:
        }
    }

    @Override
    public void click_keyboard_submit() {
        given(tv.keyboard).perform(clickXY(getSubmitXY()));
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
    public void into_ordering_page(String symbol, Position position) {
        qa.search_stock(symbol);
        qa.waiting_search_result();
        qa.into_search_stock_quote(symbol);
        this.click_order_button_with(position);
        Log.i(TAG, String.format("into_ordering_page: has entered %s ordering page .", symbol));
    }

    @Override
    public void check_order_default_show() {
        given(tv.bullBearBar).check(matches(isDisplayed()));
        given(tv.supportPrice).check(matches(isDisplayed()));
        given(tv.resistancePrice).check(matches(isDisplayed()));
        given(tv.guessRising).check(matches(isDisplayed()));
        given(tv.guessFalling).check(matches(isDisplayed()));
        // 上述是一些宽松的验证, 尚未进一步做严格验证
    }

    @Override
    public void select_commission_way(Commissioned limitOrMarket) {
        switch (limitOrMarket) {
            case limit:
                given(tv.limit).check(matches(withText(limitBuyTab)))
                        .perform(click());
                break;
            case market:
                given(tv.market).check(matches(withText(marketBuyTab)))
                        .perform(click());
                break;
        }
    }

    @Override
    public void check_assets_order_list_show(String symbol, Date now, String price, String shareAmount, Position position) {
        ///- 尚未实现
    }




}
