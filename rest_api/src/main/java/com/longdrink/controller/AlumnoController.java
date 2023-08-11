package com.longdrink.controller;

import com.longdrink.model.Alumno;
import com.longdrink.services.AlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alumno")
public class AlumnoController {
    @Autowired
    private AlumnoService servAlum;

    //FRONT: Listar TODOS los alumnos.
    @GetMapping()
    public List<Alumno> listarAlumnos(){
        return servAlum.listarAlumnos();
    }

    //FRONT: Listar activos.
    @GetMapping("activos")
    public List<Alumno> listarAlumnosActivos(){
        return servAlum.listarAlumnosActivos();
    }

    //FRONT: Guardar alumno.
    @PostMapping("nuevo")
    public boolean guardarAlumno(@RequestBody Alumno a){
        Alumno alum = servAlum.guardarAlumno(a);
        if(alum != null){
            return true;
        }
        else{ return false; }
    }

    //FRONT: Actualizar alumno.
    //Nota: No debe actualizar campo activo, para eso esta el eliminar.
    @PutMapping("editar")
    public boolean editarAlumno(@RequestBody Alumno a){
        boolean respuesta = servAlum.buscarPorID(a.getId_alumno());
        if(respuesta){
            Alumno alum = servAlum.obtenerAlumno(a.getId_alumno());
            if(a.getAp_materno() == null) a.setAp_materno(alum.getAp_materno());
            if(a.getAp_paterno() == null) a.setAp_paterno(alum.getAp_paterno());
            if(a.getNombre() == null) a.setNombre(alum.getNombre());
            if(a.getDni() == null) a.setDni(alum.getDni());
            if(a.getEmail() == null) a.setEmail(alum.getEmail());
            a.setActivo(1);
            servAlum.actualizarAlumno(a);
            return true;

        }
        else{
            return false;
        }
    }
    //FRONT: Obtener alumno por ID.
    @GetMapping("id")
    public Alumno obtenerPorID(@RequestParam int id){
        try{
            return servAlum.obtenerAlumno(id);
        }
        catch(Exception ex){ return new Alumno(); }
    }

    //FRONT: Obtener alumno por DNI.
    @GetMapping("dni")
    public Alumno obtenerPorDNI(@RequestParam String dni){
        try{
            return servAlum.obtenerAlumDNI(dni);
        }
        catch(Exception ex){
            return new Alumno();
        }
    }

    //FRONT: Eliminar alumno por ID.
    @DeleteMapping("eliminar/id")
    public boolean eliminarAlumID(@RequestParam int id){
        return servAlum.eliminarAlumnoID(id);
    }


}
