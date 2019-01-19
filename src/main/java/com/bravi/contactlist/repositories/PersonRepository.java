package com.bravi.contactlist.repositories;

import com.bravi.contactlist.models.entity.Contact;
import com.bravi.contactlist.models.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person,Long> {
    Person findByContacts(Contact contact);
}
