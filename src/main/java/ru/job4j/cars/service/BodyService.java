package ru.job4j.cars.service;

import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Body;
import ru.job4j.cars.model.Model;
import ru.job4j.cars.persistence.BodyRepository;

import java.util.Collection;

@Service
public class BodyService {

    private final BodyRepository repository;

    public BodyService(BodyRepository repository) {
        this.repository = repository;
    }

    public Collection<Body> findAll() {
        return repository.findAll();
    }

    public Body findById(int id) {
        return repository.findById(id);
    }

    public Collection<Body> findBodyByCategory(int categoryId) {
        return repository.findBodyByCategory(categoryId);
    }
}