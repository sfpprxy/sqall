package sqall.result;

import sqall.table.Table;

public interface Result<R> {

    R asRawType();

    Table asTable();

}
