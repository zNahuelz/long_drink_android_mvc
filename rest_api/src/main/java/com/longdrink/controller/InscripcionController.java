package com.longdrink.controller;

import com.longdrink.model.Inscripcion;
import com.longdrink.services.InscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/inscripcion")
public class InscripcionController {
    //TODO : Testear en Postman....!
    @Autowired
    private InscripcionService servIns;

    //FRONT: Listar inscripciones
    @GetMapping
    public List<Inscripcion> listarInscripcion() { return servIns.listarInscripcion(); }

    //FRONT : Listar inscripciones activas
    @GetMapping("activos")
    public List<Inscripcion> listarActivos() {return servIns.listarActivos(1); }

    //FRONT: Listar inscripcion por id alumno.
    @GetMapping("id_alum")
    public List<Inscripcion> listarPorIDAlum(@RequestParam int id_alum) { return servIns.listarPorIDAlum(id_alum); }

    //FRONT: Listar inscripciones terminadas
    @GetMapping("terminado")
    public List<Inscripcion> listarPorTerminado(){ return servIns.listarPorTerminado(1); }

    //FRONT: Guardar inscripcion
    @PostMapping("nuevo")
    public boolean guardarInscripcion(@RequestBody Inscripcion i){
        int ins = servIns.guardarIns(i); 
        if(ins >0){
            return true;
        }
        else { return false; }
    }

    //FRONT: Editar inscripcion
    @PutMapping("editar")
    public String editarInscripcion(@RequestBody Inscripcion i){
        Optional<Inscripcion> respuesta = servIns.buscarPorIDAlum(i.getId_alumno());
        if(respuesta.isPresent()){
            Inscripcion ins = servIns.obtenerPorIDAlum(i.getId_alumno());
            if(i.getFecha_inscripcion() == null) i.setFecha_inscripcion(ins.getFecha_inscripcion());
            if(i.getFecha_inicio_curso() == null) i.setFecha_inicio_curso(ins.getFecha_inicio_curso());
            if(i.getFecha_final_curso() == null) i.setFecha_final_curso(ins.getFecha_final_curso());
            i.setActivo(ins.getActivo());
            i.setTerminado(ins.getTerminado());
            servIns.guardarInscripcion(i);
            return "Success";
        }
        else{ return "Failed"; }

    }

    //FRONT: Marcar inscripcion como curso terminado.
    @DeleteMapping("eliminar")
    public boolean terminarInscripcion(@RequestParam int id_alum){
        return servIns.terminarInscripcionIDAlum(id_alum);
    }

    //FRONT: Obtener inscripcion por id_alum
    @GetMapping("id")
    public Inscripcion obtenerPorIDAlum(int id_alum){
        return servIns.obtenerPorIDAlum(id_alum);
    }

}
