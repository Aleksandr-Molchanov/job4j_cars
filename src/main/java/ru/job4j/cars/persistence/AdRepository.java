package ru.job4j.cars.persistence;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Ad;
import ru.job4j.cars.model.Car;

import javax.persistence.Query;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class AdRepository implements DBStore {

    private final SessionFactory sf;

    public AdRepository(SessionFactory sf) {
        this.sf = sf;
    }

    public List<Ad> findAdLastDay() {
        return tx(
                session -> session.createQuery(
                        "select distinct a from Ad a "
                                + "join fetch a.car c "
                                + "join fetch c.engine e "
                                + "join fetch c.brand br "
                                + "join fetch c.category ca "
                                + "join fetch br.models m "
                                + "join fetch c.body bo "
                                + "where a.created between :aYesterday and :aToday "
                                + "order by a.created desc", Ad.class
                ).setParameter("aYesterday", Timestamp.valueOf(LocalDateTime.now().minusDays(1)))
                        .setParameter("aToday", Timestamp.valueOf(LocalDateTime.now()))
                        .list(), sf
        );
    }

    public List<Ad> findAdIsPhoto() {
        return tx(
                session -> session.createQuery(
                        "select distinct a from Ad a "
                                + "join fetch a.car c "
                                + "join fetch c.engine e "
                                + "join fetch c.brand br "
                                + "join fetch c.category ca "
                                + "join fetch br.models m "
                                + "join fetch c.body bo "
                                + "where a.photo.size > 0 "
                                + "order by a.created desc", Ad.class
                ).list(), sf
        );
    }

    public List<Ad> findAdWithBrand(String brandName) {
        return tx(
                session -> session.createQuery(
                        "select distinct a from Ad a "
                                + "join fetch a.car c "
                                + "join fetch c.engine e "
                                + "join fetch c.brand br "
                                + "join fetch c.category ca "
                                + "join fetch br.models m "
                                + "join fetch c.body bo "
                                + "where br.name = :brName "
                                + "order by a.created desc", Ad.class
                ).setParameter("brName", brandName).list(), sf
        );
    }

    public List<Ad> findAdBrandAndSold(String brandName, boolean sold) {
        return tx(
                session -> session.createQuery(
                        "select distinct a from Ad a "
                                + "join fetch a.car c "
                                + "join fetch c.engine e "
                                + "join fetch c.brand br "
                                + "join fetch c.category ca "
                                + "join fetch br.models m "
                                + "join fetch c.body bo "
                                + "where br.name = :brName and a.sold = :aSold "
                                + "order by a.created desc", Ad.class
                ).setParameter("brName", brandName).setParameter("aSold", sold).list(), sf
        );
    }

    public List<Ad> findAdWithCategory(String categoryName) {
        return tx(
                session -> session.createQuery(
                        "select distinct a from Ad a "
                                + "join fetch a.car c "
                                + "join fetch c.engine e "
                                + "join fetch c.brand br "
                                + "join fetch c.category ca "
                                + "join fetch br.models m "
                                + "join fetch c.body bo "
                                + "where ca.name = :caName "
                                + "order by a.created desc", Ad.class
                ).setParameter("caName", categoryName).list(), sf
        );
    }

    public List<Ad> findAdCategoryAndSold(String categoryName, boolean sold) {
        return tx(
                session -> session.createQuery(
                        "select distinct a from Ad a "
                                + "join fetch a.car c "
                                + "join fetch c.engine e "
                                + "join fetch c.brand br "
                                + "join fetch c.category ca "
                                + "join fetch br.models m "
                                + "join fetch c.body bo "
                                + "where ca.name = :caName and a.sold = :aSold "
                                + "order by a.created desc", Ad.class
                ).setParameter("caName", categoryName).setParameter("aSold", sold).list(), sf
        );
    }

    public Ad findById(int id) {
        return tx(
                session -> session.get(Ad.class, id), sf
        );
    }

    public List<Ad> findAll() {
        return tx(
                session -> session.createQuery(
                        "select distinct a from Ad a "
                                + "join fetch a.car c "
                                + "join fetch c.engine e "
                                + "join fetch c.brand br "
                                + "join fetch c.category ca "
                                + "join fetch br.models m "
                                + "join fetch c.body bo "
                                + "order by a.created desc", Ad.class
                ).list(), sf
        );
    }

    public List<Ad> findSoldAll(boolean sold) {
        return tx(
                session -> session.createQuery(
                        "select distinct a from Ad a "
                                + "join fetch a.car c "
                                + "join fetch c.engine e "
                                + "join fetch c.brand br "
                                + "join fetch c.category ca "
                                + "join fetch br.models m "
                                + "join fetch c.body bo "
                                + "where a.sold = :aSold "
                                + "order by a.created desc", Ad.class
                ).setParameter("aSold", sold).list(), sf
        );
    }

    public Ad add(Ad ad) {
        return tx(
                session -> {
                    session.save(ad);
                    return ad;
                }, sf
        );
    }

    public void update(Ad ad, String idCar) {
        tx(
                session -> {
                    Car car = session.find(Car.class, Integer.parseInt(idCar));
                    ad.setCar(car);
                    session.merge(ad);
                    return new Object();
                }, sf
        );
    }

    public boolean delete(int id) {
        return tx(
                session -> {
                    String hql = "delete Ad a "
                            + " where a.id = :id";
                    final Query query = session.createQuery(hql);
                    query.setParameter("id", id);
                    int rsl = query.executeUpdate();
                    return rsl != 0;
                }, sf
        );
    }
    public boolean setSold(int id) {
        return tx(
                session -> {
                    String hql = "update Ad a "
                            + " SET a.sold = :sold "
                            + " where a.id = :id";
                    final Query query = session.createQuery(hql);
                    query.setParameter("id", id);
                    query.setParameter("sold", true);
                    int rsl = query.executeUpdate();
                    return rsl != 0;
                }, sf
        );
    }

    public boolean setNewCar(int id) {
        return tx(
                session -> {
                    String hql = "update Ad a"
                            + " SET a.newCar = :newCar "
                            + " where a.id = :id";
                    final Query query = session.createQuery(hql);
                    query.setParameter("id", id);
                    query.setParameter("newCar", true);
                    int rsl = query.executeUpdate();
                    return rsl != 0;
                }, sf
        );
    }
}
