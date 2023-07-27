package com.longdrink.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.longdrink.model.Usuario;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuarioDAO extends CrudRepository<Usuario, Integer> {

    List<Usuario> findAllByactivo(int activo);

    Optional<Usuario> findByidusuario(Integer id);

    Optional<Usuario> findBynombreusuario(String nombre_usuario);
}