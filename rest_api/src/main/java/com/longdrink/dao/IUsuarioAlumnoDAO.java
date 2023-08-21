package com.longdrink.dao;

import com.longdrink.model.UsuarioAlumno;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IUsuarioAlumnoDAO extends CrudRepository<UsuarioAlumno,Integer> {
    Optional<UsuarioAlumno> findByidUsuario(int idUsuario);
    Optional<UsuarioAlumno> findByidAlumno(int idAlumno);
}
