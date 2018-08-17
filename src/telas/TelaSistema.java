package telas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaSistema extends JFrame implements ActionListener {

    //gerencia a parte interna da janela, gerenciando as JFrame dentro dele.
    public static JDesktopPane jdp = new JDesktopPane() {
        private Image imagem = new ImageIcon(getClass().getResource("/imagens/background.png")).getImage();

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponents(g);
            Dimension dimension = this.getSize();
            int x = (int) (dimension.getWidth() - imagem.getWidth(this)) / 2;
            int y = (int) (dimension.getHeight() - imagem.getHeight(this)) / 2;
            g.drawImage(imagem, x, y, imagem.getWidth(this), imagem.getHeight(this), this);
        }
    };
    public JMenuBar jmb = new JMenuBar();
    public JMenu jmCadastros = new JMenu("Cadastros");
    public JMenu jmMovimentos = new JMenu("Movimentos");
    public JMenu jmRelatorios = new JMenu("Relatórios");
    public JMenuItem jmiPais = new JMenuItem("País");
    public JMenuItem jmiEstado = new JMenuItem("Estado");
    public JMenuItem jmiCidade = new JMenuItem("Cidade");
    public JMenuItem jmiCor = new JMenuItem("Cor");
    public JMenuItem jmiCategoria = new JMenuItem("Categoria");
    public JMenuItem jmiSubCategoria = new JMenuItem("SubCategoria");
    public JMenuItem jmiCondicaoPgt = new JMenuItem("Condição Pagamento");
    public JMenuItem jmiProduto = new JMenuItem("Produto");
    public JMenuItem jmiMoveis = new JMenuItem("Moveis");
    public JMenuItem jmiFornecedor = new JMenuItem("Fornecedores");
    public JMenuItem jmiCompra = new JMenuItem("Compra");

    public TelaSistema() {
        setTitle("Sistema Gerenciador de Floricultura - 2014");
        setExtendedState(MAXIMIZED_BOTH);
        setJMenuBar(jmb);
        jmb.add(jmCadastros);
        jmb.add(jmMovimentos);
        jmb.add(jmRelatorios);
        jmCadastros.add(jmiPais);
        jmCadastros.add(jmiEstado);
        jmCadastros.add(jmiCidade);
        jmCadastros.add(jmiCor);
        jmCadastros.add(jmiCategoria);
        jmCadastros.add(jmiSubCategoria);
        jmCadastros.add(jmiCondicaoPgt);
        jmCadastros.add(jmiProduto);
        jmCadastros.add(jmiMoveis);
        jmCadastros.add(jmiFornecedor);
        jmMovimentos.add(jmiCompra);
        //this significa "este" no caso do ActionListener, o this quer dizer que o ouvinte é a propria classe
        jmiPais.addActionListener(this);
        jmiEstado.addActionListener(this);
        jmiCidade.addActionListener(this);
        jmiCor.addActionListener(this);
        jmiCategoria.addActionListener(this);
        jmiSubCategoria.addActionListener(this);
        jmiCondicaoPgt.addActionListener(this);
        jmiProduto.addActionListener(this);
        jmiMoveis.addActionListener(this);
        jmiFornecedor.addActionListener(this);
        jmiCompra.addActionListener(this);
        getContentPane().add(jdp);
        //Sempre lembrar do setVisible(true) senão a tela não será apresentada 
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == jmiPais) {
            TelaCadastroPais.getTela();
        }
        if (ae.getSource() == jmiEstado) {
            TelaCadastroEstado.getTela();
        }
        if (ae.getSource() == jmiCidade) {
            TelaCadastroCidade.getTela();
        }
        if (ae.getSource() == jmiCor) {
            TelaCadastroCor.getTela();
        }
        if (ae.getSource() == jmiCategoria) {
            TelaCadastroCategoria.getTela();
        }
        if (ae.getSource() == jmiSubCategoria) {
            TelaCadastroSubCategoria.getTela();
        }
        if (ae.getSource() == jmiCondicaoPgt) {
            TelaCadastroCondicaoPagamento.getTela();
        }
        if (ae.getSource() == jmiProduto) {
            TelaCadastroProduto.getTela();
        }
        if (ae.getSource() == jmiMoveis) {
            TelaCadastroMoveis.getTela();
        }
        if (ae.getSource() == jmiFornecedor) {
            TelaCadastroFornecedor.getTela();
        }
        if (ae.getSource() == jmiCompra) {
            TelaCompra.getTela();
        }
    }
}
