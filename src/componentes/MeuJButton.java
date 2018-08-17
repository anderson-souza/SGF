package componentes;

import javax.swing.*;

public class MeuJButton extends JButton {

    public MeuJButton(String texto) {
        super(texto);
    }

    public MeuJButton(String localImagem, boolean areaFilled, boolean borderPainted, boolean focusable) {
        setIcon(new ImageIcon(getClass().getResource(localImagem)));
        setContentAreaFilled(areaFilled);
        setBorderPainted(borderPainted);
        setFocusable(focusable);
    }
}

/*package componentes;

 import javax.swing.Icon;
 import javax.swing.JButton;

 public class MeuJButton extends JButton {

 public MeuJButton(String texto, Icon icone) {
 //super(texto, icone);
 //setBackground(Color.blue);
 //setIcon(icone);
 //Biblioteca JAI - Java Advanced Image, seria um mini Photoshop que pode tratar várias propriedades de imagens
 }
 }
 */
