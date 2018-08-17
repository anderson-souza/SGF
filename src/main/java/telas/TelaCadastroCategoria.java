package telas;

import componentes.MeuCampoComboBox;
import componentes.MeuCampoInteiro;
import componentes.MeuCampoTexto;
import dao.CategoriaDao;
import pojo.Categoria;

import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class TelaCadastroCategoria extends TelaCadastro {

    public static TelaCadastroCategoria tela;
    public Categoria categoria = new Categoria();
    public CategoriaDao categoriaDao = new CategoriaDao(categoria);
    public MeuCampoInteiro campoCodigo = new MeuCampoInteiro(5, false, false, true, "Código");
    public MeuCampoTexto campoNome = new MeuCampoTexto(30, true, true, "Descrição");
    private MeuCampoComboBox campoSituacao = new MeuCampoComboBox(new String[][]{{"A", "Ativo"}, {"I", "Inativo"}},
            true, true, "Situação");

    public TelaCadastroCategoria() {
        super("Cadastro de Categoria");
        adicionaCampo(1, 1, 1, 1, campoCodigo);
        adicionaCampo(2, 1, 1, 1, campoNome);
        adicionaCampo(3, 1, 1, 1, campoSituacao);
        //pack();
        setSize(600, 250);
        habilitarCampos(false);
    }

    public static TelaCadastro getTela() {  //Estático para poder ser chamado de outras classes sem a necessidade de ter criado um objeto anteriormente.
        if (tela == null) {   //Tela não está aberta, pode criar uma nova tela
            tela = new TelaCadastroCategoria();
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
        categoria.setCodcat((Integer) campoCodigo.getValor());
        categoria.setDescat((String) campoNome.getValor());
        categoria.setSitcat((String) campoSituacao.getValor());
    }

    public void getPersistencia() {
        campoCodigo.setValor(categoria.getCodcat());
        campoNome.setValor(categoria.getDescat());
        campoSituacao.setValor(categoria.getSitcat());
    }

    @Override
    public boolean incluirBD() {
        super.incluirBD();
        boolean retorno = categoriaDao.inserir();
        getPersistencia();
        return retorno;
    }

    @Override
    public boolean duplicidadeBD() {
        super.duplicidadeBD();
        boolean duplicidade = categoriaDao.duplicidade();
        System.out.println(duplicidade);
        return duplicidade;
    }

    @Override
    public boolean alterarBD() {
        super.alterarBD();
        boolean duplicidade = categoriaDao.duplicidade();
        return categoriaDao.alterar();
    }

    @Override
    public boolean excluirBD() {
        super.excluirBD();
        return categoriaDao.excluir();
    }

    @Override
    public void consultarBD(int pk) {
        super.consultarBD(pk);
        categoria.setCodcat(pk);
        categoriaDao.consultar();
        getPersistencia(); //Em cursos anteriores era o setGUI
    }

    @Override
    public void consultar() {
        TelaPesquisa.getTela("Pesquisa de Categoria", categoriaDao.PESQUISASQL, new String[]{
                "Código", "Categoria"}, this);
    }
}
