package gestores;

import entidades.Servico;
import java.time.Duration;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Classe responsável pela gestão de serviços no sistema.
 * 
 * Centraliza operações como cadastro, remoção, busca e listagem
 * de serviços, mantendo uma lista interna de serviços cadastrados.
 */
public class GestaoServico {

    /** Scanner para leitura de dados do usuário */
    private final Scanner input = new Scanner(System.in);

    /** Lista que armazena todos os serviços cadastrados */
    private final List<Servico> listaServicos = new ArrayList<>();

    // ====================== Métodos principais ======================

    /**
     * Cadastra um novo serviço no sistema.
     * Solicita dados do usuário via console: nome, preço, duração e tipo (simples ou completo).
     * Adiciona o serviço à lista interna.
     */
    public void cadastrar() {
        try {
            System.out.println("\n=== Cadastro de Serviço ===");
            System.out.print("Nome: ");
            String nome = input.nextLine();

            System.out.print("Preço: ");
            double preco = Double.parseDouble(input.nextLine());

            System.out.println("Serviço simples ou completo:");
            System.out.println("1. Simples\n2. Completo");
            int resposta = Integer.parseInt(input.nextLine());
            boolean servicoCompleto = resposta != 1;

            System.out.print("Duração do serviço em minutos: ");
            int duracao = Integer.parseInt(input.nextLine());

            listaServicos.add(new Servico(nome, preco, duracao, servicoCompleto));
            System.out.println("\nServiço cadastrado com sucesso!");
        } catch (NumberFormatException | InputMismatchException e) {
            System.out.println("Entrada inválida! Operação cancelada.");
            input.nextLine(); // limpa buffer do Scanner
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar serviço: " + e.getMessage());
        }
    }

    /**
     * Remove um serviço existente pelo ID informado.
     * Solicita confirmação antes de removê-lo da lista.
     */
    public void remover() {
        try {
            System.out.print("Informe o ID do serviço a ser removido: ");
            String idBusca = input.nextLine();

            Servico servicoRemover = listaServicos.stream()
                    .filter(s -> s.getId().equals(idBusca))
                    .findFirst()
                    .orElse(null);

            if (servicoRemover == null) {
                System.out.println("Serviço não encontrado!");
                return;
            }

            System.out.print("Tem certeza que deseja remover o serviço " + servicoRemover.getNome() + "? (s/n): ");
            char confirmacao = input.next().charAt(0);
            input.nextLine(); // limpa buffer do Scanner

            if (confirmacao == 's' || confirmacao == 'S') {
                listaServicos.remove(servicoRemover);
                System.out.println("Serviço removido com sucesso!");
            } else {
                System.out.println("Remoção cancelada.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao remover serviço: " + e.getMessage());
        }
    }

    /**
     * Busca um serviço pelo ID informado pelo usuário.
     * Exibe o serviço encontrado ou mensagem caso não exista.
     */
    public void buscar() {
        System.out.print("Entre com o ID do serviço: ");
        String id = input.nextLine();

        Servico servico = listaServicos.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (servico == null) {
            System.out.println("Serviço não encontrado.");
        } else {
            System.out.println(servico);
        }
    }

    /**
     * Lista todos os serviços cadastrados de forma detalhada.
     */
    public void listar() {
        System.out.println("\n=== Lista de Serviços ===");
        if (listaServicos.isEmpty()) {
            System.out.println("Nenhum serviço cadastrado.");
            return;
        }

        for (Servico s : listaServicos) {
            System.out.println(s);
            System.out.println();
        }
    }

    /**
     * Lista todos os serviços cadastrados de forma enumerada e resumida.
     * @param servicos lista de serviços a ser listada (opcional)
     */
    public void listarEnumerado(List<Servico> servicos) {
        List<Servico> lista = servicos != null ? servicos : listaServicos;

        if (lista.isEmpty()) {
            System.out.println("Nenhum serviço cadastrado.");
            return;
        }

        System.out.println("\n=== Lista de Serviços ===");
        int i = 0;
        for (Servico s : lista) {
            i++;
            System.out.println(i + ". " + s.toStringSimplificado());
        }
    }

    /**
     * Lista todos os serviços cadastrados de forma enumerada resumida.
     */
    public void listarEnumerado() {
        listarEnumerado(null);
    }

    /**
     * Retorna a lista de serviços cadastrados.
     * @return lista de serviços
     */
    public List<Servico> getListaServicos() {
        return listaServicos;
    }

    /**
     * Calcula a duração total de uma lista de serviços.
     * @param listaServicos lista de serviços
     * @return duração total em Duration
     */
    public static Duration getDuracaoServicos(List<Servico> listaServicos) {
        Duration duracao = Duration.ZERO;
        for (Servico s : listaServicos) {
            duracao = duracao.plus(s.getDuracao());
        }
        return duracao;
    }

    /**
     * Calcula o valor total de uma lista de serviços.
     * @param listaServicos lista de serviços
     * @return valor total em double
     */
    public static double getValorServicos(List<Servico> listaServicos) {
        double valor = 0.0;
        for (Servico s : listaServicos) {
            valor += s.getPreco();
        }
        return valor;
    }
}
