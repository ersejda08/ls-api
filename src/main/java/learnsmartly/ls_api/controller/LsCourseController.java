package learnsmartly.ls_api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import learnsmartly.ls_api.entity.LsCourses;
import learnsmartly.ls_api.service.LsCourseService;

@RestController
@RequestMapping("/api/courses")
public class LsCourseController {

    private final LsCourseService courseService;

    public LsCourseController(LsCourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public LsCourses create(@RequestBody LsCourses course) {
        return courseService.createCourse(course);
    }

    @GetMapping
    public List<LsCourses> all() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    public LsCourses one(@PathVariable Long id) {
        return courseService.getCourseById(id);
    }
}
