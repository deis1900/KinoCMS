package ua.des.kino.model.submodel;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import ua.des.kino.config.Views;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

@Data
@Embeddable
public class CinemaInfo implements Serializable {

    @JsonView(Views.Public.class)
    @Column(table = "cinema_info", columnDefinition = "TEXT")
    private String info;

    @JsonView(Views.Public.class)
    @NotEmpty()
    @Column(table = "cinema_info")
    private String address;

    @JsonView(Views.Public.class)
    @Column(table = "cinema_info", name = "location")
    private String location;

    @JsonView(Views.Public.class)
    @Pattern(regexp="(^$|[0-9]{10})")
    @Column(table = "cinema_info")
    private String phone;

    @JsonView(Views.Public.class)
    @Column(table = "cinema_info")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "cinema_id")
    private List<Photo> photos;


}
