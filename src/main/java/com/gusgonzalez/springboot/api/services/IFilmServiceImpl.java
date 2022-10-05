package com.gusgonzalez.springboot.api.services;
import com.gusgonzalez.springboot.api.daos.IFilmDao;
import com.gusgonzalez.springboot.api.entities.Film;
import com.gusgonzalez.springboot.api.entities.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class IFilmServiceImpl implements IFilmService{

    @Autowired
    private IFilmDao filmDao;

    @Override
    @Transactional(readOnly = true)
    public List<Film> findAll() {
        return filmDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Film findById(Long id) {
        return filmDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Film save(Film film) {
        return filmDao.save(film);
    }

    @Override
    @Transactional
    public void delete(Long id) {
         filmDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Gender> findAllGenders() {
        return filmDao.findAllGenders();
    }

}
