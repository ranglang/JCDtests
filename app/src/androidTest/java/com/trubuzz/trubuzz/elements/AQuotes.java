package com.trubuzz.trubuzz.elements;

import com.trubuzz.trubuzz.shell.Element;
import com.trubuzz.trubuzz.shell.beautify.ActivityElement;

import static com.trubuzz.trubuzz.test.R.string.buy;
import static com.trubuzz.trubuzz.test.R.string.sell;
import static com.trubuzz.trubuzz.test.R.string.watchlist_cn;
import static com.trubuzz.trubuzz.test.R.string.watchlist_customized;
import static com.trubuzz.trubuzz.test.R.string.watchlist_editor;
import static com.trubuzz.trubuzz.test.R.string.watchlist_global;
import static com.trubuzz.trubuzz.test.R.string.watchlist_hk;
import static com.trubuzz.trubuzz.test.R.string.watchlist_us;
import static com.trubuzz.trubuzz.utils.God.getString;

/**
 * Created by king on 16/11/22.
 */

public class AQuotes {
    private static final Element symbol_text = new ActivityElement().setId("subtitle");
    private static final Element security_text = new ActivityElement().setId("title");
    private static final Element price_text = new ActivityElement().setId("price");
    private static final Element country_icon = new ActivityElement().setId("country");

    public static final Element watchlist_fence = new ActivityElement().setText(getString("Watchlist" ,watchlist_customized));
    public static final Element us_fence = new ActivityElement().setText(getString("美股" ,watchlist_us)).setCousinry(watchlist_fence);
    public static final Element hk_fence = new ActivityElement().setText(getString("港股" ,watchlist_hk)).setCousinry(watchlist_fence);;
    public static final Element cn_fence = new ActivityElement().setText(getString("沪深" ,watchlist_cn)).setCousinry(watchlist_fence);;
    public static final Element global_fence = new ActivityElement().setText(getString("环球" ,watchlist_global)).setCousinry(watchlist_fence);;
    public static final Element editor_fence = new ActivityElement().setId("action_watchlist_edit")
            .setText(getString("编辑" ,watchlist_editor));


    public static final Element default_stock = new ActivityElement().setIndex(5).
            setChildren(
                new ActivityElement().setChildren(country_icon ) ,
                new ActivityElement().setChildren(symbol_text ,security_text) ,
                price_text
            );



    public static class details{
        public static final Element buy_button = new ActivityElement().setId("button1").setText(getString("Buy" ,buy));
        public static final Element sell_button = new ActivityElement().setId("button2").setText(getString("Sell" ,sell));
    }

}
