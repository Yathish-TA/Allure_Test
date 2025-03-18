package mobileapp.mobileapppageobjects;


import java.util.LinkedHashMap;
import java.util.Map;

public class MobileTestContext {

    private Map<String, Object> valueMap = new LinkedHashMap<String, Object>();

    public static ThreadLocal<MobileTestContext> threadLocal =new ThreadLocal<MobileTestContext>(){
        protected MobileTestContext initialValue() {

            return new MobileTestContext();
        }
    };


    public static MobileTestContext get() {
        return threadLocal.get();
    }

    public void setValueToMap(String key, Object value) {
        valueMap.put(key, value);
    }

    public Object getValueFromMap(String key) {
        return valueMap.get(key);
    }

}
