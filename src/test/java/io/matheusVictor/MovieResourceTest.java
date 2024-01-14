package io.matheusVictor;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static jakarta.ws.rs.core.Response.Status.OK;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
@Tag("integration")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MovieResourceTest {


    @Test
    @Order(2)
    void getAllMovies() {
        given()
                .when().get("/movies")
                .then()
                .statusCode(OK.getStatusCode())
                .body("id", hasItems(1))
                .body("size()", equalTo(1));
    }

    @Test
    @Order(3)
    void getMovieByID() {
        given()
                .when().get("/movies/1")
                .then()
                .statusCode(OK.getStatusCode())
                .body("id", equalTo(1))
                .body("title", equalTo("Teste"))
                .body("description", equalTo("Teste"))
                .body("director", equalTo("Teste"))
                .body("country", equalTo("Teste"));
    }

    @Test
    @Order(1)
    void createMovie() {
        String body = "{\"title\": \"Teste\", \"description\": \"Teste\", \"director\": \"Teste\", \"country\": \"Teste\"}";
        given()
                .when()
                .body(body)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .post("/movies")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode());
    }

    @Test
    @Order(4)
    void deleteMovie() {
        given()
                .when().delete("/movies/1")
                .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());
    }
}