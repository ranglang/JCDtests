package com.trubuzz.trubuzz.test;

import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.elements.ASettings;
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
import static com.trubuzz.trubuzz.shell.Park.getText;
import static com.trubuzz.trubuzz.shell.Park.given;
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

    @Rule
    public ActivityTestRule<?> matr = new ActivityTestRule(God.getFixedClass(AName.MAIN));

    @Before
    public void intoSettings(){
        Wish.wantLogin();
        given(ASettings.left_drawer).perform(click());
        given(aSet.drawer_layout).check(matches(isDisplayed()));
    }
    @Test
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
    public void rising_red_falling_green_test(){
        //涨跌显示方式 , 涉及持仓/行情/下单/投资组合等
        given(aSet.setting).perform(click());
        String rising_falling = getText(aSet.rising_falling_set);
        if(! redUp.equals(rising_falling)){     //如果不是红涨绿跌则点击一下
            given(aSet.rising_falling_set).perform(click());
        }
        given(aSet.rising_falling_set).check(matches(withText(redUp)));
        //开始在各页面校验
        ViewActions.swipeLeft();        //向左轻扫

    }
}
