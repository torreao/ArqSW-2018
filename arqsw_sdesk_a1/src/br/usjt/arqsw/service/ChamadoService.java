package br.usjt.arqsw.service;

import java.io.IOException;
import java.util.ArrayList;

import br.usjt.arqsw.dao.ChamadoDAO;
import br.usjt.arqsw.entity.Chamado;
import br.usjt.arqsw.entity.Fila;
/**
 * 
 * @author Marcelo Torreão 816113657 SI3AN-MCA
 *
 */
public class ChamadoService {
	private ChamadoDAO dao;
	
	public ChamadoService(){
		dao = new ChamadoDAO();
	}
	
	public ArrayList<Chamado> listarChamados(Fila fila) throws IOException{
		return dao.listarChamados(fila);
	}
	public int cadastrarChamado(String descricao, int idFila) throws IOException{
		return dao.cadastrarChamado(descricao, idFila);
	}

}
