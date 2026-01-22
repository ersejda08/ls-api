package learnsmartly.ls_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import learnsmartly.ls_api.entity.LsEnrollments;

public interface LsEnrollmentsRepository extends JpaRepository<LsEnrollments, Long> 
{

    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);

    List<LsEnrollments> findByStudentId(Long studentId);

    List<LsEnrollments> findByCourseId(Long courseId);

    long countByCourseId(Long courseId);

    void deleteByStudentIdAndCourseId(Long studentId, Long courseId);
}



