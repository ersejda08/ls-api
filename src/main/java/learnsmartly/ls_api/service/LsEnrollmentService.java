package learnsmartly.ls_api.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import learnsmartly.ls_api.entity.LsCourses;
import learnsmartly.ls_api.entity.LsEnrollments;
import learnsmartly.ls_api.entity.LsUser;
import learnsmartly.ls_api.repository.LsCoursesRepository;
import learnsmartly.ls_api.repository.LsEnrollmentsRepository;
import learnsmartly.ls_api.repository.LsUserRepository;

@Service
public class LsEnrollmentService {

    private final LsEnrollmentsRepository enrollmentsRepository;
    private final LsCoursesRepository coursesRepository;
    private final LsUserRepository userRepository;

    public LsEnrollmentService(
            LsEnrollmentsRepository enrollmentsRepository,
            LsCoursesRepository coursesRepository,
            LsUserRepository userRepository
    ) {
        this.enrollmentsRepository = enrollmentsRepository;
        this.coursesRepository = coursesRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public LsEnrollments enroll(Long courseId, Long studentId) {
        LsCourses course = coursesRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found with id: " + courseId));

        LsUser student = userRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + studentId));

        // Prevent duplicate enrollments
        if (enrollmentsRepository.existsByCourseAndStudent(course, student)) {
            throw new IllegalArgumentException("Student is already enrolled in this course");
        }

        
        long currentEnrollmentCount = enrollmentsRepository.countByCourseId(courseId);
        Integer capacity = course.getCapacity();

        if (capacity != null && currentEnrollmentCount >= capacity) {
            throw new IllegalArgumentException("Course is full");
        }

        LsEnrollments enrollment = new LsEnrollments();
        enrollment.setCourse(course);
        enrollment.setStudent(student);

        return enrollmentsRepository.save(enrollment);
    }

    public List<LsEnrollments> getEnrollmentsByStudent(Long studentId) {
        return enrollmentsRepository.findByStudentId(studentId);
    }

    public List<LsEnrollments> getEnrollmentsByCourse(Long courseId) {
        return enrollmentsRepository.findByCourseId(courseId);
    }
}
