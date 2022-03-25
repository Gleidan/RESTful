package org.school21.restful.service;

import org.school21.restful.model.Course;

import java.util.List;

public interface CoursesService {

    List<Course> getAllCourses();
    Course addCourse(Course course);
    Course getCourseById(Long id);
    Course updateCourseById(Long id, Course course);
    void deleteUser(Long id);
}
