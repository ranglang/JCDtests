package com.trubuzz.trubuzz.test.login;


import com.trubuzz.trubuzz.shell.beautify.ActivityElement;

import static com.trubuzz.trubuzz.constant.Env.appName;
import static com.trubuzz.trubuzz.test.R.string.forget_password;
import static com.trubuzz.trubuzz.test.R.string.input_password;
import static com.trubuzz.trubuzz.test.R.string.login;
import static com.trubuzz.trubuzz.test.R.string.sign_up_description;
import static com.trubuzz.trubuzz.test.R.string.tutorial_content_1;
import static com.trubuzz.trubuzz.test.R.string.tutorial_content_2;
import static com.trubuzz.trubuzz.test.R.string.tutorial_content_3;
import static com.trubuzz.trubuzz.test.R.string.tutorial_start;
import static com.trubuzz.trubuzz.test.R.string.tutorial_title_1;
import static com.trubuzz.trubuzz.test.R.string.tutorial_title_2;
import static com.trubuzz.trubuzz.test.R.string.tutorial_title_3;
import static com.trubuzz.trubuzz.test.R.string.tutorial_title_4;
import static com.trubuzz.trubuzz.utils.God.getString;
import static com.trubuzz.trubuzz.utils.God.getStringFormat;

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

    // 第四页
    public final String tutorial_4_title_text = getStringFormat("TRUBUZZ为您开启全球投资理财梦想" ,tutorial_title_4 ,appName);

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

    private static final int login_account_hint = com.trubuzz.trubuzz.test.R.string.login_account_hint;
    /************** 登录 ***************/
    // 用户名输入框
    public static final ActivityElement account_input = new ActivityElement().setId("account").setHint(getString("请输入您的邮箱或手机号" , login_account_hint));

    // 密码输入框
    public final ActivityElement pwd_input = new ActivityElement().setId("password").setHint(getString("输入密码",input_password));

    // 忘记密码按钮
    public static final ActivityElement forget_pwd_button = new ActivityElement().setText(getString("忘记密码",forget_password));

    // 快速注册链接
    public static final ActivityElement sign_up_link = new ActivityElement().setId("signup")
            .setText(getString("请邮箱或手机快速注册登录", sign_up_description));

    // 登入按钮
    public final ActivityElement login_button = new ActivityElement().setId("submit").setText(getString("登入" ,login));

    // 清除密码按钮
    public final ActivityElement clean_pwd_image = new ActivityElement().setSibling(pwd_input , forget_pwd_button);

}
