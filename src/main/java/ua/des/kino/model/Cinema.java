package ua.des.kino.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.des.kino.config.Views;
import ua.des.kino.model.submodel.CinemaInfo;
import ua.des.kino.model.submodel.Room;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cinema")
@SecondaryTable(name = "cinema_info")
public class Cinema {

    @JsonView(Views.Public.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonView(Views.Public.class)
    @Column
    private String name;

    @JsonView(Views.Public.class)
    @Column(name = "main_photo")
    private String mainPhoto;

    @JsonView(Views.Internal.class)
    @OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Room> room;

    @JsonView(Views.Internal.class)
    @NotBlank(message = "Description about cinema is mandatory")
    @Embedded
    private CinemaInfo cinemaInfo;

    @Override
    public String toString() {
        return "Cinema{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mainPhoto='" + mainPhoto + '\'' +
                ", cinemaInfo=" + cinemaInfo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cinema cinema = (Cinema) o;
        return Objects.equals(id, cinema.id) && Objects.equals(name, cinema.name)
                && Objects.equals(mainPhoto, cinema.mainPhoto) && Objects.equals(cinemaInfo, cinema.cinemaInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, mainPhoto, cinemaInfo);
    }
}
