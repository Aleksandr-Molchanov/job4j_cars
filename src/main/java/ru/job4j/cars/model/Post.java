package ru.job4j.cars.model;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @ManyToOne
    @JoinColumn(name = "car_id", foreignKey = @ForeignKey(name = "CAR_ID_FK"))
    private Car car;

    private byte[] photo;

    private boolean sold;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "USER_ID_FK"))
    private User user;

    public Post() {
    }

    public Post(int id, String description, Date created, Car car, byte[] photo, boolean sold, User user) {
        this.id = id;
        this.description = description;
        this.created = created;
        this.car = car;
        this.photo = photo;
        this.sold = sold;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return id == post.id
                && sold == post.sold
                && Objects.equals(description, post.description)
                && Objects.equals(car, post.car)
                && Arrays.equals(photo, post.photo)
                && Objects.equals(user, post.user);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, description, car, sold, user);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }

    @Override
    public String toString() {
        return "Post{"
                + "id=" + id
                + ", description='" + description + '\''
                + ", car=" + car
                + ", sold=" + sold
                + ", user=" + user
                + '}';
    }
}
