package ua.des.kino.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "sessions")
public class Session implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "show_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime showTime;

    @OneToMany(mappedBy = "room", cascade = CascadeType.REFRESH, orphanRemoval = true)
    private Set<Seat> seats;

    @OneToOne
    private Film film;

    @OneToMany(mappedBy = "session", cascade = {CascadeType.REMOVE, CascadeType.REFRESH})
    private Set<Ticket> ticket;

}
