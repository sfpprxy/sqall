package sqall.sql.parser;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Token {

    private TokenType type;
    private String value;

    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    public Token(TokenType type) {
        this.type = type;
        if (type.value != null) {
            this.value = type.value;
        }
    }

    public TokenType type() {
        return type;
    }

    public String value() {
        return value;
    }

    public Object objValue() {
        if (type == TokenType.NUMBER) {
            return numericValue();
        } else {
            return value();
        }
    }

    public Number numericValue() {
        if (value.contains(".")) {
            return new Double(value);
        } else {
            return new Long(value);
        }
    }

    public boolean isNumeric() {
        return type == TokenType.NUMBER;
    }

    private static final Map<String, TokenType> MAP;
    static {
        Map<String, TokenType> map = new HashMap<>();
        for (TokenType tokenType : TokenType.values()) {
            map.put(tokenType.value, tokenType);
        }
        MAP = map;
    }

    public static TokenType getType(String value) {
        return MAP.get(value);
    }

    public void prettyPrint() {
        String s = "Token{" +
                String.format("%1$-15s", "type=" + type) +
                String.format("%1$-20s", ", value='" + value + '\'') +
                '}';
        System.out.println(s);
    }

    @Override
    public String toString() {
        return value;
    }

}
