package com.codequest.controller;

import com.codequest.entity.Course;
import com.codequest.entity.CourseModule;
import com.codequest.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<List<Course>> getPublishedCourses() {
        return ResponseEntity.ok(courseService.getAllPublishedCourses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @GetMapping("/{id}/modules")
    public ResponseEntity<List<CourseModule>> getCourseModules(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getModulesByCourseId(id));
    }
}
