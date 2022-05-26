
import java.util.InputMismatchException;
import java.util.Scanner;

import controleProduto.Produto;
import controleProduto.Excecoes.ProdutoNaoEncontradoBuscaException;
import controleProduto.Excecoes.ProdutoNaoEncontradoVendaException;
import controleProduto.Venda;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.DoubleSummaryStatistics;
import java.util.stream.Collectors;


public class App {
    
    private final static int TAMANHO_INICIAL_LISTAS = 100;
    private static Scanner scanner = new Scanner(System.in);
    private static Produto[] _produtos = new Produto[TAMANHO_INICIAL_LISTAS];
    static List<Venda> _vendas = new ArrayList<>();
    private static int _numeroProdutos = 0;
    private static int pesquisa = 0;
    
    public static void main(String[] args) throws Exception {
        boolean continuarExecutando = true;

        do {
            
            try {
                imprimirMenu();
                int opcao = lerOpcao();
                continuarExecutando = executarOpcao(opcao);
            } catch (Exception e) {
                System.out.println("Ocorreu um erro durante a operação: " + e.getMessage());
                continuarExecutando = true;
            }
        } while (continuarExecutando);
    }

    private static boolean executarOpcao(int opcao) throws Exception {
        switch (opcao) {
            case 1: {
                cadastrarProduto();
                break;
            }

            case 2: {
                consultarProduto();
                break;
            }

            case 3: {
                listarProdutos();
                break;
            }


            case 4: {
                relatoriodevendasporperiodo();
                break;
            }           

            case 5: {
                lancarVenda();
                break;
            }  


            case 0: {
                System.out.println("Saindo do sistema...");
                return false;
            }


            default: {
                System.out.println("Ainda não implementado!");
                break;
            }
        }

        return true;
    }

   


    private static void listarProdutos() {
        System.out.println("MENU > 3 - Listagem de produtos");



        for (int i = 0; i < _numeroProdutos; i++) {
            System.out.println(_produtos[i]);
        }
    }

    




    
    private static void adicionarProdutoNaLista(Produto produto) {
        if (_numeroProdutos == _produtos.length) {
            Produto[] novaLista = new Produto[_produtos.length ];
            
            // Copio os elementos da lista antiga para a nova lista.
            for (int i = 0; i < _produtos.length; i++) {
                novaLista[i] = _produtos[i];
            }

            // Substituo a lista antiga pela nova.
            _produtos = novaLista;
        }

        // Adiciona o produto a lista.
        _produtos[_numeroProdutos] = produto;
        _numeroProdutos++;
    }

    private static Produto consultarProduto() throws ProdutoNaoEncontradoBuscaException{
        System.out.println("Digite o codigo do produto: ");
        //percorre lista para encontrar produto
        pesquisa = Integer.parseInt(scanner.nextLine());
        for (Produto produto: _produtos) {
            if (produto != null && produto.getCodigo() == pesquisa ) {
                System.out.println("MENU > 2 - Consultar produto");
                System.out.println(produto);
                return (produto);
            }
        }
        // Quando produto não encontrado
        throw new ProdutoNaoEncontradoBuscaException(pesquisa);
    }




    private static void cadastrarProduto() throws InputMismatchException {
        

        System.out.println("MENU > 1 - Incluir produto");
        //System.out.println("Codigo: ");
       // int codigo = Integer.parseInt(scanner.nextLine()); 
        int codigo = _numeroProdutos+1; 
        System.out.println("Nome: ");
        String nome = scanner.nextLine();
        System.out.println("Valor: ");
        double valor = Double.parseDouble(scanner.nextLine());
        System.out.println("Estoque: ");
        int quantidadeEmEstoque = Integer.parseInt(scanner.nextLine()); 


        

        Produto produto = new Produto(codigo, nome, valor, quantidadeEmEstoque);
        adicionarProdutoNaLista(produto);
        
        
    }


    private static Venda lancarVenda() throws ProdutoNaoEncontradoVendaException{
        System.out.println("MENU > 5 - Realizar venda");
        System.out.println("Digite o código do produto vendido:");
        int produtoVendido = Integer.parseInt(scanner.nextLine());
        for (Produto produto: _produtos) {
            if (produto != null && produto.getCodigo() == produtoVendido) {
                System.out.println("Data da venda no formato dd/MM/yy: ");
                String datavenda = scanner.nextLine();
                System.out.println("Quantidade vendida: ");
                int quantidadeVendida = scanner.nextInt();

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yy");
                LocalDate dataVenda = LocalDate.parse(datavenda, dtf);
                //Salvando produto na lista
                Double valorVenda = produto.getValor()*quantidadeVendida;
                Venda venda = new Venda(dataVenda, produtoVendido, quantidadeVendida,valorVenda);
                _vendas.add(venda);
                System.out.println("Venda lançada com sucesso");
                scanner.nextLine();
                return(venda);
            }
        }
        throw new ProdutoNaoEncontradoVendaException(produtoVendido);
    }

    private static Venda relatoriodevendasporperiodo(){
        System.out.println("MENU > 4 - Vendas por período - detalhado");
        System.out.println("Digite a data inícial:");
        String dataInicio = scanner.nextLine();
        System.out.println("Digite a data final:");
        String dataFim = scanner.nextLine();

        // definindo a formatação da data
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yy");
        LocalDate dataInicioLocalDate = LocalDate.parse(dataInicio, dtf);
        LocalDate dataFimLocalDate = LocalDate.parse(dataFim, dtf);
        
        //Filtrando a lista de vendas pelo período
        _vendas.stream()
        .filter( venda-> 
        venda.getdataVenda().compareTo(dataInicioLocalDate) > -1 
            && venda.getdataVenda().compareTo(dataFimLocalDate) < 1 );
        DoubleSummaryStatistics resumovenda = _vendas.stream()
        .collect(Collectors.summarizingDouble(Venda::getvalorVenda));
        System.out.println("--------------------------------Vendas por Período------------------------------------");
        System.out.printf("%7s %20s %20s %20s\n", "Data Venda", "Produto", "Quantidade","Valor Total");
        System.out.println("--------------------------------------------------------------------------------------");
        for (Venda venda : _vendas) {
            System.out.printf( "%7s %20s %20s %20s\n",venda.getdataVenda(), venda.getprodutoVendido(), venda.getquantidadevendida(), venda.getvalorVenda());
            }
        System.out.println("----------------------------------Resumo Médio---------------------------------------");
        System.out.printf( "Menor valor %s - Média de valor %s - Maior valor %s\n",resumovenda.getMin(), resumovenda.getAverage(),resumovenda.getMax());

        return null;
        
    }



    private static void imprimirMenu() {

        System.out.println("\n************\n    MENU    \n************\n");
        System.out.println("1 - Incluir produto");
        System.out.println("2 - Consultar produto");
        System.out.println("3 - Listagem de produtos");
        System.out.println("4 - Vendas por período - detalhado");        
        System.out.println("5 - Realizar venda");
        System.out.println("0 - Sair");
        System.out.print("Opção: ");



    }

    private static boolean validarOpcaoMenu(int opcao) {
        return (opcao >= 0 && opcao <= 5 );
    }

    private static int lerOpcao() {
        int opcao = 0;
        do {
            System.out.println("Selecione a opção desejada: ");
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                if (!validarOpcaoMenu(opcao)) {
                    System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("Opção inválida!");
                scanner.nextLine();
            }
        } while (!validarOpcaoMenu(opcao));

        return opcao;
    }



}
