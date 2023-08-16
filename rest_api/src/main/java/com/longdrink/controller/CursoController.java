package com.longdrink.controller;

import com.longdrink.model.*;
import com.longdrink.services.CursoFrecuenciaService;
import com.longdrink.services.CursoService;
import com.longdrink.services.CursoTurnoService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/curso")
public class CursoController {
    //TODO : Testear en Postman....!
    @Autowired
    private CursoFrecuenciaService servCF;
    @Autowired
    private CursoTurnoService servCT;
    @Autowired
    private CursoService servCurs;

    //FRONT: Listar TODOS los cursos.
    @GetMapping
    public List<Curso> listarCursos(){ return servCurs.listarCursos(); }

    //FRONT: Listar activos.
    @GetMapping("activos")
    public List<Curso> listarCursosActivos(){ return servCurs.listarCursosActivos(); }

    //FRONT: Guardar curso.
    @PostMapping("nuevo")
    public boolean guardarCurso(@RequestBody Curso c){
        Curso cur = servCurs.guardarCurso(c);
        if(cur != null){
            return true;
        }
        else{ return false; }
    }

    //FRONT: Actualizar curso.
    @PutMapping("editar")
    public String editarCurso(@RequestBody Curso c){
        boolean respuesta = servCurs.buscarPorID(c.getId_curso());
        if (respuesta){
            Curso curs = servCurs.obtenerCurso(c.getId_curso());
            if(c.getNombre() == null) c.setNombre(curs.getNombre());
            if(c.getDescripcion() == null) c.setDescripcion(curs.getDescripcion());
            if(c.getCosto() <= 0) c.setCosto(curs.getCosto());
            if(c.getDuracion() <= 0) c.setDuracion(curs.getDuracion());
            if(c.getFoto() == null) c.setFoto(curs.getFoto());
            c.setActivo(curs.getActivo());
            servCurs.actualizarCurso(c);
            return "Success";

        }
        else{  return "Failed"; }
    }

    @PutMapping("fEditar")
    @Transactional
    public boolean editarCursoF(@RequestBody SQEditCurso c){
        //TODO : REVISAR.....
        boolean respuesta = servCurs.buscarPorID(c.getId_curso());
        if(respuesta){
            Curso curs = servCurs.obtenerCurso(c.getId_curso());
            //Curso actualizar = curs;
            if(c.getNombre() != null) curs.setNombre(c.getNombre());
            if(c.getDescripcion() != null) curs.setDescripcion(c.getDescripcion());
            if(c.getCosto() >= 1) curs.setCosto(c.getCosto());
            if(c.getDuracion() >= 1) curs.setDuracion(c.getDuracion());
            if(c.getFoto() != null) curs.setFoto(c.getFoto());
            curs.setActivo(c.getActivo());
            servCurs.actualizarCurso(curs);
            CursoFrecuencia cf = new CursoFrecuencia(c.getId_curso(),c.getId_frecuencia());
            CursoTurno ct = new CursoTurno(c.getId_curso(),c.getId_turno());
            servCF.actualizarCF(cf);
            servCT.actualizarCT(ct);
            return true;
        }
        else{ return false; }
    }

    //FRONT: Obtener ID de Turno y Frecuencia, por ID de Curso.
    @GetMapping("turnofrecuencia")
    public FrontFrecuenciaTurno obtenerTurnoFrecuencia(@RequestParam int id){
        CursoFrecuencia cf = servCF.buscarFrecuencia(id);
        CursoTurno ct = servCT.buscarTurno(id);
        return new FrontFrecuenciaTurno(cf.getId_frecuencia(),ct.getId_turno());
    }

    //FRONT: Obtener curso por ID.
    @GetMapping("id")
    public Curso obtenerPorID(@RequestParam int id){ return servCurs.obtenerCurso(id); }

    //FRONT: Eliminar curso.
    @DeleteMapping("eliminar/id")
    public boolean eliminarCursoID(@RequestParam int id){ return servCurs.eliminarCursoID(id);}



}
