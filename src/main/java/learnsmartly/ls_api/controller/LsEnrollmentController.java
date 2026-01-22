package learnsmartly.ls_api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import learnsmartly.ls_api.dto.response.EnrollmentResponseDTO;
import learnsmartly.ls_api.service.LsEnrollmentService;

@RestController
@RequestMapping("/api")
public class LsEnrollmentController {

    private final LsEnrollmentService enrollmentService;

    public LsEnrollmentController(LsEnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    // Student enrolls themselves (userId taken from JWT principal)
    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/courses/{courseId}/enroll")
    public ResponseEntity<EnrollmentResponseDTO> enroll(@PathVariable Long courseId) {
        Long studentId = getCurrentUserId();
        return ResponseEntity.ok(enrollmentService.enrollSelf(studentId, courseId));
    }

    @PreAuthorize("hasRole('STUDENT')")
    @DeleteMapping("/courses/{courseId}/unenroll")
    public ResponseEntity<Void> unenroll(@PathVariable Long courseId) {
        Long studentId = getCurrentUserId();
        enrollmentService.unenrollSelf(studentId, courseId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/my/enrollments")
    public ResponseEntity<List<EnrollmentResponseDTO>> myEnrollments() {
        Long studentId = getCurrentUserId();
        return ResponseEntity.ok(enrollmentService.listMyEnrollments(studentId));
    }

    // Teacher can see enrollments and counts (MVP: one teacher => no ownership checks)
    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/courses/{courseId}/enrollments")
    public ResponseEntity<List<EnrollmentResponseDTO>> courseEnrollments(@PathVariable Long courseId) {
        return ResponseEntity.ok(enrollmentService.listEnrollmentsForCourse(courseId));
    }

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/courses/{courseId}/enrollments/count")
    public ResponseEntity<Long> enrollmentCount(@PathVariable Long courseId) {
        return ResponseEntity.ok(enrollmentService.countEnrollmentsForCourse(courseId));
    }

    private Long getCurrentUserId() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getPrincipal() == null) {
            throw new RuntimeException("Unauthorized");
        }
        return Long.valueOf(auth.getPrincipal().toString());
    }
}
