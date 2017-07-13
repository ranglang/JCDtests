package com.trubuzz.trubuzz.test.login;


import com.trubuzz.trubuzz.shell.beautify.ActivityElement;
import com.trubuzz.trubuzz.shell.beautify.AtomElement;
import com.trubuzz.trubuzz.shell.beautify.ToastElement;

import static com.trubuzz.trubuzz.test.R.string.cancel;
import static com.trubuzz.trubuzz.test.R.string.confirm_resend_mail;
import static com.trubuzz.trubuzz.test.R.string.confirm_resend_mail_hint;
import static com.trubuzz.trubuzz.test.R.string.forget_password;
import static com.trubuzz.trubuzz.test.R.string.forget_password_email;
import static com.trubuzz.trubuzz.test.R.string.forget_password_phone;
import static com.trubuzz.trubuzz.test.R.string.get_sms;
import static com.trubuzz.trubuzz.test.R.string.incorrect_account_format;
import static com.trubuzz.trubuzz.test.R.string.incorrect_email_format;
import static com.trubuzz.trubuzz.test.R.string.incorrect_password_confirm;
import static com.trubuzz.trubuzz.test.R.string.incorrect_password_empty;
import static com.trubuzz.trubuzz.test.R.string.incorrect_password_format;
import static com.trubuzz.trubuzz.test.R.string.incorrect_phone_format;
import static com.trubuzz.trubuzz.test.R.string.incorrect_sms_format;
import static com.trubuzz.trubuzz.test.R.string.login;
import static com.trubuzz.trubuzz.test.R.string.login_account_hint;
import static com.trubuzz.trubuzz.test.R.string.login_failed;
import static com.trubuzz.trubuzz.test.R.string.login_password_hint;
import static com.trubuzz.trubuzz.test.R.string.ok;
import static com.trubuzz.trubuzz.test.R.string.reset_pass_code_error;
import static com.trubuzz.trubuzz.test.R.string.reset_password_mail_sent;
import static com.trubuzz.trubuzz.test.R.string.sign_up_confirm_hint;
import static com.trubuzz.trubuzz.test.R.string.sign_up_email_hint;
import static com.trubuzz.trubuzz.test.R.string.sign_up_password_hint;
import static com.trubuzz.trubuzz.test.R.string.sign_up_phone_hint;
import static com.trubuzz.trubuzz.test.R.string.sign_up_sms_auth_sent;
import static com.trubuzz.trubuzz.test.R.string.sign_up_sms_hint;
import static com.trubuzz.trubuzz.test.R.string.submit;
import static com.trubuzz.trubuzz.test.R.string.tutorial_content_1;
import static com.trubuzz.trubuzz.test.R.string.tutorial_content_2;
import static com.trubuzz.trubuzz.test.R.string.tutorial_content_3;
import static com.trubuzz.trubuzz.test.R.string.tutorial_start;
import static com.trubuzz.trubuzz.test.R.string.tutorial_title_1;
import static com.trubuzz.trubuzz.test.R.string.tutorial_title_2;
import static com.trubuzz.trubuzz.test.R.string.tutorial_title_3;
import static com.trubuzz.trubuzz.test.R.string.tutorial_title_4;
import static com.trubuzz.trubuzz.test.R.string.user_not_exist;
import static com.trubuzz.trubuzz.utils.God.getString;

/**
 * Created by king on 2016/8/23.
 */
public class LoginView {
    /************** 用户指导 ***************/
    // 第一页
    public final String tutorial_1_title_text = getString("全云化实时金融信息" ,tutorial_title_1);
    public final String tutorial_1_content_text = getString("365全天更新国际行情动态，一键捕捉17个国家60多个\n" +
            "交易所的6万多个品种投资机会。" ,tutorial_content_1);

    // 第二页
    public final String tutorial_2_title_text = getString("全球化财富管理平台" ,tutorial_title_2);
    public final String tutorial_2_content_text = getString("实现外币投资组合，布局境外资产配置，\n" +
            "依据风险偏好提供理财建议与合规财富管理产品。" ,tutorial_content_2);

