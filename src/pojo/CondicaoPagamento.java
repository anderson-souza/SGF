package pojo;

/**
 * @author Anderson
 */
public class CondicaoPagamento {

    private int codpgt;
    private String despgt;
    private int numpar;
    private int prapar;
    private int diacar;
    private String sitpgt;

    public String getSitpgt() {
        return sitpgt;
    }

    public void setSitpgt(String sitpgt) {
        this.sitpgt = sitpgt;
    }

    public int getCodpgt() {
        return codpgt;
    }

    public void setCodpgt(int codpgt) {
        this.codpgt = codpgt;
    }

    public String getDespgt() {
        return despgt;
    }

    public void setDespgt(String despgt) {
        this.despgt = despgt;
    }

    public int getNumpar() {
        return numpar;
    }

    public void setNumpar(int numpar) {
        this.numpar = numpar;
    }

    public int getPrapar() {
        return prapar;
    }

    public void setPrapar(int prapar) {
        this.prapar = prapar;
    }

    public int getDiacar() {
        return diacar;
    }

    public void setDiacar(int diacar) {
        this.diacar = diacar;
    }

}
