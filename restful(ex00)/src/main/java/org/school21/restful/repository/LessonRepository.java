package org.school21.restful.repository;

import org.school21.restful.model.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @Query(value = "SELECT * FROM lessons WHERE course_id = ?1",
            nativeQuery = true)
    Page<Lesson> getLessonsByCourse(Long courseId, Pageable pageable);
}
