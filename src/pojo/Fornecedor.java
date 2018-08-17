package pojo;

import java.util.Date;

/**
 * @author Anderson
 */
public class Fornecedor {

    private int codfor;
    private String tipfor;
    private String nomraz;
    private String apefan;
    private String rgie;
    private Date datnas;
    private String cpfcnpj;
    private String cepfor;
    private String endfor;
    private String numend;
    private String cplend;
    private String telfor;
    private String celfor;
    private String emailfor;
    private String obsfor;
    private Cidade cidade = new Cidade();
    private String sitfor;

    public String getSitfor() {
        return sitfor;
    }

    public void setSitfor(String sitfor) {
        this.sitfor = sitfor;
    }

    public int getCodfor() {
        return codfor;
    }

    public void setCodfor(int codfor) {
        this.codfor = codfor;
    }

    public String getTipfor() {
        return tipfor;
    }

    public void setTipfor(String tipfor) {
        this.tipfor = tipfor;
    }

    public String getNomraz() {
        return nomraz;
    }

    public void setNomraz(String nomraz) {
        this.nomraz = nomraz;
    }

    public String getApefan() {
        return apefan;
    }

    public void setApefan(String apefan) {
        this.apefan = apefan;
    }

    public String getRgie() {
        return rgie;
    }

    public void setRgie(String rgie) {
        this.rgie = rgie;
    }

    public Date getDatnas() {
        return datnas;
    }

    public void setDatnas(Date datnas) {
        this.datnas = datnas;
    }

    public String getCpfcnpj() {
        return cpfcnpj;
    }

    public void setCpfcnpj(String cpfcnpj) {
        this.cpfcnpj = cpfcnpj;
    }

    public String getCepfor() {
        return cepfor;
    }

    public void setCepfor(String cepfor) {
        this.cepfor = cepfor;
    }

    public String getEndfor() {
        return endfor;
    }

    public void setEndfor(String endfor) {
        this.endfor = endfor;
    }

    public String getNumend() {
        return numend;
    }

    public void setNumend(String numend) {
        this.numend = numend;
    }

    public String getCplend() {
        return cplend;
    }

    public void setCplend(String cplend) {
        this.cplend = cplend;
    }

    public String getTelfor() {
        return telfor;
    }

    public void setTelfor(String telfor) {
        this.telfor = telfor;
    }

    public String getCelfor() {
        return celfor;
    }

    public void setCelfor(String celfor) {
        this.celfor = celfor;
    }

    public String getEmailfor() {
        return emailfor;
    }

    public void setEmailfor(String emailfor) {
        this.emailfor = emailfor;
    }

    public String getObsfor() {
        return obsfor;
    }

    public void setObsfor(String obsfor) {
        this.obsfor = obsfor;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }
}
