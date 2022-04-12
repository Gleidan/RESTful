package org.school21.restful.config;

import org.school21.restful.controller.CourseController;
import org.school21.restful.model.Course;
import org.school21.restful.model.State;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CourseStateConfig implements RepresentationModelProcessor<EntityModel<Course>> {

    @Override
    public EntityModel<Course> process(EntityModel<Course> model) {
        if (model.getContent().getState().equals(State.DRAFT)) {
            model.add(WebMvcLinkBuilder.linkTo(methodOn(CourseController.class).publish(model.getContent().getId(), null)).withRel("publish"));
        }
        return model;
    }
}
