package br.usjt.arqsw.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.usjt.arqsw.entity.Chamado;
import br.usjt.arqsw.entity.Fila;
/**
 * 
 * @author Marcelo Torreão 816113657 SI3AN-MCA
 *
 */
public class ChamadoDAO {
	/**
	 * Busca dos chamados no BD de acordo com as filas
	 * @param fila
	 * @return
	 * @throws IOException
	 */
	public ArrayList<Chamado> listarChamados(Fila fila) throws IOException {
		String query = "select c.id_chamado,c.descricao,c.status,c.dt_abertura,c.dt_fechamento from chamado c,fila f where f.id_fila="+fila.getId();
		ArrayList<Chamado> lista = new ArrayList<>();
		try(Connection conn = ConnectionFactory.getConnection();
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();){

			while(rs.next()) {
				Chamado chamado = new Chamado();
				chamado.setId(rs.getInt("id_chamado"));
				chamado.setDescricao(rs.getString("descricao"));
				chamado.setStatus(rs.getString("status"));
				chamado.setDt_abertura(rs.getDate("dt_abertura"));
				chamado.setDt_fechamento(rs.getDate("dt_fechamento"));
				lista.add(chamado);
			}
			
		} catch (SQLException e) {
			throw new IOException(e);
		}
		return lista;
	}
	/**
	 * Cadastro de chamados
	 * @param descricao
	 * @param idFila
	 * @return
	 * @throws IOException
	 */
	public int cadastrarChamado(String descricao, int idFila) throws IOException{
		String query = "insert into chamado "
				+ "values (null,'"+descricao+"', 'aberto', NOW(),null,"+idFila+")";
		try(Connection conn = ConnectionFactory.getConnection();
				PreparedStatement pst = conn.prepareStatement(query);){
			return pst.executeUpdate();
		}catch (SQLException e) {
			throw new IOException(e);
		}
	}
}
