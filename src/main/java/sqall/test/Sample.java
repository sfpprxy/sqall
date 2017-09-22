package sqall.test;

public class Sample {

    public static final String SELECT_SQL
            = "select id, name, age, telephone from student.info";

    public static final String SELECT_SQL_ESID
            = "select id, name, age, telephone from student.info where _id = 5";

    public static final String SELECT_WHERE
            = "select id, name, age from student.info where age = 36";

    public static final String SELECT_WHERE_1
            = "select id, name, age from student.info " +
            "where a=a and b=b and a=a or a=a or a=a or not c=c or d=d or e=e and f=f ";

    public static final String INSERT_SQL // not support
            = "insert into mytable values ('c1v', 'c2v', 'c3v')";

    public static final String INSERT_SQL_WITH_COLUMNS
            = "insert into student.info (co1, co2, co3) values ('c1v', 'c2v', 'c3v')";

    public static final String INSERT_SQL_NUM_COLUMNS
            = "insert into student.info (co1, co2, co3, co4) values (011, 11.0, 11.1, 'c3v')";

}
