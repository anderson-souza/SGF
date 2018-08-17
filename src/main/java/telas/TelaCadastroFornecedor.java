package telas;

import Componentes.MeuCampoCEP;
import Componentes.MeuCampoTel;
import componentes.*;
import dao.FornecedorDao;
import pojo.Fornecedor;

import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Date;

public class TelaCadastroFornecedor extends TelaCadastro implements FocusListener {

    public static TelaCadastroFornecedor tela;
    public Fornecedor fornecedor = new Fornecedor();
    public FornecedorDao fornecedorDao = new FornecedorDao(fornecedor);
    public MeuCampoInteiro campoCodigo = new MeuCampoInteiro(5, false, false, true, "Código");
    //public MeuCampoTexto campoTipo = new MeuCampoTexto(10, true, true, "Tipo");
    public MeuCampoTexto campoNome = new MeuCampoTexto(30, true, true, "Razão Social");
    public MeuCampoTexto campoApelido = new MeuCampoTexto(30, true, true, "Nome Fantasia");
    public MeuCampoTexto campoRG = new MeuCampoTexto(10, true, false, "Inscrição Estadual/RG");
    public MeuCampoData campoDtNas = new MeuCampoData(8, true, "Data Nascimento", true);
    public MeuCampoCpf campoCpf = new MeuCampoCpf(20, false, "CPF", true);
    public MeuCampoCnpj campoCnpj = new MeuCampoCnpj(20, false, "CNPJ", true);
    public MeuCampoCEP campoCEP = new MeuCampoCEP(8, false, "CEP", true);
    public MeuCampoTexto campoEndereco = new MeuCampoTexto(50, true, false, "Endereço");
    public MeuCampoTexto campoNumEnd = new MeuCampoTexto(10, true, false, "Número");
    public MeuCampoTexto campoCplEnd = new MeuCampoTexto(10, true, false, "Complemento");
    public MeuCampoTel campoTelefone = new MeuCampoTel(15, false, "Telefone", true);
    public MeuCampoTel campoCelular = new MeuCampoTel(15, false, "Celular", true);
    public MeuCampoTexto campoEmail = new MeuCampoTexto(30, true, false, "Email");
    public MeuCampoTextArea campoObservacao = new MeuCampoTextArea("Observação", false, 400, 100);
    public MeuCampoDBComboBox campoCidade = new MeuCampoDBComboBox(TelaCadastroCidade.class, "Select CodCid, NomCid From Cidade where SITCID = 'A'", true, true, "Cidade");
    private MeuCampoComboBox campoTipo = new MeuCampoComboBox(new String[][]{{"F", "Pessoa Física"}, {"J", "Pessoa Jurídica"}},
            true, true, "Tipo");
    private MeuCampoComboBox campoSituacao = new MeuCampoComboBox(new String[][]{{"A", "Ativo"}, {"I", "Inativo"}},
            true, true, "Situação");

    public TelaCadastroFornecedor() {
        super("Cadastro de Fornecedor");
        montatela();
        habilitarCampos(false);
    }

