package com.gusgonzalez.springboot.api.controllers;
import com.gusgonzalez.springboot.api.entities.Film;
import com.gusgonzalez.springboot.api.entities.Gender;
import com.gusgonzalez.springboot.api.services.IFilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class FilmRestController {

    @Autowired
    private IFilmService iFilmService;

    @GetMapping("/movies")
    public List<Film> index() {
        return iFilmService.findAll();
    }
    @GetMapping("/movies/genders")
    public List<Gender> listGenders() {
        return iFilmService.findAllGenders();
    }
    @GetMapping("/movies/{id}")
    public ResponseEntity<?>show(@PathVariable Long id){
       Film film = null;
       Map<String,Object> response = new HashMap<>();

       try{
           film = iFilmService.findById(id);
       }catch (DataAccessException e){
           response.put("message","Error al realizar la consulta en la base de datos");
           response.put("error",e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
           return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
       }
       if(film == null){
           response.put("message","la film con :".concat(id.toString().concat("no se encuentra en la base de datos!")));
           return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
       }
       return new ResponseEntity<Film>(film,HttpStatus.OK);
    }
    public ResponseEntity<?>create(@Valid @RequestBody Film film, BindingResult result){
        Map<String,Object> response = new HashMap<>();
        Film filmNew = null;

        if(result.hasErrors()){
            List<String>errors= result.getFieldErrors()
                               .stream()
                               .map(err->"el campo".concat(err.getField().concat(" ").concat(err.getDefaultMessage())))
                               .collect(Collectors.toList());
            response.put("error",errors);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
        }
        try {
            filmNew = iFilmService.save(film);
        }catch (DataAccessException e){
            response.put("message","error al crear una pelicula");
            response.put("error",e.getMessage().concat(" ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message","la pelicula se a creado con exito!");
        response.put("film",filmNew);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
    }
    @PutMapping("/movies/{id}")
    public ResponseEntity<?>update(@Valid @RequestBody Film film, BindingResult result,@PathVariable Long id){
        Map<String,Object> response = new HashMap<>();
        Film filmCurrent = iFilmService.findById(id);
        Film filmUpdate = null;

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                                 .stream()
                                 .map(err-> "el campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()))
                                 .collect(Collectors.toList());
            response.put("error",errors);
            return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST);
        }
        if (filmCurrent == null){
            response.put("message","error al actualizar la pelicula no se encuentra en la base de datos");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try{
            filmCurrent.setTitle(film.getTitle());
            filmCurrent.setImage(film.getImage());
            filmCurrent.setQualification(film.getQualification());
            filmCurrent.setCreationDate(film.getCreationDate());
            filmCurrent.setCharacter(film.getCharacter());
            filmUpdate = iFilmService.save(filmCurrent);
        }catch (DataAccessException e){
            response.put("message","error al actualizar la pelicula");
            response.put("error",e.getMessage().concat(" ").concat(e.getMostSpecificCause().getMessage()));
        }
           response.put("message","la pelicula se a actualizado con exito");
           response.put("film",filmUpdate);
           return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
    }
    @DeleteMapping("/movies/{id}")
    public ResponseEntity<?>delete(@PathVariable Long id){
        Map<String,Object> response = new HashMap<>();
        try{
            Film film = iFilmService.findById(id);
            iFilmService.delete(id);
        }catch (DataAccessException e){
            response.put("message","error al eliminar la pelicula");
            response.put("error",e.getMessage().concat(" ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message","la pelicula se a eliminado con exito");
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);

    }

}
