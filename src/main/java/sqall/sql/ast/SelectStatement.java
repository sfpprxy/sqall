package sqall.sql.ast;

import sqall.sql.parser.Token;
import sqall.sql.visitor.Visitor;

import java.util.List;

import static sqall.util.U.list;

public class SelectStatement implements Statement {

    private List<String> selectList = list();
    public boolean allColumn = false;

    private TableSource from;

    // structure: [or1[and, and, and ..], or2[and, and ..]]
    private List<List<DefaultExpr>> where = list();

    public void addSelect(Token identifier) {
        selectList.add(identifier.value());
    }

    public void setFrom(TableSource from) {
        this.from = from;
    }

    public List<String> getSelectList() {
        return selectList;
    }

    public TableSource getFrom() {
        return from;
    }

    public List<List<DefaultExpr>> getWhere() {
        return where;
    }

    public List<DefaultExpr> newExprGroup() {
        List<DefaultExpr> groupByOr = list();
        where.add(groupByOr);
        return groupByOr;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}
