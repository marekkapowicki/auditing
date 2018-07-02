package pl.marekk.auditing.category.domain;


import org.hibernate.envers.Audited;
import pl.marekk.auditing.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "category")
@Audited
class Category extends AbstractEntity {

    private String name;


    Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    Category(String name) {
        this.name = name;
    }

    private Category() {
    }

    String getName() {
        return name;
    }
}
