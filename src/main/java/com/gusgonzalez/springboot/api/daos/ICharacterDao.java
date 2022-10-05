package com.gusgonzalez.springboot.api.daos;
import com.gusgonzalez.springboot.api.entities.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ICharacterDao extends JpaRepository<Character,Long> {

    Optional<Character>findByName(String name);

    Optional<Character> findByAge(Long age);
}
