package telas;

import componentes.MeuCampoComboBox;
import componentes.MeuCampoInteiro;
import componentes.MeuCampoTextArea;
import componentes.MeuCampoTexto;
import dao.PaisDao;
import pojo.Pais;

import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class TelaCadastroPais extends TelaCadastro {

    public static TelaCadastroPais tela;
    public Pais pais = new Pais();
    public PaisDao paisDao = new PaisDao(pais);
    public MeuCampoInteiro campoCodigo = new MeuCampoInteiro(5, false, false, true, "Código");
    public MeuCampoTexto campoNome = new MeuCampoTexto(30, true, true, "Nome");
    public MeuCampoTextArea campoObservacao = new MeuCampoTextArea("Observação", false, 300, 50);
    private MeuCampoComboBox campoSituacao = new MeuCampoComboBox(new String[][]{{"A", "Ativo"}, {"I", "Inativo"}},
            true, true, "Situação");

    public TelaCadastroPais() {
        super("Cadastro de País");
        adicionaCampo(1, 1, 1, 1, campoCodigo);
        adicionaCampo(2, 1, 1, 1, campoNome);
        adicionaCampo(3, 1, 1, 1, campoObservacao);
        adicionaCampo(4, 1, 1, 1, campoSituacao);
        setSize(600, 250);
        //pack();
        //Ao abrir a tela os campos aparecem desativados
        habilitarCampos(false);
    }

    public static TelaCadastro getTela() {  //Estático para poder ser chamado de outras classes sem a necessidade de ter criado um objeto anteriormente.
        if (tela == null) {   //Tela não está aberta, pode criar uma nova tela
            tela = new TelaCadastroPais();
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
    public void incluir() {
        super.incluir();
        campoSituacao.setValor("A");
    }

    @Override
    public void setPersistencia() {
        pais.setId((Integer) campoCodigo.getValor());
        pais.setNome((String) campoNome.getValor());
        pais.setObservacao((String) campoObservacao.getValor());
        pais.setSituacao((String) campoSituacao.getValor());
    }

    public void getPersistencia() {
        campoCodigo.setValor(pais.getId());
        campoNome.setValor(pais.getNome());
        campoObservacao.setValor(pais.getObservacao());
        campoSituacao.setValor(pais.getSituacao());
    }

    @Override
    public boolean incluirBD() {
        super.incluirBD();
        boolean retorno = paisDao.inserir();
        getPersistencia();
        return retorno;

    }

    @Override
    public boolean duplicidadeBD() {
        super.duplicidadeBD();
        boolean duplicidade = paisDao.duplicidade();
        System.out.println(duplicidade);
        return duplicidade;
    }

    @Override
    public boolean alterarBD() {
        super.alterarBD();
        return paisDao.alterar();
    }

    @Override
    public boolean excluirBD() {
        super.excluirBD();
        return paisDao.excluir();
    }

    @Override
    public void consultarBD(int pk) {
        super.consultarBD(pk);
        pais.setId(pk);
        paisDao.consultar();
        getPersistencia(); //Em cursos anteriores era o setGUI
    }

    @Override
    public void consultar() {
        TelaPesquisa.getTela("Pesquisa de País", paisDao.PESQUISASQL, new String[]{
                "Código", "País"}, this);
    }
}
