package DAO;

import DTO.ProdutosDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;
    ArrayList<ProdutosDTO> lista = new ArrayList<>();

    // ✅ LISTAR TODOS OS PRODUTOS
    public ArrayList<ProdutosDTO> listarProdutos() throws SQLException {
        String sql = "SELECT * FROM produtos ORDER BY id DESC";
        conn = new conectaDAO().connectDB();
        pstm = conn.prepareStatement(sql);
        rs = pstm.executeQuery();

        lista.clear();
        while (rs.next()) {
            ProdutosDTO p = new ProdutosDTO();
            p.setId(rs.getInt("id"));
            p.setNome(rs.getString("nome"));
            p.setValor(rs.getInt("valor"));
            p.setStatus(rs.getString("status"));
            lista.add(p);
        }

        rs.close();
        pstm.close();
        conn.close();
        return lista;
    }

    // ✅ CADASTRAR PRODUTO
    public void cadastrarProduto(ProdutosDTO objProduto) throws SQLException {
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
        conn = new conectaDAO().connectDB();
        pstm = conn.prepareStatement(sql);
        pstm.setString(1, objProduto.getNome());
        pstm.setInt(2, objProduto.getValor());
        pstm.setString(3, objProduto.getStatus());
        pstm.execute();
        pstm.close();
        conn.close();
    }

    // ✅ VENDER PRODUTO (ATUALIZA STATUS PARA 'Vendido')
    public void venderProduto(int id) throws SQLException {
        String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";
        conn = new conectaDAO().connectDB();
        pstm = conn.prepareStatement(sql);
        pstm.setInt(1, id);
        pstm.executeUpdate();
        pstm.close();
        conn.close();
    }

    // ✅ LISTAR PRODUTOS VENDIDOS
    public ArrayList<ProdutosDTO> listarProdutosVendidos() throws SQLException {
        String sql = "SELECT * FROM produtos WHERE status = 'Vendido' ORDER BY id DESC";
        conn = new conectaDAO().connectDB();
        pstm = conn.prepareStatement(sql);
        rs = pstm.executeQuery();

        ArrayList<ProdutosDTO> vendidos = new ArrayList<>();
        while (rs.next()) {
            ProdutosDTO p = new ProdutosDTO();
            p.setId(rs.getInt("id"));
            p.setNome(rs.getString("nome"));
            p.setValor(rs.getInt("valor"));
            p.setStatus(rs.getString("status"));
            vendidos.add(p);
        }

        rs.close();
        pstm.close();
        conn.close();
        return vendidos;
    }
}