    // 第三页
    public final String tutorial_3_title_text = getString("顶级国际操盘手圈子" ,tutorial_title_3);
    public final String tutorial_3_content_text = getString("订阅顶尖基金经理人、卓越对冲基金操盘手实盘投资组合，\n" +
            "不定期交流投资策略理念。" ,tutorial_content_3);

    // 第四页 .该字符串需要在使用的时候再format , 将其中的 %s替换成实际的 appName
    // 不可直接使用
    public final String tutorial_4_title_text = getString("TRUBUZZ为您开启全球投资理财梦想" ,tutorial_title_4 );

    // 用户指导父view
    private final ActivityElement tutorial_page = new ActivityElement().setId("pager");
    // 用户指导标题
    public final ActivityElement tutorial_1_title = new ActivityElement().setId("title")
            .setParent(new ActivityElement().setParent(tutorial_page));
    
    // 用户指导内容
    public final ActivityElement tutorial_1_content = new ActivityElement().setId("content")
            .setParent(new ActivityElement().setParent(tutorial_page));
    
    // 立即启程按钮
    public final ActivityElement tutorial_start_button = new ActivityElement().setId("btn_done")
            .setText(getString("立即启程" ,tutorial_start));

    /************** 登录 ***************/
    // 用户名输入框
    public final ActivityElement username_input = new ActivityElement().setId("account").setHint(getString("请输入您的邮箱或手机号" , login_account_hint));

    // 密码输入框
    public final String login_pwd_input_hint = getString("输入密码",login_password_hint);
    public final ActivityElement login_pwd_input = new ActivityElement().setId("password").setHint(login_pwd_input_hint);

    // 忘记密码按钮
    public final ActivityElement forget_pwd_button = new ActivityElement().setText(getString("忘记密码",forget_password));

    // 登入按钮
    public final ActivityElement login_button = new ActivityElement().setId("submit").setText(getString("登入" ,login));

    // 清除密码按钮
    public final ActivityElement clean_pwd_image = new ActivityElement().setSiblings(login_pwd_input, forget_pwd_button);

    /********************* 开户申请 *******************/
    // 开户申请 WebView
    public final ActivityElement broker_webView = new ActivityElement().setId("webview")
            .setAssignableClass(android.webkit.WebView.class);

    // IB开户申请
    public final String  ib_broker_title_text = "Interactive Brokers";
    public final AtomElement ib_broker_title = new AtomElement().setCss("span.broker_logo>span.ng-binding");

    // 7天未验证邮箱地址
    public final ActivityElement not_verify_7days_layout = new ActivityElement().setId("parentPanel");
    // 标题
    public final ActivityElement not_verify_7days_title = new ActivityElement().setId("alertTitle").setAncestor(not_verify_7days_layout);
    public final String not_verify_7days_title_text = getString("重新发送认验信", confirm_resend_mail);
    // 内容
    public final ActivityElement not_verify_7days_content = new ActivityElement().setId("message").setAncestor(not_verify_7days_layout);
    public final String not_verify_7days_content_text = getString("您将收到一封验证邮件，请在邮件中激活您的账号，未激活账户七天后自动失效",
            confirm_resend_mail_hint);
    // 确定
    public final ActivityElement confirm_send = new ActivityElement().setId("button1").setText(getString("确定", ok));
    // 取消
    public final ActivityElement cancel_send = new ActivityElement().setId("button2").setText(getString("取消", cancel));

    // 首次登录客户经理来信
    public final ActivityElement intercom_layout = new ActivityElement().setId("note_layout");
    // 关闭对话
    public final ActivityElement intercom_close = new ActivityElement().setId( "intercom_toolbar_close").setAncestor(intercom_layout);


    /****************************-- forget password --***************************/
    // 邮箱找回文本
    private final ActivityElement email_retrieve_text = new ActivityElement().setId("text1")
            .setText(getString("邮箱找回",forget_password_email));
    // 邮箱找回 icon
    private final ActivityElement email_image = new ActivityElement().setId("icon");
    // 邮箱找回 Tab
    public final ActivityElement mail_retrieve_tab = new ActivityElement()
            .setChildren(new ActivityElement().setChildren(email_retrieve_text, email_image)
                    .setAssignableClass(android.widget.LinearLayout.class));
//    public final ActivityElement mail_retrieve_tab = new ActivityElement().setChildren(email_retrieve_text, email_image);

