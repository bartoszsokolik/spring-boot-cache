package pl.solutions.software.sokolik.bartosz.movie;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.solutions.software.sokolik.bartosz.movie.dto.MovieDTO;
import pl.solutions.software.sokolik.bartosz.movie.dto.UpdateMovieDTO;

import java.net.URI;

@RestController
@RequestMapping("/api/movies")
class MovieController {

    private final MovieFacade movieFacade;

    public MovieController(MovieFacade movieFacade) {
        this.movieFacade = movieFacade;
    }

    @GetMapping("/{title}")
    ResponseEntity<MovieDTO> findMovie(@PathVariable String title) {
        return new ResponseEntity<>(movieFacade.findByTitle(title), HttpStatus.OK);
    }

    @PutMapping
    ResponseEntity<Void> changeTitle(@RequestBody UpdateMovieDTO dto) {
        MovieDTO movie = movieFacade.changeTitle(dto.getTitle(), dto.getNewTitle());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(movie.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{title}")
    ResponseEntity<Void> deletMovie(@PathVariable String title) {
        movieFacade.deleteMovie(title);
        return ResponseEntity.ok().build();
    }
}
