package com.longdrink.dao;

import com.longdrink.model.UsuarioProfesor;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IUsuarioProfesorDAO extends CrudRepository<UsuarioProfesor, Integer> {
    Optional<UsuarioProfesor> findByidUsuario(int idUsuario);
    Optional<UsuarioProfesor> findByidProfesor(int idProfesor);
}
