package gestores;

import entidades.Produto;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Classe responsável pela gestão de produtos no sistema.
 * 
 * Centraliza operações como cadastro, remoção, busca e listagem
 * de produtos, mantendo uma lista interna de todos os produtos cadastrados.
 */
public class GestaoProduto {

    /** Scanner para leitura de dados do usuário */
    private final Scanner input = new Scanner(System.in);
    
    /** Lista que armazena todos os produtos cadastrados */
    private final List<Produto> listaProdutos = new ArrayList<>();
    
    // ====================== Métodos principais ======================

    /**
     * Cadastra um novo produto no sistema.
     * Solicita dados do usuário via console: nome, preço e quantidade.
     * Cria um objeto Produto e adiciona à lista interna.
     */
    public void cadastrar() {
        try {
            System.out.println("\n=== Cadastro de Produto ===");
            System.out.print("Nome: ");
            String nome = input.nextLine();

            System.out.print("Preço: ");
            double preco = Double.parseDouble(input.nextLine());

            System.out.print("Quantidade: ");
            int quantidade = Integer.parseInt(input.nextLine());

            listaProdutos.add(new Produto(nome, preco, quantidade));
            System.out.println("\nProduto cadastrado com sucesso!");
        } catch (NumberFormatException e) {
            System.out.println("Valor inválido! Operação cancelada.");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar produto: " + e.getMessage());
        }
    }
    
    /**
     * Remove um produto existente.
     * Solicita confirmação antes de removê-lo da lista.
     */
    public void remover() {
        try {
            System.out.print("Informe o ID do produto a ser removido: ");
            String idBusca = input.nextLine();

            Produto produtoRemover = listaProdutos.stream()
                    .filter(p -> p.getId().equals(idBusca))
                    .findFirst()
                    .orElse(null);

            if (produtoRemover == null) {
                System.out.println("Produto não encontrado!");
                return;
            }

            System.out.print("Tem certeza que deseja remover o Produto " + produtoRemover.getNome() + "? (s/n): ");
            char confirmacao = input.next().charAt(0);
            input.nextLine(); // limpa buffer do Scanner

            if (confirmacao == 's' || confirmacao == 'S') {
                listaProdutos.remove(produtoRemover);
                System.out.println("Produto removido com sucesso!");
            } else {
                System.out.println("Remoção cancelada.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Operação cancelada.");
            input.nextLine(); // limpa buffer
        } catch (Exception e) {
            System.out.println("Erro ao remover produto: " + e.getMessage());
        }
    }

    /**
     * Busca um produto pelo ID informado pelo usuário.
     * Exibe o produto encontrado ou mensagem caso não exista.
     */
    public void buscar() {
        System.out.print("Entre com o ID do Produto: ");
        String id = input.nextLine();

        Produto produto = listaProdutos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (produto == null) {
            System.out.println("Produto não encontrado.");
        } else {
            System.out.println(produto);
        }
    }
    
    /**
     * Lista todos os produtos cadastrados.
     * Imprime cada produto utilizando o método toString da classe Produto.
     */
    public void listar() {
        System.out.println("\n=== Lista de Produtos ===");
        if (listaProdutos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }

        for (Produto p : listaProdutos) {
            System.out.println(p);
            System.out.println();
        }
    }
    
    /**
     * Lista os produtos de forma enumerada, mostrando nome e quantidade disponível.
     */
    public void listarEnumerado() {
        System.out.println("\n=== Lista de Produtos ===");
        if (listaProdutos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }

        int i = 0;
        for (Produto p : listaProdutos) {
            i++;
            System.out.println(i + ". " + p.getNome() + " | " + p.getQuantidade() + " unidades disponíveis.");
        }
        System.out.println();
    }

    /**
     * Retorna a lista de produtos cadastrados.
     * @return lista de produtos
     */
    public List<Produto> getListaProdutos() {
        return listaProdutos;
    }
}
