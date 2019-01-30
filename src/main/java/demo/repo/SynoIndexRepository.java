package demo.repo;

import demo.doc.SynoIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.UUID;

public interface SynoIndexRepository extends ElasticsearchRepository<SynoIndex, UUID> {
}
