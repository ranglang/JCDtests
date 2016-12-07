package com.trubuzz.trubuzz.elements;

import com.trubuzz.trubuzz.shell.Element;
import com.trubuzz.trubuzz.shell.beautify.ActivityElement;

import static com.trubuzz.trubuzz.test.R.string.delete;
import static com.trubuzz.trubuzz.test.R.string.modify_name;
import static com.trubuzz.trubuzz.utils.God.getString;

/**
 * Created by king on 16/12/6.
 */

public class AWatchlist {
    public final Element add_icon = new ActivityElement().setId("add");
    public final Element default_item = new ActivityElement().setId("title").setIndex(0);

    private final Element actions = new ActivityElement().setId("action_menu");
    public final Element del = new ActivityElement().setId("delete")
            .setText(getString("删除", delete))
            .setParent(actions);
    public final Element alter = new ActivityElement().setId("modify")
            .setText(getString("修改名称", modify_name))
            .setParent(actions);

}
