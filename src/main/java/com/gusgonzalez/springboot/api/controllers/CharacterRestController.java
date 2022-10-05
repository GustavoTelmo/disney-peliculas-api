package com.gusgonzalez.springboot.api.controllers;
import com.gusgonzalez.springboot.api.entities.Character;
import com.gusgonzalez.springboot.api.services.ICharacterService;
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
@RequestMapping("/api/movies")
public class CharacterRestController {

    @Autowired
    private ICharacterService characterService;

    @GetMapping("/characters")
    public List<Character> listCharacters() {
        return characterService.findAll();
    }
    @GetMapping("/characters/id/{id}")
    public ResponseEntity<?> showCharactersId(@PathVariable Long id){
        Character character = null;
        Map<String,Object> response = new HashMap<>();

        try{
            character = characterService.findById(id);
        }catch (DataAccessException e){
            response.put("message","Error al realizar la consulta en la base de datos");
            response.put("error",e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(character == null){
            response.put("message","el personaje no se encuentra en la base de datos");
            return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Character>(character,HttpStatus.OK);
    }

    @GetMapping("/characters/name/{name}")
    public ResponseEntity<?> showCharactersName(@PathVariable String name) {
        Character character = null;
        Map<String, Object> response = new HashMap<>();

        try {
            character =  characterService.findByName(name).orElse(null);;
        } catch (DataAccessException e) {
            response.put("message", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (character == null) {
            response.put("message", "el nombre no se encuentra en la base de datos");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Character>(character, HttpStatus.OK);

     }
    @GetMapping("/characters/age/{age}")
    public ResponseEntity<?> showCharacterAge(@PathVariable Long age) {
        Character character = null;
        Map<String, Object> response = new HashMap<>();

        try {
            character =  characterService.findByAge(age).orElse(null);
        } catch (DataAccessException e) {
            response.put("message", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (character == null) {
            response.put("message", "la edad no se encuentra en la base de datos");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Character>(character, HttpStatus.OK);

    }
    @PostMapping("/characters")
    public ResponseEntity<?>create(@Valid @RequestBody Character character, BindingResult result){
        Character characterNew = null;
        Map<String, Object> response = new HashMap<>();

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                                  .stream()
                                  .map(err-> "el campo".concat(err.getField().concat(" ").concat(err.getDefaultMessage())))
                                  .collect(Collectors.toList());
        }
        try{
            characterNew = characterService.save(character);
        }catch (DataAccessException e){
            response.put("message","error al crear el personaje");
            response.put("error",e.getMessage().concat(" ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
           response.put("message","el personaje se a creado con exito");
           response.put("characters","characterNew");
           return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
    }
    @PutMapping("/characters/{id}")
    public ResponseEntity<?>update(@Valid @RequestBody Character character, BindingResult result, @PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        Character characterCurrent = characterService.findById(id);
        Character characterUpdate = null;

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "el campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()))
                    .collect(Collectors.toList());
            response.put("error", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        if (characterCurrent == null) {
            response.put("message", "error al actualizar el film no se encuentra en la base de datos");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {
            characterCurrent.setName(character.getName());
            characterCurrent.setAge(character.getAge());
            characterCurrent.setWeight(character.getWeight());
            characterCurrent.setHistory(character.getHistory());
            characterCurrent.setImage(character.getImage());
            characterUpdate = characterService.save(characterCurrent);
        } catch (DataAccessException e) {
            response.put("message", "error al actualizar el personaje");
            response.put("error", e.getMessage().concat(" ").concat(e.getMostSpecificCause().getMessage()));
        }
           response.put("message", "el personaje se a actualizado con exito");
           response.put("characters", characterUpdate);
           return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
    @DeleteMapping("/characters/{id}")
    public ResponseEntity<?>delete(@PathVariable Long id){
        Map<String,Object> response = new HashMap<>();
        try{
           Character character = characterService.findById(id);
            characterService.delete(id);
        }catch (DataAccessException e){
            response.put("message","error al eliminar el personaje");
            response.put("error",e.getMessage().concat(" ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message","el personaje se a eliminado con exito");
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);

    }
}
