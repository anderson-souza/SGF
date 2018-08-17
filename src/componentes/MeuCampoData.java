package componentes;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class MeuCampoData extends MeuCampoFormatado {

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public MeuCampoData(int colunas, boolean obrigatorio, String dica, boolean podeHabilitar) {
        super(colunas, obrigatorio, dica, podeHabilitar);
        sdf.setLenient(false);
        try {
            MaskFormatter mf = new MaskFormatter("##/##/####");
            mf.install(this);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível iniciar MeuCampoData");
        }
    }

    @Override
    public boolean eVazio() {
        return getText().replace("/", "").trim().isEmpty();
    }

    @Override
    public boolean eValido() {
        /*try {
         sdf.parse(getText());        
         return true;
         } catch (Exception e) {
         System.out.println("Não ok");
         return false;
         }*/

        try {
            Date dataVerificavel = sdf.parse(getText());
            GregorianCalendar dataNascimento = new GregorianCalendar();
            dataNascimento.setTime(dataVerificavel);
            GregorianCalendar dataAtual = new GregorianCalendar();
            dataAtual.add(GregorianCalendar.YEAR, +0);
            if (dataNascimento.after(dataAtual)) {
                return false;
            }
            return true;
        } catch (ParseException ex) {
            return false;
        }
    }

    @Override
    public void limpar() {
        setText("  /  /    ");
    }

    @Override
    public void setValor(Object valor) {
        setText(sdf.format((Date) valor));
    }

    public Date getValorDate() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.parse(getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível obter a data.");
            e.printStackTrace();
            return null;
        }
    }
}
