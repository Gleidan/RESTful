package org.school21.restful.repository;

import org.school21.restful.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface LessonsRepository extends JpaRepository<Lesson, Long> {
}
