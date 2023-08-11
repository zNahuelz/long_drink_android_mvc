package com.longdrink.controller;

import java.util.List;

import com.longdrink.model.UsuarioAlumno;
import com.longdrink.model.UsuarioProfesor;
import com.longdrink.services.UsuarioAlumnoService;
import com.longdrink.services.UsuarioProfesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.longdrink.model.Usuario;
import com.longdrink.services.UsuarioService;
import com.longdrink.model.UsuarioAlumno;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService servUsu;

    @Autowired
    private UsuarioAlumnoService servUsrAlum;

    @Autowired
    private UsuarioProfesorService servUsrProf;

    //FRONT: Listar TODOS los usuarios.
    @GetMapping()
    public List<Usuario> listarUsuarios(){
        return servUsu.listarUsuarios();
    }

    //FRONT: Listar activos.
    @GetMapping("activos")
    public List<Usuario> listarUsuariosActivos(){
        return servUsu.listarUsuariosActivos();
    }

    //FRONT: Guardar usuario.
    @PostMapping("nuevo")
    public boolean guardarAlumno(@RequestBody Usuario a){
        Usuario usu = servUsu.guardarUsuario(a);
        if(usu != null){
            return true;
        }
        else{ return false; }
    }

    //FRONT: Actualizar usuario.
    //Nota: No debe actualizar campo activo, para eso esta el eliminar.
    @PutMapping("editar")
    public boolean editarAlumno(@RequestBody Usuario a){
        boolean respuesta = servUsu.buscarPorID(a.getId_usuario());
        if(respuesta){
            Usuario usu = servUsu.obtenerUsuario(a.getId_usuario());
            if(a.getContrasena() == null) a.setContrasena(usu.getContrasena());
            if(a.getNombre_usuario() == null) a.setNombre_usuario(usu.getNombre_usuario());
            if(a.getPermisos() == 0) a.setPermisos(usu.getPermisos());
            if(a.getActivo() == 0) a.setActivo(usu.getActivo());

            a.setActivo(usu.getActivo());
            servUsu.actualizarUsuario(a);
            return true;

        }
        else{
            return false;
        }
    }

    //FRONT: Obtener datos del usuario por ID.
    @GetMapping("id_usuario")
    public Usuario obtenerPorId(@RequestParam int id_usuario){
        try{
            return servUsu.obtenerUsuario(id_usuario);
        }
        catch(Exception ex){ return new Usuario(); }
    }

    //FRONT: Eliminar alumno por ID.
    @DeleteMapping("eliminar/id")
    public boolean eliminarUsuID(@RequestParam int id){
        return servUsu.eliminarUsuarioID(id);
    }

    //FRONT: Obtener ID de alumno segun id_usuario (UsuarioAlumno)
    @GetMapping("usr_alum/id")
    public UsuarioAlumno obtener_IDAlum(@RequestParam int id_usr){
        return servUsrAlum.obtenerPorIDUsuario(id_usr);
    }

    //FRONT: Obtener ID de profesor segun id_usuario (UsuarioProfesor)
    @GetMapping("usr_prof/id")
    public UsuarioProfesor obtener_IDProf(@RequestParam int id_usr){
        return servUsrProf.obtenerPorIDUsuario(id_usr);
    }
}