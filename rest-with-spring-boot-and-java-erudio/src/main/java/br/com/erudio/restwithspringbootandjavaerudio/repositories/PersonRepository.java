package br.com.erudio.restwithspringbootandjavaerudio.repositories;

import br.com.erudio.restwithspringbootandjavaerudio.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> { }
