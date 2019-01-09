package demo.entity;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.UUID;

/**
 * @author yaodw
 */
public interface EsLastTimestampRepo extends JpaRepositoryImplementation<EsLastTimestamp, UUID> {
}
