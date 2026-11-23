package br.edu.ifrs.trabalholp.repository;
import br.edu.ifrs.trabalholp.model.*; import br.edu.ifrs.trabalholp.persistencia.JpaUtil;
import jakarta.persistence.EntityManager; import java.util.List; import java.util.function.Consumer;
public class ProdutoRepository {
 public List<Produto> listar(){try(EntityManager em=JpaUtil.getEntityManager()){return em.createQuery("select p from Produto p join fetch p.fornecedor order by p.nome",Produto.class).getResultList();}}
 public void salvar(Produto p){exec(em->em.persist(p));}
 public void atualizar(Produto p){exec(em->{Produto x=em.find(Produto.class,p.getId());x.setNome(p.getNome());x.setCategoria(p.getCategoria());x.setValidade(p.getValidade());x.setQuantidade(p.getQuantidade());x.setFornecedor(em.getReference(Fornecedor.class,p.getFornecedor().getId()));});}
 public void excluir(Long id){exec(em->{Produto p=em.find(Produto.class,id);if(em.createQuery("select count(m) from Movimentacao m where m.produto.id=:id",Long.class).setParameter("id",id).getSingleResult()>0)throw new IllegalArgumentException("Este produto possui movimentações registradas.");em.remove(p);});}
 private void exec(Consumer<EntityManager>a){EntityManager em=JpaUtil.getEntityManager();try{em.getTransaction().begin();a.accept(em);em.getTransaction().commit();}catch(RuntimeException e){if(em.getTransaction().isActive())em.getTransaction().rollback();throw e;}finally{em.close();}}
}
