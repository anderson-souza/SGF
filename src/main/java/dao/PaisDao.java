package dao;

import bd.Conexao;
import pojo.Pais;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaisDao extends DaoPadrao {

    public static final String PESQUISADUPLICIDADE = "Select CODPAI, NOMPAI from PAIS where NOMPAI = ?";
    public final String PESQUISASQL = "SELECT CODPAI, NOMPAI FROM PAIS";
    private Pais pais;

    public PaisDao(Pais pais) {
        this.pais = pais;
    }

    public boolean inserir() {
        try {
            String INSERTSQL = "INSERT INTO PAIS VALUES(?,?,?,?)";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(INSERTSQL);
            pais.setId(pegaGenerator("GPAIS"));
            ps.setInt(1, pais.getId());
            ps.setString(2, pais.getNome());
            ps.setString(3, pais.getObs());
            ps.setString(4, pais.getSitpai());
            ps.executeUpdate();//
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível incluir o País");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean alterar() {
        try {
            String UPDATESQL = "UPDATE PAIS SET NOMPAI = ?, OBSPAI = ?, SITPAI = ? WHERE CODPAI = ?";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(UPDATESQL);
            ps.setString(1, pais.getNome());
            ps.setString(2, pais.getObs());
            ps.setString(3, pais.getSitpai());
            ps.setInt(4, pais.getId());
            ps.executeUpdate();//
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível alterar o País");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean excluir() {
        try {
            String DELETESQL = "DELETE FROM PAIS WHERE CODPAI = ?";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(DELETESQL);
            ps.setInt(1, pais.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível excluir o País");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean consultar() {
        try {
            String CONSULTASQL = "SELECT * FROM PAIS WHERE CODPAI = ?";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(CONSULTASQL);
            ps.setInt(1, pais.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                pais.setId(rs.getInt(1));
                pais.setNome(rs.getString(2));
                pais.setObs(rs.getString(3));
                pais.setSitpai(rs.getString(4));
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "País não encontrado");
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível consultar o País");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean duplicidade() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(PESQUISADUPLICIDADE);
            ps.setString(1, pais.getNome());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getString("NOMPAI").equals(pais.getNome()) && rs.getInt("CODPAI") != pais.getId()) {
                    System.out.println("Registro igual");
                    JOptionPane.showMessageDialog(null, "País já cadastrado com este Nome");
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
