package learnsmartly.ls_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import learnsmartly.ls_api.entity.LsCourses;
import learnsmartly.ls_api.repository.LsCoursesRepository;

@Service
public class LsCourseService {

    private final LsCoursesRepository coursesRepository;

    public LsCourseService(LsCoursesRepository coursesRepository) {
        this.coursesRepository = coursesRepository;
    }

    public LsCourses createCourse(LsCourses course) {
        // minimal validation (keep simple)
        if (course.getCourseName() == null || course.getCourseName().trim().isEmpty()) {
            throw new IllegalArgumentException("courseName is required");
        }
        if (course.getCapacity() == null || course.getCapacity() < 1) {
            throw new IllegalArgumentException("capacity must be >= 1");
        }

        // Create should NOT accept id (prevents accidental updates)
        course.setId(null);

        return coursesRepository.save(course);
    }

    public LsCourses updateCourse(Long id, LsCourses course) {
        // minimal validation (keep simple)
        if (course.getCourseName() == null || course.getCourseName().trim().isEmpty()) {
            throw new IllegalArgumentException("courseName is required");
        }
        if (course.getCapacity() == null || course.getCapacity() < 1) {
            throw new IllegalArgumentException("capacity must be >= 1");
        }

        // Ensure the course exists, then update only the allowed fields
        LsCourses existing = coursesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Course not found with id: " + id));

        existing.setCourseName(course.getCourseName());
        existing.setDescription(course.getDescription());
        existing.setCapacity(course.getCapacity());

        return coursesRepository.save(existing);
    }

    public void deleteCourse(Long id) {
        // Ensure the course exists (otherwise delete may silently do nothing)
        if (!coursesRepository.existsById(id)) {
            throw new IllegalArgumentException("Course not found with id: " + id);
        }
        coursesRepository.deleteById(id);
    }

    public List<LsCourses> getAllCourses() {
        return coursesRepository.findAll();
    }

    public LsCourses getCourseById(Long id) {
        return coursesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Course not found with id: " + id));
    }
}
