package telas;

import componentes.*;
import dao.MoveisDao;
import pojo.Moveis;

import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.math.BigDecimal;

public class TelaCadastroMoveis extends TelaCadastro {

    public static TelaCadastroMoveis tela;
    public Moveis moveis = new Moveis();
    public MoveisDao moveisDao = new MoveisDao(moveis);
    public MeuCampoInteiro campoCodigo = new MeuCampoInteiro(5, false, false, true, "Código");
    public MeuCampoTexto campoDescricao = new MeuCampoTexto(30, true, true, "Descrição");
    public MeuCampoDBComboBox campoCor = new MeuCampoDBComboBox(TelaCadastroCor.class, "Select CODCOR, DESCOR From COR where SITCOR = 'A'", true, true, "Cor");
    public MeuCampoDBComboBox campoSubCategoria = new MeuCampoDBComboBox(TelaCadastroSubCategoria.class, "Select CODSCT, DESSCT From SUBCATEGORIA where SITSCT = 'A'", true, true, "Subcategoria");
    public MeuCampoMonetario campoValorAluguel = new MeuCampoMonetario(10, false, true, false, "Valor Aluguel");
    public MeuCampoInteiro campoQuantidade = new MeuCampoInteiro(5, false, false, true, "Quantidade");
    private MeuCampoComboBox campoSituacao = new MeuCampoComboBox(new String[][]{{"A", "Ativo"}, {"I", "Inativo"}},
            true, true, "Situação");

    public TelaCadastroMoveis() {
        super("Cadastro de Moveis");
        adicionaCampo(1, 1, 1, 1, campoCodigo);
        adicionaCampo(2, 1, 1, 1, campoDescricao);
        adicionaCampo(3, 1, 1, 1, campoSubCategoria);
        adicionaCampo(4, 1, 1, 1, campoCor);
        adicionaCampo(5, 1, 1, 1, campoValorAluguel);
        adicionaCampo(6, 1, 1, 1, campoQuantidade);
        adicionaCampo(7, 1, 1, 1, campoSituacao);
        setSize(500, 400);
        //pack();
        //Ao abrir a tela os campos aparecem desativados
        habilitarCampos(false);
    }

    public static TelaCadastro getTela() {  //Estático para poder ser chamado de outras classes sem a necessidade de ter criado um objeto anteriormente.
        if (tela == null) {   //Tela não está aberta, pode criar uma nova tela
            tela = new TelaCadastroMoveis();
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
        moveis.setCodmov((Integer) campoCodigo.getValor());
        moveis.setDesmov((String) campoDescricao.getValor());
        moveis.getSubCategoria().setCodsct((Integer) campoSubCategoria.getValor());
        moveis.getCor().setCodcor((Integer) campoCor.getValor());
        moveis.setVlralu((BigDecimal) campoValorAluguel.getValor());
        moveis.setQtdmov((Integer) campoQuantidade.getValor());
        moveis.setSitmov((String) campoSituacao.getValor());
    }

    public void getPersistencia() {
        campoCodigo.setValor(moveis.getCodmov());
        campoDescricao.setValor(moveis.getDesmov());
        campoSubCategoria.setValor(moveis.getSubCategoria().getCodsct());
        campoCor.setValor(moveis.getCor().getCodCor());
        campoValorAluguel.setValor(moveis.getVlralu());
        campoQuantidade.setValor(moveis.getQtdmov());
        campoSituacao.setValor(moveis.getSitmov());
    }

    @Override
    public boolean incluirBD() {
        super.incluirBD();
        boolean retorno = moveisDao.inserir();
        getPersistencia();
        return retorno;
    }

    @Override
    public boolean duplicidadeBD() {
        super.duplicidadeBD();
        boolean duplicidade = moveisDao.duplicidade();
        System.out.println(duplicidade);
        return duplicidade;
    }

    @Override
    public boolean alterarBD() {
        super.alterarBD();
        return moveisDao.alterar();
    }

    @Override
    public boolean excluirBD() {
        super.excluirBD();
        return moveisDao.excluir();
    }

    @Override
    public void consultarBD(int pk) {
        super.consultarBD(pk);
        moveis.setCodmov(pk);
        moveisDao.consultar();
        getPersistencia(); //Em cursos anteriores era o setGUI
    }

    @Override
    public void consultar() {
        TelaPesquisa.getTela("Pesquisa de Moveis", moveisDao.PESQUISASQL, new String[]{
                "Código", "Descrição", "Cor", "SubCategoria"}, this); //SELECT CODPRO, DESPRO, CODCOR, CODSCT FROM PRODUTO";
    }
}
