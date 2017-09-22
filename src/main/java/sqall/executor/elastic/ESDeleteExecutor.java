package sqall.executor.elastic;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.io.UncheckedIOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ESDeleteExecutor {

    Client client;

    public ESDeleteExecutor connect(String ip) {
        try {
            client = TransportClient.builder().build()
                    .addTransportAddress(new InetSocketTransportAddress(
                            InetAddress.getByName(ip), 9300));
        } catch (UnknownHostException e) {
            throw new UncheckedIOException("connection to es failed", e);
        }
        return this;
    }

}
