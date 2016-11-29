package com.trubuzz.trubuzz.elements;

import com.trubuzz.trubuzz.shell.Element;
import com.trubuzz.trubuzz.shell.beautify.ActivityElement;
import com.trubuzz.trubuzz.shell.beautify.AtomElement;

import static com.trubuzz.trubuzz.test.R.string.cancel;
import static com.trubuzz.trubuzz.test.R.string.change_nickname;
import static com.trubuzz.trubuzz.test.R.string.logout;
import static com.trubuzz.trubuzz.test.R.string.ok;
import static com.trubuzz.trubuzz.test.R.string.preference;
import static com.trubuzz.trubuzz.test.R.string.privacy_policy;
import static com.trubuzz.trubuzz.test.R.string.tutorial;
import static com.trubuzz.trubuzz.test.R.string.tutorial_start;
import static com.trubuzz.trubuzz.utils.God.getString;

/**
 * Created by king on 2016/9/6.
 */
public class ASettings {
    public static final Element left_drawer = new ActivityElement().setId("action_drawer");

    public final Element setting = new ActivityElement().setText(getString("设置",preference));

    public final Element private_set = new ActivityElement().setId("private_mode");             //隐私模式
    public final Element personal = new ActivityElement().setId("navi_profile");  //用户信息
    public final Element drawer_layout = new ActivityElement().setChildren(new ActivityElement()
            .setChildren(setting));     //左侧抽屉

    public final Element head_picture  = new ActivityElement().setId("icon").setParent(personal);   //用户头像
    public final Element nickname  = new ActivityElement().setId("title").setParent(personal);      //昵称


    public final Element notify_switch = new ActivityElement().setId("notify"); //  can checked .
    public final Element rising_falling_set = new ActivityElement().setId("rising_falling_display");
    public final Element k_chart_set = new ActivityElement().setId("chart_type");   //K线图设置
    public final Element change_trade_pwd_view = new ActivityElement().setId("change_trade_password");   //交易密码修改
    public final Element privacy_policy_view = new ActivityElement().setChildren(
            new ActivityElement().setText(getString("隐私权政策" ,privacy_policy))
    );
    public final Element tutorial_view = new ActivityElement().setChildren(
            new ActivityElement().setText(getString("新用户指导" ,tutorial))
    );
    public final Element version_text = new ActivityElement().setId("version");

    public final Element logout_button = new ActivityElement().setText(getString("退出" ,logout));

    public final Element trade_pwd_old = new ActivityElement().setId("password_old");
    public final Element trade_pwd_new = new ActivityElement().setId("password_new");
    public final Element trade_pwd_confirm = new ActivityElement().setId("password_confirm");
    public final Element trade_submit = new ActivityElement().setId("submit");

    public static class EPrivacy{
        public final static Element privacy_terms = new AtomElement().setCss("section.terms");
    }

    private final Element tutorial_page = new ActivityElement().setId("pager");
    public final Element tutorial_1_title = new ActivityElement().setId("title").setParent(
            new ActivityElement().setParent(tutorial_page)
    );
    public final Element tutorial_1_content = new ActivityElement().setId("content").setParent(
            new ActivityElement().setParent(tutorial_page)
    );
    public final Element tutorial_start_button = new ActivityElement().setId("btn_done")
            .setText(getString("立即启程" ,tutorial_start));

    /************ 用户信息 ************/
    public final Element nickname_text = new ActivityElement().setId("nickname");
    public final Element nickname_change_view = new ActivityElement().setChildren(nickname_text);
    public final Element birthday_text = new ActivityElement().setId("birthday");
    public final Element birthday_change_view = new ActivityElement().setChildren(birthday_text);
    public final Element gender_text = new ActivityElement().setId("gender");
    public final Element gender_change_view = new ActivityElement().setChildren(gender_text);

    public final Element nickname_change_title = new ActivityElement().setId("alertTitle").setText(getString("修改昵称" ,change_nickname));
    public final Element nickname_change_edit = new ActivityElement().setId("value");
    public final Element nickname_ok = new ActivityElement().setId("button1").setText(getString("确定" ,ok));
    public final Element nickname_cancel = new ActivityElement().setId("button2").setText(getString("取消",cancel));
}
