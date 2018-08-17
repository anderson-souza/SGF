package dao;

import bd.Conexao;
import pojo.ItemCompra;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ItemCompraDao extends DaoPadrao {

    private ItemCompra itemCompra;

    public void setitemCompra(ItemCompra itemCompra) {
        this.itemCompra = itemCompra;
    }

    public boolean incluir() {
        try {
            String SQLINCLUIR = "INSERT INTO ITEMCOMPRA VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(SQLINCLUIR);
            itemCompra.setCoditc(pegaGenerator("GITEMCOMPRA"));
            ps.setInt(1, itemCompra.getCoditc());
            ps.setInt(2, itemCompra.getCodcpr().getCodcpr());
            ps.setInt(3, itemCompra.getCodpro().getCodpro());
            ps.setInt(4, itemCompra.getQtdcpr());
            ps.setBigDecimal(5, itemCompra.getVlruni());
            ps.setBigDecimal(6, itemCompra.getVlrdes());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível incluir o Item de Compra.");
            e.printStackTrace();
            return false;
        }
    }

    public boolean alterar() {
        try {
            String SQLALTERAR = "UPDATE ITEMCOMPRA SET CODCPR = ?, CODPRO = ?, CODMOV = ?, QTDCPR = ?, VLRUNI = ?,"
                    + "VLRDES = ? WHERE CODITC = ?";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(SQLALTERAR);
            ps.setInt(1, itemCompra.getCodcpr().getCodcpr());
            ps.setInt(2, itemCompra.getCodpro().getCodpro());
            ps.setInt(3, itemCompra.getCodmov().getCodmov());
            ps.setInt(4, itemCompra.getQtdcpr());
            ps.setBigDecimal(5, itemCompra.getVlruni());
            ps.setBigDecimal(6, itemCompra.getVlrdes());
            ps.setInt(7, itemCompra.getCoditc());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir() {
        try {
            String SQLEXCLUIR = "DELETE FROM ITEMCOMPRA WHERE CODITC = ?";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(SQLEXCLUIR);
            ps.setInt(1, itemCompra.getCoditc());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean consultar() {
        try {
            String SQLCONSULTAR = "SELECT I.CODITC, I.CODCPR, I.CODPRO, P.DESPRO, I.QTDITC,"
                    + "I.QTDITC, I.VLRUNI, I.VLRDES FROM ITEMCOMPRA I, PRODUTO P WHERE I.CODITC = ? and I.CODPRO = P.CODPRO";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(SQLCONSULTAR);
            ps.setInt(1, itemCompra.getCoditc());
            ResultSet rs = ps.executeQuery();
            System.out.println("Abri a parada pra consultar");
            if (rs.next()) {
                System.out.println("Consultei essa parada");
                itemCompra.setCoditc(rs.getInt(1));
                itemCompra.getCodcpr().setCodcpr(rs.getInt(2));
                itemCompra.getCodpro().setCodpro(rs.getInt(3));
                itemCompra.getCodpro().setDespro(rs.getString(4));
                itemCompra.setQtdcpr((rs.getInt(5)));
                itemCompra.setVlruni(rs.getBigDecimal(6));
                itemCompra.setVlrdes((rs.getBigDecimal(7)));
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível consultar o Item de Compra.");
            e.printStackTrace();
            return false;
        }
    }
}
