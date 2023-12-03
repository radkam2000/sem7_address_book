package pollub.address_book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pollub.address_book.models.Contact;
import pollub.address_book.repository.ContactRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = AddressBookApplication.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
//@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@AutoConfigureTestDatabase

public class ContactControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ContactRepository contactRepository;

    @Test
    public void givenStudents_whenGetStudents_thenStatus200()
            throws Exception {
        createTestContact("Paul", "Nazwisko", "Skrzatów", 23, 12, "123123123");
        mvc.perform(get("/admin/users").contentType(MediaType.APPLICATION_JSON))
//        mvc.perform(get("/api/contacts").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    private void createTestContact(String name, String surname, String address, Integer buildingNumber, Integer flatNuber, String phoneNumber) {
        Contact contact = new Contact(null, name, surname, address, buildingNumber, flatNuber, phoneNumber);
        contactRepository.saveAndFlush(contact);
    }
}
