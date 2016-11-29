package com.trubuzz.trubuzz.test;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.web.webdriver.DriverAtoms;
import android.support.test.rule.ActivityTestRule;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.elements.AQuotes;
import com.trubuzz.trubuzz.elements.ASettings;
import com.trubuzz.trubuzz.elements.AWealth;
import com.trubuzz.trubuzz.elements.Global;
import com.trubuzz.trubuzz.shell.Element;
import com.trubuzz.trubuzz.shell.Uncalibrated;
import com.trubuzz.trubuzz.shell.Var;
import com.trubuzz.trubuzz.utils.God;
import com.trubuzz.trubuzz.utils.Judge;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.isSelected;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.web.assertion.WebViewAssertions.webMatches;
import static com.trubuzz.trubuzz.constant.ToastInfo.incorrect_password_confirm_toast;
import static com.trubuzz.trubuzz.constant.ToastInfo.incorrect_trade_pwd_format_toast;
import static com.trubuzz.trubuzz.elements.AQuotes.default_stock;
import static com.trubuzz.trubuzz.feature.custom.CustomMatcher.isPassword;
import static com.trubuzz.trubuzz.feature.custom.CustomMatcher.singleSpaceContains;
import static com.trubuzz.trubuzz.feature.custom.CustomMatcher.thisString;
import static com.trubuzz.trubuzz.shell.Park.getText;
import static com.trubuzz.trubuzz.shell.Park.given;
import static com.trubuzz.trubuzz.shell.Park.webGiven;
import static com.trubuzz.trubuzz.test.R.string.chart_candle;
import static com.trubuzz.trubuzz.test.R.string.chart_line;
import static com.trubuzz.trubuzz.test.R.string.privacy_policy_terms;
import static com.trubuzz.trubuzz.test.R.string.rising_green_falling_red;
import static com.trubuzz.trubuzz.test.R.string.rising_red_falling_green;
import static com.trubuzz.trubuzz.test.R.string.tutorial_content_1;
import static com.trubuzz.trubuzz.test.R.string.tutorial_content_2;
import static com.trubuzz.trubuzz.test.R.string.tutorial_content_3;
import static com.trubuzz.trubuzz.test.R.string.tutorial_title_1;
import static com.trubuzz.trubuzz.test.R.string.tutorial_title_2;
import static com.trubuzz.trubuzz.test.R.string.tutorial_title_3;
import static com.trubuzz.trubuzz.test.R.string.tutorial_title_4;
import static com.trubuzz.trubuzz.utils.DoIt.notEmpty;
import static com.trubuzz.trubuzz.utils.God.getString;
import static com.trubuzz.trubuzz.utils.God.getStringFormat;
import static org.hamcrest.Matchers.not;

/**
 * Created by king on 16/11/18.
 */
@RunWith(JUnitParamsRunner.class)
public class SettingsTest extends BaseTest {
    private ASettings aSet = new ASettings();
    private String redUp = getString("红涨绿跌",rising_red_falling_green);
    private String greenUp = getString("绿涨红跌",rising_green_falling_red);
    private String candle = getString("蜡烛图" ,chart_candle);
    private String line = getString("曲线图" ,chart_line);
    @Rule
    public ActivityTestRule<?> matr = new ActivityTestRule(God.getFixedClass(AName.MAIN));

    private String appName = God.getAppName(this.matr.getActivity());

    private Object[] trade_password_change_data(){
        return new Object[]{
                new Object[]{ null , null , null , incorrect_trade_pwd_format_toast },      //all empty
                new Object[]{ "123" ,"1234" ,"1234" ,incorrect_trade_pwd_format_toast },    //旧密码格式不正确
                new Object[]{ "1234" ,"123" ,"123" ,incorrect_trade_pwd_format_toast },     //新密码格式不正确
                new Object[]{ "1234" ,"%%%%12" ,"%%%%12" ,incorrect_trade_pwd_format_toast },     //新密码格式不正确
                new Object[]{ "1234" ,"12345" ,"12346" ,incorrect_password_confirm_toast}   //确认密码不一致
        };
    }

    @Before
    public void intoSettings(){
        Wish.wantLogin();
        given(ASettings.left_drawer).perform(click());
        given(aSet.drawer_layout).check(matches(isDisplayed()));
        given(aSet.setting).perform(click());
    }

   // @Test
    public void notify_test() throws Exception {
        // 消息免打扰测试 ( 目前需手动配合测试 )
        if(Judge.isChecked(aSet.notify_switch)){
            given(aSet.notify_switch).perform(click())
                    .check(matches(not(isChecked())));
        }else{
            given(aSet.notify_switch).perform(click())
                    .check(matches(isChecked()));
        }
        // ...
        //具体的免打扰push 需手动测试
    }

