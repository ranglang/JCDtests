package com.trubuzz.trubuzz.elements;

import android.widget.TextView;

import com.trubuzz.trubuzz.shell.Element;
import com.trubuzz.trubuzz.shell.beautify.ActivityElement;

import static com.trubuzz.trubuzz.R.string.abc_action_bar_up_description;
import static com.trubuzz.trubuzz.test.R.string.investment_risk_assessment;
import static com.trubuzz.trubuzz.test.R.string.portfolio_set;
import static com.trubuzz.trubuzz.test.R.string.subscribe;
import static com.trubuzz.trubuzz.test.R.string.subscribed;
import static com.trubuzz.trubuzz.test.R.string.wealth_management;
import static com.trubuzz.trubuzz.utils.God.getString;

/**
 * Created by king on 16/11/22.
 */

public class AWealth {


    public final Element portfolio_button = new ActivityElement().setId("button1")
            .setText(getString("Portfolio" ,portfolio_set));    //投资组合按钮

    public final Element default_portfolio = new ActivityElement().setIndex(1).
            setChildren(
                new ActivityElement().setId("avatar")
//                new ActivityElement().setChildren(subscribe_button)
            ); //一项投资组合

    public final ActivityElement banner = new ActivityElement().setId("image");     //轮播图
    public final ActivityElement wealth = new ActivityElement().setId("button2").setText(getString("财富管理", wealth_management));
    public final ActivityElement risk = new ActivityElement().setId("button3")
            .setChildren(new ActivityElement().setText(getString("投资风险测评", investment_risk_assessment)));

    /****
     * 投资组合详情
     ****/
    public final ActivityElement back_up = new ActivityElement().setContent_desc(getString("转到上一层级", abc_action_bar_up_description))
            .setParent(new ActivityElement().setId("action_bar"));
    public final ActivityElement recycler = new ActivityElement().setId("recycler");
    public final ActivityElement subscribe_status = new ActivityElement().setId("subscribe");   //订阅状态（ 订阅 / 已订阅 )
    public final ActivityElement subscribe_button = new ActivityElement().setId("subscribe").setText(getString("订阅",subscribe));
    public final ActivityElement subscribed_button = new ActivityElement().setId("subscribe").setText(getString("已订阅",subscribed));
    public final ActivityElement title_text = new ActivityElement().setId("title").setAssignableClass(TextView.class);  //标题
    public final ActivityElement total_assets_value = new ActivityElement().setId("total_assets");  //总收益
    public final ActivityElement total_gains_value = new ActivityElement().setAssignableClass(TextView.class)
            .setParent(new ActivityElement().setId("total_pnl")); //总收益






}
