package pojo;

/**
 * @author Anderson
 */
public class Estado {

    private int codest;
    private Pais pais = new Pais();
    private String nomest;
    private String sigest;
    private String obsest;
    private String sitest;

    public String getSitest() {
        return sitest;
    }

    public void setSitest(String sitest) {
        this.sitest = sitest;
    }

    public int getCodest() {
        return codest;
    }

    public void setCodest(int codest) {
        this.codest = codest;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public String getNomest() {
        return nomest;
    }

    public void setNomest(String nomest) {
        this.nomest = nomest;
    }

    public String getSigest() {
        return sigest;
    }

    public void setSigest(String sigest) {
        this.sigest = sigest;
    }

    public String getObsest() {
        return obsest;
    }

    public void setObsest(String obsest) {
        this.obsest = obsest;
    }

}
