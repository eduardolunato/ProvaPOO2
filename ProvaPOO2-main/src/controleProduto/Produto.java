package controleProduto;




public class Produto  {

    public  int _codigo;
    public String _nome;
    public double _valor;
    public int _quantidadeEmEstoque;

    public Produto(int codigo, String nome, double valor, int quantidadeEmEstoque) {

        
        _codigo = codigo;
        _nome = nome;
        _valor = valor;
        _quantidadeEmEstoque = quantidadeEmEstoque;
    }


    public int getCodigo() {
        return _codigo;
    }


    public String getNome() {
        return _nome;
    }

    public double getValor() {
        return _valor;
    }

    public int getquantidadeEmEstoque() {
        return _quantidadeEmEstoque;
    }    


    //public abstract String getTipo();

    @Override
    public String toString() {
        return getNome() + " [codigo=" + _codigo + ", nome=" + _nome + ", valor=" + _valor + ", quantidade=" + _quantidadeEmEstoque + "]";
    }


    

}
