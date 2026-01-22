package learnsmartly.ls_api.dto.response;

public class EnrollmentResponseDTO {

    private Long enrollmentId;

    private Long studentId;
    private String studentUsername;
    private String studentEmail;

    private Long courseId;
    private String courseName;

    public EnrollmentResponseDTO() {}

    public EnrollmentResponseDTO(Long enrollmentId,
                                 Long studentId, String studentUsername, String studentEmail,
                                 Long courseId, String courseName) {
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.studentUsername = studentUsername;
        this.studentEmail = studentEmail;
        this.courseId = courseId;
        this.courseName = courseName;
    }

    public Long getEnrollmentId() { return enrollmentId; }
    public void setEnrollmentId(Long enrollmentId) { this.enrollmentId = enrollmentId; }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public String getStudentUsername() { return studentUsername; }
    public void setStudentUsername(String studentUsername) { this.studentUsername = studentUsername; }

    public String getStudentEmail() { return studentEmail; }
    public void setStudentEmail(String studentEmail) { this.studentEmail = studentEmail; }

    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
}
