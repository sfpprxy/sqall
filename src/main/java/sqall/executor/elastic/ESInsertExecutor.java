package sqall.executor.elastic;

import org.elasticsearch.action.ActionRequestBuilder;
import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import sqall.result.InsertResult;
import sqall.result.Result;
import sqall.sql.ast.ElasticsearchTableSource;
import sqall.sql.ast.InsertStatement;
import sqall.sql.ast.Statement;
import sqall.util.U;

import java.util.Map;

public class ESInsertExecutor extends ESAbstractExecutor {

    public ESInsertExecutor(Client client, Statement st) {
        super(client, st);
    }

    @Override
    public ActionRequestBuilder buildRequest() {
        ElasticsearchTableSource into = (ElasticsearchTableSource) ((InsertStatement) st).getInto();
        Map<String, Object> map = U.zipToMap(((InsertStatement) st).getColumns(), ((InsertStatement) st).getValues());
        IndexRequestBuilder rb = client.prepareIndex()
                .setIndex(into.getIndex())
                .setType(into.getType())
                .setSource(map);
        return rb;
    }

    @Override
    public Result getResult(ActionResponse response) {
        IndexResponse resp = (IndexResponse) response;
        int success = 0;
        if (resp.isCreated()) {
            success = 1;
        }
        return new InsertResult(success);
    }

}
