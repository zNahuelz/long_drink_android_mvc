package com.longdrink.controller;

import java.util.List;

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

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService servUsu;

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
    public String editarAlumno(@RequestBody Usuario a){
        boolean respuesta = servUsu.buscarPorID(a.getId_usuario());
        if(respuesta){
            Usuario usu = servUsu.obtenerUsuario(a.getId_usuario());
            if(a.getContrasena() == null) a.setContrasena(usu.getContrasena());
            if(a.getNombre_usuario() == null) a.setNombre_usuario(usu.getNombre_usuario());
            if(a.getPermisos() == 0) a.setPermisos(usu.getPermisos());
            if(a.getActivo() == 0) a.setActivo(usu.getActivo());

            a.setActivo(usu.getActivo());
            servUsu.actualizarUsuario(a);
            return "Success";

        }
        else{
            return "Failed";
        }
    }

    //FRONT: Obtener alumno por DNI.
    @GetMapping("id_usuario")
    public Usuario obtenerPorId(@RequestParam Integer id_usuario){
        return servUsu.obtenerUsuario(id_usuario);
    }

    //FRONT: Eliminar alumno por ID.
    @DeleteMapping("eliminar/id")
    public boolean eliminarUsuID(@RequestParam int id){
        return servUsu.eliminarUsuarioID(id);
    }


}