    @Test
    @Uncalibrated
    public void rising_red_falling_green_test(){
        //涨跌显示方式 , 涉及持仓/行情/下单/投资组合等
        String rising_falling = getText(aSet.rising_falling_set);
        if(! redUp.equals(rising_falling)){     //如果不是红涨绿跌则点击一下
            given(aSet.rising_falling_set).perform(click());
        }
        given(aSet.rising_falling_set).check(matches(withText(redUp)));
        Espresso.pressBack();
        /*开始在各页面校验*/
        this.page_check("rising red falling green");
    }
    @Test
    @Uncalibrated
    public void rising_green_falling_red_test(){
        //涨跌显示方式 , 涉及持仓/行情/下单/投资组合等
        String rising_falling = getText(aSet.rising_falling_set);
        if(! greenUp.equals(rising_falling)){     //如果不是绿涨红跌则点击一下
            given(aSet.rising_falling_set).perform(click());
        }
        given(aSet.rising_falling_set).check(matches(withText(greenUp)));
        Espresso.pressBack();
        /*开始在各页面校验*/
        this.page_check("rising green falling red");
    }

    @Test
    @Uncalibrated
    public void k_chart_candle_test(){
        String chart_type = getText(aSet.k_chart_set);
        if(! candle.equals(chart_type)){
            given(aSet.k_chart_set).perform(click());
        }
        given(aSet.k_chart_set).check(matches(withText(candle)));
        Espresso.pressBack();

        given(Global.quotes_radio).perform(click());
        /*开始在各页面校验*/
        this.k_chart_check("K chart candle");
    }
    @Test
    @Uncalibrated
    public void k_chart_line_test(){
        String chart_type = getText(aSet.k_chart_set);
        if(! line.equals(chart_type)){
            given(aSet.k_chart_set).perform(click());
        }
        given(aSet.k_chart_set).check(matches(withText(line)));
        Espresso.pressBack();

        /*开始在各页面校验*/
        given(Global.quotes_radio).perform(click());
        this.k_chart_check("K chart line");
    }

    @Test
    @Parameters(method = "trade_password_change_data")
    public void trade_password_change(@Var("old_pwd")String old_pwd ,@Var("new_pwd")String new_pwd,
                                      @Var("confirm_pwd")String confirm_pwd ,@Var("expect")Element expect){

        given(aSet.change_trade_pwd_view).perform(click());

        given(aSet.trade_pwd_old).check(matches(isPassword()));
        if(notEmpty(old_pwd)) {
            given(aSet.trade_pwd_old).perform(replaceText(old_pwd));
            given(aSet.trade_pwd_old).check(matches(withText(God.getCutString(old_pwd ,6))));
        }
        given(aSet.trade_pwd_new).check(matches(isPassword()));
        if(notEmpty(new_pwd)){
            given(aSet.trade_pwd_new).perform(replaceText(new_pwd));
        }
        given(aSet.trade_pwd_confirm).check(matches(isPassword()));
        if(notEmpty(confirm_pwd)){
            given(aSet.trade_pwd_confirm).perform(replaceText(confirm_pwd));
        }

        given(aSet.trade_submit).perform(click());
        given(expect).check(matches(isDisplayed()));

    }
    @Test
    public void privacy_policy_test(){
        given(aSet.privacy_policy_view).perform(click());
        webGiven()
                .withElement(ASettings.EPrivacy.privacy_terms)
                .check(webMatches(DriverAtoms.getText() , singleSpaceContains(getString(privacy_policy_terms))));
    }

    private final String tutorial_1_title_text = getString("全云化实时金融信息" ,tutorial_title_1);
    private final String tutorial_1_content_text = getString("365全天更新国际行情动态，一键捕捉17个国家60多个\n" +
            "交易所的6万多个品种投资机会。" ,tutorial_content_1);

    private final String tutorial_2_title_text = getString("全球化财富管理平台" ,tutorial_title_2);
    private final String tutorial_2_content_text = getString("实现外币投资组合，布局境外资产配置，\n" +
            "依据风险偏好提供理财建议与合规财富管理产品。" ,tutorial_content_2);

    private final String tutorial_3_title_text = getString("顶级国际操盘手圈子" ,tutorial_title_3);
    private final String tutorial_3_content_text = getString("订阅顶尖基金经理人、卓越对冲基金操盘手实盘投资组合，\n" +
            "不定期交流投资策略理念。" ,tutorial_content_3);

