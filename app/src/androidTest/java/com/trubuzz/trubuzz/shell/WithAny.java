package com.trubuzz.trubuzz.shell;

import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import com.trubuzz.trubuzz.constant.Env;
import com.trubuzz.trubuzz.feature.custom.CustomMatcher;
import com.trubuzz.trubuzz.utils.God;

import org.hamcrest.Matcher;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withResourceName;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.trubuzz.trubuzz.feature.custom.CustomMatcher.hasSiblingNoSelf;
import static com.trubuzz.trubuzz.feature.custom.CustomMatcher.withIndex;
import static com.trubuzz.trubuzz.feature.custom.CustomMatcher.withUncle;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by king on 2016/9/29.
 * 做一些简单的匹配元素操作 具有局限性 .
 * 一些复杂的匹配依然需要自定义实现
 */

public class WithAny {

    /**
     * 将封装的element 转换成 matcher list , 摒弃null和空值
     * @param element
     * @return
     */
    private static List<Matcher<View>> element2matcher(Element element){
        List<Matcher<View>> ms = new ArrayList<Matcher<View>>();

        String id = element.getId();
        if(id != null && !id.isEmpty()) ms.add(withResourceName(id));

        String text = element.getText();
        if(text !=null && !text.isEmpty()) ms.add(withText(text));

        String hint = element.getHint();
        if(hint != null && !hint.isEmpty()) ms.add(withHint(hint));

        Element[] children = element.getChildren();
        if(children != null && children.length > 0)  ms.add(children(elements2matcher(children)));

        Element[] sibling = element.getSibling();
        if(sibling != null && sibling.length > 0) ms.add(sibling(elements2matcher(sibling)));

        Element parent = element.getParent();
        if(parent != null ) ms.add(withParent(all(element2matcher(parent))));

        Element uncle = element.getUncle();
        if(uncle != null) ms.add(withUncle(all(element2matcher(uncle))));

        int index = element.getIndex();
        if(index >= 0) ms.add(withIndex(index));

        Class clz = element.getAssignableClass();
        if(clz != null) ms.add(isAssignableFrom(clz));

        Matcher<View>[] matchers = element.getMatchers();
        if(matchers != null && matchers.length > 0) ms.addAll(God.array2list(matchers));

        return ms;
    }

    /**
     * 多element 的合并
     * @param elements
     * @return
     */
    private static List<List<Matcher<View>>> elements2matcher(Element... elements){
        List<List<Matcher<View>>> ms = new ArrayList<>();
        for(Element element : elements){
            ms.add(element2matcher(element));
        }
        return ms;
    }
    /**
     * 这是通过封装后的匹配方式
     * @param element 封装的元素信息
     * @return
     */
    public static ViewInteraction getViewInteraction(Element element){
        Element.ToastMsg toastMsg = element.getToastMsg();
        if(toastMsg != null){
            return getToast(toastMsg.getMsg() , toastMsg.getActivity());
        }
        return onView(all(element2matcher(element)));
    }

    /**
     * 使用自定义的匹配器获取toast
     * @param toastStr
     * @return
     */
    public static ViewInteraction getToast_d(String toastStr){
        return onView(withText(toastStr))
                .inRoot(CustomMatcher.withToast());
    }

    /**
     * 使用activity来获取 Context 的方式匹配toast ( 官方使用方法 )
     * 优点 : 清晰的错误日志, 方便调试 ( 推荐使用 )
     * @param toastStr
     * @param activities
     * @return
     */
    public static ViewInteraction getToast(String toastStr , ActivityTestRule... activities){
        if(activities.length > 0 && activities[0] != null) {
            return onView(withText(toastStr))
                    .inRoot(withDecorView(not(is(activities[0].getActivity().getWindow().getDecorView()))));
        }
        return onView(withText(toastStr))
                .inRoot(withDecorView(not(is(God.getCurrentActivity(Env.instrumentation).getWindow().getDecorView()))));
    }

    /**
     * List<Matcher<View>>中包含了单个child的匹配方式,
     * 使用all(List<Matcher<View>> list ) 提取出child的Matcher<View> .
     * 如果有多个children 则使用allOf 多次匹配即可
     * @param children
     * @return
     */
    private static Matcher<View> children(List<List<Matcher<View>>> children){
        List<Matcher<View>> ms = new ArrayList<Matcher<View>>();
        for(List<Matcher<View>> matcher : children){
            ms.add(withChild(all(matcher)));
        }
        return allOf(God.list2array(Matcher.class ,ms));
    }
    @SafeVarargs
    private static Matcher<View> children(Matcher<View> ...children){
        List<Matcher<View>> ms = new ArrayList<Matcher<View>>();
        for(Matcher<View> matcher : children){
            ms.add(withChild(matcher));
        }
        return allOf(God.list2array(Matcher.class ,ms));
    }

