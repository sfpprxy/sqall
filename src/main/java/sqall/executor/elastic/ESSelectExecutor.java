package sqall.executor.elastic;

import org.elasticsearch.action.ActionRequestBuilder;
import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import sqall.result.Result;
import sqall.result.SelectResult;
import sqall.sql.ast.ElasticsearchTableSource;
import sqall.sql.ast.DefaultExpr;
import sqall.sql.ast.SelectStatement;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static sqall.util.U.list;

public class ESSelectExecutor extends ESAbstractExecutor {

    private SelectStatement sst;

    public ESSelectExecutor(Client client, SelectStatement st) {
        super(client, st);
        this.sst = st;
    }

    @Override
    public ActionRequestBuilder buildRequest() {
        ElasticsearchTableSource from = (ElasticsearchTableSource) sst.getFrom();
        String[] selects = sst.getSelectList().toArray(new String[0]);

//        DefaultExpr defaultExpr = sst.getWhere();
//        String key = defaultExpr.getLeft().getValue().value();
//        Object value = defaultExpr.getRight().getValue().objValue();
//
//        BoolQueryBuilder bqb = QueryBuilders.boolQuery();
//        bqb.must(QueryBuilders.termQuery(key, value))
//                .should(QueryBuilders.termQuery("name", "qwe"));


        return buildSearchRequest(
                from.getIndex(),
                from.getType(),
                selects,
                null,
                0,
                1000 // by default
        );
    }

    private SearchRequestBuilder buildSearchRequest(
            String index, String type, String[] include, QueryBuilder qb, int from, int limit) {
        return client.prepareSearch(index)
                .setTypes(type)
                .setFetchSource(include, null)
                .setQuery(qb)
                .setFrom(from)
                .setSize(limit);
    }

    @Override
    public Result getResult(ActionResponse response) {
        SearchHit[] hits = ((SearchResponse) response).getHits().getHits();
        List<Map<String, Object>> result = Arrays.stream(hits)
                .map(SearchHit::getSource)
                .filter(hit -> !hit.isEmpty())
                .collect(Collectors.toList());
        List<String> selectList = sst.getSelectList();
        return new SelectResult(result, selectList);
    }

}
