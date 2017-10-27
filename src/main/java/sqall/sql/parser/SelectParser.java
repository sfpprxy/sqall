package sqall.sql.parser;

import sqall.sql.ast.DefaultTableSource;
import sqall.sql.ast.ElasticsearchTableSource;
import sqall.sql.ast.DefaultExpr;
import sqall.sql.ast.NotExpr;
import sqall.sql.ast.SelectStatement;

import java.util.List;

import static sqall.sql.parser.TokenType.*;
import static sqall.util.U.list;

public class SelectParser {

    private Lexer lexer;

    private SelectStatement ss = new SelectStatement();

    public SelectParser(Lexer lexer) {
        this.lexer = lexer;
    }

    public SelectStatement parse() {
        parseSelect();
        parseFrom();
        parseWhere();
        validate();
        return ss;
    }

    private void parseSelect() {
        while (true) {
            next();
            if (meet(FROM)) {
                break;
            }
            if (meet(STAR)) {
                ss.allColumn = true;
                break;
            }
            if (meet(IDENTIFIER)) {
                ss.addSelect(token());
            } else if (meet(COMMA)) {
                continue;
            } else {
                throw new ParseException("invalid select columns");
            }
        }
    }

    private void parseFrom() {
        List<Token> from = list();
        while (true) {
            next();
            if (meetAny(WHERE, EOF)) {
                break;
            }
            if (meet(IDENTIFIER)) {
                from.add(token());
            }
        }
        if (from.size() == 1) {
            ss.setFrom(new DefaultTableSource(from.get(0)));
        }
        if (from.size() == 2) {
            Token index = from.get(0);
            Token type = from.get(1);
            ss.setFrom(new ElasticsearchTableSource(index, type));
        }
    }

    private void parseWhere() {
        next(); // expect _ESID, head of Exprs
        parseEsId();
        parseAndExpersGroupByOr();
    }

    private void parseAndExpersGroupByOr() {
        // parse single expr
        DefaultExpr firstExpr = nextExpr();
        List<DefaultExpr> group = ss.newExprGroup();
        group.add(firstExpr);

        next(); // expect AND, OR
        if (meet(AND)) { // parse  expr and expr and expr ...
            parseAndExprs(group);
        }
        if (meet(OR)) {
            parseOrExprs();
        }
    }

    private void parseOrExprs() {
        while (true) {
            if (meet(OR)) {
                List<DefaultExpr> newGroup = ss.newExprGroup();
                parseAndExprs(newGroup);
            } else {
                break;
            }
        }
    }

    private void parseAndExprs(List<DefaultExpr> group) {
        while (true) {
            next(); // expect expr head
            group.add(nextExpr());
            next(); // expect AND
            if (!meet(AND)) {
                break;
            }
        }
    }

    private DefaultExpr nextExpr() {
        DefaultExpr expr = new DefaultExpr();
        if (meet(NOT)) {
            expr = new NotExpr();
            next();
        }
        if (meet(IDENTIFIER)) {
            expr.setLeft(token());
            next();
            if (meetAny(EQ, GT, LT, GTEQ, LTEQ)) {
                expr.setOperator(token());
                 next();
                if (meetAny(NUMBER, STRING, IDENTIFIER)) {
                    return (DefaultExpr) expr.setRight(token());
                }
            }
        }
        throw new ParseException("invalid expression near the end of: " + lexer.info());
    }

    private void parseEsId() {
        DefaultExpr expr = new DefaultExpr();
        if (meet(ESID)) {
            expr.setLeft(token());
            next();
            if (meet(EQ)) {
                expr.setOperator(token());
                next();
                if (meet(IDENTIFIER)) {
                    expr.setRight(token());
                    List<DefaultExpr> exprGroup = ss.newExprGroup();
                    exprGroup.add(expr);
                }
            }
        }
    }

    private boolean validate() {
        if (ss.getWhere() == null) {
            throw new ParseException("where expression not valid");
        }
        return true;
    }

    private Token next() {
        return lexer.next();
    }

    private Token token() {
        return lexer.token();
    }

    private boolean meet(TokenType type) {
        return lexer.meet(type);
    }

    private boolean meetAny(TokenType... types) {
        boolean meet = false;
        for (TokenType type : types) {
            if (lexer.meet(type)) {
                meet = true;
            }
        }
        return meet;
    }

}
