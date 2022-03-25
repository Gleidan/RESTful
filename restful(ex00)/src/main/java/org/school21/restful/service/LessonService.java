package org.school21.restful.service;

import org.school21.restful.model.Lesson;

import java.util.List;

public interface LessonService {

    List<Lesson> getLessonsByCourse(Long courseId);
    Lesson addLessonToCourse(Lesson lesson);
    Lesson updateLessonInCourse(Lesson lesson, Long courseId);

}
