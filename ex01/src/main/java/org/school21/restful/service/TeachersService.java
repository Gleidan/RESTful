package org.school21.restful.service;

import org.school21.restful.model.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TeachersService {

    List<User> getTeachersByCourse(Long courseId, Pageable pageable);
    void addTeacherToCourse(Long courseId, User teacher);
    void deleteTeacherFromCourse(Long courseId, Long teacherId);
}
