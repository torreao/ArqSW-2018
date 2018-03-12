package br.usjt.arqsw.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.usjt.arqsw.entity.Chamado;
import br.usjt.arqsw.entity.Fila;
import br.usjt.arqsw.service.ChamadoService;
import br.usjt.arqsw.service.FilaService;
/**
 * 
 * @author Marcelo Torre�o 816113657 SI3AN-MCA
 *
 */
@Controller
public class ManterChamadosController {
	private FilaService filaService;
	private ChamadoService chamadoService;
	
	public ManterChamadosController() {
		filaService = new FilaService();
		chamadoService = new ChamadoService();
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping("index")
	public String inicio() {
		return "index";
	}

	private List<Fila> listarFilas() throws IOException{
			return filaService.listarFilas();
	}
	
	private List<Chamado> listarChamados(Fila fila) throws IOException{
		return chamadoService.listarChamados(fila);
	}
	
	private int cadastrarChamado(String desc, Fila fila) throws IOException{
		return chamadoService.cadastrarChamado(desc, fila.getId());
	}
	
	/**
	 * 
	 * @param model Acesso à request http
	 * @return JSP de Listar Chamados
	 */
	@RequestMapping("/listar_filas_exibir")
	public String listarFilasExibir(Model model) {
		try {
			model.addAttribute("filas", listarFilas());
			return "ChamadoListar";
		} catch (IOException e) {
			e.printStackTrace();
			return "Erro";
		}
	}
	/**
	 * 
	 * @param model lista as filas para cadastro do chamado
	 * @return JSP de cadastro
	 */
	@RequestMapping("/cadastrar_chamado")
	public String cadastrarChamado(Model model) {
		try {
			model.addAttribute("filas", listarFilas());
			return "CadastrarChamado";
		} catch (IOException e) {
			e.printStackTrace();
			return "Erro";
		}
	}
	/**
	 * 
	 * @param desc descri��o do chamado
	 * @param fila do chamado a ser cadastrado
	 * @return JSP para informar o cadastro
	 */
	@RequestMapping("/chamado_cadastrado")
	public String chamadoCadastrado(String desc, Fila fila) {
		try {
			if(cadastrarChamado(desc, fila)==1) {
				return "ChamadoCadastrado";		
			}	
			else
				return "Erro";
		} catch (IOException e) {
			e.printStackTrace();
			return "Erro";
		}
	}
	/**
	 * 
	 * @param fila lista de filas
	 * @param result resultado da requisi��o
	 * @param model parametros
	 * @return JSP de chamados
	 */
	@RequestMapping("/listar_chamados_exibir")
	public String listarChamadosExibir(@Valid Fila fila, BindingResult result, Model model) {
		try {
			if (result.hasFieldErrors("id")) {
				model.addAttribute("filas", listarFilas());
				System.out.println("Deu erro " + result.toString());
				return "ChamadoListar";
				//return "redirect:listar_filas_exibir";
			}
			fila = filaService.carregar(fila.getId());
			model.addAttribute("chamados", listarChamados(fila));
			
			return "ChamadoListarExibir";

		} catch (IOException e) {
			e.printStackTrace();
			return "Erro";
		}
	}

}
