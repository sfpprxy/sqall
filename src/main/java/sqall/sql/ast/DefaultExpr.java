package sqall.sql.ast;

import sqall.sql.parser.Token;
import sqall.sql.parser.TokenType;
import sqall.util.Union;

import static sqall.util.U.printl;

public class DefaultExpr implements Expr { // inner class implement value instead of UNION

    private Expr left;
    private Expr right;
    private Expr parent;

    private Token operator;
    private Token value;

//    Union uValue = new Union(DefaultExpr.class, Token.class);
//
//    public boolean valueIsExpr() {
//        return uValue.isFirstType();
//    }
//
//    public boolean valueIsToken() {
//        return uValue.isSecondType();
//    }

    public DefaultExpr() {

    }

    public DefaultExpr(Token value) {
        this.value = value;
    }

    public DefaultExpr(Expr Expr) {
        this.value = value;
    }

    public Expr getLeft() {
        return null;
    }

    public Expr setLeft(Expr left) {
        this.left = left;
        return this;
    }

    public Expr setLeft(Token left) {
        this.left = new DefaultExpr(left);
        return this;
    }

    public Token getOperator() {
        return operator;
    }

    public Expr setOperator(Token operator) {
        this.operator = operator;
        return this;
    }

    public Expr getRight() {
        return null;
    }

    public Expr setRight(Expr right) {
        this.right = right;
        return this;
    }

    public Expr setRight(Token right) {
        this.right = new DefaultExpr(right);
        return this;
    }

    public Token getValue() {
        return value;
    }

    public Expr setValue(Token value) {
//        this.value = value;
        return this;
    }

    public boolean hasLeft() {
        return left != null;
    }

    public boolean hasRight() {
        return right != null;
    }

    public boolean hasValue() {
        return value != null;
    }

    public boolean hasChild() {
        return hasLeft() && hasRight();
    }

    public void iterPrint() {
        iterPrint(this, 0);
        printl(" ");
    }

    private void iterPrint(DefaultExpr expr, int i) {
        StringBuilder ident = ident(i);
        if (expr == null) {
            return;
        }
        if (!expr.hasLeft() && !expr.hasRight()) {
            return;
        }
        String value = "null";
        if (expr.hasValue()) {
            value = expr.getValue().toString();
            printl(ident + value);
        }
        String left = "null";
        if (expr.hasLeft()) {
            left = expr.getLeft().toString();
            printl("  " + ident + left);
//            iterPrint(expr.getLeft(), i + 1);
        }
        String right = "null";
        if (expr.hasRight()) {
            right = expr.getRight().toString();
            printl("  " + ident + right);
//            iterPrint(expr.getRight(), i + 1);
        }
    }

    private StringBuilder ident(int time) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < time; i++) {
            sb.append("  ");
        }
        return sb;
    }

//    class Value {
//        private Expr Expr;
//        private Token token;
//
//        public Value(Expr Expr) {
//            this.Expr = Expr;
//        }
//
//        public Value(Token token) {
//            this.token = token;
//        }
//
//        public boolean getExpr() {
//            return Expr != null;
//        }
//
//        public boolean isToken() {
//            return Expr != null;
//        }
//
//        public Expr getExpr() {
//            return Expr;
//        }
//
//        public Token getToken() {
//            return token;
//        }
//    }

    @Override
    public String toString() {
        String left = null;
        String oper = null;
        String right = null;
        if (this.left != null) {
            if (this.left.getValue() != null){
                left = this.left.getValue().value();
            }
        }
        if (this.operator != null) {
            oper = this.operator.value();
        }
        if (this.right != null) {
            if (this.right.getValue() != null) {
                right = this.right.getValue().value();
            }
        }
        return left + " " + oper + " " + right;
//        return value.value();
    }
}