    /**
     * <List<Matcher<View>> 中包含了单个的sibling的匹配方式
     * @param sibling
     * @return
     */
    private static Matcher<View> sibling(List<List<Matcher<View>>> sibling){
        List<Matcher<View>> ms = new ArrayList<Matcher<View>>();
        for(List<Matcher<View>> matcher : sibling){
            ms.add(hasSiblingNoSelf(all(matcher)));
        }
        return allOf(God.list2array(Matcher.class ,ms));
    }
    @SafeVarargs
    private static Matcher<View> sibling(Matcher<View> ...sibling){
        List<Matcher<View>> ms = new ArrayList<Matcher<View>>();
        for(Matcher<View> matcher : sibling){
            ms.add(hasSiblingNoSelf(matcher));
        }
        return allOf(God.list2array(Matcher.class ,ms));
    }

    /**
     * allOf 的封装实现
     * @param matcher
     * @return
     */
    @SafeVarargs
    private static Matcher<View> all(Matcher<View>... matcher){
        return allOf(matcher);
    }

    private static Matcher<View> all(List<Matcher<View>> list){
        return allOf(God.list2array(Matcher.class ,list));
    }



    /************************************** The End ***************************************/

    /**
     * @deprecated
     * @param matcherStr
     * @return
     */
    public static Matcher<View>[] getMatchers(final String[] matcherStr){
        String[] strings = new String[3];
        Matcher<View>[] matchers = (Matcher<View>[]) Array.newInstance(Matcher.class,matcherStr.length +1);
        for(int i=0 ;i<matcherStr.length ;i++){
            if(i==0 && !(matcherStr[i] == null || matcherStr[i].isEmpty())){
                matchers[i] = withResourceName(matcherStr[i]);
                continue;
            }
            if(i==1 && !(matcherStr[i] == null || matcherStr[i].isEmpty())){
                matchers[i] = withText(matcherStr[i]);
                continue;
            }
            if(i==2 && !(matcherStr[i] == null || matcherStr[i].isEmpty())){
                matchers[i] = withHint(matcherStr[i]);
            }
        }
        matchers[matcherStr.length] = isDisplayed();
        return matchers;
    }

    /**
     * 将 {@link #getMatchers(String[])} 方法 用{@link #all(Matcher[])} 组合
     * @param matcherStr
     * @return
     * @deprecated
     */
    public static Matcher<View> getAllMatcher(final String[] matcherStr){
        return all(getMatchers(matcherStr));
    }

    /**
     * 若 T 为 String[] 则必须按顺序输入
     * array .输入顺序: { id , text ,hint } 若不具备则使用null or "" 占位 , 必须这么做
     *                   因为这只是一个简易的实现 {@link #getMatchers(String[])}
     * 多方式获取 , 待完善
     * @param desc
     * @param <T>
     * @return
     * @deprecated
     */
    public static <T> ViewInteraction getViewInteraction(T desc){
        if (desc instanceof ViewInteraction){
            return (ViewInteraction)desc;
        }
        if (desc instanceof String[]){
            return onView(getAllMatcher((String[]) desc));
        }
        if (desc instanceof Matcher){
            return onView((Matcher<View>) desc);
        }
        if (desc instanceof Matcher[]){
            return onView(allOf((Matcher[]) desc));
        }
        if (desc instanceof Element){
            return onView(all(element2matcher((Element) desc)));
        }

       return null;
    }



    /**
     * 多方式匹配 , 定格的 id text hint 和 自定义的 Matcher<View>... ms
     * 这是独立的查找方式 跟该类中的其他 getViewInteraction 方法无关
     * @param matcherStr
     * @param ms
     * @return
     * @deprecated
     */
    public static ViewInteraction getViewInteraction(String[] matcherStr,Matcher<View>... ms){
        return onView(all(createMatchers(matcherStr , ms)));
    }

    /**
     * @deprecated
     * @param matcherStr
     * @param ms
     * @return
     */
    static Matcher<View>[] createMatchers(String[] matcherStr ,Matcher<View>... ms){
        List<Matcher<View>> matcherList = new ArrayList<Matcher<View>>();
        for(int i=0 ;i<matcherStr.length ;i++){

            if(i==0 && !(matcherStr[i] == null || matcherStr[i].isEmpty())){
               matcherList.add(withResourceName(matcherStr[i]));
                continue;
            }
            if(i==1 && !(matcherStr[i] == null || matcherStr[i].isEmpty())){
                matcherList.add(withText(matcherStr[i]));
                continue;
            }
            if(i==2 && !(matcherStr[i] == null || matcherStr[i].isEmpty())){
                matcherList.add(withHint(matcherStr[i]));
            }
        }
        for(Matcher<View> matcher : ms){
            matcherList.add(matcher);
        }
        matcherList.add(isDisplayed());
        return God.list2array(Matcher.class ,matcherList);
    }


}
