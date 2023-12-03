package pollub.address_book.services.Contact;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import pollub.address_book.models.Contact;
import pollub.address_book.repository.ContactRepository;

import java.util.List;

@Service
@Transactional
public class ContactServiceImpl implements ContactService {
    private ContactRepository contactRepository;

    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    @Override
    public Contact createContact(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public Boolean deleteContact(Long id) {
        contactRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found"));
        contactRepository.deleteById(id);
        return true;
    }

    @Override
    public Contact updateContact(Long id, Contact contact) {
        Contact foundContact = contactRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found"));
        foundContact.setAddress(contact.getAddress());
        foundContact.setName(contact.getName());
        foundContact.setBuildingNumber(contact.getBuildingNumber());
        foundContact.setFlatNumber(contact.getFlatNumber());
        foundContact.setPhoneNumber(contact.getPhoneNumber());
        return contactRepository.save(foundContact);
    }

    @Override
    public Boolean validateContact(Contact contact) {
        return null;
    }
}