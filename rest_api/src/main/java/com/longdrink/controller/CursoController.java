package com.longdrink.controller;

import com.longdrink.model.*;
import com.longdrink.services.CursoFrecuenciaService;
import com.longdrink.services.CursoService;
import com.longdrink.services.CursoTemaService;
import com.longdrink.services.CursoTurnoService;
import com.longdrink.services.ProfesorCursoService;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/curso")
public class CursoController {

    @Autowired
    private CursoFrecuenciaService servCF;
    @Autowired
    private CursoTurnoService servCT;
    @Autowired
    private CursoService servCurs;
    @Autowired
    private ProfesorCursoService servProfCur;
    @Autowired
    private CursoTemaService servCurTem;

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

    //FRONT: Actualizar curso (Simple)
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

    //FRONT: Actualizar Curso, CursoTurno y CursoFrecuencia.
    @PutMapping("fEditar")
    @Transactional
    public boolean editarCursoF(@RequestBody SQEditCurso c){
        boolean respuesta = servCurs.buscarPorID(c.getId_curso());
        if(respuesta){
            Curso curs = servCurs.obtenerCurso(c.getId_curso());
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


    @GetMapping("cursofrecuencia/listar/id_curso")
    public List<CursoFrecuencia> listaFrecuencias(@RequestParam int id_curso){
        return servCF.findAllCursoFrecuencia(id_curso);
    }

    @GetMapping("cursoturno/listar/id_curso")
    public List<CursoTurno> listaTurnos(@RequestParam int id_curso){
        return servCT.findAllCursoTurno(id_curso);
    }

    @GetMapping("profesorcurso/id_curso")
    public ProfesorCurso obtenerProfesorCurso(@RequestParam int id_curso){
        return servProfCur.obtenerProfesorCurso(id_curso);
    }

    @GetMapping("cursotema/listar/id_curso")
    public List<CursoTema> obtenerCursoTema(@RequestParam int id_curso){
        return servCurTem.getALlCursoTemasByID(id_curso);
    }
}
