package ru.job4j.cars.persistence;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Model;

import java.util.List;

@Repository
public class ModelRepository implements DBStore {

    private final SessionFactory sf;

    public ModelRepository(SessionFactory sf) {
        this.sf = sf;
    }

    public Model findById(int id) {
        return tx(
                session -> session.get(Model.class, id), sf
        );
    }

    public List<Model> findAll() {
        return tx(
                session -> session.createQuery(
                        "from Model "
                ).list(), sf
        );
    }

    public List<Model> findModelByBrand(int brandId) {
        return tx(
                session -> session.createQuery(
                        "select distinct m from Model m "
                                + "join fetch m.brand b "
                                + "where b.id = :bId "
                                + "order by m.name asc "
                ).setParameter("bId", brandId).list(), sf
        );
    }
}
