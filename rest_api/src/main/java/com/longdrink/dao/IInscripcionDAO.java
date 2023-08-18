package com.longdrink.dao;

import com.longdrink.model.Inscripcion;
import org.springframework.data.repository.CrudRepository;
import java.util.List;


public interface IInscripcionDAO extends CrudRepository<Inscripcion,Integer> {
    List<Inscripcion> findAllByidalumno(int id_alum); //Funcionara?!
    List<Inscripcion> findAllByterminado(int terminado);
    List<Inscripcion> findAllByactivo(int activo);
    Inscripcion findByidalumno(int id_alum);
}
