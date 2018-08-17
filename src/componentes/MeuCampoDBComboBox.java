package componentes;

import bd.Conexao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MeuCampoDBComboBox extends JPanel implements MeuComponente, FocusListener {

    private JComboBox jcb = new JComboBox();
    private MeuJButton mjb = new MeuJButton("...");
    private List<Integer> pks;
    private String sql;
    private boolean podeHabilitar;
    private boolean obrigatorio;
    private String dica;

    public MeuCampoDBComboBox(final Class tela, String sql, boolean podeHabilitar, boolean obrigatorio, String dica) {
        this.podeHabilitar = podeHabilitar;
        this.obrigatorio = obrigatorio;
        this.dica = dica;
        setLayout(new FlowLayout());
        add(jcb);
        add(mjb);
        //jcb.addFocusListener(this);
        preencher(sql);
        mjb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    Method metodoGetTela = tela.getMethod("getTela", new Class[]{});
                    metodoGetTela.invoke(null, new Object[]{});
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void preencher(String sql) {
        try {
            this.sql = sql;
            pks = new ArrayList();
            jcb.removeAllItems();
            ResultSet rs = Conexao.getConexao().createStatement().executeQuery(sql);
            while (rs.next()) {
                pks.add(rs.getInt(1));
                jcb.addItem(rs.getString(2));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível preencher o MeuCampoDBComboBox");
            e.printStackTrace();
        }
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
    public boolean eVazio() {
        return (jcb.getSelectedIndex() == -1);
    }

    @Override
    public String getDica() {
        return dica;
    }

    @Override
    public void habilitar(boolean status) {
        jcb.setEnabled(podeHabilitar && status);
        mjb.setEnabled(podeHabilitar && status);
    }

    @Override
    public void limpar() {
        jcb.setSelectedIndex(-1);
    }

    @Override
    public Object getValor() {
        return pks.get(jcb.getSelectedIndex());
    }

    @Override
    public void setValor(Object valor) {
        int v = (Integer) valor;
        for (int i = 0; i < pks.size(); i++) {
            if (pks.get(i) == v) {
                jcb.setSelectedIndex(i);
                return;
            }
        }
        JOptionPane.showMessageDialog(null, dica + " (" + v + ") não encontrado(a)");
    }

    public String getValorTexto() {
        return (String) jcb.getSelectedItem();
    }

    public void addItemListener(ItemListener il) {
        jcb.addItemListener(il);
    }

    @Override
    public void focusGained(FocusEvent fe) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        preencher(sql);
    }

    @Override
    public void focusLost(FocusEvent fe) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

/*package componentes;

 import bd.Conexao;
 import java.awt.FlowLayout;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import java.awt.event.FocusEvent;
 import java.awt.event.FocusListener;
 import java.awt.event.ItemListener;
 import java.lang.reflect.Method;
 import java.sql.ResultSet;
 import java.util.ArrayList;
 import java.util.List;
 import javax.swing.Icon;
 import javax.swing.ImageIcon;
 import javax.swing.JComboBox;
 import javax.swing.JOptionPane;
 import javax.swing.JPanel;

 public class MeuCampoDBComboBox extends JPanel implements MeuComponente, FocusListener {

 private JComboBox jcb = new JComboBox();
 private Icon icone = new ImageIcon("icones/inserir.png");
 private MeuJButton mjb = new MeuJButton("...", icone);
 private List<Integer> pks;
 private String sql;
 private boolean podeHabilitar;
 private boolean obrigatorio;
 private String dica;

 public MeuCampoDBComboBox(final Class tela, String sql, boolean podeHabilitar, boolean obrigatorio, String dica) {
 this.podeHabilitar = podeHabilitar;
 this.obrigatorio = obrigatorio;
 this.dica = dica;
 setLayout(new FlowLayout());
 add(jcb);
 add(mjb);
 jcb.addFocusListener(this);
 preencher(sql);
 mjb.addActionListener(new ActionListener() {
 @Override
 public void actionPerformed(ActionEvent ae) {
 try {
 Method metodoGetTela = tela.getMethod("getTela", new Class[]{});
 metodoGetTela.invoke(null, new Object[]{});
 } catch (Exception e) {
 e.printStackTrace();
 }
 }
 });
 }

 public void preencher(String sql) {
 try {
 this.sql = sql;
 pks = new ArrayList();
 jcb.removeAllItems();
 ResultSet rs = Conexao.getConexao().createStatement().executeQuery(sql);
 while (rs.next()) {
 pks.add(rs.getInt(1));
 jcb.addItem(rs.getString(2));
 }
 } catch (Exception e) {
 JOptionPane.showMessageDialog(null, "Não foi possível preencher o MeuCampoDBComboBox");
 e.printStackTrace();

 }
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
 public boolean eVazio() {
 return (jcb.getSelectedIndex() == -1);
 }

 @Override
 public String getDica() {
 return dica;
 }

 @Override
 public void habilitar(boolean status) {
 jcb.setEnabled(podeHabilitar && status);
 mjb.setEnabled(podeHabilitar && status);
 }

 @Override
 public void limpar() {
 jcb.setSelectedIndex(-1);
 }

 @Override
 public Object getValor() {
 return pks.get(jcb.getSelectedIndex());
 }

 @Override
 public void setValor(Object valor) {
 int v = (Integer) valor;
 for (int i = 0; i < pks.size(); i++) {
 if (pks.get(i) == v) {
 jcb.setSelectedIndex(i);
 return;
 }
 }
 JOptionPane.showMessageDialog(null, dica + " (" + v + ") não encontrado(a)");
 }

 public String getValorTexto() {
 return (String) jcb.getSelectedItem();
 }

 public void addItemListener(ItemListener il) {
 jcb.addItemListener(il);
 }

 @Override
 public void focusGained(FocusEvent fe) {
 preencher(sql);
 }

 @Override
 public void focusLost(FocusEvent fe) {
 //preencher(getValorTexto());
 }
 }
 */
