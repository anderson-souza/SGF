package telas;

import componentes.MeuCampoComboBox;
import componentes.MeuCampoInteiro;
import componentes.MeuCampoTexto;
import dao.CondicaoPagamentoDao;
import pojo.CondicaoPagamento;

import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class TelaCadastroCondicaoPagamento extends TelaCadastro {

    public static TelaCadastroCondicaoPagamento tela;
    public CondicaoPagamento condicaoPagamento = new CondicaoPagamento();
    public CondicaoPagamentoDao condicaoPagamentoDao = new CondicaoPagamentoDao(condicaoPagamento);
    public MeuCampoInteiro campoCodigo = new MeuCampoInteiro(5, false, false, true, "Código");
    public MeuCampoTexto campoDescricao = new MeuCampoTexto(30, true, true, "Descrição");
    public MeuCampoInteiro campoParcelas = new MeuCampoInteiro(5, true, true, true, "Qtd. Parcelas");
    public MeuCampoInteiro campoPrazo = new MeuCampoInteiro(5, true, true, true, "Prazo");
    public MeuCampoInteiro campoCarencia = new MeuCampoInteiro(5, true, true, true, "Dias Carência");
    private MeuCampoComboBox campoSituacao = new MeuCampoComboBox(new String[][]{{"A", "Ativo"}, {"I", "Inativo"}},
            true, true, "Situação");

    public TelaCadastroCondicaoPagamento() {
        super("Cadastro de Condição de Pagamento");
        adicionaCampo(1, 1, 1, 1, campoCodigo);
        adicionaCampo(2, 1, 1, 1, campoDescricao);
        adicionaCampo(3, 1, 1, 1, campoParcelas);
        adicionaCampo(4, 1, 1, 1, campoPrazo);
        adicionaCampo(5, 1, 1, 1, campoCarencia);
        adicionaCampo(6, 1, 1, 1, campoSituacao);
        setSize(500, 300);
        //pack();
        //Ao abrir a tela os campos aparecem desativados
        habilitarCampos(false);
    }

    public static TelaCadastro getTela() {  //Estático para poder ser chamado de outras classes sem a necessidade de ter criado um objeto anteriormente.
        if (tela == null) {   //Tela não está aberta, pode criar uma nova tela
            tela = new TelaCadastroCondicaoPagamento();
            tela.addInternalFrameListener(new InternalFrameAdapter() { //Adiciona um listener para verificar quando a tela for fechada, fazendo assim a remoção da mesma junto ao JDesktopPane da TelaSistema e setando a variável tela = null para permitir que a tela possa ser aberta novamente em outro momento. Basicamente o mesmo controle efetuado pela tela de pesquisa, porém de uma forma um pouco diferente.
                @Override
                public void internalFrameClosed(InternalFrameEvent e) {
                    TelaSistema.jDesktopPane.remove(tela);
                    tela = null;
                }
            });
            TelaSistema.jDesktopPane.add(tela);
        }
        //Depois do teste acima, independentemente dela já existir ou não, ela é selecionada e movida para frente
        TelaSistema.jDesktopPane.setSelectedFrame(tela);
        TelaSistema.jDesktopPane.moveToFront(tela);
        return tela;
    }

    @Override
    public void setPersistencia() {
        condicaoPagamento.setId((Integer) campoCodigo.getValor());
        condicaoPagamento.setDescricao((String) campoDescricao.getValor());
        condicaoPagamento.setNumeroParcelas((Integer) campoParcelas.getValor());
        condicaoPagamento.setPrazoParcelas((Integer) campoPrazo.getValor());
        condicaoPagamento.setDiasCarencia((Integer) campoCarencia.getValor());
        condicaoPagamento.setSituacao((String) campoSituacao.getValor());
    }

    public void getPersistencia() {
        campoCodigo.setValor(condicaoPagamento.getId());
        campoDescricao.setValor(condicaoPagamento.getDescricao());
        campoParcelas.setValor(condicaoPagamento.getNumeroParcelas());
        campoPrazo.setValor(condicaoPagamento.getPrazoParcelas());
        campoCarencia.setValor(condicaoPagamento.getDiasCarencia());
        campoSituacao.setValor(condicaoPagamento.getSituacao());
    }

    @Override
    public boolean incluirBD() {
        super.incluirBD();
        boolean retorno = condicaoPagamentoDao.inserir();
        getPersistencia();
        return retorno;
    }

    @Override
    public boolean duplicidadeBD() {
        super.duplicidadeBD();
        boolean duplicidade = condicaoPagamentoDao.duplicidade();
        System.out.println(duplicidade);
        return duplicidade;
    }

    @Override
    public boolean alterarBD() {
        super.alterarBD();
        return condicaoPagamentoDao.alterar();
    }

    @Override
    public boolean excluirBD() {
        super.excluirBD();
        return condicaoPagamentoDao.excluir();
    }

    @Override
    public void consultarBD(int pk) {
        super.consultarBD(pk);
        condicaoPagamento.setId(pk);
        condicaoPagamentoDao.consultar();
        getPersistencia(); //Em cursos anteriores era o setGUI
    }

    @Override
    public void consultar() {
        TelaPesquisa.getTela("Pesquisa de Condição de Pagamento", condicaoPagamentoDao.PESQUISASQL, new String[]{
                "Código", "Descrição", "Qtd. Parcelas", "Prazo Parcelas", "Dias Carencia"}, this);
    }
}