    private final String tutorial_4_title_text = getStringFormat("TRUBUZZ为您开启全球投资理财梦想" ,tutorial_title_4 ,appName);

    @Test
    public void tutorial_test(){
        given(aSet.tutorial_view).perform(click());
        given(aSet.tutorial_1_title).check(matches(withText(tutorial_1_title_text)));
        given(aSet.tutorial_1_content).check(matches(withText(tutorial_1_content_text)));
        given(isRoot()).perform(swipeLeft());

        given(aSet.tutorial_1_title).check(matches(withText(tutorial_2_title_text)));
        given(aSet.tutorial_1_content).check(matches(withText(tutorial_2_content_text)));
        given(isRoot()).perform(swipeLeft());

        given(aSet.tutorial_1_title).check(matches(withText(tutorial_3_title_text)));
        given(aSet.tutorial_1_content).check(matches(withText(tutorial_3_content_text)));
        given(isRoot()).perform(swipeLeft());

        given(aSet.tutorial_1_title).check(matches(withText(tutorial_4_title_text)));
        given(aSet.tutorial_start_button).check(matches(isDisplayed()))
                .perform(click());
        assertThat(AName.SETTINGS , thisString(God.getTopActivityName(this.matr.getActivity())));
        given(isRoot()).perform(swipeLeft());
    }

    @Test
    public void version_show_test(){
        String version = getText(aSet.version_text);
        this.compareTakeScreenshot("version "+version);
    }

    private void page_check(String rising_falling){
        //持仓页面
        given(Global.assets_radio).check(true, matches(isChecked()));
        this.compareTakeScreenshot(rising_falling +" assets ");

        given(Global.quotes_radio).perform(true, click());    //进入行情
        this.compareTakeScreenshot(rising_falling +" watchlist ");

        //自选列表
        given(AQuotes.watchlist_fence).check(true, matches(isSelected()));
        this.compareTakeScreenshot(rising_falling +" watchlist");
        given(default_stock).perform(true, click());
        this.compareTakeScreenshot(rising_falling +" watchlist stock details ");
        Espresso.pressBack();

        //美股行情
        given(AQuotes.us_fence).perform(true, click());
        this.compareTakeScreenshot(rising_falling +" quotes us ");
        given(default_stock).perform(true, click());
        this.compareTakeScreenshot(rising_falling +" us stock details ");

        Espresso.pressBack();

        //港股行情
        given(AQuotes.hk_fence).perform(true, click());
        this.compareTakeScreenshot(rising_falling +" quotes hk ");
        given(default_stock).perform(true, click());
        this.compareTakeScreenshot(rising_falling +" hk stock details ");

        Espresso.pressBack();

        //沪深行情
        given(AQuotes.cn_fence).perform(true, click());
        this.compareTakeScreenshot(rising_falling +" quotes cn ");
        given(default_stock).perform(true, click());
        this.compareTakeScreenshot(rising_falling +" cn stock details ");
        Espresso.pressBack();

        //环球行情
        given(AQuotes.global_fence).perform(true, click());
        this.compareTakeScreenshot(rising_falling +" quotes global ");

        //投资组合
        given(Global.wealth_radio).perform(true, click());
        given(AWealth.portfolio_button).perform(true, click());
        this.compareTakeScreenshot(rising_falling +" portfolio list ");
        given(AWealth.default_portfolio).perform(true, click());
        this.compareTakeScreenshot(rising_falling +" portfolio details ");
    }

    private void k_chart_check(String k_type){
        //自选列表
        given(AQuotes.watchlist_fence).check(true, matches(isSelected()));
        given(default_stock).perform(true, click());
        this.compareTakeScreenshot(k_type +" watchlist stock details ");
        Espresso.pressBack();

        //美股行情
        given(AQuotes.us_fence).perform(true, click());
        given(default_stock).perform(true, click());
        this.compareTakeScreenshot(k_type +" us stock details ");

        Espresso.pressBack();

        //港股行情
        given(AQuotes.hk_fence).perform(true, click());
        given(default_stock).perform(true, click());
        this.compareTakeScreenshot(k_type +" hk stock details ");

        Espresso.pressBack();

        //沪深行情
        given(AQuotes.cn_fence).perform(true, click());
        given(default_stock).perform(true, click());
        this.compareTakeScreenshot(k_type +" cn stock details ");
        Espresso.pressBack();

        //投资组合
        given(Global.wealth_radio).perform(true, click());
        given(AWealth.portfolio_button).perform(true, click());
        given(AWealth.default_portfolio).perform(true, click());
        this.compareTakeScreenshot(k_type +" portfolio details ");
    }
}
