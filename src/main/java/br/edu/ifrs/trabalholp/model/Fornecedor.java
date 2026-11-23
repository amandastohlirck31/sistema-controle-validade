package br.edu.ifrs.trabalholp.model;
import jakarta.persistence.*;
@Entity @Table(name="fornecedores")
public class Fornecedor {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String nome; private String telefone; private String email;
 public Fornecedor() {}
 public Fornecedor(String n,String t,String e){nome=n;telefone=t;email=e;}
 public Long getId(){return id;} public String getNome(){return nome;} public void setNome(String v){nome=v;}
 public String getTelefone(){return telefone;} public void setTelefone(String v){telefone=v;}
 public String getEmail(){return email;} public void setEmail(String v){email=v;}
 public String toString(){return nome;}
}
