package main;

import entidades.Item;
import entidades.Produto;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Classe responsável pelas operações de venda na loja.
 * 
 * Permite selecionar produtos, definir quantidades e gerar itens de venda.
 */
public class Loja {

    /** Scanner para leitura de dados do usuário */
    private final Scanner input = new Scanner(System.in);

    /**
     * Realiza uma venda de produtos.
     * 
     * O método exibe os produtos disponíveis, permite selecionar múltiplos produtos,
     * verifica a quantidade disponível e gera uma lista de itens.
     * 
     * @return lista de itens selecionados, ou null se houver erro de quantidade
     */
    public List<Item> realizarVenda() {
        List<Item> itens = new ArrayList<>();
        try {
            // Lista produtos disponíveis
            Sistema.getSistema().getGestaoProduto().listarEnumerado();
            char resposta = 's';

            while (resposta == 's' || resposta == 'S') {
                System.out.print("Selecione o produto (número): ");
                int posicao = input.nextInt() - 1;
                input.nextLine(); // limpa buffer do Scanner

                // Valida posição
                if (posicao < 0 || posicao >= Sistema.getSistema().getGestaoProduto().getListaProdutos().size()) {
                    System.out.println("Produto inválido. Tente novamente.");
                    continue;
                }

                Produto produto = Sistema.getSistema().getGestaoProduto().getListaProdutos().get(posicao);

                System.out.print("Qual a quantidade desejada? ");
                int quantidade = input.nextInt();
                input.nextLine(); // limpa buffer do Scanner

                if (quantidade <= 0) {
                    System.out.println("Quantidade deve ser maior que zero.");
                    continue;
                }

                if (quantidade > produto.getQuantidade()) {
                    System.out.println("Quantidade insuficiente. Produto possui " + produto.getQuantidade() + " unidades.");
                    continue;
                }

                Item item = new Item(produto, quantidade);
                itens.add(item);

                // Atualiza estoque
                produto.baixarQuantidade(quantidade);

                System.out.println("Produto " + produto.getNome() + " adicionado à venda.");
                System.out.print("Deseja adicionar mais algum produto? (s/n): ");
                resposta = input.next().charAt(0);
                input.nextLine(); // limpa buffer do Scanner
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Operação cancelada.");
            input.nextLine(); // limpa buffer do Scanner
            return null;
        } catch (Exception e) {
            System.out.println("Erro ao realizar venda: " + e.getMessage());
            return null;
        }

        return itens;
    }
}
