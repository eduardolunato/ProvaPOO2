package controleProduto.Excecoes;

public class ProdutoNaoEncontradoVendaException extends Exception {
    public ProdutoNaoEncontradoVendaException(int codigo) {
        super("Não foi possível encontrar nenhum produto pelo código: " + codigo);
    }   
}
