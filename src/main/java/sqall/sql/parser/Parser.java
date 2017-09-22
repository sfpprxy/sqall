package sqall.sql.parser;

import sqall.sql.ast.InsertStatement;
import sqall.sql.ast.SelectStatement;
import sqall.sql.ast.Statement;

public class Parser {

    private final Lexer lexer;

    public Parser(String text) {
        this.lexer = new Lexer(text);
    }

    public Statement parse() {
        Token token = lexer.next();
        switch (token.type()) {
            case SELECT:
                return parseSelect();
            case INSERT:
                return parseInsert();
            case DELETE:
                parseDelete();
        }
        return null;
    }

    private SelectStatement parseSelect() {
        return new SelectParser(lexer).parse();
    }

    private InsertStatement parseInsert() {
        return new InsertParser(lexer).parse();
    }

    private void parseDelete() {

    }

    private void test() {

    }

}
