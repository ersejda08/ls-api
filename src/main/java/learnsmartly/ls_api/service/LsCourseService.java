package learnsmartly.ls_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import learnsmartly.ls_api.dto.request.CourseRequestDTO;
import learnsmartly.ls_api.dto.response.CourseResponseDTO;
import learnsmartly.ls_api.entity.LsCourses;
import learnsmartly.ls_api.repository.LsCoursesRepository;
import learnsmartly.ls_api.repository.LsEnrollmentsRepository;
import learnsmartly.ls_api.exception.NotFoundException;

@Service
public class LsCourseService {

    private final LsCoursesRepository coursesRepository;
    private final LsEnrollmentsRepository enrollmentsRepository;

    public LsCourseService(LsCoursesRepository coursesRepository,
                           LsEnrollmentsRepository enrollmentsRepository) {
        this.coursesRepository = coursesRepository;
        this.enrollmentsRepository = enrollmentsRepository;
    }

    public List<CourseResponseDTO> listAllCourses() {
        return coursesRepository.findAll().stream()
                .map(c -> toCourseResponse(c, enrollmentsRepository.countByCourseId(c.getId())))
                .toList();
    }

    public CourseResponseDTO getCourse(Long id) {
        LsCourses c = coursesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Course not found: " + id));
        long count = enrollmentsRepository.countByCourseId(id);
        return toCourseResponse(c, count);
    }

    public CourseResponseDTO createCourse(CourseRequestDTO req) {
        LsCourses c = new LsCourses();
        c.setCourseName(req.getCourseName());
        c.setDescription(req.getDescription());
        c.setCapacity(req.getCapacity());

        LsCourses saved = coursesRepository.save(c);
        return toCourseResponse(saved, 0);
    }

    public CourseResponseDTO updateCourse(Long id, CourseRequestDTO req) {
        LsCourses c = coursesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Course not found: " + id));

        c.setCourseName(req.getCourseName());
        c.setDescription(req.getDescription());
        c.setCapacity(req.getCapacity());

        LsCourses saved = coursesRepository.save(c);
        long count = enrollmentsRepository.countByCourseId(id);
        return toCourseResponse(saved, count);
    }

    public void deleteCourse(Long id) {
        if (!coursesRepository.existsById(id)) {
            throw new NotFoundException("Course not found: " + id);
        }
        coursesRepository.deleteById(id);
    }

    private CourseResponseDTO toCourseResponse(LsCourses c, long enrolledCount) {
        return new CourseResponseDTO(
                c.getId(),
                c.getCourseName(),
                c.getDescription(),
                c.getCapacity(),
                enrolledCount
        );
    }
}
