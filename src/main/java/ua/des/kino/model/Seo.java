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
    private SeoType  type;

    @Column
    private String url;

    @Column
    private String title;

    @Column(columnDefinition = "TEXT")
    private String keyword;

    @Column(columnDefinition = "TEXT")
    private String description;
}
