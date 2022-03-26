package org.school21.restful.service.impl;

import lombok.AllArgsConstructor;
import org.school21.restful.exception.EntityNotFoundException;
import org.school21.restful.model.Course;
import org.school21.restful.model.User;
import org.school21.restful.repository.CoursesRepository;
import org.school21.restful.repository.StudentRepository;
import org.school21.restful.service.StudentsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class StudentsServiceImpl implements StudentsService {

    private final StudentRepository studentRepository;
    private final CoursesRepository coursesRepository;

    @Override
    public List<User> getStudentsByCourse(Long courseId) {
        return studentRepository.getStudentsByCourse(courseId);
    }

    @Override
    public User addStudentToCourse(Long courseId, User student) {
        if (!coursesRepository.existsById(courseId)) {
            throw new EntityNotFoundException("Курс с id " + courseId + " не найден");
        }
        if (!studentRepository.existsById(student.getId())) {
            throw new EntityNotFoundException("Студент с id " + student.getId() + " не найден");
        }
        student = studentRepository.getById(student.getId());
        Course course = coursesRepository.getById(courseId);
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        student.setCourse(courses);
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudentFromCourse(Long courseId, Long studentId) {
        if (!coursesRepository.existsById(courseId)) {
            throw new EntityNotFoundException("Курс с id " + courseId + " не найден");
        }
        if (!studentRepository.existsById(studentId)) {
            throw new EntityNotFoundException("Студент с id " + studentId + " не найден");
        }
        Course course = coursesRepository.getById(courseId);
        User student = studentRepository.getById(studentId);
        course.getStudents().remove(student);
        student.getCourse().remove(course);
        coursesRepository.save(course);
        studentRepository.save(student);
    }
}
