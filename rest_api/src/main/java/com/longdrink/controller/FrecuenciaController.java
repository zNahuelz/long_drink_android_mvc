package com.longdrink.controller;

import com.longdrink.model.Frecuencia;
import com.longdrink.services.FrecuenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/frecuencia")
public class FrecuenciaController {
    //TODO : Testear en Postman...!
    @Autowired
    private FrecuenciaService servFrec;

    //FRONT: Listar frecuencias...
    @GetMapping
    public List<Frecuencia> listarFrecuencias(){
        return servFrec.listarFrecuencias();
    }

    //FRONT: Listar activos
    @GetMapping("activos")
    public List<Frecuencia> listarFrecActivas(){
        return servFrec.listarFrecuenciasAct();
    }

    //FRONT: Guardar frecuencia.
    @PostMapping("nuevo")
    public boolean guardarFrecuencia(@RequestBody Frecuencia f){
        Frecuencia frec = servFrec.guardarFrecuencia(f);
        if(frec != null){
            return true;
        }
        return false;
    }

    //FRONT: Actualizar frecuencia...!
    @PutMapping("editar")
    public String editarFrecuencia(@RequestBody Frecuencia f){
        boolean respuesta = servFrec.buscarPorID(f.getId_frecuencia());
        if(respuesta){
            Frecuencia fre = servFrec.obtenerFrecuencia(f.getId_frecuencia());
            if (f.getNombre() == null) f.setNombre(fre.getNombre());
            f.setActivo(fre.getActivo());
            servFrec.actualizarFrecuencia(f);
            return "Success";
        }
        else{ return "Failed"; }
    }

    //FRONT: Obtener frecuencia por ID.
    @GetMapping("id")
    public Frecuencia obtenerPorID(@RequestParam int id){ return servFrec.obtenerFrecuencia(id); }

    //FRONT: Eliminar frecuencia.
    @DeleteMapping("eliminar/id")
    public boolean eliminarFrecuenciaID(@RequestParam int id){ return servFrec.eliminarFrecuenciaID(id); }


}
