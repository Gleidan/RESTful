package org.school21.restful.repository;

import org.school21.restful.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @Query("SELECT l FROM Lesson l WHERE l.course.id = ?1")
    List<Lesson> getLessonsByCourse(Long courseId);
}
