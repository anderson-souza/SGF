package pojo;

/**
 * @author Anderson
 */
public class Cidade {

    private int codcid;
    private String nomcid;
    private Estado estado = new Estado();
    private String obscid;
    private String sitcid;

    public String getNomcid() {
        return nomcid;
    }

    public void setNomcid(String nomcid) {
        this.nomcid = nomcid;
    }

    public String getSitcid() {
        return sitcid;
    }

    public void setSitcid(String sitcid) {
        this.sitcid = sitcid;
    }

    public String getObscid() {
        return obscid;
    }

    public void setObscid(String obscid) {
        this.obscid = obscid;
    }

    public int getCodcid() {
        return codcid;
    }

    public void setCodcid(int codcid) {
        this.codcid = codcid;
    }

    public String getNomCid() {
        return nomcid;
    }

    public void setNomCid(String nomcid) {
        this.nomcid = nomcid;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

}
