package telas;

import componentes.*;
import dao.ProdutoDao;
import pojo.Produto;

import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.math.BigDecimal;

public class TelaCadastroProduto extends TelaCadastro {

    public static TelaCadastroProduto tela;
    public Produto produto = new Produto();
    public ProdutoDao produtoDao = new ProdutoDao(produto);
    public MeuCampoInteiro campoCodigo = new MeuCampoInteiro(5, false, false, true, "Código");
    public MeuCampoTexto campoDescricao = new MeuCampoTexto(30, true, true, "Descrição");
    public MeuCampoDBComboBox campoCor = new MeuCampoDBComboBox(TelaCadastroCor.class, "Select CODCOR, DESCOR From COR where SITCOR = 'A'", true, true, "Cor");
    public MeuCampoDBComboBox campoSubCategoria = new MeuCampoDBComboBox(TelaCadastroSubCategoria.class, "Select CODSCT, DESSCT From SUBCATEGORIA where SITSCT = 'A'", true, true, "Subcategoria");
    public MeuCampoMonetario campoPreçoCusto = new MeuCampoMonetario(10, false, true, false, "Preço custo");
    public MeuCampoMonetario campoPreçoVenda = new MeuCampoMonetario(10, false, true, false, "Preço Venda");
    public MeuCampoInteiro campoQuantidade = new MeuCampoInteiro(5, false, false, true, "Quantidade");
    private MeuCampoComboBox campoSituacao = new MeuCampoComboBox(new String[][]{{"A", "Ativo"}, {"I", "Inativo"}},
            true, true, "Situação");

    public TelaCadastroProduto() {
        super("Cadastro de Produto");
        adicionaCampo(1, 1, 1, 1, campoCodigo);
        adicionaCampo(2, 1, 1, 1, campoDescricao);
        adicionaCampo(3, 1, 1, 1, campoSubCategoria);
        adicionaCampo(4, 1, 1, 1, campoCor);
        adicionaCampo(5, 1, 1, 1, campoPreçoCusto);
        adicionaCampo(6, 1, 1, 1, campoPreçoVenda);
        adicionaCampo(7, 1, 1, 1, campoQuantidade);
        adicionaCampo(8, 1, 1, 1, campoSituacao);
        setSize(500, 400);
        //pack();
        //Ao abrir a tela os campos aparecem desativados
        habilitarCampos(false);
    }

    public static TelaCadastro getTela() {  //Estático para poder ser chamado de outras classes sem a necessidade de ter criado um objeto anteriormente.
        if (tela == null) {   //Tela não está aberta, pode criar uma nova tela
            tela = new TelaCadastroProduto();
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
        produto.setCodpro((Integer) campoCodigo.getValor());
        produto.setDespro((String) campoDescricao.getValor());
        produto.getSubCategoria().setCodsct((Integer) campoSubCategoria.getValor());
        produto.getCor().setCodcor((Integer) campoCor.getValor());
        produto.setPrecus((BigDecimal) campoPreçoCusto.getValor());
        produto.setPreven((BigDecimal) campoPreçoVenda.getValor());
        produto.setQtdpro((Integer) campoQuantidade.getValor());
        produto.setSitpro((String) campoSituacao.getValor());
    }

    public void getPersistencia() {
        campoCodigo.setValor(produto.getCodpro());
        campoDescricao.setValor(produto.getDespro());
        campoSubCategoria.setValor(produto.getSubCategoria().getCodsct());
        campoCor.setValor(produto.getCor().getCodCor());
        campoPreçoCusto.setValor(produto.getPrecus());
        campoPreçoVenda.setValor(produto.getPreven());
        campoQuantidade.setValor(produto.getQtdpro());
        campoSituacao.setValor(produto.getSitpro());
    }

    @Override
    public boolean incluirBD() {
        super.incluirBD();
        boolean retorno = produtoDao.inserir();

        getPersistencia();
        return retorno;
    }

    @Override
    public boolean duplicidadeBD() {
        super.duplicidadeBD();
        boolean duplicidade = produtoDao.duplicidade();
        System.out.println(duplicidade);
        return duplicidade;
    }

    @Override
    public boolean alterarBD() {
        super.alterarBD();
        return produtoDao.alterar();
    }

    @Override
    public boolean excluirBD() {
        super.excluirBD();
        return produtoDao.excluir();
    }

    @Override
    public void consultarBD(int pk) {
        super.consultarBD(pk);
        produto.setCodpro(pk);
        produtoDao.consultar();
        getPersistencia(); //Em cursos anteriores era o setGUI
    }

    @Override
    public void consultar() {
        TelaPesquisa.getTela("Pesquisa de Produto", produtoDao.PESQUISASQL, new String[]{
                "Código", "Descrição", "Cor", "SubCategoria"}, this); //SELECT CODPRO, DESPRO, CODCOR, CODSCT FROM PRODUTO";
    }
}
