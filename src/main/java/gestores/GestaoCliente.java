package gestores;

import entidades.Cliente;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Classe responsável pela gestão de clientes no sistema.
 * 
 * Centraliza operações como cadastro, alteração, remoção e listagem
 * de clientes, mantendo uma lista interna de todos os clientes cadastrados.
 */
public class GestaoCliente {
    
    /** Scanner para leitura de dados do usuário */
    private final Scanner sc = new Scanner(System.in);
    
    /** Lista que armazena todos os clientes do sistema */
    private final List<Cliente> listaCliente = new ArrayList<>();
    
    // ====================== Métodos principais ======================

    /**
     * Cadastra um novo cliente no sistema.
     * Solicita dados do usuário via console, cria um objeto Cliente
     * e adiciona à lista interna.
     */
    public void cadastrar() {
        try {
            System.out.println("\n=== Cadastrar dados do cliente ===");
            System.out.print("Nome completo: ");
            String nome = sc.nextLine();
            System.out.print("Endereço: ");
            String endereco = sc.nextLine();
            System.out.print("Telefone: ");
            String telefone = sc.nextLine();
            System.out.print("E-mail: ");
            String email = sc.nextLine();
            System.out.print("CPF: ");
            String cpf = sc.nextLine();
            
            listaCliente.add(new Cliente(nome, endereco, telefone, email, cpf));
            System.out.println("\nDados cadastrados com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }
    
    /**
     * Altera os dados de um cliente existente.
     * Busca o cliente pelo CPF informado. Caso seja encontrado,
     * solicita novos valores para cada atributo, permitindo que o usuário
     * pressione ENTER para manter o valor atual.
     */
    public void alterar() {
        System.out.print("Informe o CPF do cliente a ser alterado: ");
        String cpfBusca = sc.nextLine();

        Cliente cliente = listaCliente.stream()
                .filter(c -> c.getCpf().equals(cpfBusca))
                .findFirst()
                .orElse(null);

        if (cliente == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }

        System.out.println("\n=== Alterar dados do cliente ===");
        System.out.println("(Pressione ENTER para manter o valor atual)");

        java.util.function.Function<String, String> lerOuManter = (rotulo) -> {
            System.out.print(rotulo + ": ");
            String entrada = sc.nextLine();
            return entrada.isEmpty() ? null : entrada;
        };

        try {
            String novoNome = lerOuManter.apply("Nome completo (" + cliente.getNome() + ")");
            if (novoNome != null) cliente.setNome(novoNome);

            String novoEndereco = lerOuManter.apply("Endereço (" + cliente.getEndereco() + ")");
            if (novoEndereco != null) cliente.setEndereco(novoEndereco);

            String novoTelefone = lerOuManter.apply("Telefone (" + cliente.getTelefone() + ")");
            if (novoTelefone != null) cliente.setTelefone(novoTelefone);

            String novoEmail = lerOuManter.apply("E-mail (" + cliente.getEmail() + ")");
            if (novoEmail != null) cliente.setEmail(novoEmail);

            String novoCpf = lerOuManter.apply("CPF (" + cliente.getCpf() + ")");
            if (novoCpf != null) cliente.setCpf(novoCpf);

            System.out.println("\nDados atualizados com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao alterar cliente: " + e.getMessage());
        }
    }
    
    /**
     * Remove um cliente existente.
     * Solicita confirmação antes de remover o cliente da lista.
     */
    public void remover() {
        System.out.print("Informe o CPF do cliente a ser removido: ");
        String cpfBusca = sc.nextLine();

        Cliente clienteRemover = listaCliente.stream()
                .filter(c -> c.getCpf().equals(cpfBusca))
                .findFirst()
                .orElse(null);

        if (clienteRemover == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }

        System.out.print("Tem certeza que deseja remover o cliente " + clienteRemover.getNome() + "? (s/n): ");
        char confirmacao;
        try {
            confirmacao = sc.next().charAt(0);
            sc.nextLine(); // limpa buffer
            if (confirmacao == 's' || confirmacao == 'S') {
                listaCliente.remove(clienteRemover);
                System.out.println("Cliente removido com sucesso!");
            } else {
                System.out.println("Remoção cancelada.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Remoção cancelada.");
            sc.nextLine(); // limpa buffer
        }
    }
    
    /**
     * Lista todos os clientes cadastrados.
     * Caso a lista esteja vazia, exibe uma mensagem informando.
     */
    public void listar() {
        if (listaCliente.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }

        System.out.println("\n=== Lista de Clientes ===");
        listaCliente.forEach(cliente -> {
            System.out.println(cliente);
            System.out.println();
        });
    }
    
    /**
     * Busca um cliente pelo CPF.
     * Exibe os dados do cliente se encontrado.
     * 
     * @return Cliente encontrado ou null se não existir
     */
    public Cliente buscar() {
        System.out.print("Entre com o CPF do cliente: "); 
        String cpf = sc.nextLine();
        
        Cliente cliente = listaCliente.stream()
                .filter(c -> c.getCpf().equals(cpf))
                .findFirst()
                .orElse(null);
        
        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
        } else {
            System.out.println(cliente);
        }
        return cliente;
    }

    /**
     * Retorna a lista de clientes cadastrados.
     * @return lista de clientes
     */
    public List<Cliente> getListaCliente() {
        return listaCliente;
    }
}
