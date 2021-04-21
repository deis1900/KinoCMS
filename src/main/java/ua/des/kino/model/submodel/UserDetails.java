package ua.des.kino.model.submodel;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Embeddable
public class UserDetails implements Serializable {

    @Column(table = "users_details")
    @NotBlank(message = "Field sex is mandatory")
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Column(table = "users_details")
    @Enumerated
    private UserLang language;

    @Past(message = "Date input is invalid for a birth date.")
    @Column(table = "users_details")
    private LocalDate birthday;

    @Column(table = "users_details")
    private String city;

    @Column(name = "registr_date", table = "users_details", columnDefinition = "TIMESTAMP")
    private LocalDateTime registrationDate;
}