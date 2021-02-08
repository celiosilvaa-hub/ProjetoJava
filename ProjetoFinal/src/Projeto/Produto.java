package Projeto;

import java.io.Serializable;

//Classe produto
public class Produto implements Serializable {
	private String codigo;
	private String nome;
	private double preco;
	private int quantidade;
	
	//Método Construtor
	public Produto(String codigo, String nome, double preco, int quantidade){
				
		this.codigo = codigo;
		this.nome = nome;
		this.preco = preco;
		this.quantidade = quantidade;
	}
	
	//Gets e Sets
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public double getPreco() {
		return preco;
	}
	public void setPreco(double preco) {
		this.preco = preco;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("Produto{Codigo=%s, Nome=%s, Preco=%f, Qtde=%d}", codigo, nome, preco, quantidade);
	}

	public void printf(String string, String codigo2, String nome2, double preco2, int quantidade2) {
		// TODO Auto-generated method stub
		
	}
	
}