package dao;

import bd.Conexao;
import pojo.Categoria;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoriaDao extends DaoPadrao {

    public static final String PESQUISADUPLICIDADE = "Select CODCAT, DESCAT from CATEGORIA where DESCAT = ?";
    public final String PESQUISASQL = "SELECT CODCAT, DESCAT, SITCAT FROM CATEGORIA";
    private Categoria categoria;

    public CategoriaDao(Categoria categoria) {
        this.categoria = categoria;
    }

    public boolean inserir() {
        try {
            String INSERTSQL = "INSERT INTO CATEGORIA VALUES(?,?,?)";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(INSERTSQL);
            categoria.setCodcat(pegaGenerator("GCATEGORIA"));
            ps.setInt(1, categoria.getCodcat());
            ps.setString(2, categoria.getDescat());
            ps.setString(3, categoria.getSitcat());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível incluir a Categoria");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean alterar() {
        try {
            String UPDATESQL = "UPDATE CATEGORIA SET DESCAT = ?, SITCAT = ? WHERE CODCAT = ?";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(UPDATESQL);
            ps.setString(1, categoria.getDescat());
            ps.setString(2, categoria.getSitcat());
            ps.setInt(3, categoria.getCodcat());
            ps.executeUpdate();//
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível alterar a Categoria");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean excluir() {
        try {
            String DELETESQL = "DELETE FROM CATEGORIA WHERE CODCAT = ?";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(DELETESQL);
            ps.setInt(1, categoria.getCodcat());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível excluir a Categoria");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean consultar() {
        try {
            String CONSULTASQL = "SELECT * FROM CATEGORIA WHERE CODCAT = ?";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(CONSULTASQL);
            ps.setInt(1, categoria.getCodcat());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                categoria.setCodcat(rs.getInt(1));
                categoria.setDescat(rs.getString(2));
                categoria.setSitcat(rs.getString(3));
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Categoria não encontrado");
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível consultar a Categoria");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean duplicidade() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(PESQUISADUPLICIDADE);
            //ps.setInt(1, estado.getId());
            ps.setString(1, categoria.getDescat());
            //ps.setString(2, categoria.getSigla());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getString("DESCAT").equals(categoria.getDescat()) && rs.getInt("CODCAT") != categoria.getCodcat()) {
                    System.out.println("Registro igual");
                    JOptionPane.showMessageDialog(null, "Categoria já cadastrada com este Nome");
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
