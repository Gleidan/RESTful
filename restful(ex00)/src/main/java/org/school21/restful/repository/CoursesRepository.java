package org.school21.restful.repository;

import org.school21.restful.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CoursesRepository extends JpaRepository<Course, Long> {
}
