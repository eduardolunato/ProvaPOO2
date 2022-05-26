package controleProduto.Excecoes;

public class ProdutoNaoEncontradoBuscaException extends Exception {
    public ProdutoNaoEncontradoBuscaException(int codigo) {
        super("Não foi possível encontrar nenhum produto pelo código: " + codigo);
    } 
}
