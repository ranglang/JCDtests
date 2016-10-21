package com.trubuzz.trubuzz.elements;

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
    public static final String [] ID_TEXT_net_worth = {"title",getString("净资产", net_liqidation)};
    public static final String [] ID_TEXT_buying_power = {"title",getString("购买力", buying_power)};
    public static final String [] ID_TEXT_today_portfolio = {"title",getString("当日收益", today_portfolio)};
    public static final String [] ID_TEXT_total_portfolio = {"title",getString("总收益", total_portfolio)};
    public static final String [] ID_TEXT_Available_Funds = {"title",getString("可用资金", available_funds)};
    public static final String [] ID_TEXT_total_amount = {"title",getString("持仓总额", total_amount)};


    public static class Details{
        public static final String [] ID_TEXT_account_type = {"title",getString("交易账号类型", account_type)};
        public static final String check_account_type = getString("交易账号类型", account_type);
        public static final String [] ID_TEXT_individual = {"value",getString("INDIVIDUAL")};
        public static final String check_individual = getString("INDIVIDUAL");

        public static final String [] ID_TEXT_net_liquidation = {"title",getString("净清算值", net_liquidation)};
        public static final String [] ID_TEXT_total_cash_value = {"title",getString("总资金", total_cash_value)};
        public static final String [] ID_TEXT_accrued_cash = {"title",getString("净累计利息", accrued_cash)};
        public static final String [] ID_TEXT_buying_power_d = {"title",getString("购买力", buying_power)};
        public static final String [] ID_TEXT_equity_with_loan_value = {"title",getString("有贷款价值的资产", equity_with_loan_value)};
        public static final String [] ID_TEXT_gross_position_value = {"title",getString("股票+期权", gross_position_value)};
        public static final String [] ID_TEXT_init_margin_req = {"title",getString("初始准备金", init_margin_req)};
        public static final String [] ID_TEXT_maint_margin_req = {"title",getString("维持准备金", maint_margin_req)};
        public static final String [] ID_TEXT_available_funds_d = {"title",getString("可用资金", available_funds)};
        public static final String [] ID_TEXT_excess_liquidity = {"title",getString("剩余流动性", excess_liquidity)};
    }
}
