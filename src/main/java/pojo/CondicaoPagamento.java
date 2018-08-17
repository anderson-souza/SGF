package pojo;

/**
 * @author Anderson
 */
public class CondicaoPagamento {

    private int id;
    private String descricao;
    private int numeroParcelas;
    private int prazoParcelas;
    private int diasCarencia;
    private String situacao;

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getNumeroParcelas() {
        return numeroParcelas;
    }

    public void setNumeroParcelas(int numeroParcelas) {
        this.numeroParcelas = numeroParcelas;
    }

    public int getPrazoParcelas() {
        return prazoParcelas;
    }

    public void setPrazoParcelas(int prazoParcelas) {
        this.prazoParcelas = prazoParcelas;
    }

    public int getDiasCarencia() {
        return diasCarencia;
    }

    public void setDiasCarencia(int diasCarencia) {
        this.diasCarencia = diasCarencia;
    }

}
