package ua.des.kino.model.postgres;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import ua.des.kino.config.Views;
import ua.des.kino.model.mysql.Session;
import ua.des.kino.model.postgres.submodel.Seat;

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

    @NonNull
    @Column(name = "Session_id")
    private Long sessionId;

    @JsonView(Views.Custom.class)
    @NonNull
    @Transient
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
                ", session=" + sessionId +
                ", seat=" + seat +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id) && sessionId.equals(ticket.sessionId)
                && Objects.equals(seat, ticket.seat) && Objects.equals(price, ticket.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sessionId, seat, price);
    }


}
