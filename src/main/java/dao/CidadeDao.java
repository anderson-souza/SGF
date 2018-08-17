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
    private Cidade cidade;

    public CidadeDao(Cidade cidade) {
        this.cidade = cidade;
    }

    public boolean inserir() {
        try {
            String INSERTSQL = "INSERT INTO CIDADE VALUES(?,?,?,?,?)";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(INSERTSQL);
            cidade.setId(pegaGenerator("GCIDADE"));
            ps.setInt(1, cidade.getId());
            ps.setString(2, cidade.getNomCid());
            ps.setInt(3, cidade.getEstado().getId());
            ps.setString(4, cidade.getObservacao());
            ps.setString(5, cidade.getSituacao());
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
            String UPDATESQL = "UPDATE CIDADE SET NOMCID = ?, CODEST = ?, OBSCID = ?, SITCID = ? WHERE CODCID = ?";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(UPDATESQL);
            ps.setString(1, cidade.getNomCid());
            ps.setInt(2, cidade.getEstado().getId());
            ps.setString(3, cidade.getObservacao());
            ps.setString(4, cidade.getSituacao());
            ps.setInt(5, cidade.getId());
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
            String DELETESQL = "DELETE FROM CIDADE WHERE CODCID = ?";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(DELETESQL);
            ps.setInt(1, cidade.getId());
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
            String CONSULTASQL = "SELECT * FROM CIDADE WHERE CODCID = ?";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(CONSULTASQL);
            ps.setInt(1, cidade.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cidade.setId(rs.getInt(1));
                cidade.setNomCid(rs.getString(2));
                cidade.getEstado().setId(rs.getInt(3));
                cidade.setObservacao(rs.getString(4));
                cidade.setSituacao(rs.getString(5));
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
            ps.setInt(2, cidade.getEstado().getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getString("NOMCID").equals(cidade.getNomCid()) && rs.getInt("CODEST") == cidade.getEstado().getId() && rs.getInt("CODCID") != cidade.getId()) {
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
