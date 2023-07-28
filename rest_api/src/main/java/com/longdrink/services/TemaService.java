package com.longdrink.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.longdrink.dao.ITemaDAO;
import com.longdrink.model.Tema;

@Service
public class TemaService {
	
	@Autowired
	private ITemaDAO temaDAO;
	
	public List<Tema> listarTemas(){
		return (List<Tema>) temaDAO.findAll();
	}
    
	
	public List<Tema> listarTemasActivos(){
		List<Tema> listaT = listarTemas();
		List<Tema> retorno = new ArrayList<Tema>();
		for (Tema t : listaT) {
			if (t.getActivo() == 1) {
				retorno.add(t);
			}
		}
		return retorno;
	}
	
	public Tema guardarTema(Tema t) {
		t.setId_tema(0);
		boolean vacio = t.isEmpty();
		if(!vacio) {
			return temaDAO.save(t);
		}
		return null;
	}
	
	public Tema actualizarTema(Tema t) {
		return temaDAO.save(t);
	}
	
	public boolean eliminarTemaID(int id) {
		try {
			Tema t = temaDAO.findById(id).get();
			if(t.getActivo() != 0) {
				t.setActivo(0);
				temaDAO.save(t);
				return true;
			}
		} catch (Exception ex) {
			return false;
		}
		return false;
	}
	
	public boolean buscarPorID(int id) {
		Optional<Tema> t = temaDAO.findById(id);
		return t.isPresent();
	}
	
	public Tema obtenerTema(int id) {
		try{
			return temaDAO.findById(id).get();
		}
		catch(Exception ex){
			return new Tema();
		}

	}
}
