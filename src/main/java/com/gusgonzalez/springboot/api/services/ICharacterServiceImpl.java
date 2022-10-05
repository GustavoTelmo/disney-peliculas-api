package com.gusgonzalez.springboot.api.services;
import com.gusgonzalez.springboot.api.daos.ICharacterDao;
import com.gusgonzalez.springboot.api.entities.Character;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ICharacterServiceImpl implements ICharacterService{

    @Autowired
    private ICharacterDao characterDao;

    @Override
    @Transactional(readOnly = true)
    public List<Character> findAll() {
        return characterDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Character findById(Long id) {
        return characterDao.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Character> findByName(String name) {
        return  characterDao.findByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Character> findByAge(Long age) {
        return  characterDao.findByAge(age);
    }

    @Override
    @Transactional
    public Character save(Character character) {
        return characterDao.save(character);
    }

    @Override
    @Transactional
    public void delete(Long id) {
              characterDao.deleteById(id);
    }
}
