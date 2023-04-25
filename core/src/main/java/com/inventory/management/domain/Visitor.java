package com.inventory.management.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "visitor")
public class Visitor extends EntityLog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = false)
    private Long id;
    @Column(nullable = false)
    private String user;
    @Column(nullable = false)
    private String domain;
    @Column
    private String aux;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Visitor visitor = (Visitor) o;
        return id != null && Objects.equals(id, visitor.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
