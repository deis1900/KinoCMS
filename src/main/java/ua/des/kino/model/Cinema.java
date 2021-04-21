package ua.des.kino.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(name = "main_photo")
    private String mainPhoto;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Room> room;

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
