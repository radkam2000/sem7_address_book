package pollub.address_book.services.Auth;

import pollub.address_book.models.ApplicationUser;
import pollub.address_book.models.LoginResponseDTO;

public interface AuthenticationService {
    public ApplicationUser registerUser(String username, String password);

    public LoginResponseDTO loginUser(String username, String password);
}
