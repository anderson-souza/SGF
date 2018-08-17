package dao;

import bd.Conexao;
import pojo.Fornecedor;
import util.MetodosGenericos;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FornecedorDao extends DaoPadrao {

    public static final String PESQUISADUPLICIDADE = "Select CODFOR, NOMRAZ, CPFCNPJ from FORNECEDORES where NOMRAZ = ? or CPFCNPJ = ? "; //or RGIE = ?
    public static final String SQLCOMBOBOX = "Select CODFOR, NOMRAZ From FORNECEDORES where SITFOR = 'A'"; //or RGIE = ?
    public final String PESQUISASQL = "SELECT CODFOR, NOMRAZ, APEFAN, CPFCNPJ FROM FORNECEDORES";
    private Fornecedor fornecedor;

    public FornecedorDao(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public boolean inserir() {
        try {
            String INSERTSQL = "INSERT INTO FORNECEDORES VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(INSERTSQL);
            fornecedor.setCodfor(pegaGenerator("GFORNECEDORES"));
            ps.setInt(1, fornecedor.getCodfor());
            ps.setString(2, fornecedor.getTipfor());
            ps.setString(3, fornecedor.getNomraz());
            ps.setString(4, fornecedor.getApefan());
            ps.setString(5, fornecedor.getRgie());
            ps.setDate(6, MetodosGenericos.dataParaBanco(fornecedor.getDatnas()));
            ps.setString(7, fornecedor.getCpfcnpj());
            ps.setString(8, fornecedor.getCepfor());
            ps.setString(9, fornecedor.getEndfor());
            ps.setString(10, fornecedor.getNumend());
            ps.setString(11, fornecedor.getCplend());
            ps.setString(12, fornecedor.getTelfor());
            ps.setString(13, fornecedor.getCelfor());
            ps.setString(14, fornecedor.getEmailfor());
            ps.setString(15, fornecedor.getObsfor());
            ps.setInt(16, fornecedor.getCidade().getCodcid());
            ps.setString(17, fornecedor.getSitfor());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível incluir o Fornecedor");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean alterar() {
        try {
            String UPDATESQL = "UPDATE FORNECEDORES SET TIPFOR = ?, NOMRAZ = ?, APEFAN = ?, RGIE = ?, DATNAS = ?, "
                    + "CPFCNPJ = ?, CEPFOR = ?, ENDFOR = ?, NUMEND = ?, CPLEND = ?, TELFOR = ?, CELFOR = ?, EMAILFOR = ?, "
                    + "OBSFOR = ?, CODCID = ? , SITFOR = ? WHERE CODFOR = ?";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(UPDATESQL);
            ps.setString(1, fornecedor.getTipfor());
            ps.setString(2, fornecedor.getNomraz());
            ps.setString(3, fornecedor.getApefan());
            ps.setString(4, fornecedor.getRgie());
            ps.setDate(5, MetodosGenericos.dataParaBanco(fornecedor.getDatnas()));
            ps.setString(6, fornecedor.getCpfcnpj());
            ps.setString(7, fornecedor.getCepfor());
            ps.setString(8, fornecedor.getEndfor());
            ps.setString(9, fornecedor.getNumend());
            ps.setString(10, fornecedor.getCplend());
            ps.setString(11, fornecedor.getTelfor());
            ps.setString(12, fornecedor.getCelfor());
            ps.setString(13, fornecedor.getEmailfor());
            ps.setString(14, fornecedor.getObsfor());
            ps.setInt(15, fornecedor.getCidade().getCodcid());
            ps.setString(16, fornecedor.getSitfor());
            ps.setInt(17, fornecedor.getCodfor());
            ps.executeUpdate();//
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível alterar o Fornecedor");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean excluir() {
        try {
            String DELETESQL = "DELETE FROM FORNECEDORES WHERE CODFOR = ?";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(DELETESQL);
            ps.setInt(1, fornecedor.getCodfor());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível excluir o Fornecedor");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean consultar() {
        try {
            String CONSULTASQL = "SELECT * FROM FORNECEDORES WHERE CODFOR = ?";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(CONSULTASQL);
            ps.setInt(1, fornecedor.getCodfor());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                fornecedor.setCodfor(rs.getInt(1));
                fornecedor.setTipfor(rs.getString(2));
                fornecedor.setNomraz(rs.getString(3));
                fornecedor.setApefan(rs.getString(4));
                fornecedor.setRgie(rs.getString(5));
                fornecedor.setDatnas(rs.getDate(6));
                fornecedor.setCpfcnpj(rs.getString(7));
                fornecedor.setCepfor(rs.getString(8));
                fornecedor.setEndfor(rs.getString(9));
                fornecedor.setNumend(rs.getString(10));
                fornecedor.setCplend(rs.getString(11));
                fornecedor.setTelfor(rs.getString(12));
                fornecedor.setCelfor(rs.getString(13));
                fornecedor.setEmailfor(rs.getString(14));
                fornecedor.setObsfor(rs.getString(15));
                fornecedor.getCidade().setCodcid(rs.getInt(16));
                fornecedor.setSitfor(rs.getString(17));
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Fornecedor não encontrado");
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível consultar o Fornecedor");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean duplicidade() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(PESQUISADUPLICIDADE);
            ps.setString(1, fornecedor.getNomraz());//NOMRAZ = ? or CPFNPJ = ? or RGIE = ?           
            ps.setString(2, fornecedor.getCpfcnpj());
            //ps.setString(3, fornecedor.getRgie());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getString("NOMRAZ").equals(fornecedor.getNomraz()) || rs.getString("CPFCNPJ").equals(fornecedor.getCpfcnpj())
                        && rs.getInt("CODFOR") != fornecedor.getCodfor()) { //|| rs.getString("RGIE").equals(fornecedor.getRgie()) 
                    System.out.println("Registro igual");
                    JOptionPane.showMessageDialog(null, "Fornecedor já cadastrado com estes dados");
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
