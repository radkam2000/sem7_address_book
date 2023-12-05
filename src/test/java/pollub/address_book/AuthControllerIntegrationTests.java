package pollub.address_book;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import pollub.address_book.model.ApplicationUser;
import pollub.address_book.model.Role;
import pollub.address_book.repository.RoleRepository;
import pollub.address_book.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = AddressBookApplication.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@AutoConfigureTestDatabase

public class AuthControllerIntegrationTests {

    @Autowired
    PasswordEncoder passwordEncode;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void givenRegisterData_whenPostRegister_thenStatus200() throws Exception {
        JSONObject registerData = new JSONObject();
        registerData.put("username", "user");
        registerData.put("password", "password");

        mvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON).content(String.valueOf(registerData)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void givenUserLoginData_whenPostLogin_thenStatus200() throws Exception {
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.save(new Role("USER"));
        roles.add(userRole);
        createTestUser(2, "user", "password", roles);
        JSONObject loginData = new JSONObject();
        loginData.put("username", "user");
        loginData.put("password", "password");

        mvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(String.valueOf(loginData)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    private void createTestUser(Integer userId, String userName, String password, Set<Role> roles) {
        ApplicationUser user = new ApplicationUser(userId, userName, passwordEncode.encode(password), roles);
        userRepository.saveAndFlush(user);
    }
}
