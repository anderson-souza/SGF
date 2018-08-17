package dao;

import bd.Conexao;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.Statement;

public class DaoPadrao {

    public Integer pegaGenerator(String nomeGenerator) {
        try {
            String sql = "select gen_id(" + nomeGenerator + ", 1) from RDB$DATABASE";
            Statement ps = Conexao.getConexao().createStatement();
            ResultSet rs = ps.executeQuery(sql);
            rs.next();
            return rs.getInt(1);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível obter o generator");
            return null; //Indica erro
        }
    }
}
