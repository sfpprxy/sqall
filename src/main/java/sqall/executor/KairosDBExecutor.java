package sqall.executor;


import org.kairosdb.client.builder.Metric;
import org.kairosdb.client.builder.MetricBuilder;
import org.kairosdb.client.builder.QueryBuilder;
import org.kairosdb.client.builder.QueryMetric;
import org.kairosdb.client.response.QueryResponse;
import sqall.result.Result;

public class KairosDBExecutor implements SQall {

    @Override
    public SQall connect(String ip) {
        return null;
    }

    @Override
    public SQall query(String sql) {
        Metric mb = MetricBuilder.getInstance()
                .addMetric("name").addDataPoint(1.0);
        return null;
    }

    @Override
    public Result get() {
        return null;
    }

}
