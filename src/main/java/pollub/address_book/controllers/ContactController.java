package pollub.address_book.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pollub.address_book.models.Contact;
import pollub.address_book.services.Contact.ContactService;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {
    @Autowired
    private ContactService contactService;

    @GetMapping("")
    public List<Contact> getAllUsers() {
        return contactService.getAllContacts();
    }

    @PostMapping("")
    public ResponseEntity<Contact> createContact(@Valid @RequestBody Contact contact) {
        Contact createdContact = contactService.createContact(contact);
        return ResponseEntity.ok(createdContact);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable Long id, @Valid @RequestBody Contact contact) {
        Contact updatedContact = contactService.updateContact(id, contact);
        return ResponseEntity.ok(updatedContact);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContact(@PathVariable Long id) {
        if (contactService.deleteContact(id)) return ResponseEntity.ok("Deleted");
        return ResponseEntity.badRequest().build();
    }
}