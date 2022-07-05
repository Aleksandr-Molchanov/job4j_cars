package ru.job4j.cars.service;

import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Body;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.persistence.EngineRepository;

import java.util.Collection;

@Service
public class EngineService {

    private final EngineRepository repository;

    public EngineService(EngineRepository repository) {
        this.repository = repository;
    }

    public Engine findById(int id) {
        return repository.findById(id);
    }

    public Collection<Engine> findAll() {
        return repository.findAll();
    }
}
