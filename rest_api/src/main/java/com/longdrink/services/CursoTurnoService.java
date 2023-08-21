package com.longdrink.services;

import com.longdrink.model.CursoTurno;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CursoTurnoService {

    @Autowired
    private EntityManager em;

    //Actualizar CursoTurno - 1:1 (Un curso - Un turno)
    public int actualizarCT(CursoTurno ct){
        Query query = em.createQuery("UPDATE CursoTurno c SET c.idTurno = "+ct.getId_turno()+" WHERE c.idCurso ="+ct.getId_curso());
        return query.executeUpdate();
    }

    public CursoTurno buscarTurno(int id_curso){
        try{
            Query query = em.createQuery("SELECT t from CursoTurno t where t.idCurso = "+id_curso);
            return (CursoTurno) query.getSingleResult();
        }
        catch(Exception ex){
            return new CursoTurno();
        }
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<CursoTurno> findAllCursoTurno(int id_curso){
        return (List<CursoTurno>)em.createQuery("SELECT ct from CursoTurno ct where ct.id_curso = " + id_curso).getResultList();
    }
}
