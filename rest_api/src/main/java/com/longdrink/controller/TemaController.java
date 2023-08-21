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

import com.longdrink.model.Tema;
import com.longdrink.services.TemaService;

@RestController
@RequestMapping("/tema")
public class TemaController {
	
	@Autowired
	private TemaService servTem;
	
	
	//FRONT: Listar todos los temas.
	@GetMapping
	public List<Tema> listarTemas(){ return servTem.listarTemas();} 
	
	//FRONT: Listar Temas Activos.
	@GetMapping("activos")
	public List<Tema> listarTemasActivos(){ return servTem.listarTemasActivos();}
	
	//FRONT: Guardar Tema.
	@PostMapping("nuevo")
	public boolean guardarTema(@RequestBody Tema t) {
		Tema tem = servTem.guardarTema(t);
		if(tem != null) {
			return true;
		}
		else {return false;}		
	}
	
	//FRONT: Actualizar tema.
	@PutMapping("editar")
	public String editarTema(@RequestBody Tema t) {
		boolean respuesta = servTem.buscarPorID(t.getId_tema());
		if (respuesta) {
			Tema tem = servTem.obtenerTema(t.getId_tema());
			if(t.getNombre() == null) t.setNombre(tem.getNombre());
			if(t.getGuia() == null) t.setGuia(tem.getGuia());
			t.setActivo(tem.getActivo());
			servTem.actualizarTema(t);
			return "Success";
		}
		else { return "Failed";}
	}
    
	//FRONT: Obtener tema por ID.
	@GetMapping("id")
	public Tema obtenerPorID(@RequestParam int id) { return servTem.obtenerTema(id); }
	
	//FRONT: Eliminar Tema.
	@DeleteMapping("eliminar/id")
	public boolean eliminarTemaID(@RequestParam int id) { return servTem.eliminarTemaID(id); }
	
}
