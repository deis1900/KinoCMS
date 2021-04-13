package ua.des.kino.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@Data
@Entity
@Table(name = "booking")
public class Booking implements Serializable {
// user get ticket from booking, pay set true. delete booking. create ticket
    //check before place is free or not
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "booking_id")
    private Set<Ticket> ticket;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "create_date")
    private Timestamp createDate;

    @Column
    private Boolean pay;
}
