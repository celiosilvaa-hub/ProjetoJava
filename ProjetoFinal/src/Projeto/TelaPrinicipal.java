package Projeto;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.InputMismatchException;
import javax.swing.JOptionPane;


import Projeto.InOut;

public class TelaPrinicipal {
	//Criando uma lista
	static ArrayList<Produto> listaProduto = new ArrayList<Produto>();
	
	public static void main(String[] args) throws IOException{
	//chamando a funa��o MEU
		Menu();
		System.out.println("Programa Finzalizado");
	}
	
	// M�todo criar menu
	static void Menu() throws IOException{
		
		int op;
		try {
			do{
				String opcoes = "Escolha a Opera��o:\n"+
						"1 - Cadastrar um produto\n"+
						"2 - Buscar um produto\n"+
						"3 - Exibir todos os produtos\n"+
						"4 - Atualizar um produto\n"+
						"0 - Sair";
				op = InOut.InInt(opcoes);
		
				switch(op){
		
				case 0:
					InOut.OutMessage("O programa ser� Finalizado", "Aten��o");
					break;
				case 1:
					CadastrarProduto();
					break;
				case 2:
					BuscaProduto();
					break;
				case 3:
					ListaProduto();
					break;
				case 4:
					AlterarProduto();
					break;			
				default:
					InOut.OutMessage("Op��o Invalida!", "Erro!");
					break;		
				}
			}while(op != 0);
		}catch (NumberFormatException nfe) {
        InOut.OutMessage("Op��o invalida!!");
        Menu();
		}
	}
	
	//M�todo cadastrar produto
	static void CadastrarProduto() {
		
		try {
			
			String codigoNovo = InOut.InString("Digite o c�digo do Produto:");
			
			Produto produto = ProcurarProduto(codigoNovo);
			if (produto != null) {
				
				String codigoExistente = InOut.InString("Este produto j� existe: ");
				Menu();
				return;
			}else {
			
			//String codigo = InOut.InString("Codigo do Produto: ");
			String nome = InOut.InString("Insira o Nome do Produto: ");
			double preco = InOut.InDouble("Digite o Pre�o do Produto: ");
			int quantidade = InOut.InInt("Quantidade do Produto: ");
			Produto cadProduto = new Produto(codigoNovo, nome, preco, quantidade);
			listaProduto.add(cadProduto);
			salvarListaTXT("listaProduto.txt", listaProduto);
			}
		}catch(Exception exc) {
			InOut.OutMessage("Use o seguinte formato para valor: 0.00");
			//Exception  pega todos os erros poss�veis
			exc.printStackTrace();
		}
	}
	
	//M�todo exibi lista de produtos existente
	static void ListaProduto() throws IOException{
		abrirArquivoTXT("listaProduto.txt");
		
		if(listaProduto.isEmpty()){
			InOut.OutMessage("Nenhum Produto Cadastrado");
			return;
		}
		FileWriter arq = new FileWriter("printListaProduto.txt");
		PrintWriter gravaArq = new PrintWriter(arq);
		String relatorio = "";
		gravaArq.printf("__________Lista de Produto_________\r\n");
		for(int i = 0; i < listaProduto.size(); i++){
			Produto prod = listaProduto.get(i);
			gravaArq.printf(" - |CODIGO| %s |NOME| %s |PRE�O| %f |QUANTIDADE| %d\n", 
							prod.getCodigo(), prod.getNome(), prod.getPreco(), prod.getQuantidade());
			
			relatorio += String.format("\nCodigo |___ %s ___| \nNome |___ %s ___| \nPre�o |___ R$ %f ___| \nQuantidade |___ %d ___|",
					prod.getCodigo(), prod.getNome(), prod.getPreco(), prod.getQuantidade()) +
						 "\n________________________\r";
		}
		gravaArq.close();
		InOut.OutMessage(relatorio);
	}
	
	//M�todo para alterar produtos da lista
	static void AlterarProduto(){
		abrirArquivoTXT("listaProduto.txt");
		
		if(listaProduto.size() == 0){
			InOut.OutMessage("Lista Vazia");
			return;
		}
		String codigoProdutoPesquisar = InOut.InString("Digite o c�digo do Produto:");
		
		Produto produto = ProcurarProduto(codigoProdutoPesquisar);
		if (produto != null) {
			String codigoNovo = InOut.InString("Novo c�digo: ");
			produto.setCodigo(codigoNovo);
			InOut.OutMessage("Codigo alterado com sucesso");
				
			String nomeNovo = InOut.InString("Digite o novo Nome:");
			produto.setNome(nomeNovo);
			InOut.OutMessage("Nome alterado com sucesso");
				
			double precoNovo = InOut.InDouble("Digite o novo pre�o:");
			produto.setPreco(precoNovo);
			InOut.OutMessage("Pre�o alterado com sucesso");
				
			int quantidadeNovo = InOut.InInt("Digite a nova quantidade:");
			produto.setQuantidade(quantidadeNovo);
			InOut.OutMessage("Quantidade alterada com sucesso");					
				
			salvarListaTXT("listaProduto.txt", listaProduto);
			InOut.OutMessage("Produto alterado com secesso!");			
		}
		else InOut.OutMessage("Produto n�o encontrado");		
	}
	
	//Fun��o Busca um produto na lista.
	static void BuscaProduto(){
		abrirArquivoTXT("listaProduto.txt");
		
		if(listaProduto.size() == 0){
			InOut.OutMessage("Lista Vazia");
			return;
		}
		String codigoProdutoPesquisar = InOut.InString("Digite o c�digo do Produto:");
		Produto produto = ProcurarProduto(codigoProdutoPesquisar);
		if (produto != null) {
			InOut.OutMessage(" " + produto);
			return;
		}
		InOut.OutMessage("Produto n�o Encontrado");
	}
		
	//M�todo busca um produto existente
	static Produto ProcurarProduto(String codigo) {
		for (Produto produto : listaProduto) {
			if (produto.getCodigo().equalsIgnoreCase(codigo)) {
				return produto;
			}
		}
		return null;
	}
	
	//M�todo para salavar na lista
	static void salvarListaTXT(String fileName, ArrayList<Produto> lista) {
		try
		{
			FileOutputStream writeData = new FileOutputStream(fileName);
            ObjectOutputStream writeStream = new ObjectOutputStream(writeData);

            writeStream.writeObject(lista);
            writeStream.flush();
            writeStream.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//M�todo abrir arquivo
	static void abrirArquivoTXT(String fileName) {
		try
		{
			FileInputStream readData = new FileInputStream(fileName);
            ObjectInputStream readStream = new ObjectInputStream(readData);
            
            listaProduto.clear();
            listaProduto = (ArrayList<Produto>) readStream.readObject();
            readStream.close();
		}
		catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}