package com.trubuzz.trubuzz.test.settings.views;

import android.widget.ListView;

import com.trubuzz.trubuzz.shell.Element;
import com.trubuzz.trubuzz.shell.beautify.ActivityElement;
import com.trubuzz.trubuzz.shell.beautify.AtomElement;

import static com.trubuzz.trubuzz.test.R.string.cancel;
import static com.trubuzz.trubuzz.test.R.string.change_login_password;
import static com.trubuzz.trubuzz.test.R.string.change_nickname;
import static com.trubuzz.trubuzz.test.R.string.change_trade_password;
import static com.trubuzz.trubuzz.test.R.string.confirm_trade_password_again;
import static com.trubuzz.trubuzz.test.R.string.input_new_trade_password;
import static com.trubuzz.trubuzz.test.R.string.input_old_trade_password;
import static com.trubuzz.trubuzz.test.R.string.logout;
import static com.trubuzz.trubuzz.test.R.string.ok;
import static com.trubuzz.trubuzz.test.R.string.preference;
import static com.trubuzz.trubuzz.test.R.string.privacy_policy;
import static com.trubuzz.trubuzz.test.R.string.tutorial;
import static com.trubuzz.trubuzz.test.R.string.tutorial_start;
import static com.trubuzz.trubuzz.utils.God.getString;

/**
 * Created by king on 2017/7/20.
 */

public class SettingsView {

    // 左侧抽屉展开图标
    public final ActivityElement left_drawer_ioc = new ActivityElement().setId("action_drawer");
    // 抽屉内的 " 设置 " 项
    public final ActivityElement setting = new ActivityElement().setText(getString("设置",preference));
    // 左侧抽屉
    public final ActivityElement drawer_layout = new ActivityElement().setChildren(new ActivityElement()
            .setChildren(setting));
    // 隐私模式
    public final ActivityElement private_set = new ActivityElement().setId("private_mode");
    public final ActivityElement personal = new ActivityElement().setId("navi_profile");  //用户信息

    public final ActivityElement head_picture  = new ActivityElement().setId("icon").setParent(personal);   //用户头像 -- 抽屉页面
    public final ActivityElement nickname  = new ActivityElement().setId("title").setParent(personal);      //昵称


    /* 设置页详情 */
    public final ActivityElement notify_switch = new ActivityElement().setId("notify"); //  can doCheck .
    public final ActivityElement rising_falling_set = new ActivityElement().setId("rising_falling_display");
    public final ActivityElement k_chart_set = new ActivityElement().setId("chart_type");   //K线图设置

    // 修改登录密码
    public final ActivityElement change_login_pwd = new ActivityElement().setId("change_login_password")
            .setChildren(new ActivityElement().setText(getString("修改登入密码", change_login_password)));
    // 修改交易密码
    public final ActivityElement change_trade_pwd = new ActivityElement().setId("change_trade_password")
            .setChildren(new ActivityElement().setText(getString("修改交易密码", change_trade_password)));




    public final ActivityElement privacy_policy_view = new ActivityElement().setChildren(
            new ActivityElement().setText(getString("隐私权政策" ,privacy_policy))
    );
    public final ActivityElement tutorial_view = new ActivityElement().setChildren(
            new ActivityElement().setText(getString("新用户指导" ,tutorial))
    );
    public final ActivityElement version_text = new ActivityElement().setId("version");

    // 退出按钮
    public final ActivityElement logout_button = new ActivityElement().setText(getString("退出" ,logout));

    /*** 密码修改详情 ***/
    // 输入旧密码
    public final ActivityElement old_password_input = new ActivityElement().setId("password_old")
            .setSiblings(new ActivityElement().setText(getString("输入旧密码", input_old_trade_password)));
    // 输入新密码
    public final ActivityElement new_password_input = new ActivityElement().setId("password_new")
            .setSiblings(new ActivityElement().setText(getString("输入新密码", input_new_trade_password)));
    // 确认新密码
    public final ActivityElement confirm_new_password = new ActivityElement().setId("password_confirm")
            .setSiblings(new ActivityElement().setText(getString("再次确认新密码", confirm_trade_password_again)));
    // 确定按钮
    public final ActivityElement submit_button = new ActivityElement().setId("submit").setText(getString("确定", ok));

    public static class EPrivacy{
        public final static AtomElement privacy_terms = new AtomElement().setCss("section.terms");
    }

    private final ActivityElement tutorial_page = new ActivityElement().setId("pager");
    public final ActivityElement tutorial_1_title = new ActivityElement().setId("title").setParent(
            new ActivityElement().setParent(tutorial_page)
    );
    public final ActivityElement tutorial_1_content = new ActivityElement().setId("content").setParent(
            new ActivityElement().setParent(tutorial_page)
    );
    public final ActivityElement tutorial_start_button = new ActivityElement().setId("btn_done")
            .setText(getString("立即启程" ,tutorial_start));

    /************ 用户信息 ************/
    public final ActivityElement head_image = new ActivityElement().setId("avatar");    //用户头像
    public final ActivityElement head_image_set = new ActivityElement().setChildren(head_image);
    public final ActivityElement nickname_text = new ActivityElement().setId("nickname");
    public final ActivityElement nickname_change_view = new ActivityElement().setChildren(nickname_text);
    public final ActivityElement birthday_text = new ActivityElement().setId("birthday");
    public final ActivityElement birthday_change_view = new ActivityElement().setChildren(birthday_text);
    public final ActivityElement gender_text = new ActivityElement().setId("gender");
    public final ActivityElement gender_change_view = new ActivityElement().setChildren(gender_text);

    public final ActivityElement nickname_change_title = new ActivityElement().setId("alertTitle").setText(getString("修改昵称" ,change_nickname));
    public final ActivityElement nickname_change_edit = new ActivityElement().setId("value");
    public final ActivityElement nickname_ok = new ActivityElement().setId("button1").setText(getString("确定" ,ok));
    public final ActivityElement nickname_cancel = new ActivityElement().setId("button2").setText(getString("取消",cancel));

    /********** 设置头像 *********/
    private final ActivityElement done_cancel_bar_view = new ActivityElement().setId("done_cancel_bar");
    public final ActivityElement crop_image_view = new ActivityElement().setId("crop_image").setAssignableClass(android.widget.ImageView.class);    //头像裁剪View
    public final ActivityElement crop_done_button = new ActivityElement().setId("btn_done").setParent(done_cancel_bar_view);
    public final ActivityElement crop_cancel_button = new ActivityElement().setId("btn_cancel").setParent(done_cancel_bar_view);

    /************* 设置生日 *************/
    public final ActivityElement birthday_picker_year = new ActivityElement().setId("date_picker_year");
    public final ActivityElement birthday_month_show = new ActivityElement().setId("date_picker_month");
    public final ActivityElement birthday_day_show = new ActivityElement().setId("date_picker_day");
    public final ActivityElement birthday_pick_ListView = new ActivityElement().setAssignableClass(ListView.class)
            .setParent(new ActivityElement().setId("animator"));
    public final ActivityElement birthday_ok = new ActivityElement().setId("ok").setText(getString("确定" ,ok));
    public final ActivityElement birthday_cancel = new ActivityElement().setId("cancel").setText(getString("取消",cancel));
}
