package sqall;

import sqall.executor.elastic.SQElasticsearch;
import sqall.executor.SQall;

import static sqall.sample.SQLS.*;

public class SQallQuickStart {

    public static void main(String[] args) {
        SQall client = new SQElasticsearch();
        String ip = "localhost";
        String sql = SELECT_WHERE;

        client.connect(ip);
        client.query(sql)
                .get()
                .asTable()
                .prettyPrint();
    }

}
