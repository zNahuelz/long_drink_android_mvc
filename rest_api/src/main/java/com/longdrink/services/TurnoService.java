package com.longdrink.services;

import com.longdrink.dao.ITurnoDAO;
import com.longdrink.model.Turno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService {
    @Autowired
    private ITurnoDAO turnoDAO;

    public List<Turno> listarTurnos(){ return (List<Turno>)turnoDAO.findAll(); }

    public List<Turno> listarTurnosActivos(){
        List<Turno> listaTurnos = listarTurnos();
        List<Turno> retorno = new ArrayList<Turno>();
        for(Turno t : listaTurnos){
            if(t.getActivo() == 1){
                retorno.add(t);
            }
        }
        return retorno;
    }

    public Turno guardarTurno(Turno t){
        if(t != null){
            t.setId_turno(0);
            return turnoDAO.save(t);
        }
        else{
            return null;
        }
    }

    public Turno actualizarTurno(Turno t){ return turnoDAO.save(t); }

    public boolean eliminarTurnoID(int id){
        try{
            Turno t = turnoDAO.findById(id).get();
            if(t.getActivo() != 0){
                t.setActivo(0);
                turnoDAO.save(t);
                return true;
            }
        }
        catch(Exception ex){
            return false;
        }
        return false;
    }

    public boolean buscarPorID(int id){
        Optional<Turno> t = turnoDAO.findById(id);
        return t.isPresent();
    }

    public Turno obtenerTurno(int id){
        try{
            return turnoDAO.findById(id).get();
        }
        catch(Exception ex){
            return new Turno();
        }
    }
}
