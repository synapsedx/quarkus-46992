package org.acme.quickstart;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "quarkus_entity")
@Getter
@Setter
public class QuarkusEntity {
    @Id
    private String id;
    private String name;
}
