package pojo;

import java.math.BigDecimal;

/**
 * @author Anderson
 */
public class Produto {

    private int codpro;
    private String despro;
    private Cor cor = new Cor();
    private SubCategoria subCategoria = new SubCategoria();
    private BigDecimal precus;
    private BigDecimal preven;
    private int qtdpro;
    private String sitpro;

    public String getSitpro() {
        return sitpro;
    }

    public void setSitpro(String sitpro) {
        this.sitpro = sitpro;
    }

    public int getCodpro() {
        return codpro;
    }

    public void setCodpro(int codpro) {
        this.codpro = codpro;
    }

    public String getDespro() {
        return despro;
    }

    public void setDespro(String despro) {
        this.despro = despro;
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

    public int getQtdpro() {
        return qtdpro;
    }

    public void setQtdpro(int qtdpro) {
        this.qtdpro = qtdpro;
    }

    public BigDecimal getPrecus() {
        return precus;
    }

    public void setPrecus(BigDecimal precus) {
        this.precus = precus;
    }

    public BigDecimal getPreven() {
        return preven;
    }

    public void setPreven(BigDecimal preven) {
        this.preven = preven;
    }

}
