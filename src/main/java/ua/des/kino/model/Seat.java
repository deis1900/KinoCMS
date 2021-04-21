package ua.des.kino.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.des.kino.model.submodel.Room;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Setter
@Getter
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

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "room_id")
    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @OneToMany
    @JoinColumn(name = "seat_id")
    private List<Session> session;

    @Override
    public String toString() {
        return "Seat{" +
                "id=" + id +
                ", series=" + series +
                ", place=" + place +
                ", free=" + free +
                ", room=" + room +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return Objects.equals(id, seat.id) && Objects.equals(series, seat.series)
                && Objects.equals(place, seat.place) && Objects.equals(free, seat.free) && Objects.equals(room, seat.room);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, series, place, free, room);
    }
}
