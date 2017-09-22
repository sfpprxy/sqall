package sqall.sql.ast;

import org.junit.Test;
import sqall.sql.parser.Token;

import static sqall.sql.parser.TokenType.NUMBER;

public class ExprTest {

    private Token token(Integer n) {
        String s = n.toString();
        return new Token(NUMBER, s);
    }

//    private DefaultExpr build() {
//        DefaultExpr defaultExpr = new DefaultExpr(token(1));
//        defaultExpr.setLeft(token(2))
//                .getLeft()
//                .setLeft(token(4))
//                .setRight(token(5));
//
//        defaultExpr.setRight(token(3))
//            .getRight()
//        .setRight(token(6));
//        return defaultExpr;
//    }

//    @Test
//    public void iterPrint() throws Exception {
//        build().iterPrint();
//    }

}
