package io.matheusVictor;

import io.matheusVictor.domain.Movie;
import io.matheusVictor.repository.MovieRepository;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.*;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class MovieResourceUnitTest {

    @InjectMock
    MovieRepository movieRepository;

    @Inject
    MovieResource movieResource;

    private Movie movie;

    @BeforeEach
    void setUp() {
        movie = new Movie();
        movie.setTitle("Teste");
        movie.setDescription("Teste");
        movie.setDirector("Teste");
        movie.setCountry("Teste");
    }

    @Test
    void getAllMovies() {
        List<Movie> movies = new ArrayList<>();

        Mockito.when(movieRepository.listAll())
                .thenReturn(movies);

        Response allMovies = movieResource.getAllMovies();
        assertNotNull(allMovies);
        assertEquals(Response.Status.OK.getStatusCode(), allMovies.getStatus());
        assertNotNull(allMovies.getEntity());

    }

    @Test
    void getMovieByID() {
        Mockito.when(movieRepository.findByIdOptional(1L))
                .thenReturn(ofNullable(movie));

        Response movieByID = movieResource.getMovieByID(1L);

        assertNotNull(movieByID);
        assertEquals(Response.Status.OK.getStatusCode(), movieByID.getStatus());
        assertNotNull(movieByID.getEntity());
        assertEquals(movie.getCountry(), ((Movie) movieByID.getEntity()).getCountry());
    }

    @Test
    void createMovie() {
        Mockito.doNothing().when(movieRepository).persist(ArgumentMatchers.any(Movie.class));

        Response movieResponse = movieResource.createMovie(movie);

        assertEquals(Response.Status.CREATED.getStatusCode(), movieResponse.getStatus());
        assertDoesNotThrow(() -> movieResource.createMovie(movie));
    }

    @Test
    void deleteMovie() {
        Mockito.when(movieRepository.deleteById(1L))
                .thenReturn(true);

        Response movieResponse = movieResource.deleteMovie(1L);

        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), movieResponse.getStatus());
        assertDoesNotThrow(() -> movieResource.deleteMovie(1L));
    }
}