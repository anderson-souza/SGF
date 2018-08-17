/*package componentes;

import Telas.TelaConsulta;
import Telas.TelaPadrao;
import Telas.TelaPrincipal;
import dados.ColunaConsulta;
import dados.FiltrosConsulta;
import dao.DaoPadrao;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MeuCampoPesquisa extends JPanel implements MeuComponente, ActionListener, FocusListener {

    private FiltrosConsulta[] filtros;
    private ColunaConsulta[] colunas;
    private DaoPadrao daoChamado;
    private String sql;
    private String titulo;
    private Class telaChamadora;
    private TelaPadrao telaPadrao;
    private boolean obrigatorio;
    private String dica;
    private boolean podeHabilitar;
    private JLabel rotulo;

    JFormattedTextField codigo = new JFormattedTextField();
    JFormattedTextField nome = new JFormattedTextField();
    MeuJButton botaoIncluir = new MeuJButton(new ImageIcon("imagem\\TelaPadrao\\inserir_interno.png"));
    MeuJButton botaoPesquisa = new MeuJButton(new ImageIcon("imagem\\TelaPadrao\\consulta_interno.png"));

    public MeuCampoPesquisa(boolean obrigatorio, boolean podeHabilitar, String dica, String titulo, int tamanho, String sql, FiltrosConsulta[] filtros, ColunaConsulta[] colunas, DaoPadrao daoChamado, Class telaChamadora, TelaPadrao telaPadrao) {

        this.obrigatorio = obrigatorio;
        this.podeHabilitar = podeHabilitar;
        this.dica = dica;
        this.titulo = titulo;
        this.sql = sql;
        this.filtros = filtros;
        this.colunas = colunas;
        this.daoChamado = daoChamado;
        this.telaChamadora = telaChamadora;
        this.telaPadrao = telaPadrao;

        codigo.setColumns(5);
        nome.setColumns(tamanho);
        setLayout(new FlowLayout());

        add(codigo);
        add(nome);
        add(botaoIncluir);
        add(botaoPesquisa);

        botaoIncluir.addActionListener(this);
        botaoPesquisa.addActionListener(this);
        codigo.addFocusListener(this);
        nome.setEditable(false);

        codigo.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent ke) {

            }

            @Override
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_F10) {
                    mostraTelaIncluir();
                } else if (ke.getKeyCode() == KeyEvent.VK_F5) {
                    mostraTelaConsulta();
                } else if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                    preencherFk();
                }
            }

            @Override
            public void keyReleased(KeyEvent ke) {

            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == botaoIncluir) {
            mostraTelaIncluir();
        } else if (ae.getSource() == botaoPesquisa) {
            mostraTelaConsulta();
        }
    }

    @Override
    public void focusGained(FocusEvent e) {

    }

    @Override
    public void focusLost(FocusEvent e) {
        if (!(codigo.getText().isEmpty()) && Integer.parseInt(codigo.getText()) != Integer.parseInt("0")) {
            preencherFk();
            return;
        }
        nome.setValue("");
    }

    public void preencherFk() {
        Boolean retorno = daoChamado.consultaInterna(Integer.parseInt("0" + codigo.getText()));
        telaPadrao.preencherFk(Integer.parseInt(codigo.getText()), dica, retorno);
    }

    public void mostraTelaIncluir() {
        try {
            TelaPadrao tela = (TelaPadrao) telaChamadora.newInstance();
            TelaPrincipal.jDesktopPane.add(tela);
            TelaPrincipal.jDesktopPane.moveToFront(tela);
            TelaPrincipal.jDesktopPane.setSelectedFrame(tela);
            TelaPadrao.centraliza(tela);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mostraTelaConsulta() {
        TelaConsulta telaConsulta = new TelaConsulta(dica, titulo, sql, filtros, colunas, daoChamado, telaPadrao);
    }

    @Override
    public void limpar() {
        codigo.setText("0");
        codigo.setBackground(Color.white);
        nome.setText("");
        nome.setBackground(Color.white);
    }

    @Override
    public boolean eValido() {
        return true;
    }

    @Override
    public boolean eObrigatorio() {
        if (codigo.getText().equals("") && (nome.getText().equals(""))) {
            return obrigatorio;
        }
        return false;
    }

    @Override
    public boolean eVazio() {
        return codigo.getText().trim().equals("");
    }

    @Override
    public void setValor(Object valor) {
        codigo.setText(String.valueOf((Integer) valor));
    }

    @Override
    public Object getValor() {
        return (Integer.parseInt("0" + codigo.getText()));
    }

    @Override
    public String getDica() {
        return dica;
    }

    @Override
    public void setLabel(JLabel rotulo) {
        this.rotulo = rotulo;
    }

    @Override
    public void setLabelVisible(boolean status) {
        rotulo.setVisible(status);
    }

    @Override
    public void habilitar(boolean status) {
        codigo.setEnabled(status && podeHabilitar);
        nome.setEnabled(status && podeHabilitar);
        botaoIncluir.setEnabled(status && podeHabilitar);
        botaoPesquisa.setEnabled(status && podeHabilitar);
    }

    @Override
    public void recebeFoco() {
        codigo.requestFocus();
    }

    public void setVisivelCampo(boolean status) {
        codigo.setVisible(status);
        botaoIncluir.setVisible(status);
        botaoPesquisa.setVisible(status);
    }

    public void setTexto(Object valor) {
        nome.setText(String.valueOf((Object) valor));
    }

    public Object getTexto() {
        return nome.getText();
    }
}
*/
