package sqall.executor;

import org.junit.Test;
import sqall.executor.elastic.SQElasticsearch;
import sqall.result.Result;

import static sqall.test.Sample.*;

public class ElasticsearchExecutorTest {

    @Test
    public void insert1() throws Exception {
        insert(INSERT_SQL_WITH_COLUMNS);
    }

    @Test
    public void insert2() throws Exception {
        insert(INSERT_SQL_NUM_COLUMNS);
    }

    public void insert(String sql) throws Exception {
        SQall client = new SQElasticsearch();
        String ip = "localhost";

        client.connect(ip);
        Result result = client.query(sql).get();

        result.asTable().prettyPrint();

        System.out.println(result.asRawType());
    }

}
