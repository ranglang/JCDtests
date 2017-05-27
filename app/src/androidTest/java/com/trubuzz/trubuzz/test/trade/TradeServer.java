package com.trubuzz.trubuzz.test.trade;

import com.trubuzz.trubuzz.constant.enumerate.Deal;
import com.trubuzz.trubuzz.constant.enumerate.Position;
import com.trubuzz.trubuzz.constant.enumerate.StockType;

/**
 * Created by king on 17/5/26.
 */

public interface TradeServer {


    /**
     * 限价买入默认展示
     * @param type
     */
    void check_limit_buy_default_show(StockType type);

    /**
     * 市价默认买入默认展示
     * @param type
     */
    void check_market_buy_default_show(StockType type);

    /**
     * 点击限价买入
     */
    void click_limit_buy_tab();

    /**
     * 点击市价买入
     */
    void click_market_buy_tab();

    /**
     * 输入交易价格
     * @param price 单价
     */
    void type_price(String price);

    /**
     * 输入成交金额
     * @param amount
     */
    void type_cash_amount(String amount);

    /**
     * 输入成交股数
     * @param amount
     */
    void type_shares_amount(String amount);

    /**
     * 更改成交方式 ( 金额成交 / 股数成交 )
     * @param deal 已定义的成交方式
     */
    void change_deal_type(Deal deal);

    /**
     * 点击价格微减
     * 这里只能做模糊校验 , 具体还需人工测试
     */
    void decrease_price();

    /**
     * 点击价格微加
     * 这里只能做模糊校验 , 具体还需人工测试
     */
    void increase_price();

    /**
     * 点击下单按钮
     */
    void click_submit_button();

    /**
     * 输入交易密码
     * @param pwd
     */
    void type_trade_password(String pwd);

    /**
     * 确认交易密码后下单
     */
    void confirm_trade_pwd();

    /**
     * 检查交易密码错误
     */
    void check_trade_password_error();

    /**
     * 检查交易成功
     */
    void check_trade_succeed();

    /**
     * 进入指定股票的下单页面
     * @param symbol
     * @param position
     */
    void into_ordering_page(String symbol , Position position);

    /**
     * 检查买单页面除订单填写部分的默认展示
     */
    void check_buy_order_default_show();
}
