package telas;

import componentes.MeuCampoComboBox;
import componentes.MeuCampoInteiro;
import componentes.MeuCampoTexto;
import dao.CorDao;
import pojo.Cor;

import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class TelaCadastroCor extends TelaCadastro {

    public static TelaCadastroCor tela;
    public Cor cor = new Cor();
    public CorDao corDao = new CorDao(cor);
    public MeuCampoInteiro campoCodigo = new MeuCampoInteiro(5, false, false, true, "Código");
    public MeuCampoTexto campoNome = new MeuCampoTexto(30, true, true, "Descrição");
    private MeuCampoComboBox campoSituacao = new MeuCampoComboBox(new String[][]{{"A", "Ativo"}, {"I", "Inativo"}},
            true, true, "Situação");

    public TelaCadastroCor() {
        super("Cadastro de Cores");
        adicionaCampo(1, 1, 1, 1, campoCodigo);
        adicionaCampo(2, 1, 1, 1, campoNome);
        adicionaCampo(3, 1, 1, 1, campoSituacao);
        setSize(600, 250);
        //pack();
        habilitarCampos(false);
    }

    public static TelaCadastro getTela() {  //Estático para poder ser chamado de outras classes sem a necessidade de ter criado um objeto anteriormente.
        if (tela == null) {   //Tela não está aberta, pode criar uma nova tela
            tela = new TelaCadastroCor();
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
        cor.setId((Integer) campoCodigo.getValor());
        cor.setDesCor((String) campoNome.getValor());
        cor.setSituacao((String) campoSituacao.getValor());
    }

    public void getPersistencia() {
        campoCodigo.setValor(cor.getCodCor());
        campoNome.setValor(cor.getDesCor());
        campoSituacao.setValor(cor.getSituacao());
    }

    @Override
    public boolean incluirBD() {
        super.incluirBD();
        boolean retorno = corDao.inserir();
        getPersistencia();
        return retorno;
    }

    @Override
    public boolean duplicidadeBD() {
        super.duplicidadeBD();
        boolean duplicidade = corDao.duplicidade();
        System.out.println(duplicidade);
        return duplicidade;
    }

    @Override
    public boolean alterarBD() {
        super.alterarBD();
        return corDao.alterar();
    }

    @Override
    public boolean excluirBD() {
        super.excluirBD();
        return corDao.excluir();
    }

    @Override
    public void consultarBD(int pk) {
        super.consultarBD(pk);
        cor.setId(pk);
        corDao.consultar();
        getPersistencia(); //Em cursos anteriores era o setGUI
    }

    @Override
    public void consultar() {
        TelaPesquisa.getTela("Pesquisa de Cor", corDao.PESQUISASQL, new String[]{
                "Código", "Cor"}, this);
    }
}
