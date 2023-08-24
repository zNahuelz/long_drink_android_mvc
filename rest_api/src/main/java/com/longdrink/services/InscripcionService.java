package com.longdrink.services;

import com.longdrink.dao.IInscripcionDAO;
import com.longdrink.model.Inscripcion;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class InscripcionService {
    @Autowired
    private IInscripcionDAO inscripcionDAO;

    @Autowired
    private EntityManager em;

    public List<Inscripcion> listarInscripcion(){
        return (List<Inscripcion>) inscripcionDAO.findAll();
    }

    public List<Inscripcion> listarActivos(int activo){
        return inscripcionDAO.findAllByactivo(activo);
    }
    
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<Inscripcion> listarPorIDAlum(int id_alum){
        Query query = em.createQuery("SELECT i FROM Inscripcion i WHERE idalumno = :ida and activo = 1 and terminado = 0");
        query.setParameter("ida", id_alum);
        return (List<Inscripcion>) query.getResultList();
    }

    public List<Inscripcion> listarPorTerminado(int t){
        return inscripcionDAO.findAllByterminado(t);
    }
    //En desuso. Safe delete mas adelante.
    public Inscripcion guardarInscripcion(Inscripcion i){
        boolean lleno = i.isFull();
        if(lleno){
            return inscripcionDAO.save(i);
        }
        return null;
    }
    @Transactional
    public int guardarIns(Inscripcion i){
        Query query = em.createQuery("INSERT INTO Inscripcion(id_alumno,id_curso,fechainiciocurso,fechafinalcurso,fechainscripcioncurso,terminado,activo, id_frecuencia, id_turno) values(:ida,:idc,:fic,:ffc,:fiic,:t,:a,:idf,:idt)");
        query.setParameter("ida",i.getId_alumno());
        query.setParameter("idc",i.getId_curso());
        query.setParameter("fic",i.getFecha_inicio_curso());
        query.setParameter("ffc", i.getFecha_final_curso());
        query.setParameter("fiic", i.getFecha_inscripcion());
        query.setParameter("t", i.getTerminado());
        query.setParameter("a",i.getActivo());
        query.setParameter("idf", i.getId_frecuencia());
        query.setParameter("idt", i.getId_turno());
        return query.executeUpdate();
    }

    public Inscripcion actualizarInscripcion(Inscripcion i){
        return inscripcionDAO.save(i);
    }

    @Transactional
    public boolean terminarInscripcionIDAlum(int id_alum){
        try{
            Inscripcion i = inscripcionDAO.findByidalumno(id_alum); //TODO : Check....
            System.out.println("***************************");;
            System.out.println("FRECUENCIA: " + i.getTerminado());
            int terminado = i.getTerminado();
            // if(terminado == 0){
                // System.out.println("FRECUENCIA: " + i.getTerminado());
                // i.setTerminado(1);
                // System.out.println("FRECUENCIA: " + i.getTerminado());
                Query query = em.createQuery("UPDATE Inscripcion i SET i.terminado = "+1+" AND i.activo = "+0+" WHERE i.idalumno = " + i.getId_alumno());
                query.executeUpdate();
                return true;
            // }
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }

    }

    public Optional<Inscripcion> buscarPorIDAlum(int id_alum){
        return Optional.ofNullable(inscripcionDAO.findAllByidalumno(id_alum).get(id_alum)); //TODO : Check.....
    }

    public Optional<Inscripcion> buscarPorActivo(int activo){
        return Optional.ofNullable(inscripcionDAO.findAllByactivo(activo).get(activo)); //TODO : Check....
    }

    public Inscripcion obtenerPorIDAlum(int id_alum){
        return inscripcionDAO.findByidalumno(id_alum);
    }

}
