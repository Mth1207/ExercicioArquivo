package CRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Produto;
import util.Conexao;

public class ProdutoDAO {
	public void inserir(Produto produto) throws SQLException {
		String insertSQL = "INSERT INTO produtoexercicio (nome, preco, quantidade) VALUES (?, ?, ?)";
		
		try(Connection connection = Conexao.getConexao();
				PreparedStatement stmt  = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
			
			stmt.setString(1, produto.getDescricao());
			stmt.setDouble(2, produto.getPreco());
			stmt.setInt(3, produto.getEstoque());
			
			stmt.executeUpdate();
			
			try(ResultSet geraId = stmt.getGeneratedKeys()) {
				if(geraId.next()) {
					produto.setId(geraId.getInt(1));
				}
			}
		}
	}

	public void update(Produto produto) throws SQLException {
		String updateSQL = "UPDATE produtoexercicio SET nome = ?, preco = ?, quantidade = ? WHERE id = ?";
		
		try (Connection connection = Conexao.getConexao();
				PreparedStatement stmt = connection.prepareStatement(updateSQL)){
			stmt.setString(1, produto.getDescricao());
			stmt.setDouble(2, produto.getPreco());
			stmt.setInt(3, produto.getEstoque());
			stmt.setInt(4, produto.getId());
		
			stmt.executeUpdate();
		}
	}
	
    public List<Produto> consultar() throws SQLException {
        String selectSQL = "SELECT * FROM produtoexercicio";
        List<Produto> produtos = new ArrayList<>();

        try (Connection connection = Conexao.getConexao();
             PreparedStatement stmt = connection.prepareStatement(selectSQL);
             ResultSet resultSet = stmt.executeQuery()) {

            while (resultSet.next()) {
                Produto produto = criarProduto(resultSet);
                produtos.add(produto);
            }
        }

        return produtos;
    }

    private Produto criarProduto(ResultSet resultSet) throws SQLException {
        Produto produto = new Produto(
                resultSet.getString("nome"),
                resultSet.getDouble("preco"),
                resultSet.getInt("quantidade")
        );
        produto.setId(resultSet.getInt("id"));
        return produto;
    }
	
	public Produto consultarPorId(int id) throws SQLException {
		String selectSQL = "SELECT * FROM produtoexercicio WHERE id = ?";
		
		try(Connection connection = Conexao.getConexao();
				PreparedStatement stmt = connection.prepareStatement(selectSQL)){
					
			stmt.setInt(1, id);
			
			try(ResultSet resultSet = stmt.executeQuery()) {
				if(resultSet.next()) {
					Produto produto = new Produto(
							resultSet.getString("nome"),
							resultSet.getDouble("preco"),
							resultSet.getInt("quantidade")
					);
					produto.setId(resultSet.getInt("id"));
					return produto;
				} else {
					return null;
				}
			}
		}
	}
	
	public void excluir(int id) throws SQLException {
		String deleteSQL = "DELETE FROM produtoexercicio WHERE id = ?";
		
		try(Connection connection = Conexao.getConexao();
				PreparedStatement stmt = connection.prepareStatement(deleteSQL)){
					
			stmt.setInt(1, id);
			stmt.executeUpdate();
		}
	}
	
	public int ObterUltimoId(List<Produto> listaProdutos) {
		int ultimoId = 0;
		
		for(Produto produto : listaProdutos) {
			if(produto.getId() > ultimoId) {
				ultimoId = produto.getId();
			}
		}
		
		return ultimoId;
	}
}
