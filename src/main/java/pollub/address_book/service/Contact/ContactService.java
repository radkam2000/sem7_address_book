package pollub.address_book.service.Contact;

import pollub.address_book.model.Contact;

import java.util.List;

public interface ContactService {
    public List<Contact> getAllContacts();

    public Contact createContact(Contact contact);

    public Boolean deleteContact(Long id);

    public Contact updateContact(Long id, Contact contact);

    public Boolean validateContact(Contact contact);
}
