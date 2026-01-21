package learnsmartly.ls_api.dto.response;

public class AuthResponseDTO {

    private String accessToken;
    private MeResponseDTO user;

    public AuthResponseDTO() {
    }

    public AuthResponseDTO(String accessToken, MeResponseDTO user) {
        this.accessToken = accessToken;
        this.user = user;
    }

    public String getAccessToken() {
        return accessToken;
    }
 
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public MeResponseDTO getUser() {
        return user;
    }
 
    public void setUser(MeResponseDTO user) {
        this.user = user;
    }
}
