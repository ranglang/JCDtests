package com.trubuzz.trubuzz.test.common;

import com.trubuzz.trubuzz.shell.Element;
import com.trubuzz.trubuzz.shell.beautify.ActivityElement;

import static com.trubuzz.trubuzz.test.R.string.assets;
import static com.trubuzz.trubuzz.test.R.string.money_management;
import static com.trubuzz.trubuzz.test.R.string.news;
import static com.trubuzz.trubuzz.test.R.string.price;
import static com.trubuzz.trubuzz.test.R.string.circle;
import static com.trubuzz.trubuzz.utils.God.getString;

/**
 * Created by king on 16/11/22.
 */

public class GlobalView {
    // 后退图标
    public static final Element back_up = new ActivityElement().setAssignableClass(android.widget.ImageButton.class)
            .setParent(new ActivityElement().setId("action_bar"));

    // 托盘条
    public static final ActivityElement radio_tray = new ActivityElement().setId("action_menu");

    public static final Element assets_radio = new ActivityElement().setId("action_menu_item_1")
            .setText(getString("交易" ,assets));
    public static final Element quotes_radio = new ActivityElement().setId("action_menu_item_2")
            .setText(getString("行情" ,price));
    public static final Element wealth_radio = new ActivityElement().setId("action_menu_item_3")
            .setText(getString("理财" ,money_management));
    public static final Element news_radio = new ActivityElement().setId("action_menu_item_4")
            .setText(getString("新闻" ,news));
    public static final Element circles_radio = new ActivityElement().setId("action_menu_item_5")
            .setText(getString("圈子" ,circle));
}
