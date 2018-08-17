package telas;

import componentes.*;
import dao.CidadeDao;
import pojo.Cidade;

import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class TelaCadastroCidade extends TelaCadastro {

    public static TelaCadastroCidade tela;
    public Cidade cidade = new Cidade();
    public CidadeDao cidadeDao = new CidadeDao(cidade);
    public MeuCampoInteiro campoCodigo = new MeuCampoInteiro(5, false, false, true, "Código");
    public MeuCampoTexto campoNome = new MeuCampoTexto(30, true, true, "Nome");
    public MeuCampoDBComboBox campoEstado = new MeuCampoDBComboBox(TelaCadastroEstado.class, "Select CODEST, NOMEST From ESTADO where SITEST = 'A'", true, true, "Estado");
    public MeuCampoTextArea campoObservacao = new MeuCampoTextArea("Observação", false, 300, 50);
    private MeuCampoComboBox campoSituacao = new MeuCampoComboBox(new String[][]{{"A", "Ativo"}, {"I", "Inativo"}},
            true, true, "Situação");

    public TelaCadastroCidade() {
        super("Cadastro de Cidade");
        adicionaCampo(1, 1, 1, 1, campoCodigo);
        adicionaCampo(2, 1, 1, 1, campoNome);
        adicionaCampo(3, 1, 1, 1, campoEstado);
        adicionaCampo(4, 1, 1, 1, campoObservacao);
        adicionaCampo(5, 1, 1, 1, campoSituacao);
        setSize(700, 350);
        //pack();
        //Ao abrir a tela os campos aparecem desativados
        habilitarCampos(false);
    }

    public static TelaCadastro getTela() {  //Estático para poder ser chamado de outras classes sem a necessidade de ter criado um objeto anteriormente.
        if (tela == null) {   //Tela não está aberta, pode criar uma nova tela
            tela = new TelaCadastroCidade();
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
    public void setPersistencia() {
        cidade.setCodcid((Integer) campoCodigo.getValor());
        cidade.setNomCid((String) campoNome.getValor());
        cidade.setObscid((String) campoObservacao.getValor());
        cidade.getEstado().setCodest((Integer) campoEstado.getValor());
        cidade.setSitcid((String) campoSituacao.getValor());
    }

    public void getPersistencia() {
        campoCodigo.setValor(cidade.getCodcid());
        campoNome.setValor(cidade.getNomCid());
        campoObservacao.setValor(cidade.getObscid());
        campoEstado.setValor(cidade.getEstado().getCodest());
        campoSituacao.setValor(cidade.getSitcid());
    }

    @Override
    public boolean incluirBD() {
        super.incluirBD();
        boolean retorno = cidadeDao.inserir();
        getPersistencia();
        return retorno;
    }

    @Override
    public boolean duplicidadeBD() {
        super.duplicidadeBD();
        boolean duplicidade = cidadeDao.duplicidade();
        System.out.println(duplicidade);
        return duplicidade;
    }

    @Override
    public boolean alterarBD() {
        super.alterarBD();
        return cidadeDao.alterar();
    }

    @Override
    public boolean excluirBD() {
        super.excluirBD();
        return cidadeDao.excluir();
    }

    @Override
    public void consultarBD(int pk) {
        super.consultarBD(pk);
        cidade.setCodcid(pk);
        cidadeDao.consultar();
        getPersistencia(); //Em cursos anteriores era o setGUI
    }

    @Override
    public void consultar() {
        TelaPesquisa.getTela("Pesquisa de Cidade", cidadeDao.PESQUISASQL, new String[]{
                "Código", "Nome", "Estado"}, this);
    }
}
