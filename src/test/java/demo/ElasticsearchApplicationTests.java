package demo;

import demo.doc.Conference;
import demo.doc.PersonIndex;
import demo.entity.Detail;
import demo.entity.DetailRepo;
import demo.entity.Orders;
import demo.entity.OrdersRepo;
import demo.repo.PersonRepository;
import demo.thread.OrderIndexThread;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
    private PersonRepository personRepository;
    @Autowired
    private OrdersRepo ordersRepo;
    @Autowired
    private DetailRepo detailRepo;
    @Autowired
    private ElasticsearchOperations operations;
    @Autowired
    private ExecutorService consumerQueueThreadPool;

    @Test
    public void contextLoads() {
    }

    @Test
    public void TestCrud() {
        PersonIndex p = new PersonIndex(UUID.randomUUID(), "张三", 18, "测试人物");
        p = personRepository.save(p);
        System.out.println("add person done! " + p);
        personRepository.deleteById(UUID.randomUUID());
        System.out.println("delete person done! " + personRepository.findById(UUID.randomUUID()));

        p.setId(UUID.randomUUID());
        p.setName("李四");
        p.setAge(12);
        personRepository.save(p);
        System.out.println("add person done! " + p);

        Optional<PersonIndex> op = personRepository.findById(UUID.randomUUID());
        p = op.get();
        System.out.println("findById person done! " + p);
    }


    @Test
    public void TestNamedEntityGraph() {
        List<Orders> orders = ordersRepo.findByNameLike("%订单编号%");
        orders.forEach(order -> {
            order.getDetails().forEach(System.out::println);
        });
    }

    @Test
    public void TestFetchQuery() {
        Specification<Detail> spec = (Specification<Detail>) (root, query, cb) -> {
            root.fetch("orders");
            return null;
        };
        List<Detail> all = detailRepo.findAll(spec, PageRequest.of(1, 20)).getContent();
        all.forEach(System.out::println);
    }

    @Test
    // 如果不使用迫切加载,则需要打开事务以保证不会因为懒加载而 no session 异常
    // @Transactional
    public void TestJoinQuery() {
        Specification<Orders> spec = (Specification<Orders>) (root, query, cb) -> {
            root.fetch("details");
            return null;
        };

        // 如果Fetch了子集合，因为Sql查的包含子集合明细无法分页。
        // 所以以下注释内容将会抛出 InvalidDataAccessApiUsageException count无法计算分页信息
        // List<Orders> all = ordersRepo.findAll(spec, PageRequest.of(1, 25)).getContent();

        List<Orders> all = ordersRepo.findAll(spec);

        all.forEach(System.out::println);
        all.get(0).getDetails().forEach(System.out::println);
    }

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
        org.springframework.data.elasticsearch.core.query.CriteriaQuery query = new org.springframework.data.elasticsearch.core.query.CriteriaQuery(new Criteria("location").within(startLocation, range));

        List<Conference> result = operations.queryForList(query, Conference.class);

        assertThat(result, hasSize(2));
    }
}

