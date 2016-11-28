package com.trubuzz.trubuzz.test;

import android.support.test.espresso.Espresso;
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

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.trubuzz.trubuzz.elements.AQuotes.default_stock;
import static com.trubuzz.trubuzz.shell.Park.getText;
import static com.trubuzz.trubuzz.shell.Park.given;
import static com.trubuzz.trubuzz.test.R.string.chart_candle;
import static com.trubuzz.trubuzz.test.R.string.chart_line;
import static com.trubuzz.trubuzz.test.R.string.rising_green_falling_red;
import static com.trubuzz.trubuzz.test.R.string.rising_red_falling_green;
import static com.trubuzz.trubuzz.utils.God.getString;
import static org.hamcrest.Matchers.not;

/**
 * Created by king on 16/11/18.
 */

public class SettingsTest extends BaseTest {
    private ASettings aSet = new ASettings();
    private String redUp = getString("红涨绿跌",rising_red_falling_green);
    private String greenUp = getString("绿涨红跌",rising_green_falling_red);
    private String candle = getString("蜡烛图" ,chart_candle);
    private String line = getString("曲线图" ,chart_line);

    @Rule
    public ActivityTestRule<?> matr = new ActivityTestRule(God.getFixedClass(AName.MAIN));

    private Object[] trade_password_change_data(){
        return new Object[]{
                new Object[]{},
        };
    }

    @Before
    public void intoSettings(){
        Wish.wantLogin();
        given(ASettings.left_drawer).perform(click());
        given(aSet.drawer_layout).check(matches(isDisplayed()));
    }

   // @Test
    public void notify_test() throws Exception {
        given(aSet.setting).perform(click());
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
        given(aSet.setting).perform(click());
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
        given(aSet.setting).perform(click());
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
        given(aSet.setting).perform(click());
        String chart_type = getText(aSet.k_chart_set);
        if(! candle.equals(chart_type)){
            given(aSet.k_chart_set).perform(click());
        }
        given(aSet.k_chart_set).check(matches(withText(candle)));
        Espresso.pressBack();

        /*开始在各页面校验*/
        this.k_chart_check("K chart candle");
    }
    @Test
    @Uncalibrated
    public void k_chart_line_test(){
        given(aSet.setting).perform(click());
        String chart_type = getText(aSet.k_chart_set);
        if(! line.equals(chart_type)){
            given(aSet.k_chart_set).perform(click());
        }
        given(aSet.k_chart_set).check(matches(withText(line)));
        Espresso.pressBack();

        /*开始在各页面校验*/
        this.k_chart_check("K chart line");
    }

    @Test
    public void trade_password_change(@Var("old_pwd")String old_pwd ,@Var("new_pwd")String new_pwd,
                                      @Var("confirm_pwd")String confirm_pwd ,@Var("expect")Element expect){

    }




    private void page_check(String rising_falling){
        //持仓页面
        given(Global.assets_radio).check(matches(isChecked()));
        this.compareTakeScreenshot(rising_falling +" assets ");

        given(Global.quotes_radio).perform(click());    //进入行情
        this.compareTakeScreenshot(rising_falling +" watchlist ");

        //自选列表
        given(AQuotes.watchlist_fence).check(matches(isChecked()));
        this.compareTakeScreenshot(rising_falling +" watchlist");
        given(default_stock).perform(click());
        this.compareTakeScreenshot(rising_falling +" watchlist stock details ");
        Espresso.pressBack();

        //美股行情
        given(AQuotes.us_fence).perform(click());
        this.compareTakeScreenshot(rising_falling +" quotes us ");
        given(default_stock).perform(click());
        this.compareTakeScreenshot(rising_falling +" us stock details ");

        Espresso.pressBack();

        //港股行情
        given(AQuotes.hk_fence).perform(click());
        this.compareTakeScreenshot(rising_falling +" quotes hk ");
        given(default_stock).perform(click());
        this.compareTakeScreenshot(rising_falling +" hk stock details ");

        Espresso.pressBack();

        //沪深行情
        given(AQuotes.cn_fence).perform(click());
        this.compareTakeScreenshot(rising_falling +" quotes cn ");
        given(default_stock).perform(click());
        this.compareTakeScreenshot(rising_falling +" cn stock details ");
        Espresso.pressBack();

        //环球行情
        given(AQuotes.global_fence).perform(click());
        this.compareTakeScreenshot(rising_falling +" quotes global ");

        //投资组合
        given(Global.wealth_radio).perform(click());
        given(AWealth.portfolio_button).perform(click());
        this.compareTakeScreenshot(rising_falling +" portfolio list ");
        given(AWealth.default_portfolio).perform(click());
        this.compareTakeScreenshot(rising_falling +" portfolio details ");
    }

    private void k_chart_check(String k_type){
        //自选列表
        given(AQuotes.watchlist_fence).check(matches(isChecked()));
        given(default_stock).perform(click());
        this.compareTakeScreenshot(k_type +" watchlist stock details ");
        Espresso.pressBack();

        //美股行情
        given(AQuotes.us_fence).perform(click());
        given(default_stock).perform(click());
        this.compareTakeScreenshot(k_type +" us stock details ");

        Espresso.pressBack();

        //港股行情
        given(AQuotes.hk_fence).perform(click());
        given(default_stock).perform(click());
        this.compareTakeScreenshot(k_type +" hk stock details ");

        Espresso.pressBack();

        //沪深行情
        given(AQuotes.cn_fence).perform(click());
        given(default_stock).perform(click());
        this.compareTakeScreenshot(k_type +" cn stock details ");
        Espresso.pressBack();

        //投资组合
        given(Global.wealth_radio).perform(click());
        given(AWealth.portfolio_button).perform(click());
        given(AWealth.default_portfolio).perform(click());
        this.compareTakeScreenshot(k_type +" portfolio details ");
    }
}
