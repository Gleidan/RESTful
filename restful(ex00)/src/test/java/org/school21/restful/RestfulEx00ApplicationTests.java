package org.school21.restful;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.school21.restful.model.Course;
import org.school21.restful.service.CoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
class RestfulEx00ApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CoursesService coursesService;

    private final Date now = new Date();

    @BeforeEach
    public void setUp() {
        Course testCourse = new Course(1L, now, now, "Test course", new ArrayList<>(), new ArrayList<>(), "Desc test", new ArrayList<>());
        when(coursesService.getCourseById(any())).thenReturn(testCourse);
        when(coursesService.addCourse(any())).thenReturn(testCourse);
        when(coursesService.updateCourseById(any(), any())).thenReturn(testCourse);
    }

    @Test
    public void getCourseByIdTest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/courses/1").accept(MediaType.APPLICATION_JSON)).andReturn();
        assertEquals(200, result.getResponse().getStatus());
        assertNotNull(result.getResponse().getContentAsString());

        Course course = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Course>() {
        });

        assertNotNull(course);
        assertEquals("Test course", course.getName());
    }

    @Test
    public void addCourseTest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/courses")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new Course()))
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());
        assertNotNull(result.getResponse().getContentAsString());

        Long id = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Long>() {
        });

        assertNotNull(id);
        assertEquals(1L, id);
    }

    @Test
    public void updateCourseTest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/courses/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new Course()))
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());
        assertNotNull(result.getResponse().getContentAsString());

        Course course = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Course>() {
        });

        assertNotNull(course);
        assertEquals(1L, course.getId());
    }

    @Test
    public void deleteCourseTest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/courses/1")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        assertEquals(200, result.getResponse().getStatus());
        assertNotNull(result.getResponse().getContentAsString());
    }
}
