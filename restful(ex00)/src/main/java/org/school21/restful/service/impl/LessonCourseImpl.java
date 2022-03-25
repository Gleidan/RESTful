package org.school21.restful.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.school21.restful.exception.EntityNotFoundException;
import org.school21.restful.model.Lesson;
import org.school21.restful.repository.CoursesRepository;
import org.school21.restful.repository.LessonRepository;
import org.school21.restful.service.LessonService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonCourseImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final CoursesRepository coursesRepository;

    @Override
    public List<Lesson> getLessonsByCourse(Long courseId) {
        return lessonRepository.getLessonsByCourse(courseId);
    }

    @Override
    public Lesson addLessonToCourse(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    @Override
    public Lesson updateLessonInCourse(Lesson lesson, Long courseId) {
        if (!coursesRepository.existsById(courseId)) {
            throw new EntityNotFoundException("Курс с id " + courseId + " не найден");
        }
        Lesson lessonToUpdate = lessonRepository.findById(lesson.getId()).orElse(null);
        if (lessonToUpdate == null) {
            throw new EntityNotFoundException("Урок с id " + lesson.getId() + " не найден");
        }
        if (lesson.getDayOfWeek() != null) {
            lessonToUpdate.setDayOfWeek(lesson.getDayOfWeek());
        }
        if (lesson.getStartTime() != null) {
            lessonToUpdate.setStartTime(lesson.getStartTime());
        }
        if (lesson.getEndTime() != null) {
            lessonToUpdate.setEndTime(lesson.getEndTime());
        }
        return lessonRepository.save(lessonToUpdate);
    }
}
