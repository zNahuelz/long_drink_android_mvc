package com.longdrink.controller;

import com.longdrink.model.Profesor;
import com.longdrink.services.ProfesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profesor")
public class ProfesorController {
    //TODO : Testear en Postman....!
    @Autowired
    private ProfesorService servPro;

    //FRONT: Listar profesores
    @GetMapping
    public List<Profesor> listarProfesores(){
        return servPro.listarProfesores();
    }

    //FRONT: Listar activos
    @GetMapping("activos")
    public List<Profesor> listarProfesoresActivos(){
        return servPro.listarProfesoresActivos(1);
    }

    //FRONT: Guardar profesor
    @PostMapping("nuevo")
    public boolean guardarProfesor(@RequestBody Profesor p){
        Profesor pro = servPro.guardarProfesor(p);
        if(pro != null){
            return true;
        }
        else{ return false; }
    }

    //FRONT: Actualizar profesor.
    @PutMapping("editar")
    public String editarProfesor(@RequestBody Profesor p){
        boolean respuesta = servPro.buscarPorID(p.getId_profesor());
        if(respuesta){
            Profesor pro = servPro.obtenerPorID(p.getId_profesor());
            if(p.getNombre() == null) p.setNombre(pro.getNombre());
            if(p.getAp_materno() == null) p.setAp_materno(pro.getAp_materno());
            if(p.getAp_paterno() == null) p.setAp_paterno(pro.getAp_paterno());
            if(p.getDni() == null) p.setDni(pro.getDni());
            if(p.getEmail() == null) p.setEmail(pro.getEmail());
            if (p.getFoto() == null) p.setFoto(pro.getFoto());
            p.setActivo(pro.getActivo());
            servPro.actualizarProfesor(p);
            return "Success";
        }
        else{
            return "Failed";
        }
    }

    //FRONT: Obtener profesor por ID.
    @GetMapping("id")
    public Profesor obtenerPorID(@RequestParam int id){
        try{
            return servPro.obtenerPorID(id);
        }
        catch(Exception ex){
            return new Profesor();
        }

    }
    //FRONT: Eliminar profesor por ID.
    @DeleteMapping("eliminar/id")
    public boolean eliminarPorID(@RequestParam int id){
        return servPro.eliminarProfesor(id);
    }

}
