package com.gusgonzalez.springboot.api.daos;
import com.gusgonzalez.springboot.api.entities.Film;
import com.gusgonzalez.springboot.api.entities.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface IFilmDao extends JpaRepository<Film,Long> {

    @Query("from Gender")
    List<Gender> findAllGenders();

}
