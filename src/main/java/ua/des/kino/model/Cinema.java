package ua.des.kino.model;

import lombok.Data;
import ua.des.kino.model.submodel.CinemaInfo;
import ua.des.kino.model.submodel.Room;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "cinema")
public class Cinema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(name = "main_photo")
    private String mainPhoto;

    @OneToOne(mappedBy = "cinema", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private CinemaInfo cinemaInfo;

    @OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Room> room;
}
