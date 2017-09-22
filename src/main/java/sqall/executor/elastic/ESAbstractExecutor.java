package sqall.executor.elastic;

import org.elasticsearch.action.ActionRequestBuilder;
import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.client.Client;
import sqall.result.Result;
import sqall.sql.ast.Statement;

public abstract class ESAbstractExecutor {

    protected Client client;
    protected Statement st;

    public ESAbstractExecutor(Client client, Statement st) {
        this.client = client;
        this.st = st;
    }

    public abstract ActionRequestBuilder buildRequest();

    public abstract Result getResult(ActionResponse response);

}
