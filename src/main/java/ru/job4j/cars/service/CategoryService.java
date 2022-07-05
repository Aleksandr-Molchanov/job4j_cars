package ru.job4j.cars.service;

import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Category;
import ru.job4j.cars.persistence.CategoryRepository;

import java.util.Collection;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public Category findById(int id) {
        return repository.findById(id);
    }

    public Collection<Category> findAll() {
        return repository.findAll();
    }
}