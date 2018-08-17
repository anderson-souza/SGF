package dao;

import bd.Conexao;
import pojo.Cor;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CorDao extends DaoPadrao {

    public static final String PESQUISADUPLICIDADE = "Select CODCOR, DESCOR from COR where DESCOR = ?";
    public final String PESQUISASQL = "SELECT CODCOR, DESCOR FROM COR order by 1";
    private final String INSERTSQL = "INSERT INTO COR VALUES(?,?,?)";
    private final String UPDATESQL = "UPDATE COR SET DESCOR = ?, SITCOR = ? WHERE CODCOR = ?";
    private final String DELETESQL = "DELETE FROM COR WHERE CODCOR = ?";
    private final String CONSULTASQL = "SELECT * FROM COR WHERE CODCOR = ? order by 1";
    private Cor cor;        //Huehuebrbr

    public CorDao(Cor cor) {
        this.cor = cor;
    }

    public boolean inserir() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(INSERTSQL);
            cor.setCodcor(pegaGenerator("GCOR"));
            ps.setInt(1, cor.getCodCor());
            ps.setString(2, cor.getDesCor());
            ps.setString(3, cor.getSitcor());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível incluir a Cor");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean alterar() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(UPDATESQL);
            ps.setString(1, cor.getDesCor());
            ps.setString(2, cor.getSitcor());
            ps.setInt(3, cor.getCodCor());
            ps.executeUpdate();//
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível alterar a Cor");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean excluir() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(DELETESQL);
            ps.setInt(1, cor.getCodCor());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível excluir a Cor");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean consultar() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(CONSULTASQL);
            ps.setInt(1, cor.getCodCor());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cor.setCodcor(rs.getInt(1));
                cor.setDesCor(rs.getString(2));
                cor.setSitcor(rs.getString(3));
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Cor não encontrado");
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível consultar a Cor");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean duplicidade() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(PESQUISADUPLICIDADE);
            ps.setString(1, cor.getDesCor());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getString("DESCOR").equals(cor.getDesCor()) && rs.getInt("CODCOR") != cor.getCodCor()) {
                    System.out.println("Registro igual");
                    JOptionPane.showMessageDialog(null, "Cor já cadastrada com este Nome");
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
