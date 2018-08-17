package dao;

import bd.Conexao;
import pojo.CondicaoPagamento;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CondicaoPagamentoDao extends DaoPadrao {

    public static final String PESQUISADUPLICIDADE = "Select CODPGT, DESPGT, NUMPAR, PRAPAR, DIACAR from CONDICAOPGT where DESPGT = ? or NUMPAR = ? and PRAPAR = ? and DIACAR = ?";
    public static final String SQLCOMBOBOX = "Select Codpgt, Despgt from Condicaopgt where sitpgt = 'A'";
    public final String PESQUISASQL = "SELECT CODPGT, DESPGT, NUMPAR, PRAPAR, DIACAR FROM CONDICAOPGT";
    private CondicaoPagamento condicaoPagamento;

    public CondicaoPagamentoDao(CondicaoPagamento condicaoPagamento) {
        this.condicaoPagamento = condicaoPagamento;
    }

    public boolean inserir() {
        try {
            String INSERTSQL = "INSERT INTO CONDICAOPGT VALUES(?,?,?,?,?,?)";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(INSERTSQL);
            condicaoPagamento.setId(pegaGenerator("GCONDICAOPGTO"));
            ps.setInt(1, condicaoPagamento.getId());
            ps.setString(2, condicaoPagamento.getDescricao());
            ps.setInt(3, condicaoPagamento.getNumeroParcelas());
            ps.setInt(4, condicaoPagamento.getPrazoParcelas());
            ps.setInt(5, condicaoPagamento.getDiasCarencia());
            ps.setString(6, condicaoPagamento.getSituacao());
            ps.executeUpdate();
            return duplicidade();
            //return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível incluir a Condicao de Pagamento");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean alterar() {
        try {
            String UPDATESQL = "UPDATE CONDICAOPGT SET DESPGT = ?, NUMPAR = ?, PRAPAR = ?, DIACAR = ?, SITPGT = ? WHERE CODPGT = ?";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(UPDATESQL);
            ps.setString(1, condicaoPagamento.getDescricao());
            ps.setInt(2, condicaoPagamento.getNumeroParcelas());
            ps.setInt(3, condicaoPagamento.getPrazoParcelas());
            ps.setInt(4, condicaoPagamento.getDiasCarencia());
            ps.setString(5, condicaoPagamento.getSituacao());
            ps.setInt(6, condicaoPagamento.getId());
            ps.executeUpdate();//
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível alterar a CondicaoPagamento");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean excluir() {
        try {
            String DELETESQL = "DELETE FROM CONDICAOPGT WHERE CODPGT = ?";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(DELETESQL);
            ps.setInt(1, condicaoPagamento.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível excluir a CondicaoPagamento");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean consultar() {
        try {
            String CONSULTASQL = "SELECT * FROM CONDICAOPGT WHERE CODPGT = ?";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(CONSULTASQL);
            ps.setInt(1, condicaoPagamento.getId());
            ResultSet rs = ps.executeQuery(); //CODPGT, DESPGT, NUMPAR, PRAPAR, DIACAR
            if (rs.next()) {
                condicaoPagamento.setId(rs.getInt(1));
                condicaoPagamento.setDescricao(rs.getString(2));
                condicaoPagamento.setNumeroParcelas(rs.getInt(3));
                condicaoPagamento.setPrazoParcelas(rs.getInt(4));
                condicaoPagamento.setDiasCarencia(rs.getInt(5));
                condicaoPagamento.setSituacao(rs.getString(6));
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Condicao de Pagamento não encontrado");
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível consultar a Condicao de Pagamento");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean duplicidade() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(PESQUISADUPLICIDADE);
            ps.setString(1, condicaoPagamento.getDescricao());
            ps.setInt(2, condicaoPagamento.getNumeroParcelas());
            ps.setInt(3, condicaoPagamento.getPrazoParcelas());
            ps.setInt(4, condicaoPagamento.getDiasCarencia());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println(condicaoPagamento.getDescricao());
                System.out.println(condicaoPagamento.getNumeroParcelas());
                System.out.println(condicaoPagamento.getPrazoParcelas());
                System.out.println(condicaoPagamento.getDiasCarencia());
                if ((rs.getString("DESPGT").equals(condicaoPagamento.getDescricao()) || (rs.getInt("NUMPAR") == condicaoPagamento.getNumeroParcelas()
                        && rs.getInt("PRAPAR") == condicaoPagamento.getPrazoParcelas() && rs.getInt("DIACAR") == condicaoPagamento.getDiasCarencia()))
                        && rs.getInt("CODPGT") != condicaoPagamento.getId()) {
                    System.out.println("Registro igual");
                    JOptionPane.showMessageDialog(null, "Condição de pagamento já cadastrada com esses dados");
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
        //return true;
        return true;
    }

}
