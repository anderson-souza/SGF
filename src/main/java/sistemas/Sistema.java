package sistemas;

import telas.TelaSistema;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import static java.lang.System.*;

public class Sistema {

    public static void main(String args[]) {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }

        TelaSistema telaSistema = new TelaSistema();
        //Dados.usuarioLogado.setId(1); // Forçando o usuário logado enquanto não temos a tela de login
    }
}
