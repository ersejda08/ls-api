package learnsmartly.ls_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import learnsmartly.ls_api.entity.LsCourses;

public interface LsCoursesRepository extends JpaRepository<LsCourses, Long> {
}
