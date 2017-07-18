package com.trubuzz.trubuzz.test.quote.views;

import com.trubuzz.trubuzz.shell.beautify.ToastElement;
import com.trubuzz.trubuzz.test.login.LoginView;

import static com.trubuzz.trubuzz.test.R.string.search;
import static com.trubuzz.trubuzz.utils.God.getString;

/**
 * Created by king on 2017/7/18.
 */

public class QuoteToast {
    public ToastElement searchText = new ToastElement(getString("搜寻", search));
}
