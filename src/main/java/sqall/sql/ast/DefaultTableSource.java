package sqall.sql.ast;

import sqall.sql.parser.Token;

public class DefaultTableSource implements TableSource {

    private String tableSource;

    public DefaultTableSource(Token tableSource) {
        this.tableSource = tableSource.value();
    }

    public String getTableSource() {
        return tableSource;
    }

    public void setTableSource(String tableSource) {
        this.tableSource = tableSource;
    }

}
