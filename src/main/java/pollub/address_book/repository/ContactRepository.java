package pollub.address_book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pollub.address_book.models.Contact;

@Repository
@Transactional
public interface ContactRepository extends JpaRepository<Contact, Long> {
}
