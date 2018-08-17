package bd;

import javax.swing.*;
import java.sql.*;

public class Conexao {

    public static Connection conexao;

    public static Connection getConexao() {

        try {
            if (conexao == null) {
                Class.forName("org.firebirdsql.jdbc.FBDriver");
                conexao = DriverManager.getConnection("jdbc:firebirdsql://localhost/" + System.getProperty("user.dir") + "/BANCOFLORICULTURA.FDB", "SYSDBA", "masterkey");
            }
            return conexao;
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Não foi possível encontrar o driver de acesso ao banco de dados.");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Não foi possível conectar com  o banco de dados");
            e.printStackTrace();
            return null;
        }
    }

    public static ResultSet consultaBD(String sql) {
        try {
            Statement st = getConexao().createStatement();
            return st.executeQuery(sql);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Não foi possível consultar o Banco de Dados.");
            e.printStackTrace();
            return null;
        }
    }
}
