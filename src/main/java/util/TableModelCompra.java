package util;

import dao.ProdutoDao;
import pojo.ItemCompra;
import pojo.Produto;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TableModelCompra extends AbstractTableModel {

    protected String[] colunas = new String[]{"Código", "Produto", "Valor Unitário", "Quantidade", "Desconto", "Valor Líquido"};
    protected List<ItemCompra> dados = new ArrayList();
    private Produto produto = new Produto();
    protected ProdutoDao produtoDao = new ProdutoDao(produto);

    public void addRow(ItemCompra itemCompra) {
        dados.add(itemCompra);
        int numeroLinha = dados.size() - 1;
        fireTableRowsInserted(numeroLinha, numeroLinha);
    }

    public void removeRow(int numeroLinha) {
        dados.remove(numeroLinha);
        fireTableRowsDeleted(numeroLinha, numeroLinha);
    }

    @Override
    public int getRowCount() {
        return dados.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    public ItemCompra getItemCompra(int linha) {
        return dados.get(linha);
    }

    public void setDados(List<ItemCompra> dados) {
        this.dados = dados;
        fireTableDataChanged();
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        switch (col) {
            case 0:
                dados.get(row).getCodpro().setId((Integer) value);
                break;
            case 1:
                dados.get(row).getCodpro().setId((Integer) value);
                produtoDao.setProduto(dados.get(row).getCodpro());
                produtoDao.consultar();
                break;
            case 2:
                dados.get(row).setVlruni((BigDecimal) value);
                break;
            case 3:
                dados.get(row).setQtdcpr((Integer) value);
                break;
            case 4:
                dados.get(row).setVlrdes((BigDecimal) value);
                break;
            case 5: //Não é preciso tratar a coluna 5, pois ela é resultado de um cálculo
                break;
            default:
                JOptionPane.showMessageDialog(null, "Não existe tratamento para a coluna " + col + " na tabela de Itens de Compra. (set)");
        }
        fireTableCellUpdated(row, col);
    }

    @Override
    public Object getValueAt(int row, int col) {
        switch (col) {
            case 0:
                return dados.get(row).getCodpro().getId();
            case 1:
                return dados.get(row).getCodpro().getDescricao();
            case 2:
                return dados.get(row).getVlruni();
            case 3:
                return dados.get(row).getQtdcpr();
            case 4:
                return dados.get(row).getVlrdes();
            case 5:
                BigDecimal qtde = new BigDecimal(dados.get(row).getQtdcpr());
                return dados.get(row).getVlruni().multiply(qtde).subtract(dados.get(row).getVlrdes());
            //return dados.get(row).getValorDesconto();
            default:
                JOptionPane.showMessageDialog(null, "Não existe tratamento para a coluna " + col + " na tabela de Itens de Compra. (get)*Vulgo tetraplegico");
                return "";
        }
    }

    @Override
    public String getColumnName(int numeroColuna) {
        return colunas[numeroColuna];
    }
}
