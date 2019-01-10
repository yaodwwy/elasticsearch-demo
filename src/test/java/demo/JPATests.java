package demo;

import demo.doc.PersonIndex;
import demo.entity.Detail;
import demo.entity.DetailRepo;
import demo.entity.Orders;
import demo.entity.OrdersRepo;
import demo.repo.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JPATests {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private OrdersRepo ordersRepo;
    @Autowired
    private DetailRepo detailRepo;

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
            // 统计的时候不fetch
            // 如果分页, 所有数据会加载到内存中再分! 注意!
            if (!Long.class.equals(query.getResultType())) {
                root.fetch("orders");
            }
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
            if (!Long.class.equals(query.getResultType())) {
                root.fetch("details");
            }
            return null;
        };

        // 如果Fetch了子集合，因为Sql查的包含子集合明细无法分页。
        // 所以以下注释内容将会抛出 InvalidDataAccessApiUsageException count无法计算分页信息
        // List<Orders> all = ordersRepo.findAll(spec, PageRequest.of(1, 25)).getContent();

        List<Orders> all = ordersRepo.findAll(spec, PageRequest.of(1, 20)).getContent();

        all.forEach(System.out::println);
        all.get(0).getDetails().forEach(System.out::println);
    }
}

