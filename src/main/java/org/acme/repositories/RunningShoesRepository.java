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

    public Optional<RunningShoes> findByMerkAndNameAndTypeAndColorAndSize(RunningShoes runningShoes) {
        Map<String, Object> params = new HashMap<>();
        params.put("merk", runningShoes.getMerk());
        params.put("name", runningShoes.getName());
        params.put("type", runningShoes.getType());
        params.put("color", runningShoes.getColor());
        params.put("size", runningShoes.getSize());

        return find(
                "merk = :merk and " +
                "name = :name and " +
                "type = :type and " +
                "color = :color and " +
                "size = :size", params).firstResultOptional();
    }

    public List<RunningShoes> findByName(String name) {
        return find("lower(name)",name.toLowerCase()).stream().toList();
    }

    public void save(RunningShoes runningShoes) {
        PanacheRepository.super.persist(runningShoes);
    }

    public RunningShoes updateQuantity(RunningShoes updateRunningShoe) {
        Optional<RunningShoes> optionalRunningShoes = this.findByMerkAndNameAndTypeAndColorAndSize(updateRunningShoe);
        if (optionalRunningShoes.isPresent()) {
            RunningShoes runningShoes = optionalRunningShoes.get();
            runningShoes.setQuantity(updateRunningShoe.getQuantity());
            this.save(runningShoes);
            return runningShoes;
        }
        return null;
    }

    public List<RunningShoes> findAllByPage(int pageNumber, int perPage) {
        PanacheQuery<RunningShoes> PanacheQueryRunningShoes = RunningShoes.findAll(Sort.by("id")).page(pageNumber, perPage);
        return PanacheQueryRunningShoes.list();
    }

    public int getIndexOfLastPage(int perPage) {
        return RunningShoes.findAll(Sort.by("id")).page(Page.ofSize(perPage)).pageCount() - 1;
    }
}
