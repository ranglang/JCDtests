package com.trubuzz.trubuzz.test.trade;

import com.trubuzz.trubuzz.shell.beautify.ActivityElement;
import com.trubuzz.trubuzz.shell.beautify.ToastElement;

import static com.trubuzz.trubuzz.test.R.string.confirm;
import static com.trubuzz.trubuzz.test.R.string.order_by_cash;
import static com.trubuzz.trubuzz.test.R.string.order_by_shares;
import static com.trubuzz.trubuzz.test.R.string.order_place_success;
import static com.trubuzz.trubuzz.test.R.string.order_price_warning;
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
    public final ActivityElement orderAmountType = new ActivityElement().setId("order_amount_type");
    public final ActivityElement restOfDay = new ActivityElement().setId("button_order_available_time_2");
    public final ActivityElement ioc = new ActivityElement().setId("button_order_available_time_1");

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
    public final ToastElement order_place_success_toast = new ToastElement(getString("下单成功", order_place_success));
}