    public static TelaCadastro getTela() {  //Estático para poder ser chamado de outras classes sem a necessidade de ter criado um objeto anteriormente.
        if (tela == null) {   //Tela não está aberta, pode criar uma nova tela
            tela = new TelaCadastroFornecedor();
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
        fornecedor.setId((Integer) campoCodigo.getValor());
        fornecedor.setTipoFornecedor((String) campoTipo.getValor());
        fornecedor.setRazaoSocial((String) campoNome.getValor());
        fornecedor.setNomeFantasia((String) campoApelido.getValor());
        fornecedor.setRgie((String) campoRG.getValor());
        //cidade.setUltimaAlteracao(new Date());
        fornecedor.setDatnas((Date) campoDtNas.getValorDate());
        if (campoTipo.getValor() == "F") {
            fornecedor.setCpfcnpj((String) campoCpf.getValor());
        } else {
            fornecedor.setCpfcnpj((String) campoCnpj.getValor());
        }
        fornecedor.setEndfor((String) campoEndereco.getValor());
        fornecedor.setCplend((String) campoCplEnd.getValor());
        fornecedor.setTelfor((String) campoTelefone.getValor());
        fornecedor.setCelfor((String) campoCelular.getValor());
        fornecedor.setEmailfor((String) campoEmail.getValor());
        fornecedor.setObsfor((String) campoObservacao.getValor());
        fornecedor.getCidade().setId((Integer) campoCidade.getValor());
        fornecedor.setSitfor((String) campoSituacao.getValor());
    }

    public void getPersistencia() {
        campoCodigo.setValor(fornecedor.getId());
        campoTipo.setValor(fornecedor.getTipoFornecedor());
        campoNome.setValor(fornecedor.getRazaoSocial());
        campoApelido.setValor(fornecedor.getNomeFantasia());
        campoRG.setValor(fornecedor.getRgie());
        campoDtNas.setValor(fornecedor.getDatnas());
        campoCpf.setValor(fornecedor.getCpfcnpj());
        campoEndereco.setValor(fornecedor.getEndfor());
        campoCplEnd.setValor(fornecedor.getCplend());
        campoTelefone.setValor(fornecedor.getTelfor());
        campoCelular.setValor(fornecedor.getCelfor());
        campoEmail.setValor(fornecedor.getEmailfor());
        campoObservacao.setValor(fornecedor.getObsfor());
        campoCidade.setValor(fornecedor.getCidade().getId());
        campoCnpj.setValor(fornecedor.getCpfcnpj());
        campoSituacao.setValor(fornecedor.getSitfor());
    }

    public void montatela() {
        adicionaCampo(1, 1, 1, 1, campoCodigo);
        adicionaCampo(2, 1, 1, 1, campoTipo);
        adicionaCampo(3, 1, 1, 1, campoNome);
        adicionaCampo(4, 1, 1, 1, campoApelido);
        adicionaCampo(5, 1, 1, 1, campoRG);
        adicionaCampo(6, 1, 1, 1, campoDtNas);
        adicionaCampo(7, 1, 1, 1, campoCpf);
        adicionaCampo(8, 1, 1, 1, campoCnpj);
        adicionaCampo(9, 1, 1, 1, campoCidade);
        adicionaCampo(10, 1, 1, 1, campoCEP);
        adicionaCampo(11, 1, 1, 1, campoEndereco);
        adicionaCampo(12, 1, 1, 1, campoNumEnd);
        adicionaCampo(13, 1, 1, 1, campoCplEnd);
        adicionaCampo(14, 1, 1, 1, campoTelefone);
        adicionaCampo(15, 1, 1, 1, campoCelular);
        adicionaCampo(16, 1, 1, 1, campoEmail);
        adicionaCampo(17, 1, 1, 1, campoObservacao);
        adicionaCampo(18, 1, 1, 1, campoSituacao);
        setSize(800, 680);
        //pack();
        campoTipo.addFocusListener(this);
        campoCidade.addFocusListener(this);
    }

    @Override
    public boolean incluirBD() {
        super.incluirBD();
        boolean retorno = fornecedorDao.inserir();
        getPersistencia();
        return retorno;
    }

    @Override
    public boolean duplicidadeBD() {
        super.duplicidadeBD();
        boolean duplicidade = fornecedorDao.duplicidade();
        System.out.println(duplicidade);
        return duplicidade;
    }

    @Override
    public boolean alterarBD() {
        super.alterarBD();
        return fornecedorDao.alterar();
    }

    @Override
    public boolean excluirBD() {
        super.excluirBD();
        return fornecedorDao.excluir();
    }

    @Override
    public void consultarBD(int pk) {
        super.consultarBD(pk);
        fornecedor.setId(pk);
        fornecedorDao.consultar();
        getPersistencia(); //Em cursos anteriores era o setGUI
    }

    @Override
    public void consultar() {
        TelaPesquisa.getTela("Pesquisa de Fornecedor", fornecedorDao.PESQUISASQL, new String[]{
                "Código", "Nome", "Apelido", "Cpf/Cnpj"}, this);
    }

    @Override
    public void focusGained(FocusEvent fe) {
        if (campoCidade.isFocusOwner()) {
            System.out.println("Tem Foco ComboBox");
            String sql = "Select CodCid, NomCid From Cidade";
            campoCidade.preencher(sql);
            //MeuCampoDBComboBox campoCidade = new MeuCampoDBComboBox(TelaCadastroCidade.class, "Select CodCid, NomCid From Cidade", true, true, "Cidade");
        }
    }

    @Override
    public void focusLost(FocusEvent fe) {

        if (campoTipo.getValor() == "F") {
            campoCnpj.setEnabled(false);
            campoCnpj.limpar();
            campoCpf.setEnabled(true);
        } else {
            campoCnpj.setEnabled(true);
            campoCpf.setEnabled(false);
            campoCpf.limpar();
        }
    }

}
