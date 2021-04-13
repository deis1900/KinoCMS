package ua.des.kino.model;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "tickets")
public class Ticket implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    @NonNull
    private Session session;

    @Column
    private Integer price;

    @ManyToOne
    private Booking booking;

    public Ticket() {

    }
}
