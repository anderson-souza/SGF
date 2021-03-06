package componentes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class MeuCampoTexto extends JTextField implements MeuComponente, FocusListener {

    private boolean podeHabilitar;
    private boolean obrigatorio;
    private String dica;

    public MeuCampoTexto(int colunas, boolean podeHabilitar, boolean obrigatorio, String dica) {
        super(colunas);
        this.podeHabilitar = podeHabilitar;
        this.obrigatorio = obrigatorio;
        this.dica = dica;
        if (eObrigatorio()) {
            setBackground(Color.yellow);
        }
        addFocusListener(this);
    }

    @Override
    public void limpar() {
        setText("");
    }

    @Override
    public boolean eVazio() {
        return getText().trim().isEmpty();
    }

    @Override
    public boolean eObrigatorio() {
        return obrigatorio;
    }

    @Override
    public boolean eValido() {
        return true;
    }

    @Override
    public String getDica() {
        return dica;
    }

    @Override
    public void habilitar(boolean status) {
        setEnabled(podeHabilitar && status);
    }

    @Override
    public void focusGained(FocusEvent fe) {
        setBackground(Color.BLUE);
        setForeground(Color.WHITE);
        setCaretPosition(0);
    }

    @Override
    public void focusLost(FocusEvent fe) {
        if (eObrigatorio() && eVazio()) {
            setBackground(Color.yellow);
        } else {
            setBackground(Color.white);
            setForeground(Color.black);
        }
    }

    @Override
    public Object getValor() {
        return getText();
    }

    @Override
    public void setValor(Object valor) {
        setText("" + valor);
    }
}
