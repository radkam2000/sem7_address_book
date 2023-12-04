package pollub.address_book;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import pollub.address_book.services.Token.TokenService;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TokenUnitTests {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private JwtEncoder jwtEncoder;

    @Test
    public void testGenerateJwt() {

        Authentication auth = Mockito.mock(Authentication.class);
        Mockito.when(auth.getName()).thenReturn("testUser");

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(auth);
        SecurityContextHolder.setContext(securityContext);

        String token = tokenService.generateJwt(auth);
        System.out.println(token);
        assertNotNull(token);
    }
}