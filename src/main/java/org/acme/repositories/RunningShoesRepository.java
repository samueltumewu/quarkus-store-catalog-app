package org.acme.repositories;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entities.RunningShoes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class RunningShoesRepository implements PanacheRepository<RunningShoes> {

    public List<RunningShoes> findByName(String name) {
        return find("lower(name)",name.toLowerCase()).stream().toList();
    }

    public void save(RunningShoes runningShoes) {
        PanacheRepository.super.persist(runningShoes);
    }

    public RunningShoes updateQuantity(Long shoesId, Integer quantity) {
        Optional<RunningShoes> optionalRunningShoes = this.findByIdOptional(shoesId);
        if (optionalRunningShoes.isPresent()) {
            RunningShoes runningShoes = optionalRunningShoes.get();
            System.out.println(">>Running shoes to be update: \n" + runningShoes.getQuantity());
            runningShoes.setQuantity(quantity);
            this.save(runningShoes);
            System.out.println("<<Running shoes been updated: \n" + runningShoes.getQuantity());
            return runningShoes;
        }
        return null;
    }

    public List<RunningShoes> findAllByPage(int pageNumber, int perPage) {
        PanacheQuery<RunningShoes> PanacheQueryRunningShoes = RunningShoes.findAll(Sort.by("id")).page(pageNumber, perPage);
        return PanacheQueryRunningShoes.list();
    }

    public long countTotalData() {
        return RunningShoes.findAll().count();
    }
}
