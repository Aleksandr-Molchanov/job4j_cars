package ru.job4j.cars.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "body")
public class Body {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String type;

    @ManyToOne
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "CATEGORY_ID_FK"))
    private Category category;

    public Body() {
    }

    public static Body of(String type, Category category) {
        Body body = new Body();
        body.type = type;
        body.category = category;
        return body;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Body body = (Body) o;
        return id == body.id
                && Objects.equals(type, body.type)
                && Objects.equals(category, body.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, category);
    }

    @Override
    public String toString() {
        return "Body{"
                + "id=" + id
                + ", type='" + type + '\''
                + ", category=" + category
                + '}';
    }
}
