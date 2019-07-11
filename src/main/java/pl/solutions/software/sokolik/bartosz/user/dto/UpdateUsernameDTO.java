package pl.solutions.software.sokolik.bartosz.user.dto;

public class UpdateUsernameDTO {

    private String username;

    private String newUsername;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNewUsername() {
        return newUsername;
    }

    public void setNewUsername(String newUsername) {
        this.newUsername = newUsername;
    }
}
