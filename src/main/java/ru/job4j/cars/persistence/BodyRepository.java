package ru.job4j.cars.persistence;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Body;
import ru.job4j.cars.model.Model;

import java.util.List;

@Repository
public class BodyRepository implements DBStore {

    private final SessionFactory sf;

    public BodyRepository(SessionFactory sf) {
        this.sf = sf;
    }

    public Body findById(int id) {
        return tx(
                session -> session.get(Body.class, id), sf
        );
    }

    public List<Body> findAll() {
        return tx(
                session -> session.createQuery(
                        "from Body "
                ).list(), sf
        );
    }

    public List<Body> findBodyByCategory(int categoryId) {
        return tx(
                session -> session.createQuery(
                        "select distinct bo from Body bo "
                                + "join fetch bo.category ca "
                                + "where ca.id = :caId "
                                + "order by bo.type asc "
                ).setParameter("caId", categoryId).list(), sf
        );
    }
}
