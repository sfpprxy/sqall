package sqall.executor.elastic;

import org.elasticsearch.client.Client;
import sqall.sql.ast.InsertStatement;
import sqall.sql.ast.SelectStatement;
import sqall.sql.parser.Token;
import sqall.sql.visitor.Visitor;

import static sqall.sql.parser.TokenType.ESID;

public class ESExecutorVisitor implements Visitor {

    private ESAbstractExecutor executor;
    private Client client;

    public ESExecutorVisitor setClient(Client client) {
        this.client = client;
        return this;
    }

    public ESAbstractExecutor getExecutor() {
        return executor;
    }

    @Override
    public void visit(SelectStatement sst) {
//        Token left = sst.getWhere().getLeft().getValue();
//        if (left.type() == ESID) {
//            executor = new ESSelectByIdExecutor(client, sst);
//        }
        executor = new ESSelectExecutor(client, sst);
    }

    @Override
    public void visit(InsertStatement ist) {
        executor = new ESInsertExecutor(client, ist);
    }

}
