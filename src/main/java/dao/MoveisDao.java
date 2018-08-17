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
            moveis.setCodmov(pegaGenerator("GPRODUTO"));
            ps.setInt(1, moveis.getCodmov());
            ps.setString(2, moveis.getDesmov());
            ps.setInt(3, moveis.getQtdmov());
            ps.setBigDecimal(4, moveis.getVlralu());
            ps.setInt(5, moveis.getCor().getCodCor());
            ps.setInt(6, moveis.getSubCategoria().getCodsct());
            ps.setString(7, moveis.getSitmov());
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
            ps.setString(1, moveis.getDesmov());
            ps.setInt(2, moveis.getQtdmov());
            ps.setBigDecimal(3, moveis.getVlralu());
            ps.setInt(4, moveis.getCor().getCodCor());
            ps.setInt(5, moveis.getSubCategoria().getCodsct());
            ps.setString(6, moveis.getSitmov());
            ps.setInt(7, moveis.getCodmov());
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
            ps.setInt(1, moveis.getCodmov());
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
            ps.setInt(1, moveis.getCodmov());
            ResultSet rs = ps.executeQuery(); //SELECT CODPRO, DESPRO, CODCOR, CODSCT FROM PRODUTO
            if (rs.next()) {
                moveis.setCodmov(rs.getInt(1));
                moveis.setDesmov(rs.getString(2));
                moveis.setQtdmov(rs.getInt(3));
                moveis.setVlralu(rs.getBigDecimal(4));
                moveis.getCor().setCodcor(rs.getInt(5));
                moveis.getSubCategoria().setCodsct(rs.getInt(6));
                moveis.setSitmov(rs.getString(7));
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
            ps.setString(1, moveis.getDesmov());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getString("DESMOV").equals(moveis.getDesmov()) && rs.getInt("CODMOV") != moveis.getCodmov()) {
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
