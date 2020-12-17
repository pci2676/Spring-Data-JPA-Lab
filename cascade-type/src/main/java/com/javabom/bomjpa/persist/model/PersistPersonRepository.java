package com.javabom.bomjpa.persist.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersistPersonRepository extends JpaRepository<PersistPerson, Long> {
}
