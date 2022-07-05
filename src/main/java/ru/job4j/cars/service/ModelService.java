package ru.job4j.cars.service;

import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Body;
import ru.job4j.cars.model.Model;
import ru.job4j.cars.persistence.ModelRepository;

import java.util.Collection;

@Service
public class ModelService {

    private final ModelRepository repository;

    public ModelService(ModelRepository repository) {
        this.repository = repository;
    }

    public Collection<Model> findAll() {
        return repository.findAll();
    }

    public Model findById(int id) {
        return repository.findById(id);
    }

    public Collection<Model> findModelByBrand(int brandId) {
        return repository.findModelByBrand(brandId);
    }
}