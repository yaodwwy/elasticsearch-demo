package demo;

import demo.doc.Conference;
import demo.thread.OrderIndexThread;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.ExecutorService;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItem;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticsearchApplicationTests {
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private ElasticsearchOperations operations;
    @Autowired
    private ExecutorService consumerQueueThreadPool;
    @Autowired
    private OrderIndexThread orderIndexThread;

    /**
     * 测试全量索引
     */
    @Test
    public void TestInitDataFromJPA() {
        consumerQueueThreadPool.execute(orderIndexThread);
        try {
            Thread.sleep(200_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void textSearch() throws Exception {

        String expectedDate = "2014-10-29";
        String expectedWord = "java";
        org.springframework.data.elasticsearch.core.query.CriteriaQuery query = new org.springframework.data.elasticsearch.core.query.CriteriaQuery(
                new Criteria("keywords")
                        .contains(expectedWord)
                        .and(new Criteria("date")
                                .greaterThanEqual(expectedDate)));

        List<Conference> result = operations.queryForList(query, Conference.class);
        result.forEach(conference -> {
            System.out.println(conference);
        });
        assertThat(result, hasSize(3));

        for (Conference conference : result) {
            assertThat(conference.getKeywords(), hasItem(expectedWord));
            assertThat(format.parse(conference.getDate()), greaterThan(format.parse(expectedDate)));
        }
    }

    @Test
    public void geoSpatialSearch() {

        GeoPoint startLocation = new GeoPoint(50.0646501D, 19.9449799D);
        String range = "330mi"; // or 530km
        CriteriaQuery query = new CriteriaQuery(new Criteria("location").within(startLocation, range));

        List<Conference> result = operations.queryForList(query, Conference.class);

        assertThat(result, hasSize(2));
    }
}

