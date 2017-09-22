package sqall.sql.parser;

import sqall.sql.ast.DefaultTableSource;
import sqall.sql.ast.ElasticsearchTableSource;
import sqall.sql.ast.InsertStatement;

import java.util.List;

import static sqall.sql.parser.TokenType.IDENTIFIER;
import static sqall.sql.parser.TokenType.INTO;
import static sqall.sql.parser.TokenType.LPAREN;
import static sqall.sql.parser.TokenType.NUMBER;
import static sqall.sql.parser.TokenType.RPAREN;
import static sqall.sql.parser.TokenType.STRING;
import static sqall.sql.parser.TokenType.VALUES;
import static sqall.util.U.list;

public class InsertParser {

    private Lexer lexer;

    private InsertStatement is = new InsertStatement();

    public InsertParser(Lexer lexer) {
        this.lexer = lexer;
    }

    public InsertStatement parse() {
        parseInto();
        parseValues();
        validate();
        return is;
    }

    private void parseInto() {
        next();
        if (meet(INTO)) {
            List<Token> into = list();
            while (true) {
                if (meet(LPAREN)) {
                    parseColumns();
                    break;
                }
                if (meet(VALUES)) {
                    break;
                }
                next();
                if (meet(IDENTIFIER)) {
                    into.add(token());
                }
            }
            if (into.size() == 1) {
                is.setInto(new DefaultTableSource(into.get(0)));
            }
            if (into.size() == 2) {
                Token index = into.get(0);
                Token type = into.get(1);
                is.setInto(new ElasticsearchTableSource(index, type));
            }
        }
    }

    private void parseColumns() {
        while (true) {
            if (meet(RPAREN)) {
                break;
            }
            next();
            if (meet(IDENTIFIER)) {
                is.addColumn(token());
            }
        }
        next();
    }

    private void parseValues() {
        if (meet(VALUES)) {
            next();
            if (meet(LPAREN)) {
                while (true) {
                    if (meet(RPAREN)) {
                        break;
                    }
                    next();
                    if (meet(STRING) || meet(NUMBER)) {
                        is.addValue(token());
                    }
                }
            }
        }
    }

    private boolean validate() {
        int cs = is.getColumns().size();
        int vs = is.getValues().size();
        if (cs != 0) {
            if (vs != cs) {
                throw new ParseException("number of values does not match number of columns");
            }
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

}
