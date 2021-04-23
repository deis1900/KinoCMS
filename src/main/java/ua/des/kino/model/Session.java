package ua.des.kino.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.des.kino.config.Views;
import ua.des.kino.model.submodel.Room;

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

    @JsonView(Views.Public.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonView(Views.Public.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss")
    @Column(name = "show_time", columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime showTime;

    @JsonView(Views.Public.class)
    @NotBlank(message = "Room in the cinema is mandatory")
    @OneToOne
    private Room room;

    @JsonView(Views.Public.class)
    @NotBlank(message = "Film in the session is mandatory")
    @OneToOne
    private Film film;

    @JsonView(Views.Public.class)
    @OneToMany(mappedBy = "session", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Ticket> ticket;

    @Override
    public String toString() {
        return "Session{" +
                "id=" + id +
                ", showTime=" + showTime +
                ", film=" + film +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(id, session.id) && Objects.equals(showTime, session.showTime) && Objects.equals(film, session.film);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, showTime, film);
    }
}
