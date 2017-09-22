package sqall.table;

import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static sqall.util.U.list;
import static sqall.util.U.map;

public class TableTest {

    @Test
    public void test() throws Exception {
        List<Map<String, Object>> result = list();
        Map<String, Object> map0 = map();
        Map<String, Object> map1 = map();
        Map<String, Object> map2 = map();
        map0.put("id", "id0");
        map0.put("name", 1);
        map1.put("id", "id1");
        map1.put("name", "name1");
        map2.put("id", "id2");
        map2.put("name", "name2");
        result.addAll(list(map0, map1, map2));
        Table table = new Table(list("id", "name"));
        table.putAll(result);
        table.prettyPrint();

    }

}