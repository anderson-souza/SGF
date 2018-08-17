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
    private final String INSERTSQL = "INSERT INTO PRODUTO VALUES(?,?,?,?,?,?,?,?)";
    private final String UPDATESQL = "UPDATE PRODUTO SET DESPRO = ?, QTDPRO = ?, PRECUS = ?, PREVEN = ?, CODCOR = ?, CODSCT = ?, SITPRO = ? WHERE CODPRO = ?";
    private final String DELETESQL = "DELETE FROM PRODUTO WHERE CODPRO = ?";
    private final String CONSULTASQL = "SELECT * FROM PRODUTO WHERE CODPRO = ?";
    private Produto produto;

    public ProdutoDao(Produto produto) {
        this.produto = produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public boolean inserir() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(INSERTSQL);
            produto.setCodpro(pegaGenerator("GPRODUTO"));
            ps.setInt(1, produto.getCodpro());
            ps.setString(2, produto.getDespro());
            ps.setInt(3, produto.getQtdpro());
            ps.setBigDecimal(4, produto.getPrecus());
            ps.setBigDecimal(5, produto.getPreven());
            ps.setInt(6, produto.getCor().getCodCor());
            ps.setInt(7, produto.getSubCategoria().getCodsct());
            ps.setString(8, produto.getSitpro());
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
            PreparedStatement ps = Conexao.getConexao().prepareStatement(UPDATESQL);
            ps.setString(1, produto.getDespro());
            ps.setInt(2, produto.getQtdpro());
            ps.setBigDecimal(3, produto.getPrecus());
            ps.setBigDecimal(4, produto.getPreven());
            ps.setInt(5, produto.getCor().getCodCor());
            ps.setInt(6, produto.getSubCategoria().getCodsct());
            ps.setString(7, produto.getSitpro());
            ps.setInt(8, produto.getCodpro());
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
            PreparedStatement ps = Conexao.getConexao().prepareStatement(DELETESQL);
            ps.setInt(1, produto.getCodpro());
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
            PreparedStatement ps = Conexao.getConexao().prepareStatement(CONSULTASQL);
            ps.setInt(1, produto.getCodpro());
            ResultSet rs = ps.executeQuery(); //SELECT CODPRO, DESPRO, CODCOR, CODSCT FROM PRODUTO
            if (rs.next()) {
                produto.setCodpro(rs.getInt(1));
                produto.setDespro(rs.getString(2));
                produto.setQtdpro(rs.getInt(3));
                produto.setPrecus(rs.getBigDecimal(4));
                produto.setPreven(rs.getBigDecimal(5));
                produto.getCor().setCodcor(rs.getInt(6));
                produto.getSubCategoria().setCodsct(rs.getInt(7));
                produto.setSitpro(rs.getString(8));

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
            ps.setString(1, produto.getDespro());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getString("DESPRO").equals(produto.getDespro()) && rs.getInt("CODPRO") != produto.getCodpro()) {
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