    // 邮箱地址输入框
    public final ActivityElement mail_address_input = new ActivityElement().setId("email");

    // 邮箱地址输入提示
    public final String mail_input_hint_text = getString("请输入您的邮箱地址", sign_up_email_hint);

    // 邮箱提交按钮
    public final ActivityElement mail_submit_button = new ActivityElement().setId("submit")
            .setText(getString("提交", submit))
            .setSiblings(new ActivityElement().setChildren(mail_address_input));

    // 手机找回文本
    private final ActivityElement phone_retrieve_text = new ActivityElement().setId("text1")
            .setText(getString("手机找回",forget_password_phone));
    // 手机找回icon
    private final ActivityElement phone_image = new ActivityElement().setId("icon");
    // 手机找回 Tab
    public final ActivityElement phone_retrieve_tab = new ActivityElement()
            .setChildren(new ActivityElement().setChildren(phone_retrieve_text, phone_image)
                    .setAssignableClass(android.widget.LinearLayout.class));

    // 手机号输入框
    public final ActivityElement phone_number_input = new ActivityElement().setId("phone");

    // 手机号码输入提示
    public final String phone_input_hint_text = getString("请输入手机号", sign_up_phone_hint);

    // 获取短信验证码按钮
    public final ActivityElement get_sms_button = new ActivityElement().setId("btn_sms")
            .setText(getString("获取验证码" ,get_sms));

    // 短信验证码输入框
    public final ActivityElement sms_code_input = new ActivityElement().setId("sms")
            .setHint(getString("请输入短信验证码",sign_up_sms_hint));

    // 新密码输入框
    public final ActivityElement new_password_input = new ActivityElement().setId("password")
            .setHint(getString("请输入密码" ,sign_up_password_hint));

    // 新密码确认框
    public final ActivityElement password_confirm = new ActivityElement().setId("confirm")
            .setHint(getString("请再次输入密码" ,sign_up_confirm_hint));

    // 手机找回提交
    public final ActivityElement phone_submit_button = new ActivityElement().setId("submit")
            .setText(getString("提交", submit))
            .setSiblings(new ActivityElement().setChildren(phone_number_input));



    /****************************-- toast --***************************/
    public static class Toast{
        public final ToastElement password_empty_toast = new ToastElement(
                getString("密码不能为空", incorrect_password_empty));

        public final ToastElement account_format_toast = new ToastElement(
                getString("账号必须是邮箱或是手机号", incorrect_account_format));

        public final ToastElement login_failed_toast = new ToastElement(
                getString("无效的账号或密码", login_failed));

        public final ToastElement reset_password_mail_sent_toast = new ToastElement(
                getString("重设密码信件已发送到您的邮箱，请查收并重置密码。", reset_password_mail_sent));

        public final ToastElement incorrect_email_format_toast = new ToastElement(
                getString("邮箱地址格式不正确", incorrect_email_format));

        public final ToastElement user_not_exist_toast = new ToastElement(
                getString("该用户不存在", user_not_exist));

        public final ToastElement sms_code_sent_toast = new ToastElement(
                getString("短信验证码已发送", sign_up_sms_auth_sent));

        public final ToastElement incorrect_phone_format_toast = new ToastElement(
                getString("手机号格式不正确", incorrect_phone_format));

        public final ToastElement sms_code_error_toast = new ToastElement(
                getString("验证码错误", reset_pass_code_error));

        public final ToastElement incorrect_sms_format_toast = new ToastElement(
                getString("短信验证码格式不正确", incorrect_sms_format));

        public final ToastElement incorrect_password_confirm_toast = new ToastElement(
                getString("确认密码输入不一致", incorrect_password_confirm));

        public final ToastElement incorrect_password_format_toast = new ToastElement(
                getString("请输入6–16字符的大小写字母和数字组合", incorrect_password_format));
    }
}
