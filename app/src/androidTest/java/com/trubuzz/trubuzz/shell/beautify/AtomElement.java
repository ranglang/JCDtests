package com.trubuzz.trubuzz.shell.beautify;

import android.support.test.espresso.web.model.Atom;

import com.trubuzz.trubuzz.shell.Element;

import static android.support.test.espresso.web.webdriver.DriverAtoms.findElement;
import static android.support.test.espresso.web.webdriver.Locator.CLASS_NAME;
import static android.support.test.espresso.web.webdriver.Locator.CSS_SELECTOR;
import static android.support.test.espresso.web.webdriver.Locator.ID;
import static android.support.test.espresso.web.webdriver.Locator.LINK_TEXT;
import static android.support.test.espresso.web.webdriver.Locator.NAME;
import static android.support.test.espresso.web.webdriver.Locator.PARTIAL_LINK_TEXT;
import static android.support.test.espresso.web.webdriver.Locator.TAG_NAME;
import static android.support.test.espresso.web.webdriver.Locator.XPATH;
import static com.trubuzz.trubuzz.utils.DoIt.notEmpty;

/**
 * Created by king on 16/11/8.
 */

public class AtomElement implements Element<Atom> {

    private String id;
    private String css;
    private String className;
    private String linkText;
    private String name;
    private String partialLinkText;
    private String tagName;
    private String xpath;


    @Override
    public Atom interactionWay() {
        if(notEmpty(id)) return findElement(ID , id);
        if(notEmpty(css)) return findElement(CSS_SELECTOR ,css);
        if(notEmpty(className)) return findElement(CLASS_NAME ,className);
        if(notEmpty(linkText)) return findElement(LINK_TEXT , linkText);
        if(notEmpty(name)) return findElement(NAME ,name);
        if(notEmpty(partialLinkText)) return findElement(PARTIAL_LINK_TEXT ,partialLinkText);
        if(notEmpty(tagName)) return findElement(TAG_NAME ,tagName);
        if(notEmpty(xpath)) return findElement(XPATH ,xpath);

        return null;
    }
}
