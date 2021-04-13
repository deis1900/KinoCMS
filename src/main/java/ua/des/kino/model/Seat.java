package ua.des.kino.model;

import lombok.Data;
import ua.des.kino.model.submodel.Room;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "seats")
public class Seat implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer series;

    @Column
    private Integer place;

    @Column(columnDefinition = "boolean default true")
    private Boolean free;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @OneToMany
    @JoinColumn(name = "seat_id")
    private List<Session> session;
}
