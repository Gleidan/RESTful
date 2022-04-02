package org.school21.restful.service.impl;

import lombok.AllArgsConstructor;
import org.school21.restful.exception.EntityNotFoundException;
import org.school21.restful.model.Course;
import org.school21.restful.model.User;
import org.school21.restful.repository.CoursesRepository;
import org.school21.restful.repository.TeachersRepository;
import org.school21.restful.service.TeachersService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TeachersServiceImpl implements TeachersService {
    private final TeachersRepository teachersRepository;
    private final CoursesRepository coursesRepository;

    @Override
    public List<User> getTeachersByCourse(Long courseId, Pageable pageable) {
        return teachersRepository.getTeachersByCourse(courseId, pageable).getContent();
    }

    @Override
    public void addTeacherToCourse(Long courseId, User teacher) {
        if (!coursesRepository.existsById(courseId)) {
            throw new EntityNotFoundException("Курс с id " + courseId + " не найден");
        }
        if (!teachersRepository.existsById(teacher.getId())) {
            throw new EntityNotFoundException("Учитель с id " + teacher.getId() + " не найден");
        }
        teacher = teachersRepository.getById(teacher.getId());
        Course course = coursesRepository.getById(courseId);
        if (course.getTeachers() == null) {
            List<User> teachers = new ArrayList<>();
            teachers.add(teacher);
            course.setTeachers(teachers);
        } else {
            course.getTeachers().add(teacher);
        }
        coursesRepository.save(course);
    }

    @Override
    public void deleteTeacherFromCourse(Long courseId, Long teacherId) {
        if (!coursesRepository.existsById(courseId)) {
            throw new EntityNotFoundException("Курс с id " + courseId + " не найден");
        }
        if (!teachersRepository.existsById(teacherId)) {
            throw new EntityNotFoundException("Учитель с id " + teacherId + " не найден");
        }
        Course course = coursesRepository.getById(courseId);
        User teacher = teachersRepository.getById(teacherId);
        course.getTeachers().remove(teacher);
        teacher.getTeachingCourse().remove(course);
        coursesRepository.save(course);
    }
}
