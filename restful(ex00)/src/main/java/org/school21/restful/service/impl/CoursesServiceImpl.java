package org.school21.restful.service.impl;

import lombok.RequiredArgsConstructor;
import org.school21.restful.model.Course;
import org.school21.restful.repository.CoursesRepository;
import org.school21.restful.service.CoursesService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoursesServiceImpl implements CoursesService {

    private final CoursesRepository coursesRepository;

    @Override
    public List<Course> getAllCourses() {
        return coursesRepository.findAll();
    }
}
