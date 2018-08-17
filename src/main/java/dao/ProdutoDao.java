package dao;

import bd.Conexao;
import pojo.Produto;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProdutoDao extends DaoPadrao {

    public static final String SQLCOMBOBOX = "Select CODPRO, DESPRO From PRODUTO where SITPRO = 'A'";
    public static final String PESQUISADUPLICIDADE = "Select CODPRO, DESPRO from PRODUTO where DESPRO = ?";
    public final String PESQUISASQL = "SELECT CODPRO, DESPRO, CODCOR, CODSCT FROM PRODUTO";
    private Produto produto;

    public ProdutoDao(Produto produto) {
        this.produto = produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public boolean inserir() {
        try {
            String INSERTSQL = "INSERT INTO PRODUTO VALUES(?,?,?,?,?,?,?,?)";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(INSERTSQL);
            produto.setId(pegaGenerator("GPRODUTO"));
            ps.setInt(1, produto.getId());
            ps.setString(2, produto.getDescricao());
            ps.setInt(3, produto.getQuantidade());
            ps.setBigDecimal(4, produto.getPrecoCusto());
            ps.setBigDecimal(5, produto.getPrecoVenda());
            ps.setInt(6, produto.getCor().getCodCor());
            ps.setInt(7, produto.getSubCategoria().getId());
            ps.setString(8, produto.getSituacao());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível incluir o Produto");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean alterar() {
        try {
            String UPDATESQL = "UPDATE PRODUTO SET DESPRO = ?, QTDPRO = ?, PRECUS = ?, PREVEN = ?, CODCOR = ?, CODSCT = ?, SITPRO = ? WHERE CODPRO = ?";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(UPDATESQL);
            ps.setString(1, produto.getDescricao());
            ps.setInt(2, produto.getQuantidade());
            ps.setBigDecimal(3, produto.getPrecoCusto());
            ps.setBigDecimal(4, produto.getPrecoVenda());
            ps.setInt(5, produto.getCor().getCodCor());
            ps.setInt(6, produto.getSubCategoria().getId());
            ps.setString(7, produto.getSituacao());
            ps.setInt(8, produto.getId());
            ps.executeUpdate();//
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível alterar o Produto");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean excluir() {
        try {
            String DELETESQL = "DELETE FROM PRODUTO WHERE CODPRO = ?";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(DELETESQL);
            ps.setInt(1, produto.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível excluir o Produto");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean consultar() {
        try {
            String CONSULTASQL = "SELECT * FROM PRODUTO WHERE CODPRO = ?";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(CONSULTASQL);
            ps.setInt(1, produto.getId());
            ResultSet rs = ps.executeQuery(); //SELECT CODPRO, DESPRO, CODCOR, CODSCT FROM PRODUTO
            if (rs.next()) {
                produto.setId(rs.getInt(1));
                produto.setDescricao(rs.getString(2));
                produto.setQuantidade(rs.getInt(3));
                produto.setPrecoCusto(rs.getBigDecimal(4));
                produto.setPrecoVenda(rs.getBigDecimal(5));
                produto.getCor().setId(rs.getInt(6));
                produto.getSubCategoria().setId(rs.getInt(7));
                produto.setSituacao(rs.getString(8));

                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Produto não encontrado");
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível consultar o Produto");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean duplicidade() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(PESQUISADUPLICIDADE);
            ps.setString(1, produto.getDescricao());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getString("DESPRO").equals(produto.getDescricao()) && rs.getInt("CODPRO") != produto.getId()) {
                    System.out.println("Registro igual");
                    JOptionPane.showMessageDialog(null, "Produto já cadastrado com este Nome");
                    return false;
                }
                //return true;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
