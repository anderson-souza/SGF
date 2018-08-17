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
    private final String INSERTSQL = "INSERT INTO CONDICAOPGT VALUES(?,?,?,?,?,?)";
    private final String UPDATESQL = "UPDATE CONDICAOPGT SET DESPGT = ?, NUMPAR = ?, PRAPAR = ?, DIACAR = ?, SITPGT = ? WHERE CODPGT = ?";
    private final String DELETESQL = "DELETE FROM CONDICAOPGT WHERE CODPGT = ?";
    private final String CONSULTASQL = "SELECT * FROM CONDICAOPGT WHERE CODPGT = ?";
    private CondicaoPagamento condicaoPagamento;

    public CondicaoPagamentoDao(CondicaoPagamento condicaoPagamento) {
        this.condicaoPagamento = condicaoPagamento;
    }

    public boolean inserir() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(INSERTSQL);
            condicaoPagamento.setCodpgt(pegaGenerator("GCONDICAOPGTO"));
            ps.setInt(1, condicaoPagamento.getCodpgt());
            ps.setString(2, condicaoPagamento.getDespgt());
            ps.setInt(3, condicaoPagamento.getNumpar());
            ps.setInt(4, condicaoPagamento.getPrapar());
            ps.setInt(5, condicaoPagamento.getDiacar());
            ps.setString(6, condicaoPagamento.getSitpgt());
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
            PreparedStatement ps = Conexao.getConexao().prepareStatement(UPDATESQL);
            ps.setString(1, condicaoPagamento.getDespgt());
            ps.setInt(2, condicaoPagamento.getNumpar());
            ps.setInt(3, condicaoPagamento.getPrapar());
            ps.setInt(4, condicaoPagamento.getDiacar());
            ps.setString(5, condicaoPagamento.getSitpgt());
            ps.setInt(6, condicaoPagamento.getCodpgt());
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
            PreparedStatement ps = Conexao.getConexao().prepareStatement(DELETESQL);
            ps.setInt(1, condicaoPagamento.getCodpgt());
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
            PreparedStatement ps = Conexao.getConexao().prepareStatement(CONSULTASQL);
            ps.setInt(1, condicaoPagamento.getCodpgt());
            ResultSet rs = ps.executeQuery(); //CODPGT, DESPGT, NUMPAR, PRAPAR, DIACAR
            if (rs.next()) {
                condicaoPagamento.setCodpgt(rs.getInt(1));
                condicaoPagamento.setDespgt(rs.getString(2));
                condicaoPagamento.setNumpar(rs.getInt(3));
                condicaoPagamento.setPrapar(rs.getInt(4));
                condicaoPagamento.setDiacar(rs.getInt(5));
                condicaoPagamento.setSitpgt(rs.getString(6));
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
            ps.setString(1, condicaoPagamento.getDespgt());
            ps.setInt(2, condicaoPagamento.getNumpar());
            ps.setInt(3, condicaoPagamento.getPrapar());
            ps.setInt(4, condicaoPagamento.getDiacar());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println(condicaoPagamento.getDespgt());
                System.out.println(condicaoPagamento.getNumpar());
                System.out.println(condicaoPagamento.getPrapar());
                System.out.println(condicaoPagamento.getDiacar());
                if ((rs.getString("DESPGT").equals(condicaoPagamento.getDespgt()) || (rs.getInt("NUMPAR") == condicaoPagamento.getNumpar()
                        && rs.getInt("PRAPAR") == condicaoPagamento.getPrapar() && rs.getInt("DIACAR") == condicaoPagamento.getDiacar()))
                        && rs.getInt("CODPGT") != condicaoPagamento.getCodpgt()) {
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
