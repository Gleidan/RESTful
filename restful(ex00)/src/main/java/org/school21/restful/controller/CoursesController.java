package org.school21.restful.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.school21.restful.exception.EntityNotFoundException;
import org.school21.restful.model.Course;
import org.school21.restful.model.Lesson;
import org.school21.restful.model.User;
import org.school21.restful.service.CoursesService;
import org.school21.restful.service.LessonService;
import org.school21.restful.service.StudentsService;
import org.school21.restful.service.TeachersService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Курсы, уроки, учителя, студенты", description = "Управление информацией по курсам")
public class CoursesController {

    private final CoursesService coursesService;
    private final LessonService lessonService;
    private final StudentsService studentsService;
    private final TeachersService teachersService;

    @Operation(summary = "getAllCourses")
    @GetMapping(value = {"/courses", "/courses/page/{page-num}/size/{page-size}"})
    public ResponseEntity<List<Course>> getAllCourses(@PathVariable(required = false, name = "page-num") Integer page,
                                                      @PathVariable(required = false, name = "page-size") Integer size) {
        Pageable pageable = page == null || size == null ? PageRequest.of(0, 10) : PageRequest.of(page, size);
        return ResponseEntity.ok().body(coursesService.getAllCourses(pageable));
    }

    @Operation(summary = "addCourse")
    @PostMapping("/courses")
    public ResponseEntity<Object> addCourse(@RequestBody Course course) {
        log.info("Получен запрос на добавление нового курса. Course {}", course.toString());
        return ResponseEntity.ok(coursesService.addCourse(course).getId());
    }

    @Operation(summary = "getCourse")
    @GetMapping("/courses/{course-id}")
    public ResponseEntity<Object> getCourse(@PathVariable("course-id") Long courseId) {
        return ResponseEntity.ok(coursesService.getCourseById(courseId));
    }

    @Operation(summary = "updateCourse")
    @PutMapping("/courses/{course-id}")
    public ResponseEntity<Object> updateCourse(@RequestBody Course course, @PathVariable("course-id") Long courseId) {
        return ResponseEntity.ok(coursesService.updateCourseById(courseId, course));
    }

    @Operation(summary = "deleteCourse")
    @DeleteMapping("/courses/{course-id}")
    public ResponseEntity<String> deleteCourse(@PathVariable("course-id") Long courseId) {
        coursesService.deleteCourse(courseId);
        return ResponseEntity.ok("Course with id " + courseId + " deleted");
    }

    @Operation(summary = "getLessonsByCourse")
    @GetMapping(value = {"/courses/{course-id}/lessons", "/courses/{course-id}/lessons/page/{page-num}/size/{page-size}"})
    public ResponseEntity<List<Lesson>> getLessonsByCourse(@PathVariable("course-id") Long courseId,
                                                           @PathVariable(required = false, name = "page-num") Integer page,
                                                           @PathVariable(required = false, name = "page-size") Integer size) {
        Pageable pageable = page == null || size == null ? PageRequest.of(0, 10) : PageRequest.of(page, size);
        return ResponseEntity.ok(lessonService.getLessonsByCourse(courseId, pageable));
    }

    @Operation(summary = "addLessonToCourse")
    @PostMapping("/courses/{course-id}/lessons")
    public ResponseEntity<Object> addLessonToCourse(@PathVariable("course-id") Long courseId, @RequestBody Lesson lesson) {
        log.info("Получен запрос на добавление нового урока {} в курс {}", lesson, courseId);
        Course course = new Course();
        course.setId(courseId);
        lesson.setCourse(course);
        return ResponseEntity.ok(lessonService.addLessonToCourse(lesson).getId());
    }

