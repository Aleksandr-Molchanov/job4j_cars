package ru.job4j.cars.persistence;

import org.hibernate.SessionFactory;
import ru.job4j.cars.model.Brand;
import ru.job4j.cars.model.Post;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class AdRepository implements DBStore {

    private final SessionFactory sf;

    public AdRepository(SessionFactory sf) {
        this.sf = sf;
    }

    public List<Post> findPostLastDay() {
        return tx(
                session -> session.createQuery(
                        "select distinct p from Post p "
                                + "join fetch p.car c "
                                + "join fetch c.engine e "
                                + "join fetch c.brand br "
                                + "join fetch c.body bo "
                                + "where p.created between :pYesterday and :pToday", Post.class
                ).setParameter("pYesterday", Timestamp.valueOf(LocalDateTime.now().minusDays(1)))
                        .setParameter("pToday", Timestamp.valueOf(LocalDateTime.now()))
                        .list(), sf
        );
    }

    public List<Post> findPostIsPhoto() {
        return tx(
                session -> session.createQuery(
                        "select distinct p from Post p "
                                + "join fetch p.car c "
                                + "join fetch c.engine e "
                                + "join fetch c.brand br "
                                + "join fetch c.body bo "
                                + "where p.photo.size > 0", Post.class
                ).list(), sf
        );
    }

    public List<Post> findPostWithBrand(Brand brand) {
        return tx(
                session -> session.createQuery(
                        "select distinct p from Post p "
                                + "join fetch p.car c "
                                + "join fetch c.engine e "
                                + "join fetch c.brand br "
                                + "join fetch c.body bo "
                                + "where br = :pBrand", Post.class
                ).setParameter("pBrand", brand).list(), sf
        );
    }
}
