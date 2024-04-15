package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.acme.entities.RunningShoes;
import org.acme.repositories.RunningShoesRepository;
import org.junit.jupiter.api.Test;

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

        System.out.printf("IsExist result: %s\n", runningShoesRepository.findByMerkAndNameAndTypeAndColorAndSize(runningShoes));
    }
}
