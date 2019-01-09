package demo.entity;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.List;
import java.util.UUID;

/**
 * @author yaodw
 */
public interface OrdersRepo extends JpaRepositoryImplementation<Orders, UUID> {

    @EntityGraph(value = "Orders.details", type = EntityGraph.EntityGraphType.LOAD)
    List<Orders> findByNameLike(String name);
}
