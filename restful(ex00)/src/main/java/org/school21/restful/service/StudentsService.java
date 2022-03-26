package org.school21.restful.service;

import org.school21.restful.model.User;

import java.util.List;

public interface StudentsService {

    List<User> getStudentsByCourse(Long courseId);
    User addStudentToCourse(Long courseId, User student);
    void deleteStudentFromCourse(Long courseId, Long studentId);
}
