package pojo;

/**
 * @author Anderson
 */
public class SubCategoria {

    private int codsct;
    private String dessct;
    private Categoria categoria = new Categoria();
    private String sitsct;

    public String getSitsct() {
        return sitsct;
    }

    public void setSitsct(String sitsct) {
        this.sitsct = sitsct;
    }

    public int getCodsct() {
        return codsct;
    }

    public void setCodsct(int codsct) {
        this.codsct = codsct;
    }

    public String getDessct() {
        return dessct;
    }

    public void setDessct(String dessct) {
        this.dessct = dessct;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

}
