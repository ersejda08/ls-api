package learnsmartly.ls_api.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import learnsmartly.ls_api.dto.response.EnrollmentResponseDTO;
import learnsmartly.ls_api.entity.LsCourses;
import learnsmartly.ls_api.entity.LsEnrollments;
import learnsmartly.ls_api.entity.LsUser;
import learnsmartly.ls_api.exception.ConflictException;
import learnsmartly.ls_api.exception.NotFoundException;
import learnsmartly.ls_api.repository.LsCoursesRepository;
import learnsmartly.ls_api.repository.LsEnrollmentsRepository;
import learnsmartly.ls_api.repository.LsUserRepository;

@Service
public class LsEnrollmentService {

    private final LsEnrollmentsRepository enrollmentsRepository;
    private final LsCoursesRepository coursesRepository;
    private final LsUserRepository userRepository;

    public LsEnrollmentService(LsEnrollmentsRepository enrollmentsRepository,
                               LsCoursesRepository coursesRepository,
                               LsUserRepository userRepository) {
        this.enrollmentsRepository = enrollmentsRepository;
        this.coursesRepository = coursesRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public EnrollmentResponseDTO enrollSelf(Long studentId, Long courseId) {
        LsUser student = userRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("Student not found: " + studentId));

        LsCourses course = coursesRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Course not found: " + courseId));

        if (enrollmentsRepository.existsByStudentIdAndCourseId(studentId, courseId)) {
            throw new ConflictException("You are already enrolled in this course.");
        }

        long currentCount = enrollmentsRepository.countByCourseId(courseId);
        if (course.getCapacity() != null && currentCount >= course.getCapacity()) {
            throw new ConflictException("Course is full.");
        }

        LsEnrollments e = new LsEnrollments();
        e.setStudent(student);
        e.setCourse(course);

        LsEnrollments saved = enrollmentsRepository.save(e);
        return toDTO(saved);
    }

    @Transactional
    public void unenrollSelf(Long studentId, Long courseId) {
        if (!enrollmentsRepository.existsByStudentIdAndCourseId(studentId, courseId)) {
            throw new NotFoundException("Enrollment not found for student " + studentId + " and course " + courseId);
        }
        enrollmentsRepository.deleteByStudentIdAndCourseId(studentId, courseId);
    }

    public List<EnrollmentResponseDTO> listMyEnrollments(Long studentId) {
        return enrollmentsRepository.findByStudentId(studentId)
                .stream().map(this::toDTO).toList();
    }

    public List<EnrollmentResponseDTO> listEnrollmentsForCourse(Long courseId) {
        // teacher-only endpoint will call this
        if (!coursesRepository.existsById(courseId)) {
            throw new NotFoundException("Course not found: " + courseId);
        }
        return enrollmentsRepository.findByCourseId(courseId)
                .stream().map(this::toDTO).toList();
    }

    public long countEnrollmentsForCourse(Long courseId) {
        if (!coursesRepository.existsById(courseId)) {
            throw new NotFoundException("Course not found: " + courseId);
        }
        return enrollmentsRepository.countByCourseId(courseId);
    }

    private EnrollmentResponseDTO toDTO(LsEnrollments e) {
        return new EnrollmentResponseDTO(
                e.getId(),
                e.getStudent().getId(),
                e.getStudent().getUsername(),
                e.getStudent().getEmail(),
                e.getCourse().getId(),
                e.getCourse().getCourseName()
        );
    }
}
