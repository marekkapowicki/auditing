package pl.marekk.auditing.category.domain;


import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedDate;
import pl.marekk.auditing.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "category")
@Audited
class Category extends AbstractEntity {

    private String name;

    @CreatedDate
    private LocalDateTime creationTime;

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

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

}
