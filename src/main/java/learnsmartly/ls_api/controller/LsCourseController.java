package learnsmartly.ls_api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import learnsmartly.ls_api.dto.request.CourseRequestDTO;
import learnsmartly.ls_api.dto.response.CourseResponseDTO;
import learnsmartly.ls_api.service.LsCourseService;

@RestController
@RequestMapping("/api/courses")
public class LsCourseController {

    private final LsCourseService courseService;

    public LsCourseController(LsCourseService courseService) {
        this.courseService = courseService;
    }

    // Any authenticated user can list courses
    @GetMapping
    public ResponseEntity<List<CourseResponseDTO>> listAll() {
        return ResponseEntity.ok(courseService.listAllCourses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourse(id));
    }

    // Teacher-only CRUD
    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping
    public ResponseEntity<CourseResponseDTO> create(@Valid @RequestBody CourseRequestDTO req) {
        return ResponseEntity.ok(courseService.createCourse(req));
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PutMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> update(@PathVariable Long id,
                                                    @Valid @RequestBody CourseRequestDTO req) {
        return ResponseEntity.ok(courseService.updateCourse(id, req));
    }

    @PreAuthorize("hasRole('TEACHER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}
