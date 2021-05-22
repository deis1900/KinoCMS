package ua.des.kino.model.audience.submodel;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import ua.des.kino.config.Views;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Embeddable
public class CustomerDetails implements Serializable {

    @JsonView(Views.Public.class)
    @Column(table = "customers_details")
    @NotBlank(message = "Field sex is mandatory")
    @Enumerated
    private Sex sex;

    @JsonView(Views.Public.class)
    @Column(table = "customers_details")
    @Enumerated
    private UserLang language;

    @JsonView(Views.Internal.class)
    @Past(message = "Date input is invalid for a birth date.")
    @Column(table = "customers_details")
    private LocalDate birthday;

    @JsonView(Views.Public.class)
    @Column(table = "customers_details")
    private String city;

    @JsonView(Views.Public.class)
    @Column(name = "registr_date", table = "customers_details", columnDefinition = "TIMESTAMP")
    private LocalDateTime registrationDate;
}