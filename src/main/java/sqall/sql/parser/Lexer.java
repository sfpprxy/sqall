package sqall.sql.parser;

import static sqall.sample.SQLS.SELECT_SQL_ESID;
import static sqall.util.U.or;

public class Lexer {

    // sqall.sql input
    private String text;

    // position of the current index of input chars
    private int pos = 0;

    // begin pos of next token
    private int begin = 0;

    // current Token
    private Token token;
    private TokenType type;

    public Lexer(String text) {
        this.text = text;
    }

    public Token token() {
        return token;
    }

    public TokenType type() {
        return type;
    }

    public boolean meet(TokenType type) {
        return type == type();
    }

    public Token next() {
        token = nextToken();
        type = token.type();
        return token;
    }

    public Token nextToken() {
        while (true) {
            if (endOfInput()) {
                return new Token(TokenType.EOF);
            }
            char cur = text.charAt(pos);
            switch (cur) {
                case ' ':
                    scanChar(cur);
                    continue;
                case ',':
                    scanChar(cur);
                    return new Token(TokenType.COMMA);
                case '.':
                    scanChar(cur);
                    return new Token(TokenType.DOT);
                case '=':
                    scanChar(cur);
                    return new Token(TokenType.EQ);
                case '(':
                    scanChar(cur);
                    return new Token(TokenType.LPAREN);
                case ')':
                    scanChar(cur);
                    return new Token(TokenType.RPAREN);
                case '*':
                    scanChar(cur);
                    return new Token(TokenType.STAR);
                case '\'':
                    String str = scanString();
                    return new Token(TokenType.STRING, str);
            }
            if (illegal(cur)) {
                throw new ParseException("illegal character: " + cur);
            }
            if (Character.isDigit(cur)) {
                String numericStr = scanNumber();
                return new Token(TokenType.NUMBER, numericStr);
            }
            String ident = scanIdentifier();
            if (ident == null) {
                return new Token(TokenType.EOF);
            }
            TokenType type = Token.getType(ident.toUpperCase());
            if (isIdentifier(type)) {
                return new Token(TokenType.IDENTIFIER, ident);
            } else {
                return new Token(type);
            }
        }
    }

    private char scanChar(char c) {
        pos += 1;
        begin = pos;
        return c;
    }

    private boolean illegal(char c) {
        return !isIdentifierChar(c);
    }

    private String scanNumber() {
        int dots = 0;
        StringBuilder buf = new StringBuilder();
        while (true) {
            char cur = text.charAt(pos);
            buf.append(cur);
            if (Character.isLetter(cur)) {
                throw new ParseException(buf + " is not a valid number or identifier");
            }
            if (Character.isDigit(cur) || cur == '.') {
                if (cur == '.') {
                    dots += 1;
                }
                if (dots >= 2) {
                    throw new ParseException(buf + " is not a valid number or identifier");
                }
                pos += 1;
                if (endOfInput()) {
                    return subStr();
                }
            } else {
                String sub = subStr();
                begin = pos;
                return sub;
            }
        }
    }

    private String scanIdentifier() {
        while (true) {
            char cur = text.charAt(pos);
            if (isIdentifierChar(cur)) {
                pos += 1;
                if (endOfInput()) {
                    return subStr();
                }
            } else {
                String sub = subStr();
                begin = pos;
                return sub;
            }
        }
    }

    private boolean isIdentifierChar(char c) {
        return Character.isLetter(c) || Character.isDigit(c) || c == '_';
    }

    private String scanString() {
        pos += 1;
        begin += 1;
        while (true) {
            char cur = text.charAt(pos);
            if (cur != '\'') {
                pos += 1;
            } else {
                String sub = subStr();
                pos += 1;
                begin = pos;
                return sub;
            }
        }
    }

    private String subStr() {
        return text.substring(begin, pos);
    }

    private boolean isIdentifier(TokenType tokenType) {
        return tokenType == null;
    }

    private String str(char c) {
        return String.valueOf(c);
    }

    private boolean endOfInput() {
        return pos >= text.length();
    }

    public String info() { // for easy debugging
        return text.substring(0, pos);
    }

    public static void main(String[] args) {
        String sql = "  select id, name, address from twitter/aaa where name = 'xigua' and id = 123 ";
        String sql1 = "select id, name, telephone from student.info";
        Lexer lexer = new Lexer(SELECT_SQL_ESID);
        for (;;) {
            System.out.println(lexer.next());
            if (lexer.endOfInput()) {
                break;
            }
        }
    }

}
