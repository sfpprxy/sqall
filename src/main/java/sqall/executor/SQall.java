package sqall.executor;

import sqall.result.Result;

public interface SQall {

    SQall connect(String ip);

    SQall query(String sql);

    Result get();

}
