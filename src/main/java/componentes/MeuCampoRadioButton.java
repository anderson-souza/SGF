package componentes;

import javax.swing.*;

/**
 * @author Anderson
 */
public class MeuCampoRadioButton extends JRadioButton implements MeuComponente {
    /*JRadioButton yesButton   = new JRadioButton("Yes"  , true);
     JRadioButton noButton    = new JRadioButton("No"   , false);
     JRadioButton maybeButton = new JRadioButton("Maybe", false);
     ButtonGroup bgroup = new ButtonGroup();
     bgroup.add(yesButton);
     bgroup.add(noButton);
     bgroup.add(maybeButton);*/

    private String dica1;
    private String dica2;
    private boolean cond2;

    public MeuCampoRadioButton(String dica1, boolean cond1/*, String dica2, boolean cond2*/) {
        this.dica1 = dica1;
        boolean cond11 = cond1;
        /*this.dica2 = dica2;
         this.cond2 = cond2;*/
    }

    @Override
    public void limpar() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean eVazio() {
        return false;
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean eObrigatorio() {
        return false;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean eValido() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getDica() {
        return dica1;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void habilitar(boolean status) {
        //return true;
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getValor() {
        return null;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setValor(Object valor) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
