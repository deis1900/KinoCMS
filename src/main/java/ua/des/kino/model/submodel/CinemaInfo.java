package ua.des.kino.model.submodel;

import lombok.Data;
import ua.des.kino.model.Cinema;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "cinema_info")
public class CinemaInfo implements Serializable {

    @Id
    @Column
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String info;

    @Column
    private String address;

    @Column(name = "location")
    private String location;

    @Column
    private String phone;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cinema_id")
    private List<Photo> photos;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Cinema cinema;
}
