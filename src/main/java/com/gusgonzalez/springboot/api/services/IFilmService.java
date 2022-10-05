package com.gusgonzalez.springboot.api.services;
import com.gusgonzalez.springboot.api.entities.Character;
import com.gusgonzalez.springboot.api.entities.Film;
import com.gusgonzalez.springboot.api.entities.Gender;

import java.util.List;


public interface IFilmService {

    List<Film> findAll();

    Film findById(Long id);

    Film save(Film film);

    void delete(Long id);

    List<Gender> findAllGenders();

}
