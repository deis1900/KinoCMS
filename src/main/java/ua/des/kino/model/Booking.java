package ua.des.kino.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.des.kino.config.Views;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "booking")
public class Booking implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Ticket> ticket;

    @JsonView(Views.Internal.class)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "create_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime createDate;

    @JsonView(Views.Internal.class)
    @Column
    private Boolean pay;

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", ticket=" + ticket +
                ", createDate=" + createDate +
                ", pay=" + pay +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(id, booking.id) && Objects.equals(ticket, booking.ticket)
                && Objects.equals(createDate, booking.createDate) && Objects.equals(pay, booking.pay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ticket, createDate, pay);
    }
}
