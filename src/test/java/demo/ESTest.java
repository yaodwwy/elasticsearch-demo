package demo;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RunWith(JUnit4.class)
public class ESTest {
    @Test
    public void client() throws UnknownHostException {
        Settings settings = Settings.builder()
                .put("cluster.name", "docker-cluster").build();
        TransportClient client = new PreBuiltTransportClient(settings).addTransportAddress(new TransportAddress(InetAddress.getByName("test.gomro.cn"), 9300));
        SearchResponse inquiry_index = client.prepareSearch("inquiry_index").execute().actionGet();
        System.out.println(inquiry_index.getHits().totalHits);
        client.close();
    }
}
