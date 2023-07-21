package com.longdrink.controller;

import com.longdrink.model.Curso;
import com.longdrink.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/curso")
public class CursoController {
    //TODO : Testear en Postman....!
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
            if(c.getCosto() >= 0) c.setCosto(curs.getCosto());
            if(c.getDuracion() >= 0) c.setDuracion(curs.getDuracion());
            c.setActivo(curs.getActivo());
            servCurs.actualizarCurso(c);
            return "Success";

        }
        else{  return "Failed"; }
    }

    //FRONT: Obtener curso por ID.
    @GetMapping("id")
    public Curso obtenerPorID(@RequestParam int id){ return servCurs.obtenerCurso(id); }

    //FRONT: Eliminar curso.
    @DeleteMapping("eliminar/id")
    public boolean eliminarCursoID(@RequestParam int id){ return servCurs.eliminarCursoID(id);}



}
