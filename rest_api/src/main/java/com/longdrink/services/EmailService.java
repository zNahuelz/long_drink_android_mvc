package com.longdrink.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.longdrink.model.Alumno;
import com.longdrink.model.SQRegistro;
import com.longdrink.model.Usuario;


@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void SendRegisterEmail(String to,SQRegistro sqr){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(sqr.getEmail());
        message.setSubject("Long Drink Bar Training - Información de tu registro");
        message.setText("¡Bienvenido a Long Drink Bar Training! \n"+
        "Has recibido este e-mail debido a que te has registrado en nuestro aplicativo móvil. "+ 
        "A continuación adjuntamos los datos de tu cuenta para que puedas iniciar sesión facilmente. \n\n"+
        "NOMBRE DE USUARIO: "+sqr.generarUsername()+"\n"+
        "NOMBRE COMPLETO: "+sqr.getNombre()+" "+sqr.getAppaterno()+" "+sqr.getApmaterno()+"\n"+
        "DNI: "+sqr.getDni()+"\n"+
        "E-MAIL: "+sqr.getEmail()+"\n\n"+
        "Recuerda no compartir tus credenciales con nadie.\n ¿Dudas? ¡Consultas? ¿Sugerencias? Comunicarse con administración: 994245306\n"+
        "Este es un e-mail generado automaticamente y no recibe respuestas de ningun tipo."
        );
        message.setFrom("sistemas.it.longdrink@gmail.com");
        mailSender.send(message);
    }

    public boolean SendRecoverPasswordEmail(Usuario u,String email){
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Long Drink Bar Training - Recuperación de Contraseña");
            message.setText("¡Hola! Se ha solicitado la recuperación de credenciales para tu cuenta de en la aplicación móvil de Long Drink Bar Training.\n"+
            "A continuación se detallan los datos de tu cuenta, con el objetivo de que vuelvas a iniciar sesión y cambies tus credenciales.\n\n"+
            "NOMBRE DE USUARIO: "+u.getNombre_usuario()+"\n"+
            "CONTRASEÑA: "+u.getContrasena()+
            "\nSi has solicitado este cambio debes iniciar sesión y cambiar tus credenciales a la brevedad posible.\n"+
            "En caso de no haber solicitado este cambio, puedes ignorar este mensaje."+"\n\n"+
            "Recuerda no compartir tus credenciales con nadie.\n¿Dudas? ¡Consultas? ¿Sugerencias? Comunicarse con administración: 994245306\n"+
            "Este es un e-mail generado automaticamente y no recibe respuestas de ningun tipo.");
            mailSender.send(message);
            return true;
        }
        catch(Exception ex){
            return false;
        }
    }
}
