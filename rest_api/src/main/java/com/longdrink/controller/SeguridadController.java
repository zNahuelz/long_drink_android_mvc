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

@RestController
@RequestMapping("/sq")
public class SeguridadController {
    @Autowired
    private UsuarioService usrServ;

    @Autowired
    private AlumnoService alumServ;

    @PersistenceContext
    private EntityManager em;

    //FRONT: Login.
    @PostMapping("login")
    public SQUsuario iniciarSesion(@RequestBody SQUsuario cli){
        SQUsuario retorno = new SQUsuario("blank","blank",0);
        Usuario busqueda = usrServ.obtenerNombreUsuario(cli.getNombre_usuario());
        if(busqueda != null){
            if(busqueda.getNombre_usuario().equals(cli.getNombre_usuario()) && busqueda.getContrasena().equals(cli.getContrasena())){
                retorno.setNombre_usuario(cli.getNombre_usuario());
                retorno.setContrasena(cli.getContrasena());
                retorno.setId_usuario(busqueda.getId_usuario());
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
            Alumno a = new Alumno(0,cli.getApmaterno().toUpperCase(),cli.getAppaterno().toUpperCase(),
                    cli.getDni(),cli.getEmail().toUpperCase(),cli.getNombre().toUpperCase(),1);
            Usuario u = new Usuario(0,cli.getContrasena(),"",0,1);

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
        else{ return retorno; } //DNI ó EMAIL presente en BD.

        return retorno;
    }

}
