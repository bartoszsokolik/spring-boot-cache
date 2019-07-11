package pl.solutions.software.sokolik.bartosz.movie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.solutions.software.sokolik.bartosz.movie.dto.MovieDTO;
import pl.solutions.software.sokolik.bartosz.movie.dto.MovieNotFoundException;

@Service
@Transactional
public class MovieFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieFacade.class);

    private final MovieRepository movieRepository;

    @Autowired
    public MovieFacade(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Cacheable(value = "movieCache", key = "#title")
    public MovieDTO findByTitle(String title) {
        LOGGER.info("Fetching movie from database");
        return new MovieDTO(findMovieByTitle(title).getTitle());
    }

    @CachePut(value = "movieCache", key = "#newTitle")
    public MovieDTO changeTitle(String title, String newTitle) {
        Movie updatedMovie = movieRepository.save(new Movie(findMovieByTitle(title).getId(), newTitle));
        return new MovieDTO(updatedMovie.getId(), updatedMovie.getTitle());
    }

    @CacheEvict(value = "movieCache", key = "#title")
    public void deleteMovie(String title) {
        Movie movie = findMovieByTitle(title);
        movieRepository.deleteById(movie.getId());
    }

    private Movie findMovieByTitle(String title) {
        return movieRepository.findByTitle(title)
                .orElseThrow(() -> new MovieNotFoundException(String.format("Movie with username %s not found", title)));
    }


}
