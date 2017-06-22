package br.com.casadocodigo.loja.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.casadocodigo.loja.models.Produto;

@Repository
@Transactional
public class ProdutoDAO {

	@PersistenceContext
	private EntityManager manager;
	
	public void gravar(Produto produto) {
		manager.persist(produto);
	}

	public List<Produto> getAll() {
		return manager.createQuery("Select p from Produto p", Produto.class).getResultList();
	}

	public Produto getById( Integer id ) {
		
		return manager.createQuery("Select distinct(p) from Produto p join fetch p.precos preco where p.id = :id", Produto.class)
				      .setParameter("id", id)
				      .getSingleResult();
	}

	//public Produto find(Integer produtoId) {
	//	return manager.find(Produto.class, produtoId);
	//}
}
