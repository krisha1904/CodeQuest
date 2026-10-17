package com.codequest.service;

import com.codequest.entity.Course;
import com.codequest.entity.CourseModule;
import com.codequest.exception.ResourceNotFoundException;
import com.codequest.repository.CourseModuleRepository;
import com.codequest.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseModuleRepository courseModuleRepository;

    @Transactional(readOnly = true)
    public List<Course> getAllPublishedCourses() {
        return courseRepository.findByIsPublishedTrue();
    }

    @Transactional(readOnly = true)
    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<CourseModule> getModulesByCourseId(Long courseId) {
        return courseModuleRepository.findByCourseIdOrderBySequenceOrderAsc(courseId);
    }
}
