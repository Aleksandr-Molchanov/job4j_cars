package ru.job4j.cars.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FilterService {

    private final Map<String, Integer> filter = new HashMap<>();

    public FilterService() {
        filter.put("idCategory", 0);
        filter.put("idBody", 0);
        filter.put("idBrand", 0);
        filter.put("idModel", 0);
        filter.put("idEngine", 0);
        filter.put("idCar", 0);
        filter.put("allCar", 0);
        filter.put("newCar", 0);
        filter.put("oldCar", 0);
    }

    public Integer getCategory() {
        return filter.get("idCategory");
    }

    public void setCategory(Integer id) {
        filter.replace("idCategory", id);
    }

    public Integer getBody() {
        return filter.get("idBody");
    }

    public void setBody(Integer id) {
        filter.replace("idBody", id);
    }

    public Integer getBrand() {
        return filter.get("idBrand");
    }

    public void setBrand(Integer id) {
        filter.replace("idBrand", id);
    }

    public Integer getModel() {
        return filter.get("idModel");
    }

    public void setModel(Integer id) {
        filter.replace("idModel", id);
    }

    public Integer getEngine() {
        return filter.get("idEngine");
    }

    public void setEngine(Integer id) {
        filter.replace("idEngine", id);
    }

    public Integer getCar() {
        return filter.get("idCar");
    }

    public void setCar(Integer id) {
        filter.replace("idCar", id);
    }

    public Integer getAllCar() {
        return filter.get("allCar");
    }

    public void setAllCar(Integer id) {
        filter.replace("allCar", id);
    }

    public Integer getNewCar() {
        return filter.get("newCar");
    }

    public void setNewCar(Integer id) {
        filter.replace("newCar", id);
    }

    public Integer getOldCar() {
        return filter.get("oldCar");
    }

    public void setOldCar(Integer id) {
        filter.replace("oldCar", id);
    }
}