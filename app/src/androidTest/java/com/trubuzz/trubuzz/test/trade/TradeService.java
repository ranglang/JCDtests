package com.trubuzz.trubuzz.test.trade;

import com.trubuzz.trubuzz.constant.enumerate.Commissioned;
import com.trubuzz.trubuzz.constant.enumerate.OrderType;
import com.trubuzz.trubuzz.constant.enumerate.Position;
import com.trubuzz.trubuzz.constant.enumerate.StockType;
import com.trubuzz.trubuzz.constant.enumerate.TimeInForce;
import com.trubuzz.trubuzz.shell.beautify.ToastElement;

import java.util.Date;

/**
 * Created by king on 17/5/26.
 */

public interface TradeService {


    /**
     * 输入交易价格
     * @param price 单价
     */
    void type_price(String price);

    /**
     * 输入成交金额 / 成交股数
     * @param amount
     */
    void type_amount(String amount);

    void check_invalid_price_or_amount();

    /**
     * 验证预估手续费 / 预估可买股数 / 预估所需金额等
     * @param orderType 成交方式
     * @param position 方向 [ 买/卖 ]
     * @param limitOrMarket 委托方式
     * @param amount 金额/股数
     * @param stockType 股票类型
     * @return 购买股数(预期) ,金额成交需转换
     */
    String check_forecast_show(OrderType orderType, Position position, Commissioned limitOrMarket, String amount, StockType stockType);

    /**
     * 更改成交方式 ( 金额成交 / 股数成交 )
     * @param orderType 已定义的成交方式
     */
    void change_deal_type(OrderType orderType);

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
     * 进入指定股票的下单页面
     * @param symbol
     * @param position
     */
    void into_ordering_page(String symbol , Position position);

    /**
     * 检查下单页面除订单填写部分的默认展示
     */
    void check_order_default_show();

    /**
     * 选择委托方式
     * @param limitOrMarket
     */
    void select_commission_way(Commissioned limitOrMarket);

    /**
     * 比对下单成功后的委托列表中的数据
     * @param symbol 股票代码
     * @param now 下单时间
     * @param price 委托价格
     * @param shareAmount 交易股数
     * @param position 交易方向
     */
    void check_assets_order_list_show(String symbol, Date now, String price, String shareAmount, Position position);

    /**
     * 委托默认展示(不包含时效指令)
     * @param position
     * @param limitOrMarket
     * @param stockType
     */
    void check_commission_default_show(Position position, Commissioned limitOrMarket, StockType stockType);

    /**
     * 订单时效指令默认展示
     * @param stockType
     */
    void check_time_in_force_default_show(StockType stockType);

    /**
     * 选择时效指令
     * @param timeInForce
     */
    void select_time_in_force(TimeInForce timeInForce);

    /**
     * 点击自定义键盘上的submit
     */
    void click_keyboard_submit();

}
