package org.school21.restful.controller;

import lombok.AllArgsConstructor;
import org.school21.restful.exception.EntityNotFoundException;
import org.school21.restful.model.Course;
import org.school21.restful.repository.CoursesRepository;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RepositoryRestController
@AllArgsConstructor
public class CourseController {

    private final CoursesRepository coursesRepository;

    @RequestMapping(value = "/courses/{courseId}/publish", method = RequestMethod.PUT)
    @ResponseBody
    public PersistentEntityResource publish(@PathVariable("courseId") Long courseId, PersistentEntityResourceAssembler asm) {
        Course course = coursesRepository.findById(courseId).orElseThrow(EntityNotFoundException::new);
        course.publish();
        return asm.toFullResource(coursesRepository.save(course));
    }
}
