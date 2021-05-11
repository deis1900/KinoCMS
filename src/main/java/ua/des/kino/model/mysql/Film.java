package ua.des.kino.model.mysql;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.des.kino.config.Views;
import ua.des.kino.model.mysql.submodel.FilmDetails;
import ua.des.kino.model.mysql.submodel.Quality;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "films")
@SecondaryTable(name = "films_details", pkJoinColumns = @PrimaryKeyJoinColumn(name = "id"))
public class Film implements Serializable {

    @JsonView(Views.Public.class)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonView(Views.Public.class)
    @Column
    private String name;

    @Column(name = "user_age")
    private Integer userAge;

    @Column(name = "main_photo")
    private String mainPhoto;

    @Column(columnDefinition = "TIME")
    private LocalTime duration;

    @Column(name = "start_date", columnDefinition = "TIMESTAMP")
    private LocalDate startDate;

    @Column(name = "finish_date", columnDefinition = "TIMESTAMP")
    private LocalDate finishDate;

    @Column(name = "video_type")
    private String videoType;

    @JsonView(Views.Public.class)
    @NotBlank(message = "Quality is mandatory")
    @Column
    @Enumerated
    private Quality quality;

    @JsonView({Views.Internal.class})
    @Embedded
    private FilmDetails filmDetails;
}
