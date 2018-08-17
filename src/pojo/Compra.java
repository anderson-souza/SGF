package pojo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Anderson
 */
public class Compra {

    private int codcpr;
    private Fornecedor codfor = new Fornecedor();
    private CondicaoPagamento codpgt = new CondicaoPagamento();
    private Date datcpr;
    private String numdoc;
    private BigDecimal vlrcpr;
    private BigDecimal vlrfrt;
    private BigDecimal vlrdes;
    private String stacpr;
    private String sitcpr;
    private List<ItemCompra> itens = new ArrayList();

    public List<ItemCompra> getItens() {
        return itens;
    }

    public void setItens(List<ItemCompra> itens) {
        this.itens = itens;
    }

    public BigDecimal getVlrfrt() {
        return vlrfrt;
    }

    public void setVlrfrt(BigDecimal vlrfrt) {
        this.vlrfrt = vlrfrt;
    }

    public String getStacpr() {
        return stacpr;
    }

    public void setStacpr(String stacpr) {
        this.stacpr = stacpr;
    }

    public String getNumdoc() {
        return numdoc;
    }

    public void setNumdoc(String numdoc) {
        this.numdoc = numdoc;
    }

    public CondicaoPagamento getCodpgt() {
        return codpgt;
    }

    public void setCodpgt(CondicaoPagamento codpgt) {
        this.codpgt = codpgt;
    }

    public BigDecimal getVlrdes() {
        return vlrdes;
    }

    public void setVlrdes(BigDecimal vlrdes) {
        this.vlrdes = vlrdes;
    }

    public int getCodcpr() {
        return codcpr;
    }

    public void setCodcpr(int codcpr) {
        this.codcpr = codcpr;
    }

    public Fornecedor getCodfor() {
        return codfor;
    }

    public void setCodfor(Fornecedor codfor) {
        this.codfor = codfor;
    }

    public Date getDatcpr() {
        return datcpr;
    }

    public void setDatcpr(Date datcpr) {
        this.datcpr = datcpr;
    }

    public BigDecimal getVlrcpr() {
        return vlrcpr;
    }

    public void setVlrcpr(BigDecimal vlrcpr) {
        this.vlrcpr = vlrcpr;
    }

    public String getSitcpr() {
        return sitcpr;
    }

    public void setSitcpr(String sitcpr) {
        this.sitcpr = sitcpr;
    }

}
