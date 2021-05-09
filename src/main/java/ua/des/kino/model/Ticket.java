package ua.des.kino.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import ua.des.kino.config.Views;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "tickets")
public class Ticket implements Serializable {

    @JsonView(Views.Public.class)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonView(Views.Custom.class)
    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    @JsonView(Views.Public.class)
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Seat seat;

    @JsonView(Views.Custom.class)
    @Column
    private Integer price;

    @JsonView(Views.Internal.class)
    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", session=" + session +
                ", seat=" + seat +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id) && session.equals(ticket.session)
                && Objects.equals(seat, ticket.seat) && Objects.equals(price, ticket.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, session, seat, price);
    }
}
