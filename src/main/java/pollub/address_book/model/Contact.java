package pollub.address_book.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Contacts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Size(min = 3, message = "Name too short")
    @NotBlank(message = "Name cannot be empty")
    @NotNull(message = "Name cannot be empty")
    private String name;
    @NotBlank(message = "Surname cannot be empty")
    @NotNull(message = "Surname cannot be empty")
    @Size(min = 3, message = "Surname too short")
    private String surname;
    @NotBlank(message = "Address cannot be empty")
    @NotNull(message = "Address cannot be empty")
    @Size(min = 3, message = "Address too short")
    private String address;
    @NotNull(message = "Building number cannot be empty")
    @Digits(integer = 3, fraction = 0, message = "Wrong building number")
    private Integer buildingNumber;
    @NotNull(message = "Building number cannot be empty")
    @Digits(integer = 4, fraction = 0, message = "Wrong flat number")
    private Integer flatNumber;
    @NotBlank(message = "Building number cannot be empty")
    @NotNull(message = "Building number cannot be empty")
    @Size(min = 9, max = 14, message = "Wrong phone number")
    private String phoneNumber;
}
