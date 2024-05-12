package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.acme.entities.RunningShoes;
import org.acme.repositories.RunningShoesRepository;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsStringIgnoringCase;

@QuarkusTest
class RunningShoesTest {

    @Inject
    RunningShoesRepository runningShoesRepository;

    @Test
    void testRunningShoesRepository_IsExist() {
        RunningShoes runningShoes = new RunningShoes();
        runningShoes.setMerk("ortuseight");
        runningShoes.setType("road");
        runningShoes.setName("hyperfuse");
        runningShoes.setSize(38.5F);
        runningShoes.setColor("white");

//        System.out.printf("IsExist result: %s\n", runningShoesRepository.findByMerkAndNameAndTypeAndColorAndSize(runningShoes));
    }

    @Test
    public void testFindAllByPage_forPageNumberZero() {
        given()
                .when().get("/shoes?page=0&per_page=10")
                .then()
                .statusCode(200)
                .body(containsStringIgnoringCase("merk"));
    }

    @Test
    public void testPutQuantity() {
        System.out.printf("IsExist result: %s\n", runningShoesRepository.updateQuantity(6L,2));
    }
}
