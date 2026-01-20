package learnsmartly.ls_api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import learnsmartly.ls_api.entity.LsEnrollments;
import learnsmartly.ls_api.service.LsEnrollmentService;


@RestController
@RequestMapping("/api/enrollments")
public class LsEnrollmentController {

    private final LsEnrollmentService enrollmentService;

    public LsEnrollmentController(LsEnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    // enroll: courseId + studentId (temporary until JWT)
    @PostMapping
    public LsEnrollments enroll(@RequestParam Long courseId, @RequestParam Long studentId) {
        return enrollmentService.enroll(courseId, studentId);
    }

    @GetMapping("/by-student/{studentId}")
    public List<LsEnrollments> byStudent(@PathVariable Long studentId) {
        return enrollmentService.getEnrollmentsByStudent(studentId);
    }

    @GetMapping("/by-course/{courseId}")
    public List<LsEnrollments> byCourse(@PathVariable Long courseId) {
        return enrollmentService.getEnrollmentsByCourse(courseId);
    }
}
