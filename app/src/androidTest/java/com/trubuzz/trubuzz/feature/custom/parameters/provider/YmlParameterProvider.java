package com.trubuzz.trubuzz.feature.custom.parameters.provider;

import com.trubuzz.trubuzz.constant.Conf;
import com.trubuzz.trubuzz.constant.Env;
import com.trubuzz.trubuzz.feature.custom.parameters.YmlParameter;

import org.junit.runners.model.FrameworkMethod;

import java.io.File;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import junitparams.custom.ParametersProvider;

import static com.trubuzz.trubuzz.utils.DoIt.list2array;
import static com.trubuzz.trubuzz.utils.FileRw.readYamlFile;

/**
 * Created by king on 2017/7/14.
 */

public class YmlParameterProvider implements ParametersProvider<YmlParameter> {
    private YmlParameter ymlParameter;
    private Class<?>[] parameterTypes;
    private String testMethodName;
    private String testClassName;
    private Object cache;
    private final String class_key = "class";
    private final String method_key = "method";

    @Override
    public void initialize(YmlParameter ymlParameter, FrameworkMethod frameworkMethod) {
        this.ymlParameter = ymlParameter;
        this.parameterTypes = frameworkMethod.getMethod().getParameterTypes();
        this.testMethodName = frameworkMethod.getName();

        Class<?> declaringClass = frameworkMethod.getDeclaringClass();
        this.testClassName = declaringClass.getSimpleName();

        String cacheStore = ymlParameter.cacheStore();
        String yaml = ymlParameter.value();
        if (!"".equals(cacheStore)) {
            try {
                Field field = declaringClass.getDeclaredField(cacheStore);
                field.setAccessible(true);
                Object o = field.get(null);
                if (o == null) {
                    this.cache = getYamlObject(yaml);
                    field.set(null, this.cache);
                } else {
                    this.cache = o;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            this.cache = getYamlObject(yaml);
        }


    }

    @Override
    public Object[] getParameters() {
        List params = getMethodParams();
        if (params == null) {
            throw new RuntimeException("Empty parameters .");
        }
        List res = new ArrayList();
        for (Object po : params) {
            List data = null;
            // 将每行数据封装成 List
            if (po instanceof List) {
                data = (List) po;
            } else {
                data = new ArrayList();
                data.add(po);
            }

            res.add(createParams(data ,parameterTypes));
        }
        return list2array(res);
    }

    public Object[] createParams(List datas ,Class[] types){
        int dataSize = datas.size();
        int typeLen = types.length;
        // 判断参数个数与参数类型个数是否一致
        if (dataSize != typeLen) {
            throw new RuntimeException(String.format("参数个数(%s)与参数类型个数(%s)不匹配",
                    dataSize, typeLen));
        }
        Object[] o = new Object[typeLen];
        for(int i=0;i<typeLen; i++) {
            String data = (String) datas.get(i);

            if (types[i] == Integer.class) {
                o[i] = Integer.valueOf(data);
                continue;
            }
            if (types[i] == String.class) {
                o[i] = datas.get(i);
                continue;
            }
            if (types[i] == Float.class) {
                o[i] = Float.valueOf(data);
                continue;
            }
            if (types[i] == Double.class) {
                o[i] = Double.valueOf(data);
                continue;
            }
            if (types[i] == Long.class) {
                o[i] = Long.valueOf(data);
                continue;
            }
            if (types[i] == BigDecimal.class) {
                o[i] = new BigDecimal(data);
                continue;
            }
            if (types[i].isEnum()) {
                o[i] = Enum.valueOf(types[i], data);
                continue;
            }
            o[i] = data;
            System.out.println("jcd_没有合适的类型 : "+ types[i]);
        }
        return o;
    }
    /**
     * 获取当前方法在 yml 文件中定义的参数
     * @return
     */
    private List getMethodParams(){
        if (this.cache == null) {
            throw new RuntimeException("Empty parameters .");
        }
        List datas = (List) this.cache;
        for (Object o : datas) {
            Map map = (Map)o;
            if (testMethodName.equals(map.get("name"))) {
                return (List) map.get("data");
            }
        }
        return null;
    }
    /**
     * 捕获 yml 文档中当前 test class 的所有 method 的数据
     *      每个文档必须包含 "class" , "method" 这两个key
     * @param ymlFileName
     * @return method 这个 key 的value
     *      一般为 ArrayList
     */
    private Object getYamlObject(String ymlFileName){
        String dir = Conf.condition.dir();
        String path = Env.test_data_root_dir + File.separator + dir + File.separator + ymlFileName;
        List list = readYamlFile(path);
        for (Object obj : list) {
            Map map = (Map) obj;
            Object aClass = map.get(class_key);
            if (testClassName.equals(aClass)) {
                return map.get(method_key);
            }
        }
        return null;
    }
}
