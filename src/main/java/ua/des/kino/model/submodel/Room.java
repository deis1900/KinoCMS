package ua.des.kino.model.submodel;

import lombok.Data;
import ua.des.kino.model.Cinema;
import ua.des.kino.model.Seat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "rooms")
public class Room implements Serializable {

    @Id
    @Column(name = "room_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String info;

    @Column
    private String banner;

    @Column(name = "room_schema")
    private String roomSchema;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "room_id")
    private List<Photo> photoList;

    @ManyToOne
    @JoinColumn(name = "cinema_id", nullable = false)
    private Cinema cinema;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private Set<Seat> seats;

}
