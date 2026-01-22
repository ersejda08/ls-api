package learnsmartly.ls_api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import learnsmartly.ls_api.entity.UserRole;

public class RegisterRequestDTO {

    @NotBlank(message = "username is required")
    private String username;

    @Email(message = "email must be valid")
    @NotBlank(message = "email is required")
    private String email;

    @NotBlank(message = "password is required")
    @Size(min = 8, message = "password must be at least 8 characters")
    private String password;

    // Optional (student profile)
    private String phoneNumber;
    private String address;

    // Optional. If null -> default STUDENT in controller
    private UserRole role;

    public RegisterRequestDTO() {}

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }
}
