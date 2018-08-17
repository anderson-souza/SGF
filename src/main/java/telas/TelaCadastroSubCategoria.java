package telas;

import componentes.MeuCampoComboBox;
import componentes.MeuCampoDBComboBox;
import componentes.MeuCampoInteiro;
import componentes.MeuCampoTexto;
import dao.SubCategoriaDao;
import pojo.SubCategoria;

import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class TelaCadastroSubCategoria extends TelaCadastro {

    public static TelaCadastroSubCategoria tela;
    public SubCategoria subCategoria = new SubCategoria();
    public SubCategoriaDao subCategoriaDao = new SubCategoriaDao(subCategoria);
    public MeuCampoInteiro campoCodigo = new MeuCampoInteiro(5, false, false, true, "Código");
    public MeuCampoDBComboBox campoCategoria = new MeuCampoDBComboBox(TelaCadastroCategoria.class, "Select CODCAT, DESCAT From categoria where SITCAT = 'A'", true, true, "Categoria");
    public MeuCampoTexto campoNome = new MeuCampoTexto(30, true, true, "Descrição");
    private MeuCampoComboBox campoSituacao = new MeuCampoComboBox(new String[][]{{"A", "Ativo"}, {"I", "Inativo"}},
            true, true, "Situação");

    public TelaCadastroSubCategoria() {
        super("Cadastro de SubCategoria");
        adicionaCampo(1, 1, 1, 1, campoCodigo);
        adicionaCampo(2, 1, 1, 1, campoCategoria);
        adicionaCampo(3, 1, 1, 1, campoNome);
        adicionaCampo(4, 1, 1, 1, campoSituacao);
        setSize(600, 250);
        //pack();
        habilitarCampos(false);
    }

    public static TelaCadastro getTela() {  //Estático para poder ser chamado de outras classes sem a necessidade de ter criado um objeto anteriormente.
        if (tela == null) {   //Tela não está aberta, pode criar uma nova tela
            tela = new TelaCadastroSubCategoria();
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
        subCategoria.setId((Integer) campoCodigo.getValor());
        subCategoria.setDescricao((String) campoNome.getValor());
        subCategoria.getCategoria().setId((Integer) campoCategoria.getValor());
        subCategoria.setSituacao((String) campoSituacao.getValor());
    }

    public void getPersistencia() {
        campoCodigo.setValor(subCategoria.getId());
        campoNome.setValor(subCategoria.getDescricao());
        campoCategoria.setValor(subCategoria.getCategoria().getId());
        campoSituacao.setValor(subCategoria.getSituacao());
    }

    @Override
    public boolean incluirBD() {
        super.incluirBD();
        boolean retorno = subCategoriaDao.inserir();

        setPersistencia();

        return retorno;
    }

    @Override
    public boolean duplicidadeBD() {
        super.duplicidadeBD();
        boolean duplicidade = subCategoriaDao.duplicidade();
        System.out.println(duplicidade);
        return duplicidade;
    }

    @Override
    public boolean alterarBD() {
        super.alterarBD();
        return subCategoriaDao.alterar();
    }

    @Override
    public boolean excluirBD() {
        super.excluirBD();
        return subCategoriaDao.excluir();
    }

    @Override
    public void consultarBD(int pk) {
        super.consultarBD(pk);
        subCategoria.setId(pk);
        subCategoriaDao.consultar();
        getPersistencia(); //Em cursos anteriores era o setGUI
    }

    @Override
    public void consultar() {
        TelaPesquisa.getTela("Pesquisa de SubCategoria", subCategoriaDao.PESQUISASQL, new String[]{
                "Código", "SubCategoria"}, this);
    }
}
