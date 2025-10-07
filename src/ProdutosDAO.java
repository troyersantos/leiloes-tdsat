import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ProdutosDAO {

    public void cadastrarProduto(ProdutosDTO produto) {
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";

        try (Connection conn = new conectaDAO().connectDB();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, produto.getNome());
            pst.setInt(2, produto.getValor());
            pst.setString(3, produto.getStatus());

            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto: " + e.getMessage());
        }
    }

    public ArrayList<ProdutosDTO> listarProdutos() {
        ArrayList<ProdutosDTO> listagem = new ArrayList<>();
        String sql = "SELECT id, nome, valor, status FROM produtos ORDER BY id DESC";

        try (Connection conn = new conectaDAO().connectDB();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                ProdutosDTO p = new ProdutosDTO();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setValor(rs.getInt("valor"));
                p.setStatus(rs.getString("status"));
                listagem.add(p);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + e.getMessage());
        }

        return listagem;
    }
}
