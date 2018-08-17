package pojo;

/**
 * @author Anderson
 */
public class Cor {

    private int id;
    private String nome;
    private String situacao;

    public String getNome() {
        return nome;
    }

    public void setNome(String NomCor) {
        this.nome = NomCor;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public int getCodCor() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesCor() {
        return nome;
    }

    public void setDesCor(String NomCor) {
        this.nome = NomCor;
    }

}
