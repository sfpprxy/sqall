import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlEvalVisitorImpl;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlExportParameterVisitor;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.sql.parser.Lexer;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.sql.parser.Token;


public class DruidTest {

    public static void main(String[] args){
        String sql = "select id, name from student, foo " +
                "where name = 'xigua' " +
                "and student.id = 'xId' " +
                "or foo.id != 'abc' " +
                "and aa = 'two' " +
                "or bb = 'twoOr' ";

        SQLStatementParser parser = new MySqlStatementParser(sql);

        SQLStatement statement = parser.parseStatement();

        MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
        MySqlEvalVisitorImpl visitor1 = new MySqlEvalVisitorImpl();
        MySqlExportParameterVisitor visitor2 = new MySqlExportParameterVisitor();
        statement.accept(visitor);
        statement.accept(visitor1);
        statement.accept(visitor2);


        System.out.println(visitor.getColumns());


        String sql1 = "select id, name, telephone from student.info where NOT a = 18 and b = 1 or c = 2 and d = 3 or e = 4 or f = f and g = g and h=h and i=i and j=j and k=k";

        Lexer lexer = new Lexer(sql1);
        SQLStatementParser parser1 = new MySqlStatementParser(sql1);

        SQLStatement statement1 = parser1.parseStatement();

        for (;;) {
            lexer.nextToken();
            Token token = lexer.token();
            System.out.println(lexer.stringVal() + ": " + token);
            if (token == Token.EOF) break;
        }

    }

}
