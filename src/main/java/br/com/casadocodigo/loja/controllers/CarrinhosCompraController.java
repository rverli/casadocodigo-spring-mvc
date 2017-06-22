package br.com.casadocodigo.loja.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.loja.daos.ProdutoDAO;
import br.com.casadocodigo.loja.models.CarrinhoCompras;
import br.com.casadocodigo.loja.models.CarrinhoItem;
import br.com.casadocodigo.loja.models.Preco;
import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.TipoPreco;

@Controller
@RequestMapping("/carrinho")
@Scope( value = WebApplicationContext.SCOPE_REQUEST )
public class CarrinhosCompraController {

	@Autowired
	private ProdutoDAO produtoDAO;

	@Autowired
	private CarrinhoCompras carrinho;
	
	@RequestMapping("/add")
	public ModelAndView add( Integer produtoId, TipoPreco tipoP ) {
		
		ModelAndView modelAndView = new ModelAndView("redirect:/carrinho");
		
		System.out.println(tipoP);
		
		CarrinhoItem carrinhoItem = this.criaItem(produtoId, tipoP);
		
		carrinho.add(carrinhoItem);
		
		return modelAndView;
	}

	@RequestMapping( method=RequestMethod.GET )
	public ModelAndView itens() {
		return new ModelAndView("carrinho/itens");
	}
	
	private CarrinhoItem criaItem(Integer produtoId, TipoPreco tipoPreco) {

		Produto produto = produtoDAO.getById(produtoId);
		
		CarrinhoItem carrinhoItem = new CarrinhoItem(produto, tipoPreco);
		
		return carrinhoItem;
	}
}
