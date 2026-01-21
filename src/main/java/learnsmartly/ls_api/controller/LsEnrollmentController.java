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
@RequestMapping("/api")
public class LsEnrollmentController {

    private final LsEnrollmentService enrollmentService;

    public LsEnrollmentController(LsEnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    /**
     * STUDENT action (temporary until JWT):
     * Enroll a student into a course.
     * Later: remove studentId from request and take it from the JWT (logged-in user).
     */
    @PostMapping("/courses/{courseId}/enroll")
    public LsEnrollments enroll(@PathVariable Long courseId, @RequestParam Long studentId) {
        return enrollmentService.enroll(courseId, studentId);
    }

    /**
     * STUDENT action (temporary until JWT):
     * View my enrollments.
     * Later: remove studentId from request and take it from the JWT (logged-in user).
     */
    @GetMapping("/my/enrollments")
    public List<LsEnrollments> myEnrollments(@RequestParam Long studentId) {
        return enrollmentService.getEnrollmentsByStudent(studentId);
    }

    /**
     * TEACHER action:
     * View all enrollments (students) for a specific course.
     */
    @GetMapping("/courses/{courseId}/enrollments")
    public List<LsEnrollments> courseEnrollments(@PathVariable Long courseId) {
        return enrollmentService.getEnrollmentsByCourse(courseId);
    }
}
