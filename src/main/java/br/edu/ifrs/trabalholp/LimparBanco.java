
package br.edu.ifrs.trabalholp;

import br.edu.ifrs.trabalholp.persistencia.JpaUtil;
import jakarta.persistence.EntityManager;

public class LimparBanco {

    public static void main(String[] args) {

        EntityManager em = JpaUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            int movimentacoes = em.createQuery(
                "DELETE FROM Movimentacao"
            ).executeUpdate();

            int produtos = em.createQuery(
                "DELETE FROM Produto"
            ).executeUpdate();

            int fornecedores = em.createQuery(
                "DELETE FROM Fornecedor"
            ).executeUpdate();

            em.getTransaction().commit();

            System.out.println("BANCO LIMPO COM SUCESSO!");
            System.out.println("Movimentações removidas: " + movimentacoes);
            System.out.println("Produtos removidos: " + produtos);
            System.out.println("Fornecedores removidos: " + fornecedores);

        } catch (Exception e) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            e.printStackTrace();

        } finally {
            em.close();
        }
    }
}
