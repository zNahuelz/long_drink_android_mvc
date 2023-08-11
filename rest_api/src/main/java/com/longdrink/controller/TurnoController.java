package com.longdrink.controller;

import com.longdrink.model.Turno;
import com.longdrink.services.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turno")
public class TurnoController {
    //TODO : Testear en Postman....!
    @Autowired
    private TurnoService servTurn;
    //FRONT: Listar TODOS los turnos.
    @GetMapping
    public List<Turno> listarTurnos() { return servTurn.listarTurnos(); }
    //FRONT: Listar los turnos activos.
    @GetMapping("activos")
    public List<Turno> listarTurnosActivos(){ return servTurn.listarTurnosActivos(); }
    //FRONT: Guardar turno.
    @PostMapping("nuevo")
    public boolean guardarTurno(@RequestBody Turno t){
        Turno turno = servTurn.guardarTurno(t);
        if(turno != null){
            return true;
        }
        else{ return false; }
    }
    //FRONT: Editar turno.
    @PutMapping("editar")
    public boolean editarTurno(@RequestBody Turno t){
        boolean respuesta = servTurn.buscarPorID(t.getId_turno());
        if(respuesta){
            Turno turno = servTurn.obtenerTurno(t.getId_turno());
            if(t.getNombre() == null) t.setNombre(turno.getNombre());
            if(t.getHora_inicio() == null) t.setHora_inicio(turno.getHora_inicio());
            if(t.getHora_final() == null) t.setHora_final(turno.getHora_final());
            t.setActivo(turno.getActivo());
            servTurn.actualizarTurno(t);
            return true;
        }
        else{ return false; }
    }
    //FRONT: Obtener turno por ID.
    @GetMapping("id")
    public Turno obtenerPorID(@RequestParam int id){
        return servTurn.obtenerTurno(id);
    }
    //FRONT: Eliminar Turno por ID.
    @DeleteMapping("eliminar/id")
    public boolean eliminarTurnoPorID(@RequestParam int id){ return servTurn.eliminarTurnoID(id); }


}
