package sqall.util;

import sqall.sql.parser.Token;
import sqall.sql.parser.TokenType;
import sqall.table.Table;

public class UnionTest {

//    private void expectDemo() {
//        Union union = new Union<>(1.1);
//
//        if (union.isFirstType()) {
//            System.out.println("it is int");
//            union.intFunc();
//        }
//        if (union.isSecondType()) {
//            System.out.println("it is str");
//            union.strFun();
//        }
//
//
//        Union<Token, Table> union = new Union<>(new Token(TokenType.IDENTIFIER));
//
//        if (union.isFirstType()) {
//            System.out.println("it is token");
//            union.tokenFunc();
//        }
//        if (union.isSecondType()) {
//            System.out.println("it is table");
//            union.tableFun();
//        }
//    }

    private void test() {
        Union union = new Union(String.class, Integer.class);
        union.set("my str");
        union.set(1);
        union.set(1.1);
    }

    public static void main(String[] args) {
        UnionTest t = new UnionTest();
        t.test();
    }

}
