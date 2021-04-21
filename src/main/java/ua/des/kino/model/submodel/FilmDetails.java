package ua.des.kino.model.submodel;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
@Embeddable
public class FilmDetails implements Serializable {

    @Column(table = "films_details")
    private String actors;

    @NotEmpty
    @Column(table = "films_details", columnDefinition = "TEXT")
    private String info;

    @Column(table = "films_details")
    private String director;

    @Column(table = "films_details")
    private String producer;

    @Column(table = "films_details")
    private String compositor;

    @Column(table = "films_details")
    private String scenarist;

    @Column(table = "films_details")
    private String trailer;

    @Column(table = "films_details")
    private Integer budget;

    @NotBlank(message = "Quality is mandatory")
    @Column(table = "films_details")
    @Enumerated
    private Quality quality;

    @NotBlank(message = "Genre is mandatory")
    @Enumerated(EnumType.STRING)
    private Genres genres;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "film_id")
    private List<Photo> photos;

}
