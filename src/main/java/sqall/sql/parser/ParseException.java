package sqall.sql.parser;

public class ParseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ParseException(){
    }

    public ParseException(String message){
        super(message);
    }

    public ParseException(String message, Throwable e){
        super(message, e);
    }

    public ParseException(String message, int line, int col){
        super(message);
    }

    public ParseException(Throwable ex, String ksql){
        super("parse error. detail message is :\n" + ex.getMessage() + "\nsource sql is : \n" + ksql, ex);
    }
}