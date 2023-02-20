package guru.qa.tests;

import guru.qa.models.LombokColorData;
import org.junit.jupiter.api.Test;

import static guru.qa.spec.Specs.request;
import static guru.qa.spec.Specs.response;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;

public class TestWithColors {

    @Test
    public void checkSingleColor() {
        Integer colorId = 3;
        String colorName = "true red";
        String pantoneValue = "19-1664";
        String colorCode = "#BF1932";

        LombokColorData data = given()
                .spec(request)
                .when()
                .get("/unknown/" + colorId)
                .then()
                .spec(response)
                .log().body()
                .extract().as(LombokColorData.class);

        assertThat(data.getColor().getId()).isEqualTo(colorId);
        assertThat(data.getColor().getName()).isEqualTo(colorName);
        assertThat(data.getColor().getPantoneValue()).isEqualTo(pantoneValue);
        assertThat(data.getColor().getColor()).isEqualTo(colorCode);
    }

    @Test
    public void checkListOfColors() {
        String firstColorName = "true red";
        String firstPantoneValue = "19-1664";
        String firstColorCode = "#BF1932";

        String secondColorName = "blue turquoise";
        String secondPantoneValue = "15-5217";
        String secondColorCode = "#53B0AE";

        given()
                .spec(request)
                .when()
                .get("/unknown/")
                .then()
                .spec(response)
                .log().body()
                .body("data.findAll { it.id == 3 }.name", hasItem(firstColorName))
                .body("data.findAll { it.id == 3 }.color", hasItem(firstColorCode))
                .body("data.findAll { it.id == 3 }.pantone_value", hasItem(firstPantoneValue))
                .body("data.findAll { it.id == 6 }.name", hasItem(secondColorName))
                .body("data.findAll { it.id == 6 }.color", hasItem(secondColorCode))
                .body("data.findAll { it.id == 6 }.pantone_value", hasItem(secondPantoneValue));
    }
}
