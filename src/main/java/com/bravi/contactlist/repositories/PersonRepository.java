package com.bravi.contactlist.repositories;

import com.bravi.contactlist.models.entity.Contact;
import com.bravi.contactlist.models.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person,Long> {
    Person findByContacts(Contact contact);

    @Override
    @Query( "SELECT person " +
            "FROM Person person " +
            "LEFT JOIN FETCH person.contacts " +
            "WHERE person.id = :id"
    )
    Optional findById(@Param("id")Long id);
}
