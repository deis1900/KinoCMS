package ua.des.kino.model.submodel;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "seo")
public class SeoBlock implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String url;

    @Column
    private String title;

    @Column
    private String keyword;

    @Column
    private String description;

    @Enumerated
    private PageType pageType;

    // from service get site advertising's owner
}
