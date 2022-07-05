package ru.job4j.cars.persistence;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Engine;

import java.util.List;

@Repository
public class EngineRepository implements DBStore {

    private final SessionFactory sf;

    public EngineRepository(SessionFactory sf) {
        this.sf = sf;
    }

    public Engine findById(int id) {
        return tx(
                session -> session.get(Engine.class, id), sf
        );
    }

    public List<Engine> findAll() {
        return tx(
                session -> session.createQuery(
                        "from Engine "
                ).list(), sf
        );
    }
}