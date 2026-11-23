package br.edu.ifrs.trabalholp.persistencia;
import jakarta.persistence.*;
public final class JpaUtil {
 private static final EntityManagerFactory FACTORY=Persistence.createEntityManagerFactory("controle-validade");
 private JpaUtil(){}
 public static EntityManager getEntityManager(){return FACTORY.createEntityManager();}
}
