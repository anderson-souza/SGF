package telas;

import componentes.MeuComponente;
import componentes.MeuJButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TelaCadastro extends JInternalFrame implements ActionListener {
    //JInternalFrame significa janelas internas, não são aquelas janelas soltas no sistema.

    public final int PADRAO = 0;
    public final int INCLUINDO = 1;
    public final int ALTERANDO = 2;
    public final int EXCLUINDO = 3;
    public ArrayList<MeuComponente> campos = new ArrayList();
    public boolean temDadosNaTela = false;
    public int operacao = PADRAO;
    //Icon icone = new ImageIcon(getClass().getResource("\\imagens\\icone.ico"));  
    public Icon icAlterar = new ImageIcon("src/icones/alterar.png");
    public Icon icInserir = new ImageIcon("src/icones/inserir.png");
    public Icon icExcluir = new ImageIcon("src/icones/excluir.png");
    public Icon icConsultar = new ImageIcon("src/icones/consultar.png");
    public Icon icCancelar = new ImageIcon("src/icones/cancelar.png");
    public Icon icConfirmar = new ImageIcon("src/icones/confirmar.png");
    public MeuJButton jbIncluir = new MeuJButton("Incluir");
    public MeuJButton jbAlterar = new MeuJButton("Alterar");
    public MeuJButton jbExcluir = new MeuJButton("Excluir");
    public MeuJButton jbConsultar = new MeuJButton("Consultar");
    public MeuJButton jbConfirmar = new MeuJButton("Confirmar");
    public MeuJButton jbCancelar = new MeuJButton("Cancelar");
    public JPanel jpCampos = new JPanel();
    public JPanel jpBotoes = new JPanel();

    public TelaCadastro(String titulo) {
        //super obrigatoriamente deve ser a primeira linha
        super(titulo, false, true, false, true);
        //getContentPane pega a parte interna do JInternalFrame 
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add("West", jpCampos);
        getContentPane().add("South", jpBotoes);
        jpBotoes.setLayout(new GridLayout(1, 6));
        jpCampos.setLayout(new GridBagLayout());
        insereBotao(jbIncluir);
        insereBotao(jbAlterar);
        insereBotao(jbExcluir);
        insereBotao(jbConsultar);
        insereBotao(jbConfirmar);
        insereBotao(jbCancelar);
        //Pack verifica todos os componentes presente na tela e redimensiona o tamanho correto
        //pack();
        //setSize(400,400);
        setVisible(true);
        habilitarBotoes();
    }

    public void adicionaCampo(int linha, int coluna, int altura, int largura, JComponent campo) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = linha;
        gbc.gridx = coluna;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;

        gbc.anchor = GridBagConstraints.EAST;

        if (campo instanceof MeuComponente) {
            String nomeRotulo = "<html><body>" + ((MeuComponente) campo).getDica() + ": ";
            if (((MeuComponente) campo).eObrigatorio()) {
                nomeRotulo = nomeRotulo + "<font color=red>*</font>";
            }
            nomeRotulo = nomeRotulo + "</body></html>";
            JLabel rotulo = new JLabel(nomeRotulo);
            jpCampos.add(rotulo, gbc);
            gbc.gridx++;
            gbc.insets = new Insets(1, 1, 1, 1);
            campos.add((MeuComponente) campo);
        }
        gbc.gridheight = altura;
        gbc.gridwidth = largura;
        gbc.anchor = GridBagConstraints.WEST;
        jpCampos.add((Component) campo, gbc);
    }

    public void insereBotao(MeuJButton botao) {
        jpBotoes.add(botao);
        botao.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == jbIncluir) {
            incluir();
        } else if (ae.getSource() == jbAlterar) {
            alterar();
        } else if (ae.getSource() == jbExcluir) {
            excluir();
        } else if (ae.getSource() == jbConsultar) {
            consultar();
        } else if (ae.getSource() == jbConfirmar) {
            confirmar();
        } else if (ae.getSource() == jbCancelar) {
            cancelar();
        } else {
            JOptionPane.showMessageDialog(null, "Operação não conhecida.");
        }
    }

    public void incluir() {
        operacao = INCLUINDO;
        habilitarBotoes();
        limparCampos();
        habilitarCampos(true);
    }

    public void alterar() {
        operacao = ALTERANDO;
        habilitarBotoes();
        habilitarCampos(true);
    }

    public void excluir() {
        operacao = EXCLUINDO;
        habilitarBotoes();
    }

    public void consultar() {
    }

    public void confirmar() {
        if (operacao == INCLUINDO) {
            //Se ao incluir não conseguir Validar os campos ou não conseguir incluir no Banco ele não confirma a operação.
            if ((!validarCampos()) || (!duplicidadeBD()) || (!incluirBD())) {
                return;
            }
            temDadosNaTela = true;
        } else if (operacao == ALTERANDO) {
            //Se ao alterar não conseguir Validar os campos ou não conseguir alterar no Banco ele não confirma a operação.
            if ((!validarCampos()) || (!duplicidadeBD()) || (!alterarBD())) {
                return;
            }
        } else if (operacao == EXCLUINDO) {
            int opcao = JOptionPane.showConfirmDialog(null, "Confirmar a exclusão?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if ((opcao != JOptionPane.OK_OPTION) || (!excluirBD())) {
                return;
            }
            limparCampos();
            temDadosNaTela = false;
        }
        operacao = PADRAO;
        habilitarCampos(false);
        habilitarBotoes();
    }

    public void cancelar() {
        operacao = PADRAO;
        habilitarBotoes();
        limparCampos();
        habilitarCampos(false);
    }

    public void habilitarBotoes() {
        jbIncluir.setEnabled(operacao == PADRAO);
        jbAlterar.setEnabled(operacao == PADRAO && temDadosNaTela);
        jbExcluir.setEnabled(operacao == PADRAO && temDadosNaTela);
        jbConsultar.setEnabled(operacao == PADRAO);
        jbConfirmar.setEnabled(operacao != PADRAO);
        jbCancelar.setEnabled(operacao != PADRAO);
    }

    public void limparCampos() {
        for (int i = 0; i < campos.size(); i++) {
            campos.get(i).limpar();
        }
    }

    public void habilitarCampos(boolean status) {
        for (int i = 0; i < campos.size(); i++) {
            campos.get(i).habilitar(status);
        }
    }

    public boolean validarCampos() {
        String erroObrigatorio = "";
        for (int i = 0; i < campos.size(); i++) {
            if ((campos.get(i).eObrigatorio()) && (campos.get(i).eVazio())) {
                erroObrigatorio = erroObrigatorio + campos.get(i).getDica() + "\n";
            }
        }
        String erroValidacao = "";
        for (int i = 0; i < campos.size(); i++) {
            if (!campos.get(i).eValido()) {
                erroValidacao = erroValidacao + campos.get(i).getDica() + "\n";
            }
        }
        boolean retorno = true;
        if (!erroObrigatorio.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Os campos abaixo são obrigatórios e não foram informados:\n" + erroObrigatorio);
            retorno = false;
        }

        if (!erroValidacao.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Os campos abaixo são inválidos: \n" + erroValidacao);
            retorno = false;
        }
        return retorno;
    }

    public boolean incluirBD() {
        setPersistencia();
        return true;
    }

    public boolean duplicidadeBD() {
        setPersistencia();
        return true;
    }

    public boolean alterarBD() {
        setPersistencia();
        //duplicidadeBD();
        return true;
    }

    public boolean excluirBD() {
        setPersistencia();
        return true;
    }

    public void consultarBD(int pk) {
        temDadosNaTela = true;
        habilitarBotoes();
    }

    public void setPersistencia() {
    }

}
