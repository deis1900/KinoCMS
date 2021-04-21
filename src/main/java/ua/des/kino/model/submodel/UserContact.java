package ua.des.kino.model.submodel;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

@Data
@Embeddable
public class UserContact implements Serializable {

    @Size(min = 2, max = 35, message = "Name must be 2-35 characters long.")
    @Column(table = "users_contacts")
    private String name;

    @Size(min = 2, max = 35, message = "Surname must be 2-35 characters long.")
    @Column(table = "users_contacts")
    private String surname;

    @Column(table = "users_contacts")
    private String address;

    @NotEmpty(message = "You must enter a email address.")
    @Email(message = "Enter a valid email address.")
    @Column(unique = true, table = "users_contacts")
    private String email;

    @Pattern(regexp="(^$|[0-9]{10})")
    @Column(table = "users_contacts")
    private String phone;

    @NotEmpty(message = "You must enter a credit card number.")
    @Pattern(regexp = "^(?:4[0-9]{12}(?:[0-9]{3})?|[25][1-7]"
            + "[0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}"
            + "|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])"
            + "[0-9]{11}|(?:2131|1800|35\\d{3})\\d{11})$",
            message = "Invalid card number.")
    @Column(table = "users_contacts")
    private String card;
}
