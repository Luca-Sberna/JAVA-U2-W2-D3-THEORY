package users.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserRegistrationPayload {
	@NotNull(message = "il nome è obbligatorio")
	@Size(min = 3, max = 30)
	String name;
	@NotNull
	String surname;
	@Email(message = "non hai inserito un indirizzo email valido")
	String email;
}
