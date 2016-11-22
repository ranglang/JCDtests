package com.trubuzz.trubuzz.elements;

import com.trubuzz.trubuzz.shell.Element;
import com.trubuzz.trubuzz.shell.beautify.ActivityElement;

import static com.trubuzz.trubuzz.test.R.string.logout;
import static com.trubuzz.trubuzz.test.R.string.preference;
import static com.trubuzz.trubuzz.utils.God.getString;

/**
 * Created by king on 2016/9/6.
 */
public class ASettings {
    private final Element head_picture  = new ActivityElement().setId("icon");
    private final Element nickname  = new ActivityElement().setId("title");

    public static final Element left_drawer = new ActivityElement().setId("action_drawer");

    public final Element setting = new ActivityElement().setText(getString("设置",preference));
    public final Element private_set = new ActivityElement().setId("private_mode");
    public final Element personal = new ActivityElement().setChildren(head_picture ,nickname);
    public final Element drawer_layout = new ActivityElement().setChildren(new ActivityElement()
            .setChildren(setting));


    public final Element notify_switch = new ActivityElement().setId("notify"); //  can checked .
    public final Element logout_button = new ActivityElement().setText(getString("退出" ,logout));
    public final Element rising_falling_set = new ActivityElement().setId("rising_falling_display");

}
