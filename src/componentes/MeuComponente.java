package componentes;

public interface MeuComponente {

    public void limpar();

    public boolean eVazio();

    public boolean eObrigatorio();

    public boolean eValido();

    public String getDica();

    public void habilitar(boolean status);

    public Object getValor();

    public void setValor(Object valor);
}
