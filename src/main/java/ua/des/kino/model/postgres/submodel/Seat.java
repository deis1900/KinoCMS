package ua.des.kino.model.postgres.submodel;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.des.kino.config.Views;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.io.Serializable;
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

    @Max(60)
    @JsonView(Views.Public.class)
    @Column
    private Integer series;

    @Max(120)
    @JsonView(Views.Public.class)
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
        return Objects.equals(id, seat.id) && Objects.equals(series, seat.series)
                && Objects.equals(place, seat.place) && Objects.equals(free, seat.free);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, series, place, free);
    }
}
