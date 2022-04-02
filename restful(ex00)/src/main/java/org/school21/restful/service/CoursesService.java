package org.school21.restful.service;

import org.school21.restful.model.Course;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CoursesService {

    List<Course> getAllCourses(Pageable pageable);
    Course addCourse(Course course);
    Course getCourseById(Long id);
    Course updateCourseById(Long id, Course course);
    void deleteUser(Long id);
}
