package ru.job4j.cars.service;

import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.persistence.CarRepository;

import java.util.Collection;

@Service
public class CarService {

    private final CarRepository repository;

    public CarService(CarRepository repository) {
        this.repository = repository;
    }

    public Car findById(int id) {
        return repository.findById(id);
    }

    public Collection<Car> findAll() {
        return repository.findAll();
    }

    public void add(Car car) {
        repository.add(car);
    }

    public void update(Car car) {
        repository.update(car);
    }

    public void delete(int id) {
        repository.delete(id);
    }

}