package com.trubuzz.trubuzz.test;

import android.support.test.rule.ActivityTestRule;

import com.trubuzz.trubuzz.constant.AName;
import com.trubuzz.trubuzz.constant.Nouns;
import com.trubuzz.trubuzz.elements.AAsset;
import com.trubuzz.trubuzz.idlingResource.ActivityIdlingResource;
import com.trubuzz.trubuzz.shell.Element;
import com.trubuzz.trubuzz.shell.Var;
import com.trubuzz.trubuzz.utils.DoIt;
import com.trubuzz.trubuzz.utils.God;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.web.webdriver.DriverAtoms.getText;
import static com.trubuzz.trubuzz.constant.AName.MAIN;
import static com.trubuzz.trubuzz.constant.AName.NOUNS;
import static com.trubuzz.trubuzz.constant.Nouns.accrued_cash_n;
import static com.trubuzz.trubuzz.constant.Nouns.available_funds_n;
import static com.trubuzz.trubuzz.constant.Nouns.buying_power_n;
import static com.trubuzz.trubuzz.constant.Nouns.equity_with_loan_value_n;
import static com.trubuzz.trubuzz.constant.Nouns.excess_liquidity_n;
import static com.trubuzz.trubuzz.constant.Nouns.gross_position_value_n;
import static com.trubuzz.trubuzz.constant.Nouns.init_margin_req_n;
import static com.trubuzz.trubuzz.constant.Nouns.maint_margin_req_n;
import static com.trubuzz.trubuzz.constant.Nouns.net_liquidation_n;
import static com.trubuzz.trubuzz.constant.Nouns.today_portfolio_n;
import static com.trubuzz.trubuzz.constant.Nouns.total_amount_n;
import static com.trubuzz.trubuzz.constant.Nouns.total_pnl_n;
import static com.trubuzz.trubuzz.elements.AAsset.available_funds_view;
import static com.trubuzz.trubuzz.elements.AAsset.buying_power_view;
import static com.trubuzz.trubuzz.elements.AAsset.today_portfolio_view;
import static com.trubuzz.trubuzz.elements.AAsset.total_amount_view;
import static com.trubuzz.trubuzz.elements.AAsset.total_portfolio_view;
import static com.trubuzz.trubuzz.feature.custom.assertors.CustomWebAssert.customWebMatches;
import static com.trubuzz.trubuzz.shell.Park.given;
import static com.trubuzz.trubuzz.shell.Park.webGiven;
import static org.hamcrest.CoreMatchers.containsString;

/**
 * Created by king on 2016/10/19.
 * at 11/18 真机测试跑通 .
 */
@RunWith(JUnitParamsRunner.class)
public class NounsTest extends BaseTest {

    private AAsset.Details details = new AAsset.Details();
    private Element explanation_noun_path = AAsset.ENouns.explanation_noun;
    @Rule
    public ActivityTestRule<?> matr = new ActivityTestRule(God.getFixedClass(AName.MAIN));

    private Object[] nounsData(){
        return new Object[]{
            new Object[]{details.buying_power_d_view, buying_power_n ,true },		                //购买力
            new Object[]{details.net_liquidation_view, net_liquidation_n ,true },		            //净清算值
            new Object[]{details.accrued_cash_view, accrued_cash_n ,true },		                //净累计利息
            new Object[]{details.equity_with_loan_value_view, equity_with_loan_value_n ,true },	//有贷款价值的资产
            new Object[]{details.gross_position_value_view, gross_position_value_n , true},		//股票+期权
            new Object[]{details.init_margin_req_view, init_margin_req_n , true},		            //初始准备金
            new Object[]{details.maint_margin_req_view, maint_margin_req_n , true},		        //维持准备金
            new Object[]{details.available_funds_d_view, available_funds_n , true },		        //可用资金
            new Object[]{details.excess_liquidity_view, excess_liquidity_n , true},		        //剩余流动性
            new Object[]{total_amount_view, total_amount_n , false },		                        //持仓总额
            new Object[]{available_funds_view, available_funds_n , false },		                //可用资金
            new Object[]{total_portfolio_view, total_pnl_n , false },		                        //总收益
            new Object[]{today_portfolio_view, today_portfolio_n , false },		                //当日收益
            new Object[]{buying_power_view, buying_power_n , false },		                        //购买力
        };
    }

//    @BeforeClass
//    public static void checkLogin(){
//        Wish.wantBrokerLogin();
//    }
    @AfterClass
    public static void logout(){
        Wish.logout();
    }

    @Test
    @Parameters( method = "nounsData")
    public void nouns(@Var("noun") Element noun , @Var("expect") Nouns expect ,
                      @Var("isInDetails") boolean isInDetails ){
        Wish.wantBrokerLogin();
        DoIt.regIdlingResource(new ActivityIdlingResource(MAIN ,matr.getActivity() , true));
        if(isInDetails){                                    //如果在詳情裏, 則先点击"净资产"进入详情
            given(AAsset.net_worth_view).perform(click());
        }
        DoIt.unRegIdlingResource();
        given(noun).perform(click());                              //点击名词 , 进入解释

        DoIt.regIdlingResource(new ActivityIdlingResource(NOUNS ,matr.getActivity() , true));

        webGiven()
            .withElement(explanation_noun_path)
            .perform(getText())
            .check(customWebMatches(getText(), containsString(expect.getValue())));         //对比解释结果;

        DoIt.unRegIdlingResource();

    }

//    @Test
    public void otherNouns(){
        //检查账号类型为保证金账户

//        given(AAsset.net_worth_view).perform(pause(1000),click());        //等待一秒再点击
//        given(details.account_type_view)
//                .check(matches(withText(details.check_account_type)))               //检查账户类型字段
//                .check(matches(hasSibling(getAllMatcher(details.individual_text))));     //检查含有指定子元素

    }
}
