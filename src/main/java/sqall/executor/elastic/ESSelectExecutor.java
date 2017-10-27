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

import java.util.*;
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
        BoolQueryBuilder bqb = QueryBuilders.boolQuery();
        String[] selects;
        if (sst.allColumn) {
            selects = null;
        } else {
            selects = sst.getSelectList().toArray(new String[0]);
        }
        ElasticsearchTableSource from = (ElasticsearchTableSource) sst.getFrom();
        List<List<DefaultExpr>> where = sst.getWhere();
        BoolQueryBuilder orq = QueryBuilders.boolQuery();
        where.forEach(or -> {
            BoolQueryBuilder andq = QueryBuilders.boolQuery();
            or.forEach(and -> andq.must(QueryBuilders.matchPhraseQuery(and.getLeft().getValue().value(), and.getRight().getValue().objValue())));
            orq.should(andq);
        });

        SearchRequestBuilder searchRequestBuilder = buildSearchRequest(
                from.getIndex(),
                from.getType(),
                selects,
                orq,
                0,
                1000 // by default
        );
//        System.out.println(searchRequestBuilder);
        return searchRequestBuilder;
    }

    private SearchRequestBuilder buildSearchRequest(
            String index, String type, String[] include, QueryBuilder qb, int from, int limit) {
        SearchRequestBuilder srb = client.prepareSearch(index)
                .setTypes(type)
                .setQuery(qb)
                .setFrom(from)
                .setSize(limit);
        if (include != null) {
            srb.setFetchSource(include, null);
        }
        return srb;
    }

    @Override
    public Result getResult(ActionResponse response) {
        SearchHit[] hits = ((SearchResponse) response).getHits().getHits();
        List<Map<String, Object>> result = Arrays.stream(hits)
                .map(searchHitFields -> searchHitFields.getSource())
                .filter(hit -> !hit.isEmpty())
                .collect(Collectors.toList());
        List<String> selectList;
        if (sst.allColumn) {
            Set<String> columns = new HashSet<>();
            result.forEach(e -> columns.addAll(e.keySet()));
            selectList = new ArrayList<>(columns);
        } else {
            selectList = sst.getSelectList();
        }
        return new SelectResult(result, selectList);
    }

}
