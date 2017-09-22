package sqall.util;

import java.util.Objects;

public class Union {

    private Class aType;
    private Class bType;
    private Object obj;

    public Union(Class aType, Class bType) {
        this.aType = aType;
        this.bType = bType;
    }

    public Union set(Object obj) {
        boolean typeA = isFirstType(obj);
        boolean typeB = isSecondType(obj);
        if (!typeA && !typeB) {
            throw new RuntimeException("setting invalid type");
        }
        this.obj = obj;
        return this;
    }

    public boolean isFirstType() {
        return isFirstType(obj);
    }

    public boolean isSecondType() {
        return isSecondType(obj);
    }

    private boolean isFirstType(Object obj) {
        Class<?> type = obj.getClass();
        return Objects.equals(type, aType);
    }

    private boolean isSecondType(Object obj) {
        Class<?> type = obj.getClass();
        return Objects.equals(type, bType);
    }

}
