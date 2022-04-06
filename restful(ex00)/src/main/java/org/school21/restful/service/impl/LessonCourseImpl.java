package org.school21.restful.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.school21.restful.exception.EntityNotFoundException;
import org.school21.restful.model.Course;
import org.school21.restful.model.Lesson;
import org.school21.restful.model.User;
import org.school21.restful.repository.CoursesRepository;
import org.school21.restful.repository.LessonRepository;
import org.school21.restful.repository.TeachersRepository;
import org.school21.restful.service.LessonService;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonCourseImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final CoursesRepository coursesRepository;
    private final TeachersRepository teachersRepository;

    @Override
    public List<Lesson> getLessonsByCourse(Long courseId, Pageable pageable) {
        return lessonRepository.getLessonsByCourse(courseId, pageable).getContent();
    }

    @Override
    public Lesson addLessonToCourse(Lesson lesson) {
        if (lesson.getTeacher() != null) {
            User teacher = teachersRepository.getById(lesson.getTeacher().getId());
            Course course = coursesRepository.getById(lesson.getCourse().getId());
            if (course.getTeachers() == null || !course.getTeachers().contains(teacher)) {
                throw new IllegalArgumentException("Not valid user");
            }
        }
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

    @Override
    public void deleteLessonFromCourse(Long courseId, Long lessonId) {
        if (!coursesRepository.existsById(courseId)) {
            throw new EntityNotFoundException("Курс с id " + courseId + " не найден");
        }
        if (!lessonRepository.existsById(lessonId)) {
            throw new EntityNotFoundException("Урок с id " + lessonId + " не найден");
        }
        Lesson lesson = lessonRepository.getById(lessonId);
        lessonRepository.delete(lesson);
    }
}
