package com.longdrink.services;

import com.longdrink.dao.ICursoTurnoDAO;
import com.longdrink.model.CursoTurno;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CursoTurnoService {
    @Autowired
    private ICursoTurnoDAO cursTurnoDAO;

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
}
