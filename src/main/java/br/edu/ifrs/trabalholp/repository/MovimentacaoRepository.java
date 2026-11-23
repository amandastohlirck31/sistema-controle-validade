package br.edu.ifrs.trabalholp.repository;
import br.edu.ifrs.trabalholp.model.*; import br.edu.ifrs.trabalholp.persistencia.JpaUtil;
import jakarta.persistence.EntityManager; import java.util.List;
public class MovimentacaoRepository {
 public void registrar(Long id,String tipo,int qtd){EntityManager em=JpaUtil.getEntityManager();try{em.getTransaction().begin();Produto p=em.find(Produto.class,id);if(p==null)throw new IllegalArgumentException("Produto não encontrado.");if("Saída".equals(tipo)&&p.getQuantidade()<qtd)throw new IllegalArgumentException("Estoque insuficiente.");p.setQuantidade("Entrada".equals(tipo)?p.getQuantidade()+qtd:p.getQuantidade()-qtd);em.persist(new Movimentacao(p,tipo,qtd));em.getTransaction().commit();}catch(RuntimeException e){if(em.getTransaction().isActive())em.getTransaction().rollback();throw e;}finally{em.close();}}
 public List<Movimentacao> listar(){try(EntityManager em=JpaUtil.getEntityManager()){return em.createQuery("select m from Movimentacao m join fetch m.produto order by m.dataHora desc",Movimentacao.class).getResultList();}}
}
