package com.trubuzz.trubuzz.test.quote;

import com.trubuzz.trubuzz.constant.enumerate.StockType;
import com.trubuzz.trubuzz.test.common.GlobalView;
import com.trubuzz.trubuzz.idlingResource.ViewIdlingResource;
import com.trubuzz.trubuzz.shell.beautify.ActivityElement;
import com.trubuzz.trubuzz.test.trade.TradeView;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.trubuzz.trubuzz.feature.custom.handlers.ViewInteractionHandler.getText;
import static com.trubuzz.trubuzz.feature.custom.handlers.ViewInteractionHandler.getView;
import static com.trubuzz.trubuzz.shell.Park.given;
import static com.trubuzz.trubuzz.utils.DoIt.regIdlingResource;
import static com.trubuzz.trubuzz.utils.DoIt.sleep;
import static com.trubuzz.trubuzz.utils.DoIt.unRegIdlingResource;

/**
 * Created by king on 17/5/17.
 */

public class QuoteAction  {
    private String TAG = "jcd_" + QuoteAction.class.getSimpleName();
    private QuoteView qv = new QuoteView();
    private TradeView tv = new TradeView();


    /**
     * 进入行情页面
     */
    public void into_quote(){
        given(GlobalView.quotes_radio).perform(click())
                .check(matches(isChecked()));
    }

    /**
     * 随机选择股票
     * @param stockType
     */
    public void random_select_stock(StockType stockType){
        switch (stockType) {
            case US:
                given(qv.us_fence).perform(click());
        }
    }
    /**
     * 搜索股票 ( 只输入Symbol , 不管其他 )
     * @param symbol
     */
    public void search_stock(String symbol){
        given(qv.search_icon).perform(click());
        given(qv.search_input).perform(replaceText(symbol))
                .check(matches(withText(symbol)));
    }

    /**
     * 等待搜索结果
     */
    public void waiting_search_result(){
        regIdlingResource(new ViewIdlingResource(getView(qv.stocks_recycler)));
        // 这里只等待 RecyclerView 的出现, 而不确保其中会有内容
        // app 可改善为:没有搜索出项目则展示"无项目" 或"无结果"这样将保持至少会有一条数据
        given(qv.stocks_recycler).check(matches(isDisplayed()));
        unRegIdlingResource();
    }

    /**
     * 进入搜索股票的行情详情
     * @param symbol
     */
    public void into_search_stock_quote(String symbol){
        sleep(1000);
        ActivityElement searchSymbol = qv.search_symbol.setText(symbol);
        String stockName = getText(qv.search_name.setSiblings(searchSymbol));
        given(searchSymbol).perform(click()); //选择指定记录进入详情
//        given(qv.stock_name).check(matches(withText(stockName)));
    }


    /**
     * 价格预警
     */
}
