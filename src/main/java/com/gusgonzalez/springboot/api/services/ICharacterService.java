package com.gusgonzalez.springboot.api.services;
import com.gusgonzalez.springboot.api.entities.Character;

import java.util.List;
import java.util.Optional;

public interface ICharacterService {

    List<Character> findAll();

    Character findById(Long id);

    Optional<Character> findByName(String name);

    Optional<Character> findByAge(Long age);

    Character save(Character character);

    void delete(Long id);
}
