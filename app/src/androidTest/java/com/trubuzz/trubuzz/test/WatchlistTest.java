package com.trubuzz.trubuzz.test;

import android.support.test.rule.ActivityTestRule;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.elements.AQuotes;
import com.trubuzz.trubuzz.elements.AWatchlist;
import com.trubuzz.trubuzz.elements.Global;
import com.trubuzz.trubuzz.utils.God;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.trubuzz.trubuzz.shell.Park.getText;
import static com.trubuzz.trubuzz.shell.Park.given;

/**
 * Created by king on 16/12/6.
 */

public class WatchlistTest extends BaseTest{
    private AWatchlist aw = new AWatchlist();
    @Rule
    public ActivityTestRule<?> matr = new ActivityTestRule(God.getFixedClass(AName.MAIN));

    @Before
    public void into_quotes(){
        Wish.wantLogin();
        given(Global.quotes_radio).perform(click());
    }

    @Test
    public void default_show_test(){
        String watchlist = getText(AQuotes.watchlist_default_item);
        given(AQuotes.edit_watchlist).perform(click());

        Wish.allDisplayed(
                Global.back_up,
                aw.add_icon,
                aw.del ,
                aw.alter
        );
        given(aw.default_item).check(matches(isDisplayed()))
                .check(matches(withText(watchlist)));
    }

    @Test
    /**
     * 删除列表 , 1.随机删除 , 2.全部删除 3.没有可删除的项则调用添加列表的方法 .4.默认列表不可删除
     */
    public void delete_watchlist_item(){

    }

    /**
     * 修改, 1.正常修改 2.重名修改 3.大小写修改 4.默认列表不可修改
     */
    public void alter_watchlist_item(){

    }

    /**
     * 添加 ,1.正常添加 2.重名添加 3.各种字符
     */
    public void add_watchlist_item(){

    }

    /**
     * 思考, 已添加价格预警的自选  , 删除列表
     */
}
