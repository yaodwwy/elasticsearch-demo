package demo.repo;

import demo.doc.OrderIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.UUID;

/**
 * @author yaodw
 */
public interface OrderIndexRepository extends ElasticsearchRepository<OrderIndex, UUID> {
}
