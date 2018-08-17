package dao;

import bd.Conexao;
import pojo.Estado;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EstadoDao extends DaoPadrao {

    //public static final String COMBOBOX = "SELECT CODEST, NOMEST FROM E001EST ORDER BY NOMEST";
    public static final String PESQUISADUPLICIDADE = "Select CODEST, NOMEST, SIGEST from ESTADO where NOMEST = ? or SIGEST = ?";
    public final String PESQUISASQL = "SELECT CODEST, NOMEST, SIGEST, CODPAI FROM ESTADO";
    private Estado estado;

    public EstadoDao(Estado estado) {
        this.estado = estado;
    }

    public boolean inserir() {
        try {
            String INSERTSQL = "INSERT INTO ESTADO VALUES(?,?,?,?,?,?)";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(INSERTSQL);
            estado.setCodest(pegaGenerator("GESTADO"));
            ps.setInt(1, estado.getCodest());
            ps.setString(2, estado.getNomest());
            ps.setString(3, estado.getSigest());
            ps.setInt(4, estado.getPais().getId());
            ps.setString(5, estado.getObsest());
            ps.setString(6, estado.getSitest());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível incluir o Estado.");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean alterar() {
        try {
            String UPDATESQL = "UPDATE ESTADO SET NOMEST = ?, SIGEST = ?, CODPAI = ?, OBSEST = ?, SITEST = ? WHERE CODEST = ?";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(UPDATESQL);
            ps.setString(1, estado.getNomest());
            ps.setString(2, estado.getSigest());
            ps.setInt(3, estado.getPais().getId());
            ps.setString(4, estado.getObsest());
            ps.setString(5, estado.getSitest());
            ps.setInt(6, estado.getCodest());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível alterar o Estado.");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean excluir() {
        try {
            String DELETESQL = "DELETE FROM ESTADO WHERE CODEST = ?";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(DELETESQL);
            ps.setInt(1, estado.getCodest());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível excluir o Estado.");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean consultar() {
        try {
            String CONSULTASQL = "SELECT * FROM ESTADO WHERE CODEST = ?";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(CONSULTASQL);
            ps.setInt(1, estado.getCodest());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                estado.setCodest(rs.getInt(1));
                estado.setNomest(rs.getString(2));
                estado.setSigest(rs.getString(3));
                estado.getPais().setId(rs.getInt(4));
                estado.setObsest(rs.getString(5));
                estado.setSitest(rs.getString(6));
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Estado não encontrado.");
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível consultar o Estado.");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean duplicidade() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(PESQUISADUPLICIDADE);
            //ps.setInt(1, estado.getId());
            ps.setString(1, estado.getNomest());
            ps.setString(2, estado.getSigest());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if ((rs.getString("NOMEST").equals(estado.getNomest()) || rs.getString("SIGEST").equals(estado.getSigest()))
                        && rs.getInt("CODEST") != estado.getCodest()) {
                    System.out.println("Registro igual");
                    JOptionPane.showMessageDialog(null, "Estado já cadastrado com este Nome ou Sigla");
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
