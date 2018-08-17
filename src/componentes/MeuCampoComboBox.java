package componentes;

import javax.swing.*;

public class MeuCampoComboBox extends JComboBox implements MeuComponente {

    private boolean podeHabilitar;
    private boolean obrigatorio;
    private String dica;
    private String[][] valores;

    public MeuCampoComboBox(String[][] valores, boolean podeHabilitar, boolean obrigatorio, String dica) {
        this.podeHabilitar = podeHabilitar;
        this.obrigatorio = obrigatorio;
        this.dica = dica;
        this.valores = valores;
        montaComboBox();
    }

    private void montaComboBox() {
        addItem("Selecione...");
        for (String[] valor : valores) {
            addItem(valor[1]);
        }
    }

    @Override
    public void limpar() {
        setSelectedIndex(0);
    }

    @Override
    public boolean eValido() {
        return true;
    }

    @Override
    public boolean eObrigatorio() {
        return obrigatorio;
    }

    @Override
    public boolean eVazio() {
        return getSelectedIndex() < 1;
    }

    @Override
    public String getDica() {
        return dica;
    }

    @Override
    public Object getValor() {
        return valores[getSelectedIndex() - 1][0];

    }

    @Override
    public void setValor(Object valor) {
        for (String[] valorTemp : valores) {
            if (valorTemp[0].equals((String) valor)) {
                setSelectedItem(valorTemp[1]);
            }
        }
    }

    @Override
    public void habilitar(boolean status) {
        setEnabled(podeHabilitar && status);
    }

    public String getString() {
        return getSelectedItem().toString();
    }

    public void setString(String valor) {
        setSelectedItem(valor);
    }
}
