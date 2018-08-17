package telas;

import componentes.*;
import dao.EstadoDao;
import pojo.Estado;

import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class TelaCadastroEstado extends TelaCadastro {

    public static TelaCadastroEstado tela;
    public Estado estado = new Estado();
    public EstadoDao estadoDao = new EstadoDao(estado);
    public MeuCampoInteiro campoCodigo = new MeuCampoInteiro(5, false, false, true, "Código");
    public MeuCampoTexto campoNome = new MeuCampoTexto(30, true, true, "Nome");
    public MeuCampoTexto campoSigla = new MeuCampoTexto(2, true, true, "Sigla");
    public MeuCampoDBComboBox campoPais = new MeuCampoDBComboBox(TelaCadastroPais.class, "Select CODPAI, NOMPAI From Pais where sitpai = 'A'", true, true, "País");
    public MeuCampoTextArea campoObservacao = new MeuCampoTextArea("Observação", false, 300, 50);
    private MeuCampoComboBox campoSituacao = new MeuCampoComboBox(new String[][]{{"A", "Ativo"}, {"I", "Inativo"}},
            true, true, "Situação");

    public TelaCadastroEstado() {
        super("Cadastro de Estado");
        adicionaCampo(1, 1, 1, 1, campoCodigo);
        adicionaCampo(2, 1, 1, 1, campoNome);
        adicionaCampo(3, 1, 1, 1, campoSigla);
        adicionaCampo(4, 1, 1, 1, campoPais);
        adicionaCampo(5, 1, 1, 1, campoObservacao);
        adicionaCampo(6, 1, 1, 1, campoSituacao);
        setSize(650, 350);
        //pack();
        //Ao abrir a tela os campos aparecem desativados
        habilitarCampos(false);
    }

    public static TelaCadastro getTela() {  //Estático para poder ser chamado de outras classes sem a necessidade de ter criado um objeto anteriormente.
        if (tela == null) {   //Tela não está aberta, pode criar uma nova tela
            tela = new TelaCadastroEstado();
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
        estado.setId((Integer) campoCodigo.getValor());
        estado.setNome((String) campoNome.getValor());
        estado.setSigla((String) campoSigla.getValor());
        estado.setObservacao((String) campoObservacao.getValor());
        estado.getPais().setId((Integer) campoPais.getValor());
        estado.setSituacao((String) campoSituacao.getValor());
    }

    public void getPersistencia() {
        campoCodigo.setValor(estado.getId());
        campoNome.setValor(estado.getNome());
        campoSigla.setValor(estado.getSigla());
        campoObservacao.setValor(estado.getObservacao());
        campoPais.setValor(estado.getPais().getId());
        campoSituacao.setValor(estado.getSituacao());
    }

    @Override
    public boolean incluirBD() {
        super.incluirBD();
        boolean duplicidade = estadoDao.duplicidade();
        boolean retorno = estadoDao.inserir();
        if (retorno && duplicidade) {
            getPersistencia();
        }
        return retorno;
    }

    @Override
    public boolean alterarBD() {
        super.alterarBD();
        estadoDao.duplicidade();
        return estadoDao.alterar();
    }

    @Override
    public boolean excluirBD() {
        super.excluirBD();
        return estadoDao.excluir();
    }

    @Override
    public void consultarBD(int pk) {
        super.consultarBD(pk);
        estado.setId(pk);
        estadoDao.consultar();
        getPersistencia(); //Em cursos anteriores era o setGUI
    }

    @Override
    public void consultar() {
        TelaPesquisa.getTela("Pesquisa de Estado", estadoDao.PESQUISASQL, new String[]{
                "Código", "Nome", "Sigla", "País"}, this);
    }
}
