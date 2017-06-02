package com.trubuzz.trubuzz.constant.enumerate;

import static com.trubuzz.trubuzz.test.R.string.order_by_cash;
import static com.trubuzz.trubuzz.test.R.string.order_by_shares;
import static com.trubuzz.trubuzz.utils.God.getString;

/**
 * Created by king on 17/5/26.
 */

public enum Deal {
    amount(getString("金额成交" ,order_by_cash)) ,
    volume(getString("股数成交" ,order_by_shares));

    Deal(String value) {
        this.value = value;
    }
    private String value;

    public String getValue() {
        return value;
    }
}
