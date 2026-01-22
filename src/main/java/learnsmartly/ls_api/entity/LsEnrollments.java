package learnsmartly.ls_api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "ls_enrollments",
    uniqueConstraints = @UniqueConstraint(columnNames = {"course_id", "student_id"})
)
public class LsEnrollments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many enrollments -> one course
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private LsCourses course;

    // Many enrollments -> one student (user)
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private LsUser student;


    // Constructors
    public LsEnrollments() {
    }

    public LsEnrollments(Long id, LsCourses course, LsUser student) {
        this.id = id;
        this.course = course;
        this.student = student;
       
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LsCourses getCourse() {
        return course;
    }

    public void setCourse(LsCourses course) {
        this.course = course;
    }

    public LsUser getStudent() {
        return student;
    }

    public void setStudent(LsUser student) {
        this.student = student;
    }


}