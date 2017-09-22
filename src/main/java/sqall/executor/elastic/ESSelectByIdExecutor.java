package sqall.executor.elastic;

import org.elasticsearch.action.ActionRequestBuilder;
import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;
import sqall.result.Result;
import sqall.result.SelectResult;
import sqall.sql.ast.ElasticsearchTableSource;
import sqall.sql.ast.SelectStatement;
import sqall.sql.ast.Statement;
import sqall.sql.parser.Token;

import java.util.List;
import java.util.Map;

import static sqall.util.U.list;

public class ESSelectByIdExecutor extends ESAbstractExecutor {

    public ESSelectByIdExecutor(Client client, Statement st) {
        super(client, st);
    }

    @Override
    public ActionRequestBuilder buildRequest() {
        SelectStatement sst = (SelectStatement) this.st;
        ElasticsearchTableSource from = (ElasticsearchTableSource) sst.getFrom();
//        Token esId = sst.getWhere().getRight().getValue();
        GetRequestBuilder rb = client.prepareGet()
                .setIndex(from.getIndex())
                .setType(from.getType())
                .setId(null);
        return rb;
    }

    @Override
    public Result getResult(ActionResponse response) {
        List<Map<String, Object>> result;
        result = list(((GetResponse) response).getSource());
        List<String> selectList = ((SelectStatement) st).getSelectList();
        return new SelectResult(result, selectList);
    }

}
