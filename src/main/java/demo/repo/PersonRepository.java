package demo.repo;

import demo.doc.PersonIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.UUID;

/**
 * @author yaodw
 */
public interface PersonRepository extends ElasticsearchRepository<PersonIndex, UUID> {
}
