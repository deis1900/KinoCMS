package ua.des.kino.model.submodel;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

@Data
@Embeddable
public class CinemaInfo implements Serializable {

    @Column(table = "cinema_info", columnDefinition = "TEXT")
    private String info;

    @NotEmpty()
    @Column(table = "cinema_info")
    private String address;

    @Column(table = "cinema_info", name = "location")
    private String location;

    @Pattern(regexp="(^$|[0-9]{10})")
    @Column(table = "cinema_info")
    private String phone;

    @Column(table = "cinema_info")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cinema_id")
    private List<Photo> photos;


}
