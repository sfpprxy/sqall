package sqall.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class U {

    public static String fixedLenStr(String string, int length) {
        return fixedLenStr(string, length, false);
    }

    public static String fixedLenStr(String string, int length, boolean leftPad) {
        String pad;
        if (leftPad) {
            pad = "%1$-";
        } else {
            pad = "%1$";
        }
        return String.format(pad + length + "s", string);
    }

    public static <K, V> Map<K, V> zipToMap(List<K> keys, List<V> values) {
        return IntStream.range(0, keys.size())
                .boxed()
                .collect(Collectors.toMap(keys::get, values::get));
    }

    public static <K, V> Map<K, V> map() {
        return new HashMap<K, V>();
    }

    public static <K, V> Map<K, V> map(K key, V vaule) {
        HashMap<K, V> map = new HashMap<>();
        map.put(key, vaule);
        return map;
    }

    public static <E> List<E> list() {
        return new ArrayList<E>();
    }

    public static <E> List<E> list(E e) {
        return Collections.singletonList(e);
    }

    public static <E> List<E> list(E... elements) {
        List<E> list = list();
        Collections.addAll(list, elements);
        return list;
    }

    public static boolean and(boolean left, boolean right) {
        return left && right;
    }

    public static boolean and(boolean... conds) {
        for (boolean cond : conds) {
            if (!cond) {
                return false;
            }
        }
        return true;
    }

    public static boolean or(boolean left, boolean right) {
        return left || right;
    }

    public static boolean or(boolean... conds) {
        for (boolean cond : conds) {
            if (cond) {
                return true;
            }
        }
        return false;
    }

    public static boolean match(Object a, Object b) {
        return Objects.equals(a, b);
    }

    public static void printl(Object o) {
        System.out.println(o);
    }

    public static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println(fixedLenStr("asd", 5));
        System.out.println(fixedLenStr("asd", 5, true));
    }

}
