package learnsmartly.ls_api.dto.response;

public class CourseResponseDTO {

    private Long id;
    private String courseName;
    private String description;
    private Integer capacity;
    private long enrolledCount;

    public CourseResponseDTO() {}

    public CourseResponseDTO(Long id, String courseName, String description, Integer capacity, long enrolledCount) {
        this.id = id;
        this.courseName = courseName;
        this.description = description;
        this.capacity = capacity;
        this.enrolledCount = enrolledCount;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }

    public long getEnrolledCount() { return enrolledCount; }
    public void setEnrolledCount(long enrolledCount) { this.enrolledCount = enrolledCount; }
}
