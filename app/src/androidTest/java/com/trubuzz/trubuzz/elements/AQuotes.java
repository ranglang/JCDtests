package com.trubuzz.trubuzz.elements;

import android.widget.ListView;

import com.trubuzz.trubuzz.shell.beautify.ActivityElement;

import static com.trubuzz.trubuzz.test.R.string.add;
import static com.trubuzz.trubuzz.test.R.string.all;
import static com.trubuzz.trubuzz.test.R.string.buy;
import static com.trubuzz.trubuzz.test.R.string.default_watchlist_group;
import static com.trubuzz.trubuzz.test.R.string.in_group;
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
    public static final ActivityElement symbol_text = new ActivityElement().setId("subtitle");
    private static final ActivityElement security_text = new ActivityElement().setId("title");
    private static final ActivityElement price_text = new ActivityElement().setId("price");
    private static final ActivityElement country_icon = new ActivityElement().setId("country");

    public static final ActivityElement watchlist_fence = new ActivityElement().setText(getString("Watchlist" ,watchlist_customized));
    public static final ActivityElement us_fence = new ActivityElement().setText(getString("美股" ,watchlist_us)).setCousinry(watchlist_fence);
    public static final ActivityElement hk_fence = new ActivityElement().setText(getString("港股" ,watchlist_hk)).setCousinry(watchlist_fence);;
    public static final ActivityElement cn_fence = new ActivityElement().setText(getString("沪深" ,watchlist_cn)).setCousinry(watchlist_fence);;
    public static final ActivityElement global_fence = new ActivityElement().setText(getString("环球" ,watchlist_global)).setCousinry(watchlist_fence);;
    public static final ActivityElement edit_watchlist = new ActivityElement().setId("action_watchlist_edit")
            .setText(getString("编辑" ,watchlist_editor));

    public static final ActivityElement default_stock = new ActivityElement().setIndex(5).
            setChildren(
                new ActivityElement().setChildren(country_icon ) ,
                new ActivityElement().setChildren(symbol_text ,security_text) ,
                price_text
            );
    public final ActivityElement stocks_recycler = new ActivityElement().setId("recycler");
    public final ActivityElement kind_all = new ActivityElement().setChildren(
                new ActivityElement().setText(getString("全部", all)).setDis(false)
            );
    /* 自选 */
    public static final ActivityElement watchlist_default_item = new ActivityElement().setText(getString("默认名单", default_watchlist_group));
    public static final ActivityElement watchlist_ListView = new ActivityElement().setAssignableClass(ListView.class);

    /* 行情详情 */
    public static class details{
        public final ActivityElement buy_button = new ActivityElement().setId("button1").setText(getString("Buy" ,buy));
        public final ActivityElement sell_button = new ActivityElement().setId("button2").setText(getString("Sell" ,sell));
        public final ActivityElement add_self_stock = new ActivityElement().setContent_desc(getString("新增", add));

        /* 添加自选 */
        public final ActivityElement in_group_yet = new ActivityElement().setId("select")
                .setText(getString("已在该分类中", in_group))
                .setDis(false);
        public final ActivityElement group_name = new ActivityElement().setId("title");
    }

}
