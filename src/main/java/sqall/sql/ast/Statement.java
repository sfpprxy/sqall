package sqall.sql.ast;

import sqall.sql.visitor.Visitor;

public interface Statement {

    void accept(Visitor visitor);

}