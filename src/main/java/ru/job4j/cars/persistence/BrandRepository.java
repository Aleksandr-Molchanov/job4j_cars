package ru.job4j.cars.persistence;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Brand;

import java.util.List;

@Repository
public class BrandRepository implements DBStore {

    private final SessionFactory sf;

    public BrandRepository(SessionFactory sf) {
        this.sf = sf;
    }

    public Brand findById(int id) {
        return tx(
                session -> session.get(Brand.class, id), sf
        );
    }

    public List<Brand> findAll() {
        return tx(
                session -> session.createQuery(
                        "select distinct b from Brand b "
                                + "join fetch b.models m "
                                + "order by b.name asc "
                ).list(), sf
        );
    }
}
