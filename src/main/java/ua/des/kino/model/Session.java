package ua.des.kino.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sessions")
public class Session implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "show_time", columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime showTime;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @OneToMany(mappedBy = "room", cascade = CascadeType.REFRESH, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Seat> seats;

    @NotBlank(message = "Film in the session is mandatory")
    @OneToOne
    private Film film;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @OneToMany(mappedBy = "session", fetch = FetchType.EAGER)
    private Set<Ticket> ticket;

    @Override
    public String toString() {
        return "Session{" +
                "id=" + id +
                ", showTime=" + showTime +
                ", seats=" + seats +
                ", film=" + film +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(id, session.id) && Objects.equals(showTime, session.showTime)
                && Objects.equals(seats, session.seats) && Objects.equals(film, session.film);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, showTime, seats, film);
    }
}
