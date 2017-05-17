package com.trubuzz.trubuzz.test.trade;

import com.trubuzz.trubuzz.shell.beautify.ActivityElement;

import static com.trubuzz.trubuzz.test.R.string.order_price_warning;
import static com.trubuzz.trubuzz.utils.God.getString;

/**
 * Created by king on 17/5/16.
 */

public class TradeView {
    // 股票名称
    public final ActivityElement stockName = new ActivityElement().setId("name");

    // 股票价格
    public final ActivityElement stockPrice = new ActivityElement().setId("price");

    // 限价xx
    public final ActivityElement limit = new ActivityElement().setId("order_limit_tab");

    // 选中限价xx 会在该tab底部出现一条线
    public final ActivityElement limitSelected = new ActivityElement().setId("order_limit_line").setDis(false);

    // 市价xx
    public final ActivityElement market = new ActivityElement().setId("order_market_tab");

    // 选中市价xx 会在该tab底部出现一条线
    public final ActivityElement marketSelected = new ActivityElement().setId("order_market_line").setDis(false);

    // 限价价格输入框
    public final ActivityElement limitPriceInput = new ActivityElement().setId("limit_price");

    // 市价价格展示
    public final ActivityElement marketPrice = new ActivityElement().setId("market_price");

    // 市价交易提示
    public final ActivityElement marketReminder = new ActivityElement().setSibling(marketPrice)
            .setText(getString("以最优的五档内部时价成交", order_price_warning));

    // 价格微减
    public final ActivityElement priceDecrease = new ActivityElement().setId("price_decrease");

    // 价格微增
    public final ActivityElement priceIncrease = new ActivityElement().setId("price_increase");


    public final ActivityElement orderType = new ActivityElement().setId("order_type");
    public final ActivityElement orderTypeSwitch = new ActivityElement().setId("button_order_type_switch");

    // 金额 / 股数 输入框
    public final ActivityElement amountInput = new ActivityElement().setId("amountInput");
    public final ActivityElement orderAmountType = new ActivityElement().setId("order_amount_type");
    public final ActivityElement restOfDay = new ActivityElement().setId("button_order_available_time_2");
    public final ActivityElement ioc = new ActivityElement().setId("button_order_available_time_1");

    // 下单按钮
    public final ActivityElement ordering = new ActivityElement().setId("submit");
}
