package com.group13.sysc4806;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressBookRepository extends JpaRepository<AddressBook, Long> {
    AddressBook findById(long id);
}
