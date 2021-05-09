package ua.des.kino.model;

import lombok.Data;
import ua.des.kino.model.submodel.SeoType;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

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

    @Pattern(regexp = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\." +
            "[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)")
    @Column
    private String url;

    @Column
    private String title;

    @Column(columnDefinition = "TEXT")
    private String keyword;

    @Column(columnDefinition = "TEXT")
    private String description;
}
