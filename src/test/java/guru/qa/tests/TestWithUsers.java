package guru.qa.tests;

import guru.qa.models.LombokUserData;
import org.junit.jupiter.api.Test;

import static guru.qa.spec.Specs.request;
import static guru.qa.spec.Specs.response;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;

public class TestWithUsers {

    @Test
    public void checkSingleUser() {
        Integer userId = 6;
        String firstName = "Tracey";
        String lastName = "Ramos";
        String email = "tracey.ramos@reqres.in";

        LombokUserData data = given()
                .spec(request)
                .when()
                .get("/users/" + userId)
                .then()
                .spec(response)
                .log().body()
                .extract().as(LombokUserData.class);

        assertThat(data.getUser().getId()).isEqualTo(userId);
        assertThat(data.getUser().getFirstName()).isEqualTo(firstName);
        assertThat(data.getUser().getLastName()).isEqualTo(lastName);
        assertThat(data.getUser().getEmail()).isEqualTo(email);
    }

    @Test
    public void checkListOfUsers() {
        String firstUserFirstName = "Charles";
        String firstUserLastName = "Morris";
        String firstUserEmail = "charles.morris@reqres.in";

        String secondUserFirstName = "Emma";
        String secondUserLastName = "Wong";
        String secondUserEmail = "emma.wong@reqres.in";

        given()
                .spec(request)
                .when()
                .get("/users/")
                .then()
                .spec(response)
                .log().body()
                .body("data.findAll {it.id = 5}.first_name", hasItem(firstUserFirstName))
                .body("data.findAll {it.id = 5}.last_name", hasItem(firstUserLastName))
                .body("data.findAll {it.id = 5}.email", hasItem(firstUserEmail))
                .body("data.findAll {it.id = 3}.first_name", hasItem(secondUserFirstName))
                .body("data.findAll {it.id = 3}.last_name", hasItem(secondUserLastName))
                .body("data.findAll {it.id = 3}.email", hasItem(secondUserEmail));
    }
}
