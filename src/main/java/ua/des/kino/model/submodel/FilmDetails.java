package ua.des.kino.model.submodel;

import lombok.Data;
import ua.des.kino.model.Film;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "films_details")
public class FilmDetails implements Serializable {

    @Id
    @Column(name = "film_id")
    private Long id;

    @Column
    private String actors;

    @Column(columnDefinition = "TEXT")
    private String info;

    @Column
    private String director;

    @Column
    private String producer;

    @Column
    private String compositor;

    @Column
    private String scenarist;

    @Column
    private String trailer;

    @Column
    private Integer budget;

    @Enumerated(EnumType.STRING)
    private Genres genres;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "film_id")
    private List<Photo> photos;

    @OneToOne
    @MapsId
    @JoinColumn(name = "film_id")
    private Film film;

}
