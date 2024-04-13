package org.acme.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entities.RunningShoes;

import java.util.List;

@ApplicationScoped
public class RunningShoesRepository implements PanacheRepository<RunningShoes> {
    public List<RunningShoes> findByName(String name) {
        return find("lower(name)",name.toLowerCase()).stream().toList();
    }

    public void save(RunningShoes runningShoes) {
        PanacheRepository.super.persist(runningShoes);
    }
}
