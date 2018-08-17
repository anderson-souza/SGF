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
    //public static final String COMBOBOX = "SELECT CODEST, NOMEST FROM E001EST ORDER BY NOMEST";
    //public static final String PESQUISADUPLICIDADE = "Select CODEST, NOMEST, SIGEST from ESTADO where NOMEST = ? or SIGEST = ?";
    private Compra Compra;

    public CompraDao(Compra compra) {
        this.Compra = compra;
    }

    public boolean inserir() {
        try {
            String INSERTSQL = "INSERT INTO COMPRAS VALUES(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(INSERTSQL);
            Compra.setId(pegaGenerator("GCOMPRA"));
            ps.setInt(1, Compra.getId());
            ps.setInt(2, Compra.getFornecedor().getId());
            System.out.println(Compra.getFornecedor().getId());
            ps.setInt(3, Compra.getCondicaoPagamento().getId());
            System.out.println(Compra.getCondicaoPagamento().getId());
            ps.setDate(4, MetodosGenericos.dataParaBanco(Compra.getDataCompra()));
            ps.setString(5, Compra.getNumeroDocumento());
            ps.setBigDecimal(6, Compra.getValorCompra());
            ps.setBigDecimal(7, Compra.getValorFrete());
            ps.setBigDecimal(8, Compra.getValorDesconto());
            ps.setString(9, Compra.getSituacao());
            ps.setString(10, Compra.getStatus());
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
            String UPDATESQL = "UPDATE COMPRAS SET STACPR = ?, SITCPR = ? WHERE CODCPR = ?";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(UPDATESQL);
            ps.setString(1, Compra.getSituacao());
            ps.setString(2, Compra.getStatus());
            ps.setInt(3, Compra.getId());
            ps.executeUpdate();
            List<ItemCompra> itensCompra = Compra.getItens();
            ItemCompra itemCompra;
            ItemCompraDao itemCompraDao = new ItemCompraDao();
            /*for (int i = 0; i < itensCompra.size(); i++) {
             itemCompra = itensCompra.get(i);
             itemCompra.setId(Compra);
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
            String DELETESQL = "DELETE FROM COMPRAS WHERE CODCPR = ?";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(DELETESQL);
            ps.setInt(1, Compra.getId());
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
            String CONSULTASQL = "SELECT * FROM COMPRAS WHERE CODCPR = ?";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(CONSULTASQL);
            ps.setInt(1, Compra.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Compra.setId(rs.getInt(1));
                Compra.getFornecedor().setId(rs.getInt(2));
                Compra.getCondicaoPagamento().setId(rs.getInt(3));
                Compra.setDataCompra(rs.getDate(4));
                Compra.setNumeroDocumento(rs.getString(5));
                Compra.setValorCompra(rs.getBigDecimal(6));
                Compra.setValorFrete(rs.getBigDecimal(7));
                Compra.setValorDesconto(rs.getBigDecimal(8));
                Compra.setSituacao(rs.getString(9));
                Compra.setStatus(rs.getString(10));
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
            String SQLCONSULTARITENS = "SELECT CODITC FROM ITEMCOMPRA WHERE CODCPR = ?";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(SQLCONSULTARITENS);
            ps.setInt(1, Compra.getId());
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
                produto.setId(itemCompra.getCodpro().getId());
                produtoDao.consultar();
                itensCompra.add(itemCompra);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itensCompra;
    }
}
