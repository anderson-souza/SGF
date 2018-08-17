package pojo;

import java.math.BigDecimal;

/**
 * @author Anderson
 */
public class ItemCompra {

    private int coditc;
    private Compra codcpr = new Compra();
    private Produto codpro = new Produto();
    private Moveis codmov = new Moveis();
    private int qtdcpr;
    private BigDecimal vlruni;
    private BigDecimal vlrdes;

    public BigDecimal getVlrdes() {
        return vlrdes;
    }

    public void setVlrdes(BigDecimal vlrdes) {
        this.vlrdes = vlrdes;
    }

    public int getCoditc() {
        return coditc;
    }

    public void setCoditc(int coditc) {
        this.coditc = coditc;
    }

    public int getQtdcpr() {
        return qtdcpr;
    }

    public void setQtdcpr(int qtdcpr) {
        this.qtdcpr = qtdcpr;
    }

    public BigDecimal getVlruni() {
        return vlruni;
    }

    public void setVlruni(BigDecimal vlruni) {
        this.vlruni = vlruni;
    }

    public Compra getCodcpr() {
        return codcpr;
    }

    public void setCodcpr(Compra codcpr) {
        this.codcpr = codcpr;
    }

    public Produto getCodpro() {
        return codpro;
    }

    public void setCodpro(Produto codpro) {
        this.codpro = codpro;
    }

    public Moveis getCodmov() {
        return codmov;
    }

    public void setCodmov(Moveis codmov) {
        this.codmov = codmov;
    }

}
