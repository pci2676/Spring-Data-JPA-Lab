package com.javabom.bomjpa.persist.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersistPhoneRepository extends JpaRepository<PersistPhone, Long> {
    List<PersistPhone> findAllByPersistPerson(PersistPerson persistPerson);
}
