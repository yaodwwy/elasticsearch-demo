package demo.thread;

import demo.doc.OrderIndex;
import demo.entity.*;
import demo.repo.OrderIndexRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author yaodw
 */
@Slf4j
@Service
public class OrderIndexThread implements Runnable {

    @Autowired
    private OrdersRepo ordersRepo;

    @Autowired
    private OrderIndexRepository orderIndexRepository;
    @Autowired
    private EsLastTimestampRepo esLastTimestampRepo;

    private static int size = 200;
    private static int page = 1;
    private String esTimeName = "order_details";
    private Date lastDate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void run() {

        EsLastTimestamp probe = new EsLastTimestamp();
        probe.setName(esTimeName);
        List<EsLastTimestamp> time = esLastTimestampRepo.findAll(Example.of(probe), Sort.by(Sort.Direction.DESC, "last"));

        //如果没有上次索引时间, 则以当前时间参数检索业务数据
        if (time.isEmpty()) {
            lastDate = new Date();
        } else {
            lastDate = time.get(0).getLast();
        }

        // 分页查询全部订单(可扩展时间戳为查询条件以保证一至性)
        Specification<Orders> spec = (Specification<Orders>) (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            root.joinSet("details");
            cb.and(cb.lessThanOrEqualTo(root.get("time"), lastDate));
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        Page<Orders> all = ordersRepo.findAll(spec, PageRequest.of(page, size));
        //当前页码  小于等于 总分页数的时候进行
        for (; page <= all.getTotalPages(); page++) {
            List<Orders> content = all.getContent();
            Set<OrderIndex> orderIndices = OrderIndex.orderToIndex(content);
            orderIndexRepository.saveAll(orderIndices);
            try {
                Thread.sleep(3_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            all = ordersRepo.findAll(spec, PageRequest.of(page, size));
        }

        //存入最后索引时间
        EsLastTimestamp esTime = new EsLastTimestamp();
        esTime.setName(esTimeName);
        esTime.setLast(lastDate);
        esLastTimestampRepo.save(esTime);
    }
}
