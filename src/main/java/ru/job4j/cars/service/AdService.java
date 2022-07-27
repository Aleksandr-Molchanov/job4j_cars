package ru.job4j.cars.service;

import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Ad;
import ru.job4j.cars.persistence.AdRepository;

import java.util.Collection;
import java.util.List;

@Service
public class AdService {

    private final AdRepository repository;

    public AdService(AdRepository repository) {
        this.repository = repository;
    }

    public List<Ad> findAdLastDay() {
        return repository.findAdLastDay();
    }

    public List<Ad> findAdIsPhoto() {
        return repository.findAdIsPhoto();
    }

    public List<Ad> findAdWithBrand(String brandName) {
        return repository.findAdWithBrand(brandName);
    }

    public List<Ad> findAdBrandAndSold(String brandName, boolean sold) {
        return repository.findAdBrandAndSold(brandName, sold);
    }

    public List<Ad> findAdWithCategory(String categoryName) {
        return repository.findAdWithCategory(categoryName);
    }

    public List<Ad> findACategoryAndSold(String categoryName, boolean sold) {
        return repository.findAdCategoryAndSold(categoryName, sold);
    }

    public Ad findById(int id) {
        return repository.findById(id);
    }

    public Collection<Ad> findAll() {
        return repository.findAll();
    }

    public Collection<Ad> findSoldAll(boolean sold) {
        return repository.findSoldAll(sold);
    }

    public Collection<Ad> findNewCar(boolean newCar, boolean sold) {
        return repository.findNewCar(newCar, sold);
    }

    public void add(Ad ad) {
        repository.add(ad);
    }

    public void update(Ad ad) {
        repository.update(ad);
    }

    public void delete(int id) {
        repository.delete(id);
    }

    public void setSold(int id) {
        repository.setSold(id);
    }

    public List<Ad> findAdCategoryAndBodyAndBrandAndModel(String categoryName, String bodyType, String brandName, String modelName) {
        return repository.findAdCategoryAndBodyAndBrandAndModel(categoryName, bodyType, brandName, modelName);
    }

    public List<Ad> findMyAds(String email) {
        return repository.findMyAds(email);
    }

}