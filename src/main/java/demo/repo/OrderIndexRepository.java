package demo.repo;

import demo.doc.OrderDetailsIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.UUID;

/**
 * @author yaodw
 */
public interface OrderIndexRepository extends ElasticsearchRepository<OrderDetailsIndex, UUID> {
}
