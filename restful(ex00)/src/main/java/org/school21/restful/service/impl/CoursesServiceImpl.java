package org.school21.restful.service.impl;

import lombok.RequiredArgsConstructor;
import org.school21.restful.exception.EntityNotFoundException;
import org.school21.restful.model.Course;
import org.school21.restful.repository.CoursesRepository;
import org.school21.restful.service.CoursesService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoursesServiceImpl implements CoursesService {

    private final CoursesRepository coursesRepository;

    @Override
    public List<Course> getAllCourses(Pageable pageable) {
        return coursesRepository.findAll(pageable).getContent();
    }

    @Override
    public Course addCourse(Course course) {
        return coursesRepository.save(course);
    }

    @Override
    public Course getCourseById(Long id) {
        Course course = coursesRepository.findById(id).orElse(null);
        if (course == null) {
            throw new EntityNotFoundException("Course with id " + id + " not found");
        }
        return course;
    }

    @Override
    public Course updateCourseById(Long id, Course course) {
        Course courseToUpdate = coursesRepository.findById(id).orElse(null);
        if (courseToUpdate == null) {
            throw new EntityNotFoundException("Course with id " + id + " not found");
        }
        if (course.getEndDate() != null) {
            courseToUpdate.setEndDate(course.getEndDate());
        }
        if (course.getStartDate() != null) {
            courseToUpdate.setStartDate(course.getStartDate());
        }
        if (course.getName() != null) {
            courseToUpdate.setName(course.getName());
        }
        if (course.getDescription() != null) {
            courseToUpdate.setDescription(course.getDescription());
        }
        return coursesRepository.save(courseToUpdate);
    }

    @Override
    public void deleteCourse(Long id) {
        if (!coursesRepository.existsById(id)) {
            throw new EntityNotFoundException("Course with id " + id + " not found");
        }
        Course course = coursesRepository.getById(id);
        coursesRepository.delete(course);
    }
}
