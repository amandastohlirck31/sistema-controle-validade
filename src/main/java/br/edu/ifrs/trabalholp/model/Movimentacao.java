package br.edu.ifrs.trabalholp.model;
import jakarta.persistence.*; import java.time.LocalDateTime;
@Entity @Table(name="movimentacoes")
public class Movimentacao {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false) @JoinColumn(name="produto_id") private Produto produto;
 private String tipo; private int quantidade; private LocalDateTime dataHora;
 public Movimentacao() {}
 public Movimentacao(Produto p,String t,int q){produto=p;tipo=t;quantidade=q;dataHora=LocalDateTime.now();}
 public Long getId(){return id;} public Produto getProduto(){return produto;} public String getTipo(){return tipo;}
 public int getQuantidade(){return quantidade;} public LocalDateTime getDataHora(){return dataHora;}
}
