package ua.des.kino.model;

import lombok.Data;
import ua.des.kino.model.submodel.SeoType;

import javax.persistence.*;

@Data
@Entity
@Table(name = "seo")
public class Seo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "owner_id")
    private Long ownerId;

    @Enumerated
    private SeoType type;

    @Column
    private String url;

    private String title;

    private String keyword;

    private String description;
}
