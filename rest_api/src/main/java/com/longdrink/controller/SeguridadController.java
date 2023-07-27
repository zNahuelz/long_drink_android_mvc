package com.longdrink.controller;

import com.longdrink.model.*;
import com.longdrink.services.AlumnoService;
import com.longdrink.services.UsuarioService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("/sq")
public class SeguridadController {
    @Autowired
    private UsuarioService usrServ;

    @Autowired
    private AlumnoService alumServ;

    @PersistenceContext
    private EntityManager em;

    //FRONT: Intento de login...
    @PostMapping("login")
    public SQUsuario iniciarSesion(@RequestBody SQUsuario cli){
        SQUsuario retorno = new SQUsuario("blank","blank");
        Usuario busqueda = usrServ.obtenerNombreUsuario(cli.getNombre_usuario());
        if(busqueda != null){
            if(busqueda.getNombre_usuario().equals(cli.getNombre_usuario()) && busqueda.getContrasena().equals(cli.getContrasena())){
                retorno.setNombre_usuario(cli.getNombre_usuario());
                retorno.setContrasena(cli.getContrasena());
            }
            else{
                retorno.setContrasena("blank");
                retorno.setNombre_usuario("blank");
            }
        }
        return retorno;
    }

/*
* TODO: Hacer que esto deje de ser un lio a la brevedad posible!.
*  */
    @PostMapping("registermain")
    @Transactional
    public SQRegistro registro_main(@RequestBody SQRegistro cli){
        SQRegistro retorno = new SQRegistro("blank","blank","blank","blank","blank","blank",3);
        boolean checkDNI = alumServ.buscarPorDNI(cli.getDni());
        boolean checkEmail = alumServ.buscarPorEmail(cli.getEmail());
        if(checkDNI == false && checkEmail == false){
            //No estan presentes dni ni email en db, procede a registrar.
            Alumno a = new Alumno();
            a.setNombre(cli.getNombre().toUpperCase());
            a.setAp_paterno(cli.getAppaterno().toUpperCase());
            a.setAp_materno(cli.getApmaterno().toUpperCase());
            a.setEmail(cli.getEmail().toUpperCase());
            a.setDni(cli.getDni());
            a.setActivo(1);
            Usuario u = new Usuario();
            u.setActivo(1);
            u.setContrasena(cli.getContrasena());
            u.setPermisos(0);

            retorno.setNombre(a.getNombre());
            retorno.setAppaterno(a.getAp_paterno());
            retorno.setApmaterno(a.getAp_materno());
            retorno.setDni(a.getDni());
            retorno.setEmail(a.getEmail());
            retorno.setContrasena(u.getContrasena());

            u.setNombre_usuario(retorno.generarUsername());
            em.persist(a);
            em.persist(u);
            Alumno checkAlum = alumServ.obtenerAlumDNI(a.getDni());
            Usuario checkUser = usrServ.obtenerNombreUsuario(u.getNombre_usuario());
            UsuarioAlumno usrAlum = new UsuarioAlumno(checkUser.getId_usuario(),checkAlum.getId_alumno());
            em.persist(usrAlum);
        }
        else{
            //Caso esta presente dni o email.
            retorno.setDni("PRESENTE EN BD");
            retorno.setEmail("PRESENTE EN BD");
        }

        return retorno;
    }

}
