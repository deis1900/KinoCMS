package ua.des.kino.model.kino;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.des.kino.config.Views;
import ua.des.kino.model.kino.submodel.TypeImage;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "images", schema = "kino")
public class Image implements Serializable {

    @JsonView(Views.Public.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonView(Views.Public.class)
    @NotEmpty
    @Column(name = "name")
    private String name;

    @JsonView(Views.Public.class)
    @NotEmpty
    @Enumerated(EnumType.STRING)
    @Column(name = "image_type")
    private TypeImage typeImage;

    @JsonView(Views.Public.class)
    @NotEmpty
    @Pattern(regexp = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)")
    @Column(name = "url")
    private String url;
}
