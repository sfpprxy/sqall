package sqall.sql.visitor;

import sqall.sql.ast.InsertStatement;
import sqall.sql.ast.SelectStatement;

public interface Visitor {

    void visit(SelectStatement sst);
    void visit(InsertStatement ist);

}
