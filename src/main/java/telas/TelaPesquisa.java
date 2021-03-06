package telas;

import bd.Conexao;

import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.util.Vector;

public class TelaPesquisa extends JInternalFrame implements InternalFrameListener, MouseListener {

    private static TelaPesquisa telaPesquisa = null;
    private String sql;
    private String[] titulos;
    private TelaCadastro tela;
    private JTable tabela;
    private DefaultTableModel dtm = new DefaultTableModel();

    public TelaPesquisa(String tituloJanela, String sql, String[] titulos, TelaCadastro tela) {
        super(tituloJanela, false, true, false, false);
        this.sql = sql;
        this.titulos = titulos;
        this.tela = tela;
        tabela = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;//Esta tabela não pode ser editada pois é uma consulta
            }
        };
        tabela.setModel(dtm); //Define o modelo da tabela
        for (int i = 0; i < titulos.length; i++) {
            dtm.addColumn(titulos[i]);
        }
        JScrollPane jsp = new JScrollPane(tabela);
        getContentPane().add(jsp);
        preencher();
        pack();
        Dimension tamanhoTela = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((tamanhoTela.width - getWidth()) / 2, ((tamanhoTela.height - getHeight())) / 2);
        setVisible(true);
        tabela.addMouseListener(this);
        addInternalFrameListener(this);
    }

    public static void getTela(String tituloJanela, String sql, String[] titulos, TelaCadastro tela) {
        if (telaPesquisa == null) {
            telaPesquisa = new TelaPesquisa(tituloJanela, sql, titulos, tela);
            TelaSistema.jDesktopPane.add(telaPesquisa);//Adiciona a tela de pesquisa no jDesktopPane
        }
        TelaSistema.jDesktopPane.setSelectedFrame(telaPesquisa);
        TelaSistema.jDesktopPane.moveToFront(telaPesquisa);
    }

    private void preencher() {
        try {
            Vector vetor;
            ResultSet rs = Conexao.getConexao().createStatement().executeQuery(sql);
            while (rs.next()) {
                vetor = new Vector(); // Instancia um novo Vector sobre vetor para não ocorrer a duplicação de valores, pois cada linha seria um objeto
                for (int i = 1; i <= titulos.length; i++) {
                    vetor.add(rs.getString(i));
                }
                dtm.addRow(vetor);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao preencher tela de pesquisa");
            e.printStackTrace();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            String pk = (String) tabela.getValueAt(tabela.getSelectedRow(), 0);
            tela.consultarBD(Integer.parseInt(pk));
            dispose();
            TelaSistema.jDesktopPane.remove(this);
            telaPesquisa = null;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void internalFrameOpened(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameClosing(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameClosed(InternalFrameEvent e) {
        TelaSistema.jDesktopPane.remove(telaPesquisa);
        telaPesquisa = null;
    }

    @Override
    public void internalFrameIconified(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameDeiconified(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameActivated(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameDeactivated(InternalFrameEvent e) {
    }
}
