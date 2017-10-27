package sqall.sql.parser;

import org.junit.Test;

import static sqall.sql.parser.TokenType.EOF;
import static sqall.sample.SQLS.SELECT_WHERE;

public class LexerTest {

    @Test
    public void next() throws Exception {
        Lexer lexer = new Lexer(SELECT_WHERE);
        for (;;) {
            lexer.next().prettyPrint();
            if (lexer.meet(EOF)) {
                break;
            }
        }

    }

}
