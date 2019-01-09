package demo.entity;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.UUID;

public interface DetailRepo extends JpaRepositoryImplementation<Detail, UUID> {
}
