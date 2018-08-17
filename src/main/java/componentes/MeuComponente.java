package componentes;

public interface MeuComponente {

    void limpar();

    boolean eVazio();

    boolean eObrigatorio();

    boolean eValido();

    String getDica();

    void habilitar(boolean status);

    Object getValor();

    void setValor(Object valor);
}
