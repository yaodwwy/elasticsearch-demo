package demo.repo;

import demo.doc.Conference;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author Artur Konczak
 */
public interface ConferenceRepository extends ElasticsearchRepository<Conference, String> {}
