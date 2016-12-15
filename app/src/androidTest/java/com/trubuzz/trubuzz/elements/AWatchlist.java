package com.trubuzz.trubuzz.elements;

import android.support.v7.widget.RecyclerView;

import com.trubuzz.trubuzz.shell.Element;
import com.trubuzz.trubuzz.shell.beautify.ActivityElement;
import com.trubuzz.trubuzz.shell.beautify.RecyclerViewItemElement;

import static android.R.string.cancel;
import static com.trubuzz.trubuzz.test.R.string.delete;
import static com.trubuzz.trubuzz.test.R.string.modify_name;
import static com.trubuzz.trubuzz.test.R.string.ok;
import static com.trubuzz.trubuzz.test.R.string.watchlist_add;
import static com.trubuzz.trubuzz.test.R.string.watchlist_delete;
import static com.trubuzz.trubuzz.test.R.string.watchlist_delete_message;
import static com.trubuzz.trubuzz.test.R.string.watchlist_rename;
import static com.trubuzz.trubuzz.utils.God.getString;

/**
 * Created by king on 16/12/6.
 */

public class AWatchlist {
    public final ActivityElement watchlist_recycler = new ActivityElement().setAssignableClass(RecyclerView.class)
            .setId("recycler")
            .setParent(new ActivityElement().setId("refresh"));
    public final Element add_icon = new ActivityElement().setId("add");
    public final Element default_item_text = new ActivityElement().setParent(
            new ActivityElement().setMatchers(
            new RecyclerViewItemElement(((ActivityElement) watchlist_recycler).interactionWay()).atPosition(0)));    //默认名单

    private final Element actions = new ActivityElement().setId("action_menu");
    public final Element del = new ActivityElement().setId("delete")
            .setText(getString("删除", delete))
            .setParent(actions)
            .setAssignableClass(android.widget.RadioButton.class);
    public final Element alter = new ActivityElement().setId("modify")
            .setText(getString("修改名称", modify_name))
            .setParent(actions)
            .setAssignableClass(android.widget.RadioButton.class);

    public final Element edit_input = new ActivityElement().setId("text");
    public final Element edit_ok = new ActivityElement().setId("ok").setText(getString("确定" ,ok));
    public final Element edit_cancel = new ActivityElement().setId("cancel").setText(getString("取消" ,cancel));
    public final ActivityElement watchlist_name = new ActivityElement().setId("title")
            .setAssignableClass(android.widget.TextView.class);     // 不可单独使用

    /* 添加板块 */
    public final Element add_title = new ActivityElement().setId("title").setText(getString("新增版块", watchlist_add));
    public final Element add_frame = new ActivityElement().setChildren(add_title);

    /* 修改板块 */
    public final ActivityElement alter_icon = new ActivityElement().setId("modify").setAssignableClass(android.widget.ImageButton.class);
    public final Element alter_title = new ActivityElement().setId("title").setText(getString("编辑版块", watchlist_rename));
    public final Element alter_frame = new ActivityElement().setChildren(alter_title);

    /* 删除板块 */
    public final ActivityElement del_icon = new ActivityElement().setId("delete").setAssignableClass(android.widget.ImageButton.class);
    public final ActivityElement del_title = new ActivityElement().setId("title").setText(getString("删除版块", watchlist_delete));
    public final ActivityElement del_content = new ActivityElement().setId("content");
    public final ActivityElement del_reminder = new ActivityElement().setText(getString("您确定要删除此版块吗?",watchlist_delete_message));

}
