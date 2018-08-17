package telas;

import componentes.*;
import dao.CompraDao;
import dao.CondicaoPagamentoDao;
import dao.FornecedorDao;
import dao.ProdutoDao;
import pojo.*;
import renderizador.InteiroRender;
import renderizador.MonetarioRender;
import util.TableModelCompra;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TelaCompra extends TelaCadastro {

    private static TelaCompra tela;
    public MeuCampoDBComboBox campoFornecedor = new MeuCampoDBComboBox(TelaCadastroFornecedor.class, FornecedorDao.SQLCOMBOBOX, true, true, "Fornecedor");
    //private MeuCampoDBComboBox campoProdutoItem = new MeuCampoDBComboBox(null, ProdutoDao.PESQUISASQL, true, true, "Produto");
    public MeuCampoDBComboBox campoProdutoItem = new MeuCampoDBComboBox(TelaCadastroProduto.class, ProdutoDao.SQLCOMBOBOX, true, true, "Produto");
    public MeuCampoDBComboBox campoCondicaoPagamento = new MeuCampoDBComboBox(TelaCadastroCondicaoPagamento.class, CondicaoPagamentoDao.SQLCOMBOBOX, true, true, "Condição Pagamento");
    private Compra compra = new Compra();
    private CompraDao CompraDao = new CompraDao(compra);
    private Fornecedor fornecedor = new Fornecedor();
    private FornecedorDao fornecedorDao = new FornecedorDao(fornecedor);
    private CondicaoPagamento condicaoPagamento = new CondicaoPagamento();
    private CondicaoPagamentoDao condicaoPagamentoDao = new CondicaoPagamentoDao(condicaoPagamento);
    private Produto produto = new Produto();
    private ProdutoDao produtoDao = new ProdutoDao(produto);
    private MeuCampoInteiro campoCodigo = new MeuCampoInteiro(5, false, false, false, "Código");
    private MeuCampoComboBox campoStatus = new MeuCampoComboBox(new String[][]{{"P", "Pedido"}, {"C", "Compra"}}, true, true, "Status");
    private MeuCampoComboBox campoSituacao = new MeuCampoComboBox(new String[][]{{"A", "Aberto"}, {"F", "Fechado"}}, true, true, "Situação");
    private MeuCampoData campoData = new MeuCampoData(12, true, "Data", true);
    private MeuCampoMonetario campoValorCompra = new MeuCampoMonetario(15, false, false, false, "Valor Total");
    private MeuCampoMonetario campoDescontoCompra = new MeuCampoMonetario(15, false, true, false, "Desconto");
    private MeuCampoMonetario campoValorLiquidoCompra = new MeuCampoMonetario(15, true, false, false, "Valor Líquido");
    private MeuCampoMonetario campoValorUnitarioItem = new MeuCampoMonetario(15, false, false, false, "Valor Unitário");
    private MeuCampoInteiro campoQuantidadeItem = new MeuCampoInteiro(15, true, false, false, "Quantidade");
    private MeuCampoMonetario campoDescontoItem = new MeuCampoMonetario(15, false, true, false, "Desconto");
    private MeuCampoMonetario campoValorLiquidoItem = new MeuCampoMonetario(15, true, false, false, "Valor Líquido");
    private MeuCampoMonetario campoValorFrete = new MeuCampoMonetario(15, true, true, false, "Valor do Frete");
    private TableModelCompra tm = new TableModelCompra();
    private JTable tabela = new JTable(tm) {
        @Override
        public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
            Component c = super.prepareRenderer(renderer, row, column);
            if (isRowSelected(row)) {
                c.setBackground(new Color(0, 255, 255));
            } else {
                if (row % 2 == 0) {
                    c.setBackground(Color.LIGHT_GRAY);
                } else {
                    c.setBackground(Color.GRAY);
                }
            }
            return c;
        }
    };
    private JPanel jpBotoesItem = new JPanel();
    private JButton jbConfirmarItem = new JButton("Ok");
    private JButton jbIncluirItem = new JButton("+");
    private JButton jbExcluirItem = new JButton("-");
    private ItemCompra itemCompra;

    public TelaCompra() {
        super("Cadastro de Compra");
        compra.setCodfor(fornecedor);
        compra.setCodpgt(condicaoPagamento);
        adicionaCampo(1, 1, 1, 1, campoCodigo);
        adicionaCampo(2, 1, 1, 1, campoData);
        adicionaCampo(3, 1, 1, 2, campoFornecedor);
        adicionaCampo(1, 4, 1, 1, campoValorCompra);
        adicionaCampo(2, 4, 1, 1, campoDescontoCompra);
        adicionaCampo(3, 4, 1, 1, campoValorLiquidoCompra);
//        adicionaCampo(11, 1, 1, 1, campoValorCompra);
//        adicionaCampo(11, 1, 1, 1, campoDescontoCompra);
//        adicionaCampo(11, 1, 1, 1, campoValorLiquidoCompra);
        adicionaCampo(3, 6, 1, 1, campoCondicaoPagamento);
        adicionaCampo(1, 6, 1, 1, campoStatus);
        adicionaCampo(2, 6, 1, 1, campoSituacao);
        adicionaCampo(4, 1, 1, 1, campoValorFrete);
        JLabel jlItem = new JLabel("Item");
        adicionaCampo(4, 2, 1, 1, jlItem);
        adicionaCampo(5, 1, 1, 2, campoProdutoItem);
        adicionaCampo(6, 1, 1, 1, campoValorUnitarioItem);
        adicionaCampo(7, 1, 1, 1, campoQuantidadeItem);
        adicionaCampo(8, 1, 1, 1, campoDescontoItem);
        adicionaCampo(9, 1, 1, 1, campoValorLiquidoItem);
        adicionaCampo(8, 4, 1, 1, jpBotoesItem);
        JScrollPane jsp = new JScrollPane(tabela);
        adicionaCampo(10, 1, 1, 7, jsp);

        jsp.setPreferredSize(new Dimension(800, 300));
        adicionaListeners();
        adicionaBotoesItem();
        setSize(850, 650);
//pack();
        habilitarBotoes();
        habilitarCampos(false);
        //jbAlterar.setVisible(false);
        jbExcluir.setVisible(false);
        TableColumnModel tableModel = tabela.getColumnModel();
        tableModel.getColumn(0).setCellRenderer(new InteiroRender());
        tableModel.getColumn(2).setCellRenderer(new MonetarioRender());
        tableModel.getColumn(3).setCellRenderer(new InteiroRender());
        tableModel.getColumn(4).setCellRenderer(new MonetarioRender());
        tableModel.getColumn(5).setCellRenderer(new MonetarioRender());
        tableModel.getColumn(0).setMaxWidth(50);
        tableModel.getColumn(1).setMaxWidth(350);
        tableModel.getColumn(2).setMaxWidth(100);
        tableModel.getColumn(3).setMaxWidth(100);
        tableModel.getColumn(4).setMaxWidth(100);
        tableModel.getColumn(5).setMaxWidth(100);
    }

    public static TelaCadastro getTela() {  //Estático para poder ser chamado de outras classes sem a necessidade de ter criado um objeto anteriormente.
        if (tela == null) {   //Tela não está aberta, pode criar uma nova tela
            tela = new TelaCompra();
            tela.addInternalFrameListener(new InternalFrameAdapter() { //Adiciona um listener para verificar quando a tela for fechada, fazendo assim a remoção da mesma junto ao JDesktopPane da TelaSistema e setando a variável tela = null para permitir que a tela possa ser aberta novamente em outro momento. Basicamente o mesmo controle efetuado pela tela de pesquisa, porém de uma forma um pouco diferente.
                @Override
                public void internalFrameClosed(InternalFrameEvent e) {
                    TelaSistema.jdp.remove(tela);
                    tela = null;
                }
            });
            TelaSistema.jdp.add(tela);
        }
        //Depois do teste acima, independentemente dela já existir ou não, ela é selecionada e movida para frente
        TelaSistema.jdp.setSelectedFrame(tela);
        TelaSistema.jdp.moveToFront(tela);
        return tela;
    }

    @Override
    public void habilitarBotoes() {
        super.habilitarBotoes();
        if (jbConfirmarItem != null) {
            jbConfirmarItem.setEnabled(operacao == INCLUINDO);
            jbIncluirItem.setEnabled(operacao == INCLUINDO);
            jbExcluirItem.setEnabled(operacao == INCLUINDO);
        }
    }

    @Override
    public void incluir() {
        super.incluir();
        jbIncluirItem.doClick();
    }

    @Override
    public void alterar() {
        if (campoSituacao.getSelectedItem().equals("Fechado") && campoStatus.getSelectedItem().equals("Compra")) { //Se campo for diferente de fechado permite alterar
            JOptionPane.showMessageDialog(this, "Situação não permite ser alterada");

        } else if (campoStatus.getSelectedItem().equals("Compra")) {
            operacao = ALTERANDO;
            habilitarBotoes();
            habilitarCampos(false);
            campoSituacao.setEnabled(true);
        } else {
            operacao = ALTERANDO;
            habilitarBotoes();
            habilitarCampos(false);
            campoSituacao.setEnabled(true);
            campoStatus.setEnabled(true);
        }

    }

    @Override
    public void habilitarCampos(boolean status) {
        super.habilitarCampos(status);
        tabela.setEnabled(status);
    }

    @Override
    public void limparCampos() {
        super.limparCampos();
        campoData.setValor(new Date());
        campoValorUnitarioItem.setValor(BigDecimal.ZERO);
        campoDescontoItem.setValor(BigDecimal.ZERO);
        campoValorLiquidoItem.setValor(BigDecimal.ZERO);
        campoValorCompra.setValor(BigDecimal.ZERO);
        campoDescontoCompra.setValor(BigDecimal.ZERO);
        campoValorLiquidoCompra.setValor(BigDecimal.ZERO);
        campoValorFrete.setValor(BigDecimal.ZERO);
        tm.setDados(new ArrayList());
        System.out.println("Fiz a Limpeza dos Campos");
    }

    private void calcularItem() {
        double valorUnitarioProduto = ((BigDecimal) campoValorUnitarioItem.getValor()).doubleValue();
        double quantidadeProduto = ((Integer) campoQuantidadeItem.getValor()).doubleValue();
        double descontoProduto = ((BigDecimal) campoDescontoItem.getValor()).doubleValue();
        //double valorfrete = ((BigDecimal) campoValorFrete.getValor()).doubleValue();
        double valorLiquidoProduto = (valorUnitarioProduto * quantidadeProduto) - descontoProduto;
        campoValorLiquidoItem.setValor(new BigDecimal(valorLiquidoProduto));
    }

    private void calcularTotais() {
        BigDecimal totalCompra = BigDecimal.ZERO;
        BigDecimal qtde, valorItem;
        ItemCompra itemCompra;
        for (int i = 0; i < tm.getRowCount(); i++) {
            itemCompra = tm.getItemCompra(i);
            qtde = new BigDecimal(itemCompra.getQtdcpr());
            valorItem = itemCompra.getVlruni().multiply(qtde).subtract(itemCompra.getVlrdes());
            totalCompra = totalCompra.add(valorItem);
        }
        campoValorCompra.setValor(totalCompra);
        BigDecimal desconto = (BigDecimal) campoDescontoCompra.getValor();
        BigDecimal frete = (BigDecimal) campoValorFrete.getValor();
        BigDecimal valorLiquido = ((BigDecimal) campoValorCompra.getValor()).add(frete).subtract(desconto);
        // valorLiquido = valorLiquido + frete;
        campoValorLiquidoCompra.setValor(valorLiquido);
    }

    @Override
    public void setPersistencia() {
        fornecedor.setCodfor((Integer) campoFornecedor.getValor());
        condicaoPagamento.setCodpgt((Integer) campoCondicaoPagamento.getValor());
        compra.setStacpr(((String) campoStatus.getValor()));
        compra.setSitcpr(((String) campoSituacao.getValor()));
        compra.setDatcpr((Date) campoData.getValorDate());
        compra.setVlrcpr((BigDecimal) campoValorCompra.getValor());
        compra.setVlrdes((BigDecimal) campoDescontoCompra.getValor());
        compra.setVlrfrt((BigDecimal) campoValorFrete.getValor());
    }

    @Override
    public boolean alterarBD() {
        super.alterarBD();
        return CompraDao.alterar();
    }

    @Override
    public boolean incluirBD() {
        List<ItemCompra> itensCompra = new ArrayList();
        for (int i = 0; i < tm.getRowCount(); i++) {
            itemCompra = tm.getItemCompra(i);
            itensCompra.add(itemCompra);
        }
        compra.setItens(itensCompra);
        boolean retorno = (super.incluirBD() && CompraDao.inserir());
        campoCodigo.setValor(compra.getCodcpr());
        return retorno;
    }

    @Override
    public void consultarBD(int pk) {
        super.consultarBD(pk);
        compra.setCodcpr(pk);
        CompraDao.consultar();
        temDadosNaTela = true;
        setGui();
        operacao = PADRAO;
        habilitarBotoes();
    }

    public void setGui() {
        campoCodigo.setValor(compra.getCodcpr());
        campoData.setValor(compra.getDatcpr());
        campoFornecedor.setValor(compra.getCodfor().getCodfor());
        campoValorCompra.setValor(compra.getVlrcpr());
        campoDescontoCompra.setValor(compra.getVlrdes());
        campoValorLiquidoCompra.setValor(compra.getVlrcpr().subtract(compra.getVlrdes()));
        campoValorFrete.setValor(compra.getVlrfrt());
        campoSituacao.setValor(compra.getSitcpr());
        campoStatus.setValor(compra.getStacpr());
        tm.setDados(compra.getItens());
        System.out.println("Cheguei aqui yo");
    }

    @Override
    public void consultar() {
        super.consultar();
        TelaPesquisa.getTela("Pesquisa de Compra", CompraDao.PESQUISASQL, new String[]{"Código", "Fornecedor", "Data", "Status"}, this);
    }

    private void adicionaListeners() {
        campoDescontoCompra.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        calcularTotais();
                    }
                });
            }
        });

        campoValorFrete.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        calcularTotais();
                        //System.out.println("RECALCULEI OS VALORES");
                    }
                });
            }
        });

        KeyAdapter keyAdapterCalcularItem = new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        calcularItem();
                    }
                });
            }
        };
        campoQuantidadeItem.addKeyListener(keyAdapterCalcularItem);
        campoDescontoItem.addKeyListener(keyAdapterCalcularItem);
        campoProdutoItem.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    produto.setCodpro((Integer) campoProdutoItem.getValor());
                    produtoDao.consultar();
                    campoValorUnitarioItem.setValor(produto.getPreven());
                    calcularItem();
                }
            }
        });
        tabela.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (tabela.getSelectedRow() >= 0) {
                    itemCompra = tm.getItemCompra(tabela.getSelectedRow());
                    if (itemCompra.getCodpro().getCodpro() == 0) {  //É uma nova linha, dados devem ficar em branco.
                        campoProdutoItem.limpar();
                        campoValorUnitarioItem.setValor(BigDecimal.ZERO);
                        campoQuantidadeItem.setValor(1);
                        campoDescontoItem.setValor(BigDecimal.ZERO);
                        campoValorLiquidoItem.setValor(BigDecimal.ZERO);
                    } else {
                        campoProdutoItem.setValor(itemCompra.getCodpro().getCodpro());
                        campoValorUnitarioItem.setValor(itemCompra.getVlruni());
                        campoQuantidadeItem.setValor(itemCompra.getQtdcpr());
                        campoDescontoItem.setValor(itemCompra.getVlrdes());
                        BigDecimal qtde = new BigDecimal(itemCompra.getQtdcpr());
                        BigDecimal valorTotalItem = itemCompra.getVlruni().multiply(qtde).subtract(itemCompra.getVlrdes());
                        campoValorLiquidoItem.setValor(valorTotalItem);
                    }
                }
            }
        });
    }

    private void adicionaBotoesItem() {
        jpBotoesItem.setLayout(new GridLayout(1, 3));
        jpBotoesItem.add(jbConfirmarItem);
        jpBotoesItem.add(jbIncluirItem);
        jpBotoesItem.add(jbExcluirItem);
        jbConfirmarItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (campoProdutoItem.eVazio()) {
                    JOptionPane.showMessageDialog(null, "Selecione um produto.");
                    return;
                }
                if ((Integer) campoQuantidadeItem.getValor() == 0) {
                    JOptionPane.showMessageDialog(null, "Informe a quantidade.");
                    return;
                }
                itemCompra = tm.getItemCompra(tabela.getSelectedRow());
                itemCompra.getCodpro().setCodpro((Integer) campoProdutoItem.getValor());
                itemCompra.getCodpro().setDespro((String) campoProdutoItem.getValorTexto());
                itemCompra.setVlruni((BigDecimal) campoValorUnitarioItem.getValor());
                itemCompra.setQtdcpr((Integer) campoQuantidadeItem.getValor());
                itemCompra.setVlrdes((BigDecimal) campoDescontoItem.getValor());
                tm.fireTableDataChanged();
                calcularTotais();
            }
        });
        jbIncluirItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                tm.addRow(new ItemCompra());
                tabela.getSelectionModel().setSelectionInterval(tm.getRowCount() - 1, tm.getRowCount() - 1);
            }
        });
        jbExcluirItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int linhaSelecionada = tabela.getSelectedRow();
                if (linhaSelecionada == -1) {
                    JOptionPane.showMessageDialog(null, "Você deve selecionar uma linha.");
                } else {
                    tm.removeRow(linhaSelecionada);
                    tabela.getSelectionModel().setSelectionInterval(tm.getRowCount() - 1, tm.getRowCount() - 1);
                }
            }
        });
    }
}
