package ua.des.kino.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.des.kino.config.Views;
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

    @JsonView(Views.Public.class)
    @Column(columnDefinition = "boolean default true")
    private Boolean free;

    @Override
    public String toString() {
        return "Seat{" +
                "id=" + id +
                ", series=" + series +
                ", place=" + place +
                ", free=" + free +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return Objects.equals(id, seat.id) && Objects.equals(series, seat.series) && Objects.equals(place, seat.place) && Objects.equals(free, seat.free);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, series, place, free);
    }
}
