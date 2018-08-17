package dao;

import bd.Conexao;
import pojo.SubCategoria;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SubCategoriaDao extends DaoPadrao {

    public static final String PESQUISADUPLICIDADE = "Select CODSCT, DESSCT from SUBCATEGORIA where DESSCT = ?";
    public final String PESQUISASQL = "SELECT CODSCT, DESSCT FROM SUBCATEGORIA";
    private SubCategoria subCategoria;

    public SubCategoriaDao(SubCategoria subCategoria) {
        this.subCategoria = subCategoria;
    }

    public boolean inserir() {
        try {
            String INSERTSQL = "INSERT INTO SUBCATEGORIA VALUES(?,?,?,?)";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(INSERTSQL);
            subCategoria.setId(pegaGenerator("GSUBCATEGORIA"));
            ps.setInt(1, subCategoria.getId());
            ps.setString(2, subCategoria.getDescricao());
            ps.setInt(3, subCategoria.getCategoria().getId());
            ps.setString(4, subCategoria.getSituacao());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível incluir a SubCategoria");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean alterar() {
        try {
            String UPDATESQL = "UPDATE SUBCATEGORIA SET DESSCT = ?, CODCAT = ?, SITCSCT = ? WHERE CODSCT = ?";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(UPDATESQL);
            ps.setString(1, subCategoria.getDescricao());
            ps.setInt(2, subCategoria.getCategoria().getId());
            ps.setString(3, subCategoria.getSituacao());
            ps.setInt(4, subCategoria.getId());
            ps.executeUpdate();//
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível alterar a SubCategoria");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean excluir() {
        try {
            String DELETESQL = "DELETE FROM SUBCATEGORIA WHERE CODSCT = ?";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(DELETESQL);
            ps.setInt(1, subCategoria.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível excluir a SubCategoria");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean consultar() {
        try {
            String CONSULTASQL = "SELECT * FROM SUBCATEGORIA WHERE CODSCT = ?";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(CONSULTASQL);
            ps.setInt(1, subCategoria.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                subCategoria.setId(rs.getInt(1));
                subCategoria.setDescricao(rs.getString(2));
                subCategoria.getCategoria().setId(rs.getInt(3));
                subCategoria.setSituacao(rs.getString(4));

                return true;
            } else {
                JOptionPane.showMessageDialog(null, "SubCategoria não encontrado");
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível consultar a SubCategoria");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean duplicidade() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(PESQUISADUPLICIDADE);
            ps.setString(1, subCategoria.getDescricao());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getString("DESSCT").equals(subCategoria.getDescricao()) && rs.getInt("CODSCT") != subCategoria.getId()) {
                    System.out.println("Registro igual");
                    JOptionPane.showMessageDialog(null, "SubCategoria já cadastrada com este Nome");
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
