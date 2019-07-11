package pl.solutions.software.sokolik.bartosz.movie.dto;

public class MovieDTO {

    private Long id;

    private String title;

    public MovieDTO() {
    }

    public MovieDTO(String title) {
        this.title = title;
    }

    public MovieDTO(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
