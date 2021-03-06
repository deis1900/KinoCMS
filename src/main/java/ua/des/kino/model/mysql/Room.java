package ua.des.kino.model.mysql;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.des.kino.config.Views;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "rooms")
public class Room implements Serializable {

    @JsonView(Views.Public.class)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "room_id")
    private Long id;

    @JsonView(Views.Public.class)
    @Column(nullable = false)
    private String name;

    @JsonView(Views.Internal.class)
    @Column(columnDefinition = "TEXT")
    private String info;

    @JsonView(Views.Internal.class)
    @Column
    private String banner;

    @JsonView(Views.Public.class)
    @Column(name = "room_schema")
    private String roomSchema;

    @JsonView(Views.Public.class)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id")
    private List<Photo> photoList;

    @JsonView(Views.Public.class)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    @JsonView(Views.Public.class)
    @Max(60)
    @Column(name = "place_serial")
    private Integer serial;

    @JsonView(Views.Public.class)
    @Column(name = "seat_place")
    private Integer place;

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", info='" + info + '\'' +
                ", banner='" + banner + '\'' +
                ", roomSchema='" + roomSchema + '\'' +
                ", photoList=" + photoList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(id, room.id) && Objects.equals(name, room.name)
                && Objects.equals(info, room.info) && Objects.equals(banner, room.banner)
                && Objects.equals(roomSchema, room.roomSchema) && Objects.equals(photoList, room.photoList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, info, banner, roomSchema, photoList);
    }
}
