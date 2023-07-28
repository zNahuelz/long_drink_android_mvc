package com.longdrink.services;

import com.longdrink.dao.ICursoDAO;
import com.longdrink.model.Curso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CursoService {

    @Autowired
    private ICursoDAO cursoDAO;

    public List<Curso> listarCursos(){
        return (List<Curso>)cursoDAO.findAll();
    }

    public List<Curso> listarCursosActivos(){
        List<Curso> listaC = listarCursos();
        List<Curso> retorno = new ArrayList<Curso>();
        for (Curso c : listaC){
            if (c.getActivo() == 1){
                retorno.add(c);
            }
        }
        return retorno;
    }

    public Curso guardarCurso(Curso c){
        c.setId_curso(0);
        boolean vacio = c.isEmpty();
        if(!vacio){
            return cursoDAO.save(c);
        }
        return null;
    }

    public Curso actualizarCurso(Curso c){
        return cursoDAO.save(c);
    }

    public boolean eliminarCursoID(int id){
        try{
            Curso c = cursoDAO.findById(id).get();
            if(c.getActivo() != 0){
                c.setActivo(0);
                cursoDAO.save(c);
                return true;
            }
        }
        catch(Exception ex){
            return false;
        }
        return false;
    }

    public boolean buscarPorID(int id){
        Optional<Curso> c = cursoDAO.findById(id);
        return c.isPresent();
    }

    public Curso obtenerCurso(int id){
        try{
            return cursoDAO.findById(id).get();
        }
        catch(Exception ex){
            return new Curso();
        }
    }

}
