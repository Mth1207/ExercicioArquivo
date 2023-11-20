package view;
import java.io.ObjectInputStream.GetField;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import CRUD.ProdutoDAO;
import model.Produto;

public class Main {
	
	static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		
		executarPrograma();

	}
	
	public static void executarPrograma() {
		
		ProdutoDAO produtoDAO = new ProdutoDAO();
		int tamanhoTotal = 0;
		
		while(true) {
			System.out.println("");
			System.out.println("Escolha uma opção: ");
			System.out.println("1 - Adicionar produto");
			System.out.println("2 - Excluir produto");
			System.out.println("3 - Listar todos produtos");
			System.out.println("4 - Listar por id do produto");
			System.out.println("5 - Atualizar produto");
			System.out.println("6 - Sair");
			System.out.println("");
			
			int opcao = scanner.nextInt();
			scanner.nextLine();
			
			switch(opcao) {
				case 1:
					System.out.println("Digite o nome do produto: ");
					String nome = scanner.nextLine();
					System.out.println("Digite o valor do produto: ");
					double valor = scanner.nextDouble();
					System.out.println("Digite a quantidade: ");
					int quantidade = scanner.nextInt();
			
					Produto produto = new Produto(nome, valor, quantidade);
					
					try {
						produtoDAO.inserir(produto);
						System.out.println("Produto inserido com sucesso.");
					} catch(SQLException e) {
						e.printStackTrace();
						System.out.println("Erro ao inserir produto. Erro: " + e);
					}
					break;
					
				case 2:
				    System.out.println("Digite o ID do produto a ser excluído: ");
				    int id = scanner.nextInt();
				    
				    try {
				        List<Produto> listaProdutos = produtoDAO.consultar();
				        int tamanhoLista = listaProdutos.size();

				        int ultimoId = tamanhoLista > 0 ? listaProdutos.get(tamanhoLista -1).getId() : 0;
				        
				        if (id <= 0 || id > ultimoId) {
				            System.out.println("Digite um ID válido!");
				        } else {
				            produtoDAO.excluir(id);
				            System.out.println("Produto com o ID: " + id + " excluído com sucesso.");
				        }
				    } catch(SQLException e) {
				        e.printStackTrace();
				        System.out.println("Erro ao excluir o produto. Erro: " + e);
				    }
				    break;

				case 3:					
					try {
						List<Produto> produtos = produtoDAO.consultar();
						for(Produto produto1 : produtos) {
							System.out.println(produto1);
						}
					} catch(SQLException e) {
						e.printStackTrace();
						System.out.println("Erro ao consultar produtos no banco de dados.");
					}
					break;
					
				case 4:					
					System.out.println("case 4");
					
				case 5:
					System.out.println("case 5");
					break;
					
				case 6:
					System.out.println("Encerrando programa.");
					return;
				
				default:
					System.out.println("Opção inválida. Tente novamente.");
					break;
			}	
		}
	}
}
