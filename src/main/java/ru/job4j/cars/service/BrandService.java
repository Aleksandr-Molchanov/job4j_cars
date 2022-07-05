package ru.job4j.cars.service;

import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Body;
import ru.job4j.cars.model.Brand;
import ru.job4j.cars.persistence.BrandRepository;

import java.util.Collection;

@Service
public class BrandService {

    private final BrandRepository repository;

    public BrandService(BrandRepository repository) {
        this.repository = repository;
    }

    public Brand findById(int id) {
        return repository.findById(id);
    }

    public Collection<Brand> findAll() {
        return repository.findAll();
    }
}