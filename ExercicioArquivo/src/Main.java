import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static ArrayList<Produto> produtos = new ArrayList<>();
    static String FILE = "produtos.txt";
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        executarPrograma();
  	
        try {
            lerArquivo();
        } catch (FileNotFoundException e) {
            System.err.println("Arquivo não encontrado: " + e.getMessage());
        }
    }

    public static void executarPrograma() {
        try {
            lerArquivo();
        } catch (FileNotFoundException e) {
            System.err.println("Arquivo não encontrado: " + e.getMessage());
        }

        while (true) {
            System.out.println("Escolha uma opção: ");
            System.out.println("1 - Adicionar Produto");
            System.out.println("2 - Excluir Produto");
            System.out.println("3 - Listar Produto");
            System.out.println("4 - Atualizar Produto");
            System.out.println("5 - Sair");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("Digite o nome do produto: ");
                    String nome = scanner.nextLine();
                    System.out.println("Digite o valor do produto: ");
                    double valor = scanner.nextDouble();
                    System.out.println("Digite a quantidade do produto");
                    int quantidade = scanner.nextInt();
                    adicionarProduto(new Produto(nome, valor, quantidade));
                    break;
                case 2:
                    System.out.println("Digite o nome do produto para excluir: ");
                    String produtoExcluir = scanner.nextLine();
                    excluirProduto(produtoExcluir);
                    break;
                case 3:
                    listarProdutos();
                    break;
                case 4:
                    System.out.println("Digite o nome do produto: ");
                    String nomeAtualizar = scanner.nextLine();
                    atualizar(nomeAtualizar);
                    break;
                case 5:
                    salvarArquivo();
                    System.out.println("Saindo do programa. Arquivo Salvo.");
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }

    private static void atualizar(String nomeAtualizar) {
        try {
            lerArquivo();
        } catch (FileNotFoundException e) {
            System.err.println("Arquivo não encontrado: " + e.getMessage());
            return;
        }

        for (int i = 0; i < produtos.size(); i++) {
            Produto produto = produtos.get(i);
            if (produto.getNome().equalsIgnoreCase(nomeAtualizar)) {
                System.out.println("Digite o novo nome do produto: ");
                String novoNome = scanner.nextLine();

                System.out.println("Digite o novo valor do produto: ");
                double novoValor = 0;
                while (!scanner.hasNextDouble()) {
                    System.out.println("Valor inválido. Digite novamente: ");
                    scanner.next();
                }
                novoValor = scanner.nextDouble();
                scanner.nextLine();

                System.out.println("Digite a nova quantidade do produto: ");
                int novaQuantidade = 0;
                while (!scanner.hasNextInt()) {
                    System.out.println("Quantidade inválida. Digite novamente: ");
                    scanner.next(); 
                }
                novaQuantidade = scanner.nextInt();
                scanner.nextLine(); 

                Produto produtoAtualizado = new Produto(novoNome, novoValor, novaQuantidade);
                produtos.set(i, produtoAtualizado);

                System.out.println("Produto atualizado com sucesso.");
                salvarArquivo(); 
                return;
            }
        }

        System.out.println("Produto não encontrado.");
    }


    
    private static void adicionarProduto(Produto produto) {
        produtos.add(produto);
        System.out.println("Produto adicionado: " + produto.getNome());
        scanner.nextLine();
        salvarArquivo();
    }

    
    private static void excluirProduto(String nomeProduto) {
        Produto produtoParaExcluir = null;

        for (Produto produto : produtos) {
            if (produto.getNome().equalsIgnoreCase(nomeProduto)) {
                produtoParaExcluir = produto;
                System.out.println("Produto removido: " + nomeProduto);
            }
        }

        if (produtoParaExcluir != null) {
            produtos.remove(produtoParaExcluir);
        } else {
            System.out.println("Produto não encontrado.");
        }
        salvarArquivo();
    }

    static void lerArquivo() throws FileNotFoundException {
        produtos.clear();

        File file = new File(FILE);
        Scanner reader = new Scanner(file);
        while (reader.hasNextLine()) {
            String linha = reader.nextLine();
            String[] partes = linha.split(", ");

            if (partes.length == 3) {
                String nome = partes[0];
                double preco = Double.parseDouble(partes[1]);
                int quantidade = Integer.parseInt(partes[2]);

                produtos.add(new Produto(nome, preco, quantidade));
            }
        }
    }

    private static void listarProdutos() {
        try {
            lerArquivo();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (Produto produto : produtos) {
            System.out.println(produto.getNome() + ", " + produto.getPreco() + ", " + produto.getQuantidade());
        }

        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
        }
    }

    private static void salvarArquivo() {
        try {
            FileWriter arquivo = new FileWriter("produtos.txt");
            for (Produto produto : produtos) {
                arquivo.write(produto.getNome() + ", " + produto.getPreco() + ", " + produto.getQuantidade() + "\n");
            }
            arquivo.close();
        } catch (IOException e) {
            System.err.println("Erro ao salvar o arquivo: " + e.getMessage());
        }
    }
}
