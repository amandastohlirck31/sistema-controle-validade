package br.edu.ifrs.trabalholp.model;
import jakarta.persistence.*; import java.time.LocalDate;
@Entity @Table(name="produtos")
public class Produto {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String nome; private String categoria;
 @Column(nullable=false) private LocalDate validade; private int quantidade;
 @ManyToOne(optional=false) @JoinColumn(name="fornecedor_id") private Fornecedor fornecedor;
 public Produto() {}
 public Produto(String n,String c,LocalDate v,int q,Fornecedor f){nome=n;categoria=c;validade=v;quantidade=q;fornecedor=f;}
 public Long getId(){return id;} public String getNome(){return nome;} public void setNome(String v){nome=v;}
 public String getCategoria(){return categoria;} public void setCategoria(String v){categoria=v;}
 public LocalDate getValidade(){return validade;} public void setValidade(LocalDate v){validade=v;}
 public int getQuantidade(){return quantidade;} public void setQuantidade(int v){quantidade=v;}
 public Fornecedor getFornecedor(){return fornecedor;} public void setFornecedor(Fornecedor v){fornecedor=v;}
 public String toString(){return nome;}
}
