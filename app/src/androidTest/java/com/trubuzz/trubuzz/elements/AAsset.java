package com.trubuzz.trubuzz.elements;

import com.trubuzz.trubuzz.shell.Element;
import com.trubuzz.trubuzz.shell.beautify.ActivityElement;

import static com.trubuzz.trubuzz.test.R.string.account_type;
import static com.trubuzz.trubuzz.test.R.string.accrued_cash;
import static com.trubuzz.trubuzz.test.R.string.available_funds;
import static com.trubuzz.trubuzz.test.R.string.buying_power;
import static com.trubuzz.trubuzz.test.R.string.equity_with_loan_value;
import static com.trubuzz.trubuzz.test.R.string.excess_liquidity;
import static com.trubuzz.trubuzz.test.R.string.gross_position_value;
import static com.trubuzz.trubuzz.test.R.string.init_margin_req;
import static com.trubuzz.trubuzz.test.R.string.maint_margin_req;
import static com.trubuzz.trubuzz.test.R.string.net_liqidation;
import static com.trubuzz.trubuzz.test.R.string.net_liquidation;
import static com.trubuzz.trubuzz.test.R.string.today_portfolio;
import static com.trubuzz.trubuzz.test.R.string.total_amount;
import static com.trubuzz.trubuzz.test.R.string.total_cash_value;
import static com.trubuzz.trubuzz.test.R.string.total_portfolio;
import static com.trubuzz.trubuzz.utils.God.getString;

/**
 * Created by king on 2016/10/19.
 */

public class AAsset {

    public static final Element net_worth_view = new ActivityElement().setText(getString("净资产", net_liqidation));
    public static final Element buying_power_view = new ActivityElement().setText(getString("购买力", buying_power));
    public static final Element today_portfolio_view = new ActivityElement().setText(getString("当日收益", today_portfolio));
    public static final Element total_portfolio_view = new ActivityElement().setText(getString("总收益", total_portfolio));
    public static final Element available_funds_view = new ActivityElement().setText(getString("可用资金", available_funds));
    public static final Element total_amount_view = new ActivityElement().setText(getString("持仓总额", total_amount));


    public static class Details{
        public final Element account_type_view = new ActivityElement().setText(getString("交易账号类型", account_type));
        public final Element individual_text = new ActivityElement().setText(getString("INDIVIDUAL"));

        public final Element net_liquidation_view = new ActivityElement().setText(getString("净清算值", net_liquidation));
        public final Element total_cash_value_view = new ActivityElement().setText(getString("总资金", total_cash_value));
        public final Element accrued_cash_view = new ActivityElement().setText(getString("净累计利息", accrued_cash));
        public final Element buying_power_d_view = new ActivityElement().setText(getString("购买力", buying_power));
        public final Element equity_with_loan_value_view = new ActivityElement().setText(getString("有贷款价值的资产", equity_with_loan_value));
        public final Element gross_position_value_view = new ActivityElement().setText(getString("股票+期权", gross_position_value));
        public final Element init_margin_req_view = new ActivityElement().setText(getString("初始准备金", init_margin_req));
        public final Element maint_margin_req_view = new ActivityElement().setText(getString("维持准备金", maint_margin_req));
        public final Element available_funds_d_view = new ActivityElement().setText(getString("可用资金", available_funds));
        public final Element excess_liquidity_view = new ActivityElement().setText(getString("剩余流动性", excess_liquidity));
    }
}
