package br.com.casadocodigo.loja.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.daos.ProdutoDAO;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.TipoPreco;
import br.com.casadocodigo.loja.validation.ProdutoValidation;

@Controller
@RequestMapping("/produtos")
public class ProdutosController {
	
	@Autowired
	private ProdutoDAO produtoDAO; 
	
	@Autowired
	private FileSaver fileSaver;
	
	@InitBinder
	public void intBinder( WebDataBinder binder ) {
		binder.addValidators( new ProdutoValidation() );
	}
	
	@RequestMapping("form")
	public ModelAndView form( Produto produto ) {
		
		ModelAndView modelAndView = new ModelAndView("produtos/form");
		modelAndView.addObject("tipos", TipoPreco.values() );
		return modelAndView;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView gravar( 
			MultipartFile sumario, 
			@Valid Produto produto, 
			BindingResult result, 
			RedirectAttributes redirectAttributes ) {
		
		if (result.hasErrors()) {
			return this.form( produto );
		}
		
		String path = fileSaver.write("arquivos-sumario", sumario);
		
		produto.setSumarioPath(path);
		
		produtoDAO.gravar( produto );
		
		//Escopo rápido, pequeno, dura apenas 1 request
		redirectAttributes.addFlashAttribute("sucesso", "Produto Cadastrado com Sucesso");
		
		return new ModelAndView("redirect:produtos");
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView listar() {
		
		List<Produto> prods = produtoDAO.getAll();
		
		ModelAndView modelAndView = new ModelAndView("produtos/lista");
		modelAndView.addObject("produtos", prods );
		return modelAndView;
	}
	
	@RequestMapping("/detalhe/{id}")
	public ModelAndView detalhe( @PathVariable("id") Integer id ) {
		
		ModelAndView modelAndView = new ModelAndView("produtos/detalhe");
		
		Produto prod = produtoDAO.getById(id);
		
		modelAndView.addObject("produto", prod);
		
		return modelAndView;
	}
}
