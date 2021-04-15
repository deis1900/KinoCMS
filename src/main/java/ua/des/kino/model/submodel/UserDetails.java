package ua.des.kino.model.submodel;

import lombok.Data;
import ua.des.kino.model.User;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Data
@Entity
@Table(name = "users_details")
public class UserDetails implements Serializable {

    @Id
    @Column(name = "user_id", updatable = false, nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Column
    private String language;

    @Column
    private Date birthday;

    @Column
    private String city;

    @Column(name = "registration")
    private Timestamp registrationDate;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

}
