package learnsmartly.ls_api.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CourseRequestDTO {

    @NotBlank(message = "courseName is required")
    private String courseName;

    private String description;

    @NotNull(message = "capacity is required")
    @Min(value = 1, message = "capacity must be >= 1")
    private Integer capacity;

    public CourseRequestDTO() {}

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
}
