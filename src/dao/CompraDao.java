package dao;

import bd.Conexao;
import pojo.Compra;
import pojo.ItemCompra;
import pojo.Produto;
import util.MetodosGenericos;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompraDao extends DaoPadrao {

    public final String PESQUISASQL = "SELECT CODCPR, CODFOR, DATCPR, STACPR, SITCPR FROM COMPRAS  order by 1";
    private final String INSERTSQL = "INSERT INTO COMPRAS VALUES(?,?,?,?,?,?,?,?,?,?)";
    private final String UPDATESQL = "UPDATE COMPRAS SET STACPR = ?, SITCPR = ? WHERE CODCPR = ?";
    private final String DELETESQL = "DELETE FROM COMPRAS WHERE CODCPR = ?";
    private final String CONSULTASQL = "SELECT * FROM COMPRAS WHERE CODCPR = ?";
    private final String SQLCONSULTARITENS = "SELECT CODITC FROM ITEMCOMPRA WHERE CODCPR = ?";
    //public static final String COMBOBOX = "SELECT CODEST, NOMEST FROM E001EST ORDER BY NOMEST";
    //public static final String PESQUISADUPLICIDADE = "Select CODEST, NOMEST, SIGEST from ESTADO where NOMEST = ? or SIGEST = ?";
    private Compra Compra;

    public CompraDao(Compra compra) {
        this.Compra = compra;
    }

    public boolean inserir() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(INSERTSQL);
            Compra.setCodcpr(pegaGenerator("GCOMPRA"));
            ps.setInt(1, Compra.getCodcpr());
            ps.setInt(2, Compra.getCodfor().getCodfor());
            System.out.println(Compra.getCodfor().getCodfor());
            ps.setInt(3, Compra.getCodpgt().getCodpgt());
            System.out.println(Compra.getCodpgt().getCodpgt());
            ps.setDate(4, MetodosGenericos.dataParaBanco(Compra.getDatcpr()));
            ps.setString(5, Compra.getNumdoc());
            ps.setBigDecimal(6, Compra.getVlrcpr());
            ps.setBigDecimal(7, Compra.getVlrfrt());
            ps.setBigDecimal(8, Compra.getVlrdes());
            ps.setString(9, Compra.getStacpr());
            ps.setString(10, Compra.getSitcpr());
            ps.executeUpdate();
            List<ItemCompra> itensCompra = Compra.getItens();
            ItemCompra itemCompra;
            ItemCompraDao itemCompraDao = new ItemCompraDao();
            for (int i = 0; i < itensCompra.size(); i++) {
                itemCompra = itensCompra.get(i);
                itemCompra.setCodcpr(Compra);
                itemCompraDao.setitemCompra(itemCompra);
                itemCompraDao.incluir();
            }
            //Conexao.getConexao().commit();
            Conexao.getConexao().setAutoCommit(true);
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível incluir a compra.");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean alterar() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(UPDATESQL);
            ps.setString(1, Compra.getStacpr());
            ps.setString(2, Compra.getSitcpr());
            ps.setInt(3, Compra.getCodcpr());
            ps.executeUpdate();
            List<ItemCompra> itensCompra = Compra.getItens();
            ItemCompra itemCompra;
            ItemCompraDao itemCompraDao = new ItemCompraDao();
            /*for (int i = 0; i < itensCompra.size(); i++) {
             itemCompra = itensCompra.get(i);
             itemCompra.setCodcpr(Compra);
             itemCompraDao.setitemCompra(itemCompra);
             itemCompraDao.excluir();
             }*/
            //Inserindo novamente
            for (int i = 0; i < itensCompra.size(); i++) {
                itemCompra = itensCompra.get(i);
                itemCompra.setCodcpr(Compra);
                itemCompraDao.setitemCompra(itemCompra);
                itemCompraDao.excluir();
                System.out.println("Excluir todos os itens...");
                itemCompraDao.incluir();
                System.out.println("Inseri todos os itens novamente");
            }
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível alterar a compra.");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean excluir() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(DELETESQL);
            ps.setInt(1, Compra.getCodcpr());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível excluir a compra.");
            ex.printStackTrace();
            return false;
        }
    }

    public boolean consultar() {
        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(CONSULTASQL);
            ps.setInt(1, Compra.getCodcpr());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Compra.setCodcpr(rs.getInt(1));
                Compra.getCodfor().setCodfor(rs.getInt(2));
                Compra.getCodpgt().setCodpgt(rs.getInt(3));
                Compra.setDatcpr(rs.getDate(4));
                Compra.setNumdoc(rs.getString(5));
                Compra.setVlrcpr(rs.getBigDecimal(6));
                Compra.setVlrfrt(rs.getBigDecimal(7));
                Compra.setVlrdes(rs.getBigDecimal(8));
                Compra.setStacpr(rs.getString(9));
                Compra.setSitcpr(rs.getString(10));
                Compra.setItens(consultarItens());
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Compra não encontrado.");
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível consultar o Pedido.");
            ex.printStackTrace();
            return false;
        }
    }

    public List<ItemCompra> consultarItens() {
        List<ItemCompra> itensCompra = new ArrayList();
        try {
            ItemCompraDao itemCompraDao = new ItemCompraDao();
            Produto produto = new Produto();
            ProdutoDao produtoDao = new ProdutoDao(produto);
            ItemCompra itemCompra;
            PreparedStatement ps = Conexao.getConexao().prepareStatement(SQLCONSULTARITENS);
            ps.setInt(1, Compra.getCodcpr());
            ResultSet rs = ps.executeQuery();
            List<Integer> codigosItens = new ArrayList();
            while (rs.next()) {
                codigosItens.add(rs.getInt(1));
            }
            for (int i = 0; i < codigosItens.size(); i++) {
                itemCompra = new ItemCompra();
                itemCompra.setCoditc(codigosItens.get(i));
                itemCompraDao.setitemCompra(itemCompra);
                itemCompraDao.consultar();
                produto.setCodpro(itemCompra.getCodpro().getCodpro());
                produtoDao.consultar();
                itensCompra.add(itemCompra);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itensCompra;
    }
}