package sqall.result;

import sqall.table.Table;

import static sqall.util.U.list;
import static sqall.util.U.map;

public class InsertResult implements Result {

    private Integer success;

    public InsertResult(int success) {
        this.success = success;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    @Override
    public Integer asRawType() {
        return success;
    }

    @Override
    public Table asTable() {
        String col = "INSERT ROWS";
        Table table = new Table(list(col));
        return table.putAll(list(
                map(col, success.toString())
        ));
    }
}
