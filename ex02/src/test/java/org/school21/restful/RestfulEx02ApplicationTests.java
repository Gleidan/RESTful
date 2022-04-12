package org.school21.restful;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.school21.restful.exception.EntityNotFoundException;
import org.school21.restful.model.Course;
import org.school21.restful.model.State;
import org.school21.restful.repository.CoursesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.rest.webmvc.RestMediaTypes;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureRestDocs("target/generated-snippets")
class RestfulEx02ApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CoursesRepository coursesRepository;

    @BeforeEach
    public void setUp() {
        Course course = coursesRepository.findById(1L).orElseThrow(EntityNotFoundException::new);
        course.setState(State.DRAFT);
        coursesRepository.save(course);
    }

    @Test
    public void getCourseByIdTest() throws Exception {
        ResultActions resultActions = mockMvc.perform(put("/courses/1/publish")
                .accept(RestMediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        resultActions.andDo(document("publishTest"));
    }
}
