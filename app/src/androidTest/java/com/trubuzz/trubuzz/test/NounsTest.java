package com.trubuzz.trubuzz.test;

import android.support.test.espresso.web.webdriver.Locator;
import android.support.test.rule.ActivityTestRule;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.constant.Nouns;
import com.trubuzz.trubuzz.elements.AAsset;
import com.trubuzz.trubuzz.idlingResource.SomeActivityIdlingResource;
import com.trubuzz.trubuzz.utils.DoIt;
import com.trubuzz.trubuzz.utils.God;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.web.sugar.Web.onWebView;
import static android.support.test.espresso.web.webdriver.DriverAtoms.findElement;
import static android.support.test.espresso.web.webdriver.DriverAtoms.getText;
import static com.trubuzz.trubuzz.constant.AName.NOUNS;
import static com.trubuzz.trubuzz.constant.Nouns.*;
import static com.trubuzz.trubuzz.elements.AAsset.Details.*;
import static com.trubuzz.trubuzz.elements.AAsset.*;
import static com.trubuzz.trubuzz.elements.WithAny.getAllMatcher;
import static com.trubuzz.trubuzz.feature.AdvancedViewInteraction.perform;
import static com.trubuzz.trubuzz.feature.custom.CustomViewAction.pause;
import static com.trubuzz.trubuzz.feature.custom.CustomWebAssert.customWebMatches;
import static com.trubuzz.trubuzz.shell.Park.given;
import static org.hamcrest.CoreMatchers.containsString;

/**
 * Created by king on 2016/10/19.
 */
@RunWith(JUnitParamsRunner.class)
public class NounsTest extends BaseTest {

    private final Locator locator = Locator.CSS_SELECTOR;
    private final String elementFinder = "#main>.footer.base.container-fluid";
    @Rule
    public ActivityTestRule<?> matr = new ActivityTestRule(God.getFixedClass(AName.MAIN));

    private Object[] nounsData(){
        return new Object[]{
            new Object[]{ ID_TEXT_buying_power_d, buying_power_n ,true , "购买力"},
            new Object[]{ ID_TEXT_net_liquidation, net_liquidation_n ,true , "净清算值"},
            new Object[]{ ID_TEXT_accrued_cash , accrued_cash_n ,true , "净累计利息"},
            new Object[]{ ID_TEXT_equity_with_loan_value , equity_with_loan_value_n ,true , "有贷款价值的资产"},
            new Object[]{ ID_TEXT_gross_position_value, gross_position_value_n , true, "股票+期权"},
            new Object[]{ ID_TEXT_init_margin_req, init_margin_req_n , true, "初始准备金"},
            new Object[]{ ID_TEXT_maint_margin_req, maint_margin_req_n , true, "维持准备金"},
            new Object[]{ ID_TEXT_available_funds_d, available_funds_n , true , "可用资金"},
            new Object[]{ ID_TEXT_excess_liquidity, excess_liquidity_n , true, "剩余流动性"},
            new Object[]{ ID_TEXT_total_amount, total_amount_n , false , "持仓总额"},
            new Object[]{ ID_TEXT_Available_Funds, available_funds_n , false , "可用资金"},
            new Object[]{ ID_TEXT_total_portfolio, total_pnl_n , false , "总收益"},
            new Object[]{ ID_TEXT_today_portfolio, today_portfolio_n , false , "当日收益"},
            new Object[]{ ID_TEXT_buying_power, buying_power_n , false , "购买力"},
        };
    }

    @Test
    @Parameters( method = "nounsData")
    public void nouns(String[] viewInteractionDesc , Nouns except , boolean isInDetails , String desc){

        putData(new HashMap<String,Object>(){{
            put("except" ,except);
            put("isInDetails" , isInDetails);
            put("noun_desc", desc);
        }});

        if(isInDetails){                                    //如果在詳情裏, 則先点击"净资产"进入详情
            perform(AAsset.ID_TEXT_net_worth , click());
        }
        perform(viewInteractionDesc , click());                              //点击名词 , 进入解释

        DoIt.regIdlingResource(new SomeActivityIdlingResource(NOUNS ,matr.getActivity() , true));

        onWebView()
            .withElement(findElement(locator , elementFinder))
            .perform(getText())
            .check(customWebMatches(getText(), containsString(except.getValue())));         //对比解释结果

        DoIt.unRegIdlingResource();

    }

    @Test
    public void otherNouns(){
        //检查账号类型为保证金账户

        given(AAsset.ID_TEXT_net_worth).perform(pause(1000),click());        //等待一秒再点击
        given(ID_TEXT_account_type)
                .check(matches(withText(check_account_type)))               //检查账户类型字段
                .check(matches(hasSibling(getAllMatcher(ID_TEXT_individual))));     //检查含有指定子元素

    }
}
