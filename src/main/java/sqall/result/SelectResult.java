package sqall.result;

import sqall.table.Table;

import java.util.List;
import java.util.Map;

public class SelectResult implements Result {

    private List<String> tableColumns;
    private List<Map<String, Object>> result;

    public SelectResult(List<Map<String, Object>> result, List<String> tableColumns) {
        this.tableColumns = tableColumns;
        this.result = result;
    }

    @Override
    public List<Map<String, Object>> asRawType() {
        return result;
    }

    @Override
    public Table asTable() {
        Table table = new Table(tableColumns);
        return table.putAll(result);
    }

}