    @Operation(summary = "updateLessonInCourse")
    @PutMapping("/courses/{course-id}/lessons/{lesson-id}")
    public ResponseEntity<Object> updateLessonInCourse(@PathVariable("course-id") Long courseId,
                                                       @PathVariable("lesson-id") Long lessonId,
                                                       @RequestBody Lesson lesson) {
        log.info("Получен запрос на обновление урока {} в курсе {}", lesson, courseId);
        lesson.setId(lessonId);
        return ResponseEntity.ok(lessonService.updateLessonInCourse(lesson, courseId));
    }

    @Operation(summary = "deleteLessonFromCourse")
    @DeleteMapping("/courses/{course-id}/lessons/{lesson-id}")
    public ResponseEntity<String> deleteLessonFromCourse(@PathVariable("course-id") Long courseId, @PathVariable("lesson-id") Long lessonId) {
        lessonService.deleteLessonFromCourse(courseId, lessonId);
        return ResponseEntity.ok("Lesson with id " + lessonId + " deleted");

    }

    @Operation(summary = "getStudentsByCourse")
    @GetMapping(value = {"/courses/{course-id}/students", "/courses/{course-id}/students/page/{page-num}/size/{page-size}"})
    public ResponseEntity<List<User>> getStudentsByCourse(@PathVariable("course-id") Long courseId,
                                                          @PathVariable(name = "page-num", required = false) Integer pageNum,
                                                          @PathVariable(name = "page-size", required = false) Integer pageSize) {
        Pageable pageable = pageNum == null || pageSize == null ? PageRequest.of(0, 10) : PageRequest.of(pageNum, pageSize);
        return ResponseEntity.ok().body(studentsService.getStudentsByCourse(courseId, pageable));
    }

    @Operation(summary = "addStudentToCourse")
    @PostMapping("/courses/{course-id}/students")
    public ResponseEntity<Object> addStudentToCourse(@PathVariable("course-id") Long courseId, @RequestBody User user) {
        log.info("Получен запрос на добавление нового студента {} в курс {}", user, courseId);
        return ResponseEntity.ok(studentsService.addStudentToCourse(courseId, user).getId());
    }

    @Operation(summary = "deleteStudentFromCourse")
    @DeleteMapping("/courses/{course-id}/students/{student-id}")
    public ResponseEntity<Object> deleteStudentFromCourse(@PathVariable("course-id") Long courseId, @PathVariable("student-id") Long studentId) {
        studentsService.deleteStudentFromCourse(courseId, studentId);
        return ResponseEntity.ok("SUCCESS");
    }

    @Operation(summary = "getTeachersByCourse")
    @GetMapping(value = {"/courses/{course-id}/teachers", "/courses/{course-id}/teachers/page/{page-num}/size/{page-size}"})
    public ResponseEntity<List<User>> getTeachersByCourse(@PathVariable("course-id") Long courseId,
                                                          @PathVariable(name = "page-num", required = false) Integer pageNum,
                                                          @PathVariable(name = "page-size", required = false) Integer pageSize) {
        Pageable pageable = pageNum == null || pageSize == null ? PageRequest.of(0, 10) : PageRequest.of(pageNum, pageSize);
        return ResponseEntity.ok(teachersService.getTeachersByCourse(courseId, pageable));
    }

    @Operation(summary = "addTeacherToCourse")
    @PostMapping("/courses/{course-id}/teachers")
    public ResponseEntity<Object> addTeacherToCourse(@PathVariable("course-id") Long courseId, @RequestBody User teacher) {
        teachersService.addTeacherToCourse(courseId, teacher);
        return ResponseEntity.ok("SUCCESS");
    }

    @Operation(summary = "deleteTeacherFromCourse")
    @DeleteMapping("/courses/{course-id}/teachers/{teacher-id}")
    public ResponseEntity<Object> deleteTeacherFromCourse(@PathVariable("course-id") Long courseId, @PathVariable("teacher-id") Long teacherId) {
        teachersService.deleteTeacherFromCourse(courseId, teacherId);
        return ResponseEntity.ok("SUCCESS");
    }
}
