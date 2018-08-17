package pojo;

import java.math.BigDecimal;

/**
 * @author Anderson
 */
public class Moveis {

    private int codmov;
    private String desmov;
    private int qtdmov;
    private BigDecimal vlralu;
    private Cor cor = new Cor();
    private SubCategoria subCategoria = new SubCategoria();
    private String sitmov;

    public String getSitmov() {
        return sitmov;
    }

    public void setSitmov(String sitmov) {
        this.sitmov = sitmov;
    }

    public int getCodmov() {
        return codmov;
    }

    public void setCodmov(int codmov) {
        this.codmov = codmov;
    }

    public String getDesmov() {
        return desmov;
    }

    public void setDesmov(String desmov) {
        this.desmov = desmov;
    }

    public int getQtdmov() {
        return qtdmov;
    }

    public void setQtdmov(int qtdmov) {
        this.qtdmov = qtdmov;
    }

    public BigDecimal getVlralu() {
        return vlralu;
    }

    public void setVlralu(BigDecimal vlralu) {
        this.vlralu = vlralu;
    }

    public Cor getCor() {
        return cor;
    }

    public void setCor(Cor cor) {
        this.cor = cor;
    }

    public SubCategoria getSubCategoria() {
        return subCategoria;
    }

    public void setSubCategoria(SubCategoria subCategoria) {
        this.subCategoria = subCategoria;
    }

}
