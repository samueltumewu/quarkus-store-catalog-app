package org.acme.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "RUNNING_SHOES")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class RunningShoes extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "MERK")
    private String merk;
    @Column(name = "SIZE")
    private Float size;
    @Column(name = "TYPE")
    private String type;
    @Column(name = "COLOR")
    private String color;
    @Column(name = "QUANTITY")
    private Integer quantity;
}
