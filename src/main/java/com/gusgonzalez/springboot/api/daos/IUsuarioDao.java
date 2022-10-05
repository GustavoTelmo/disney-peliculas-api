package com.gusgonzalez.springboot.api.daos;

import com.gusgonzalez.springboot.api.entities.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface IUsuarioDao extends CrudRepository<Usuario,Long> {

    Usuario findByUsername(String username);
}
