package br.edu.ifrs.trabalholp.repository;
import br.edu.ifrs.trabalholp.model.Fornecedor; import br.edu.ifrs.trabalholp.persistencia.JpaUtil;
import jakarta.persistence.EntityManager; import java.util.List; import java.util.function.Consumer;
public class FornecedorRepository {
 public List<Fornecedor> listar(){try(EntityManager em=JpaUtil.getEntityManager()){return em.createQuery("from Fornecedor f order by f.nome",Fornecedor.class).getResultList();}}
 public void salvar(Fornecedor f){exec(em->em.persist(f));}
 public void atualizar(Fornecedor f){exec(em->{Fornecedor x=em.find(Fornecedor.class,f.getId());x.setNome(f.getNome());x.setTelefone(f.getTelefone());x.setEmail(f.getEmail());});}
 public void excluir(Long id){exec(em->{Fornecedor f=em.find(Fornecedor.class,id);if(em.createQuery("select count(p) from Produto p where p.fornecedor.id=:id",Long.class).setParameter("id",id).getSingleResult()>0)throw new IllegalArgumentException("Este fornecedor está vinculado a produtos.");em.remove(f);});}
 private void exec(Consumer<EntityManager>a){EntityManager em=JpaUtil.getEntityManager();try{em.getTransaction().begin();a.accept(em);em.getTransaction().commit();}catch(RuntimeException e){if(em.getTransaction().isActive())em.getTransaction().rollback();throw e;}finally{em.close();}}
}
