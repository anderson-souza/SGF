package dao;

import bd.Conexao;
import pojo.Cidade;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CidadeDao extends DaoPadrao {

    public static final String PESQUISADUPLICIDADE = "Select CODCID, NOMCID, CODEST from CIDADE where NOMCID = ? and CODEST = ?";
    public final String PESQUISASQL = "SELECT CODCID, NOMCID, CODEST FROM CIDADE";
    private final String INSERTSQL = "INSERT INTO CIDADE VALUES(?,?,?,?,?)";
    private final String UPDATESQL = "UPDATE CIDADE SET NOMCID = ?, CODEST = ?, OBSCID = ?, SITCID = ? WHERE CODCID = ?";
    private final String DELETESQL = "DELETE FROM CIDADE WHERE CODCID = ?";
    private final String CONSULTASQL = "SELECT * FROM CIDADE WHERE CODCID = ?";
    private Cidade cidade;

    public CidadeDao(Cidade cidade) {
        this.cidade = cidade;
    }

    public boolean inserir() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(INSERTSQL);
            cidade.setCodcid(pegaGenerator("GCIDADE"));
            ps.setInt(1, cidade.getCodcid());
            ps.setString(2, cidade.getNomCid());
            ps.setInt(3, cidade.getEstado().getCodest());
            ps.setString(4, cidade.getObscid());
            ps.setString(5, cidade.getSitcid());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível incluir a Cidade");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean alterar() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(UPDATESQL);
            ps.setString(1, cidade.getNomCid());
            ps.setInt(2, cidade.getEstado().getCodest());
            ps.setString(3, cidade.getObscid());
            ps.setString(4, cidade.getSitcid());
            ps.setInt(5, cidade.getCodcid());
            ps.executeUpdate();//
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível alterar a Cidade");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean excluir() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(DELETESQL);
            ps.setInt(1, cidade.getCodcid());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível excluir a Cidade");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean consultar() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(CONSULTASQL);
            ps.setInt(1, cidade.getCodcid());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cidade.setCodcid(rs.getInt(1));
                cidade.setNomCid(rs.getString(2));
                cidade.getEstado().setCodest(rs.getInt(3));
                cidade.setObscid(rs.getString(4));
                cidade.setSitcid(rs.getString(5));
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Cidade não encontrado");
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível consultar o Cidade");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean duplicidade() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(PESQUISADUPLICIDADE);
            ps.setString(1, cidade.getNomCid());
            ps.setInt(2, cidade.getEstado().getCodest());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getString("NOMCID").equals(cidade.getNomCid()) && rs.getInt("CODEST") == cidade.getEstado().getCodest() && rs.getInt("CODCID") != cidade.getCodcid()) {
                    System.out.println("Registro igual");
                    JOptionPane.showMessageDialog(null, "Cidade já cadastrada com este Nome");
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
