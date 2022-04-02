package org.school21.restful.service;

import org.school21.restful.model.Lesson;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LessonService {

    List<Lesson> getLessonsByCourse(Long courseId, Pageable pageable);
    Lesson addLessonToCourse(Lesson lesson);
    Lesson updateLessonInCourse(Lesson lesson, Long courseId);
    void deleteLessonFromCourse(Long courseId, Long lessonId);
}
