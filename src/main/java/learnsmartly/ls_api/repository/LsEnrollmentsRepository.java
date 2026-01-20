package learnsmartly.ls_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import learnsmartly.ls_api.entity.EnrollmentStatus;
import learnsmartly.ls_api.entity.LsCourses;
import learnsmartly.ls_api.entity.LsEnrollments;
import learnsmartly.ls_api.entity.LsUser;

public interface LsEnrollmentsRepository extends JpaRepository<LsEnrollments, Long> {

    // Prevent duplicate enrollments
    boolean existsByCourseAndStudent(LsCourses course, LsUser student);

    Optional<LsEnrollments> findByCourseAndStudent(LsCourses course, LsUser student);

    // Student: list my enrollments
    List<LsEnrollments> findByStudentId(Long studentId);

    // Teacher: list enrollments for a course
    List<LsEnrollments> findByCourseId(Long courseId);

    long countByCourseIdAndStatus(Long courseId, EnrollmentStatus approved);
}
