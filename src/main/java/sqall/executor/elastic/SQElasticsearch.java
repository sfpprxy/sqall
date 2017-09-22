package sqall.executor.elastic;

import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import sqall.executor.SQall;
import sqall.result.Result;
import sqall.sql.ast.Statement;
import sqall.sql.parser.Parser;

import java.io.UncheckedIOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class SQElasticsearch implements SQall {

    private Client client;
    private ESAbstractExecutor executor;

    @Override
    public SQElasticsearch connect(String ip) {
        try {
            client = TransportClient.builder().build()
                    .addTransportAddress(new InetSocketTransportAddress(
                            InetAddress.getByName(ip), 9300));
        } catch (UnknownHostException e) {
            throw new UncheckedIOException("connection to es failed", e);
        }
        return this;
    }

    @Override
    public SQElasticsearch query(String sql) {
        Parser parser = new Parser(sql);
        Statement statement = parser.parse();

        ESExecutorVisitor visitor = new ESExecutorVisitor().setClient(client);
        statement.accept(visitor);
        executor = visitor.getExecutor();

        return this;
    }

    @Override
    public Result get() {
        ActionResponse response = executor.buildRequest().get();
        return executor.getResult(response);
    }

}
