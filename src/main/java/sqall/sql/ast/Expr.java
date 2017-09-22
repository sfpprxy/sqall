package sqall.sql.ast;

import sqall.sql.parser.Token;

public interface Expr {
    Token getValue();
}
