package dao;

import bd.Conexao;
import pojo.Moveis;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MoveisDao extends DaoPadrao {

    public static final String PESQUISADUPLICIDADE = "Select CODMOV, DESMOV from MOVEIS where DESMOV = ?";
    public final String PESQUISASQL = "SELECT CODMOV, DESMOV, CODCOR, CODSCT FROM MOVEIS";
    private Moveis moveis;

    public MoveisDao(Moveis moveis) {
        this.moveis = moveis;
    }

    public boolean inserir() {
        try {
            String INSERTSQL = "INSERT INTO MOVEIS VALUES(?,?,?,?,?,?,?)";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(INSERTSQL);
            moveis.setId(pegaGenerator("GPRODUTO"));
            ps.setInt(1, moveis.getId());
            ps.setString(2, moveis.getDescricao());
            ps.setInt(3, moveis.getQuantidade());
            ps.setBigDecimal(4, moveis.getValorAluguel());
            ps.setInt(5, moveis.getCor().getCodCor());
            ps.setInt(6, moveis.getSubCategoria().getId());
            ps.setString(7, moveis.getSituacao());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível incluir o Móvel");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean alterar() {
        try {
            String UPDATESQL = "UPDATE MOVEIS SET DESMOV = ?, QTDMOV = ?, VLRALU = ?, CODCOR = ?, CODSCT = ?, SITMOV = ? WHERE CODMOV = ?";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(UPDATESQL);
            ps.setString(1, moveis.getDescricao());
            ps.setInt(2, moveis.getQuantidade());
            ps.setBigDecimal(3, moveis.getValorAluguel());
            ps.setInt(4, moveis.getCor().getCodCor());
            ps.setInt(5, moveis.getSubCategoria().getId());
            ps.setString(6, moveis.getSituacao());
            ps.setInt(7, moveis.getId());
            ps.executeUpdate();//
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível alterar o Móvel");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean excluir() {
        try {
            String DELETESQL = "DELETE FROM MOVEIS WHERE CODMOV = ?";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(DELETESQL);
            ps.setInt(1, moveis.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível excluir o Móvel");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean consultar() {
        try {
            String CONSULTASQL = "SELECT * FROM MOVEIS WHERE CODMOV = ?";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(CONSULTASQL);
            ps.setInt(1, moveis.getId());
            ResultSet rs = ps.executeQuery(); //SELECT CODPRO, DESPRO, CODCOR, CODSCT FROM PRODUTO
            if (rs.next()) {
                moveis.setId(rs.getInt(1));
                moveis.setDescricao(rs.getString(2));
                moveis.setQuantidade(rs.getInt(3));
                moveis.setValorAluguel(rs.getBigDecimal(4));
                moveis.getCor().setId(rs.getInt(5));
                moveis.getSubCategoria().setId(rs.getInt(6));
                moveis.setSituacao(rs.getString(7));
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Móvel não encontrado");
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível consultar o Móvel");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean duplicidade() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(PESQUISADUPLICIDADE);
            ps.setString(1, moveis.getDescricao());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getString("DESMOV").equals(moveis.getDescricao()) && rs.getInt("CODMOV") != moveis.getId()) {
                    System.out.println("Registro igual");
                    JOptionPane.showMessageDialog(null, "Móvel já cadastrado com este Nome");
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
