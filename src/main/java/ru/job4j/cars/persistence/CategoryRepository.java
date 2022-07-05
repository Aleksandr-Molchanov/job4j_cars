package ru.job4j.cars.persistence;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Body;
import ru.job4j.cars.model.Category;

import java.util.List;

@Repository
public class CategoryRepository implements DBStore {

    private final SessionFactory sf;

    public CategoryRepository(SessionFactory sf) {
        this.sf = sf;
    }

    public Category findById(int id) {
        return tx(
                session -> session.get(Category.class, id), sf
        );
    }

    public List<Category> findAll() {
        return tx(
                session -> session.createQuery(
                        "from Category "
                ).list(), sf
        );
    }
}