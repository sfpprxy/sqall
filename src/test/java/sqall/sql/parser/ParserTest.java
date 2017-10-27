package sqall.sql.parser;

import org.junit.Test;
import sqall.sql.ast.SelectStatement;
import sqall.sql.ast.Statement;

import static sqall.sample.SQLS.*;

public class ParserTest {

    @Test
    public void parseSelect() throws Exception {
        Parser parser = new Parser(SELECT_WHERE);
        Statement statement = parser.parse();
        System.out.println(1);
    }

    @Test
    public void parseSelectWhereAndOr() throws Exception {
        Parser parser = new Parser("select id, name, age from student.info where age = 36 or age = 18");
        Statement statement = parser.parse();
        System.out.println(((SelectStatement) statement).getWhere());
    }


    @Test
    public void parseInsert() throws Exception {
        Parser parser = new Parser(INSERT_SQL);
        Statement statement = parser.parse();
    }

    @Test
    public void parseInsertWithColumns() throws Exception {
        Parser parser = new Parser(INSERT_SQL_WITH_COLUMNS);
        Statement statement = parser.parse();
    }

}
