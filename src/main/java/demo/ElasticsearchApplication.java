package demo;

import demo.entity.Detail;
import demo.entity.Orders;
import demo.entity.OrdersRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;
import java.util.Set;

/**
 * @author yaodw
 */
@SpringBootApplication
public class ElasticsearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElasticsearchApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(OrdersRepo ordersRepo) {
        return (args) -> {
            // 初始化测试数据
            Set<Orders> orders = new HashSet<>();
            for (int i = 0; i < 100; i++) {
                Orders order = new Orders();
                Set<Detail> details = new HashSet<>();
                for (int j = 0; j < 10; j++) {
                    Detail detail = new Detail();
                    detail.setName("商品编号" + i + ":" + j);
                    detail.setOrders(order);
                    details.add(detail);
                }
                order.setName("订单编号" + i);
                order.setDetails(details);
                orders.add(order);
            }
            ordersRepo.saveAll(orders);
        };
    }
}

