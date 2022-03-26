package org.school21.restful.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.school21.restful.exception.EntityNotFoundException;
import org.school21.restful.model.Course;
import org.school21.restful.model.Lesson;
import org.school21.restful.model.User;
import org.school21.restful.service.CoursesService;
import org.school21.restful.service.LessonService;
import org.school21.restful.service.StudentsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class CoursesController {

    private final CoursesService coursesService;
    private final LessonService lessonService;
    private final StudentsService studentsService;

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok().body(coursesService.getAllCourses());
    }

    @PostMapping("/courses")
    public ResponseEntity<Object> addCourse(@RequestBody Course course) {
        log.info("Получен запрос на добавление нового курса. Course {}", course.toString());
        return ResponseEntity.ok(coursesService.addCourse(course).getId());
    }

    @GetMapping("/courses/{course-id}")
    public ResponseEntity<Object> getCourse(@PathVariable("course-id") Long courseId) {
        try {
            return ResponseEntity.ok(coursesService.getCourseById(courseId));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/courses/{course-id}")
    public ResponseEntity<Object> updateCourse(@RequestBody Course course, @PathVariable("course-id") Long courseId) {
        try {
            return ResponseEntity.ok(coursesService.updateCourseById(courseId, course));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/courses/{course-id}")
    public ResponseEntity<String> deleteCourse(@PathVariable("course-id") Long courseId) {
        try {
            coursesService.deleteUser(courseId);
            return ResponseEntity.ok("Course with id " + courseId + " deleted");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/courses/{course-id}/lessons")
    public ResponseEntity<List<Lesson>> getLessonsByCourse(@PathVariable("course-id") Long courseId) {
        return ResponseEntity.ok(lessonService.getLessonsByCourse(courseId));
    }

    @PostMapping("/courses/{course-id}/lessons")
    public ResponseEntity<Object> addLessonToCourse(@PathVariable("course-id") Long courseId, @RequestBody Lesson lesson) {
        log.info("Получен запрос на добавление нового урока {} в курс {}", lesson, courseId);
        Course course = new Course();
        course.setId(courseId);
        lesson.setCourse(course);
        return ResponseEntity.ok(lessonService.addLessonToCourse(lesson).getId());
    }

    @PutMapping("/courses/{course-id}/lessons/{lesson-id}")
    public ResponseEntity<Object> updateLessonInCourse(@PathVariable("course-id") Long courseId,
                                                       @PathVariable("lesson-id") Long lessonId,
                                                       @RequestBody Lesson lesson) {
        log.info("Получен запрос на обновление урока {} в курсе {}", lesson, courseId);
        lesson.setId(lessonId);
        try {
            return ResponseEntity.ok(lessonService.updateLessonInCourse(lesson, courseId));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/courses/{course-id}/lessons/{lesson-id}")
    public ResponseEntity<String> deleteLessonFromCourse(@PathVariable("course-id") Long courseId, @PathVariable("lesson-id") Long lessonId) {
        try {
            lessonService.deleteLessonFromCourse(courseId, lessonId);
            return ResponseEntity.ok("Lesson with id " + lessonId + " deleted");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/courses/{course-id}/students")
    public ResponseEntity<Object> getStudentsByCourse(@PathVariable("course-id") Long courseId) {
        return ResponseEntity.ok().body(studentsService.getStudentsByCourse(courseId));
    }


    @PostMapping("/courses/{course-id}/students")
    public ResponseEntity<Object> addStudentToCourse(@PathVariable("course-id") Long courseId, @RequestBody User user) {
        log.info("Получен запрос на добавление нового студента {} в курс {}", user, courseId);
        try {
            return ResponseEntity.ok(studentsService.addStudentToCourse(courseId, user).getId());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/courses/{course-id}/students/{student-id}")
    public ResponseEntity<Object> deleteStudentFromCourse(@PathVariable("course-id") Long courseId, @PathVariable("student-id") Long studentId) {
        try {
            studentsService.deleteStudentFromCourse(courseId, studentId);
            return ResponseEntity.ok("SUCCESS");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
