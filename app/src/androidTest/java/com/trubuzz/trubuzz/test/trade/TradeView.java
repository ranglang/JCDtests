package com.trubuzz.trubuzz.test.trade;

        import com.trubuzz.trubuzz.shell.beautify.ActivityElement;
        import com.trubuzz.trubuzz.shell.beautify.ToastElement;

        import static com.trubuzz.trubuzz.feature.custom.matchers.CustomMatcher.hasChildrenCount;
        import static com.trubuzz.trubuzz.shell.beautify.ToastElement.MsgType.contain;
        import static com.trubuzz.trubuzz.test.R.string.confirm;
        import static com.trubuzz.trubuzz.test.R.string.lotsize_limit;
        import static com.trubuzz.trubuzz.test.R.string.order_by_cash;
        import static com.trubuzz.trubuzz.test.R.string.order_by_shares;
        import static com.trubuzz.trubuzz.test.R.string.order_invalid_price;
        import static com.trubuzz.trubuzz.test.R.string.order_place_success;
        import static com.trubuzz.trubuzz.test.R.string.order_price_warning;
        import static com.trubuzz.trubuzz.test.R.string.resistance_price;
        import static com.trubuzz.trubuzz.test.R.string.support_price;
        import static com.trubuzz.trubuzz.test.R.string.trade_password_mismatch;
        import static com.trubuzz.trubuzz.utils.God.getString;

/**
 * Created by king on 17/5/16.
 */

public class TradeView {
    // 顶级View
    public final ActivityElement topView = new ActivityElement().setId("decor_content_parent");

    // 股票名称
    public final ActivityElement stockName = new ActivityElement().setId("name");

    // 股票价格
    public final ActivityElement stockPrice = new ActivityElement().setId("price");

    // 看多
    public final ActivityElement guessRising = new ActivityElement().setId("guess_rising");

    // 看空
    public final ActivityElement guessFalling = new ActivityElement().setId("guess_falling");

    // 支撑价
    public final ActivityElement supportPrice = new ActivityElement().setId("support_price")
            .setSiblings(new ActivityElement().setText(getString("支撑", support_price)));

    // 压力价
    public final ActivityElement resistancePrice = new ActivityElement().setId("resistance_price")
            .setSiblings(new ActivityElement().setText(getString("压力", resistance_price)));

    // 多空图
    public final ActivityElement bullBearBar = new ActivityElement().setId("bar")
            .setMatchers(hasChildrenCount(2));

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
    public final ActivityElement marketReminder = new ActivityElement().setSiblings(marketPrice)
            .setText(getString("以最优的五档内部时价成交", order_price_warning));

    // 价格微减
    public final ActivityElement priceDecrease = new ActivityElement().setId("price_decrease");

    // 价格微增
    public final ActivityElement priceIncrease = new ActivityElement().setId("price_increase");

    // 成交方式展示
    public final ActivityElement orderType = new ActivityElement().setId("order_type");

    // 成交方式切换按钮
    public final ActivityElement orderTypeSwitch = new ActivityElement().setId("button_order_type_switch");

    // 成交方式选择对话框
    public final ActivityElement orderTypeSelectDialog= new ActivityElement().setId("select_dialog_listview").setDis(false);

    // 金额成交单选框
    public final ActivityElement amountRadio = new ActivityElement().setText(getString("金额成交" ,order_by_cash ));
    // 股数成交单选框
    public final ActivityElement volumeRadio = new ActivityElement().setText(getString("股数成交" ,order_by_shares));

    // 金额 / 股数 输入框
    public final ActivityElement amountInput = new ActivityElement().setId("amount");
    // [买/卖][金额/股数] 文字展示
    public final ActivityElement orderAmountType = new ActivityElement().setId("order_amount_type");

    // 交易单位[USD / HKD / 股]
    public final ActivityElement orderUnit = new ActivityElement().setId("order_unit");

    // 预计可交易股数
    public final ActivityElement orderBySharesNumber = new ActivityElement().setId("order_by_shares_number");

    // 预估可购股数 / 预估所需金额
    public final ActivityElement forecast_amount = new ActivityElement().setId("text1").setAssignableClass(android.widget.TextView.class)
            .setParent(new ActivityElement().setAssignableClass(android.widget.FrameLayout.class))
            .setDis(false);

    // 预估手续费
    public final ActivityElement orderBySharesFee = new ActivityElement().setId("order_by_shares_fee").setDis(false);

    // 当日有效
    public final ActivityElement restOfDay = new ActivityElement().setId("button_order_available_time_2");

    // IOC
    public final ActivityElement ioc = new ActivityElement().setId("button_order_available_time_1");

    // 自定义键盘
    public final ActivityElement keyboard = new ActivityElement().setId("keyboard_view");

    // 下单按钮
    public final ActivityElement ordering = new ActivityElement().setId("submit").setDis(false);

    // 证券账号
    public final ActivityElement account = new ActivityElement().setId("account");

    // 交易密码输入框
    public final ActivityElement tradePwdInput = new ActivityElement().setId("password");

    // 交易密码有效期选择器
    public final ActivityElement tradePwdSpinner = new ActivityElement().setId("expired");

    // 当前交易密码有效期
    public final ActivityElement tradePwdExpires = new ActivityElement().setId("text1");

    // 确定交易密码
    public final ActivityElement confirmTrade = new ActivityElement().setId("submit")
            .setText(getString("确定", confirm));


    /***************
     * toast
     **************/
    public static class Toast {
        public final ToastElement order_place_success_toast = new ToastElement(getString("下单成功", order_place_success));
        public final ToastElement trade_pwd_error_toast = new ToastElement(getString("交易密码不正确", trade_password_mismatch));
        public final ToastElement order_lotsize_limit_toast = new ToastElement(getString("当前商品的最小批量为：", lotsize_limit), contain);
        public final ToastElement order_invalid_price_toast = new ToastElement(getString("请输入合法的价格：", order_invalid_price));
    }
}

