package org.school21.restful.service;

import org.school21.restful.model.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentsService {

    List<User> getStudentsByCourse(Long courseId, Pageable pageable);
    User addStudentToCourse(Long courseId, User student);
    void deleteStudentFromCourse(Long courseId, Long studentId);
}
