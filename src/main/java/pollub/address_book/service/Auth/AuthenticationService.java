package pollub.address_book.service.Auth;

import pollub.address_book.dto.LoginResponseDTO;
import pollub.address_book.model.ApplicationUser;

public interface AuthenticationService {
    public ApplicationUser registerUser(String username, String password);

    public LoginResponseDTO loginUser(String username, String password);
}
