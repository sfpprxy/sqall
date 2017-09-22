package sqall.executor;

import org.junit.Test;
import sqall.executor.elastic.SQElasticsearch;

import static org.junit.Assert.*;

public class SQallTest {

    @Test
    public void write() throws Exception {
        query("INSERT INTO store.test01 (id, name, age) VALUES ('id1', 'someone', 18)");
    }

    @Test
    public void read() throws Exception {
        query("SELECT id, name, age FROM store.test01 WHERE age = 18");
    }

    private void query(String sql) {
        String ip = "10.40.132.31";

        SQall client = new SQElasticsearch();

        client.connect(ip);
        client.query(sql)
                .get()
                .asTable()
                .prettyPrint();
    }

}