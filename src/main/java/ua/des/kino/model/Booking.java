package ua.des.kino.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "booking_id")
    private Set<Ticket> ticket;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "create_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime createDate;

    @Column
    private Boolean pay;
}
