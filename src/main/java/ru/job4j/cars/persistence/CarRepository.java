package ru.job4j.cars.persistence;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.*;

import javax.persistence.Query;
import java.util.List;

@Repository
public class CarRepository implements DBStore {

    private final SessionFactory sf;

    public CarRepository(SessionFactory sf) {
        this.sf = sf;
    }

    public Car findById(int id) {
        return tx(
                session -> session.get(Car.class, id), sf
        );
    }

    public List<Car> findAll() {
        return tx(
                session -> session.createQuery(
                        "select distinct c from Car c "
                                + "join fetch c.engine e "
                                + "join fetch c.brand br "
                                + "join fetch c.body bo "
                                + "join fetch c.category ca ", Car.class
                ).list(), sf
        );
    }

    public Car add(Car car) {
        return tx(
                session -> {
                    session.save(car);
                    return car;
                }, sf
        );
    }

    public void update(Car car) {
        tx(
                session -> {
                    session.merge(car);
                    return new Object();
                }, sf
        );
    }

    public boolean delete(int id) {
        return tx(
                session -> {
                    final Query query = session.createQuery(
                            "delete Car c "
                            + "where c.id = :id");
                    query.setParameter("id", id);
                    int rsl = query.executeUpdate();
                    return rsl != 0;
                }, sf
        );
    }
}
