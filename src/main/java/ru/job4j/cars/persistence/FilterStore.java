package ru.job4j.cars.persistence;

import org.springframework.stereotype.Repository;
import ru.job4j.cars.service.FilterService;

@Repository
public class FilterStore {

    private final FilterService filterService;

    public FilterStore(FilterService filterService) {
        this.filterService = filterService;
    }

    public Integer getCategory() {
        return filterService.getCategory();
    }

    public void setCategory(int id) {
        filterService.setCategory(id);
    }

    public Integer getBody() {
        return filterService.getBody();
    }

    public void setBody(int id) {
        filterService.setBody(id);
    }

    public Integer getBrand() {
        return filterService.getBrand();
    }

    public void setBrand(int id) {
        filterService.setBrand(id);
    }

    public Integer getModel() {
        return filterService.getModel();
    }

    public void setModel(int id) {
        filterService.setModel(id);
    }

    public Integer getEngine() {
        return filterService.getEngine();
    }

    public void setEngine(int id) {
        filterService.setEngine(id);
    }

    public Integer getCar() {
        return filterService.getCar();
    }

    public void setCar(int id) {
        filterService.setCar(id);
    }
}