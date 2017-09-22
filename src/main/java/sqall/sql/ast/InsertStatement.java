package sqall.sql.ast;

import sqall.sql.parser.Token;
import sqall.sql.visitor.Visitor;

import java.util.List;

import static sqall.util.U.list;

public class InsertStatement implements Statement {

    private TableSource into;
    private List<String> columns = list();
    private List<Object> values = list();

    public TableSource getInto() {
        return into;
    }

    public void setInto(TableSource into) {
        this.into = into;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void addColumn(Token identifier) {
        columns.add(identifier.value());
    }

    public void addValue(Token identifier) {
        if (identifier.isNumeric()) {
            values.add(identifier.numericValue());
        } else {
            values.add(identifier.value());
        }
    }

    public List<Object> getValues() {
        return values;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
