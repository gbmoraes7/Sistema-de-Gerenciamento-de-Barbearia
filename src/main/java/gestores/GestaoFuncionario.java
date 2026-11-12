package gestores;

import entidades.Funcionario;
import entidades.enums.CargoFuncionario;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Classe responsável pela gestão de funcionários no sistema.
 * 
 * Centraliza operações como cadastro, alteração, remoção e listagem
 * de funcionários, mantendo uma lista interna de todos os funcionários cadastrados.
 */
public class GestaoFuncionario {
    
    /** Lista que armazena todos os funcionários do sistema */
    private final List<Funcionario> listaFuncionario = new ArrayList<>();
    
    /** Scanner para leitura de dados do usuário */
    private final Scanner input = new Scanner(System.in);

    // ====================== Métodos principais ======================

    /**
     * Cadastra um novo funcionário no sistema.
     * Solicita dados do usuário via console, incluindo cargo e salário,
     * cria um objeto Funcionario e adiciona à lista interna.
     */
    public void cadastrar() {
        try {
            System.out.println("\n=== CADASTRO FUNCIONÁRIO ===");
            System.out.print("Nome completo: ");
            String nome = input.nextLine();
            System.out.print("Endereço: ");
            String endereco = input.nextLine();
            System.out.print("Telefone: ");
            String telefone = input.nextLine();
            System.out.print("E-mail: ");
            String email = input.nextLine();
            System.out.print("CPF: ");
            String cpf = input.nextLine();

            // Exibe cargos disponíveis a partir do enum
            System.out.println("\nCargos disponíveis:");
            for (CargoFuncionario c : CargoFuncionario.values()) {
                System.out.println("- " + c);
            }
            System.out.print("Cargo: ");
            CargoFuncionario cargo = CargoFuncionario.valueOf(input.nextLine().toUpperCase());

            System.out.print("Salário: ");
            double salario = input.nextDouble();
            input.nextLine(); // limpa buffer do Scanner

            listaFuncionario.add(new Funcionario(nome, endereco, telefone, email, cpf, cargo, salario));
            System.out.println("\nFuncionário cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Cargo inválido! Cadastro cancelado.");
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida! Cadastro cancelado.");
            input.nextLine(); // limpa buffer
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar funcionário: " + e.getMessage());
        }
    }

    /**
     * Altera os dados de um funcionário existente.
     * Permite alterar todos os atributos, mantendo os valores atuais
     * caso o usuário pressione ENTER.
     */
    public void alterar() {
        System.out.print("Informe o CPF do funcionário a ser alterado: ");
        String cpfBusca = input.nextLine();

        Funcionario funcionario = listaFuncionario.stream()
                .filter(f -> f.getCpf().equals(cpfBusca))
                .findFirst()
                .orElse(null);

        if (funcionario == null) {
            System.out.println("Funcionário não encontrado!");
            return;
        }

        System.out.println("\n=== Alterar dados do funcionário ===");
        System.out.println("(Pressione ENTER para manter o valor atual)");

        java.util.function.Function<String, String> lerOuManter = (rotulo) -> {
            System.out.print(rotulo + ": ");
            String entrada = input.nextLine();
            return entrada.isEmpty() ? null : entrada;
        };

        try {
            String novoNome = lerOuManter.apply("Nome completo (" + funcionario.getNome() + ")");
            if (novoNome != null) funcionario.setNome(novoNome);

            String novoEndereco = lerOuManter.apply("Endereço (" + funcionario.getEndereco() + ")");
            if (novoEndereco != null) funcionario.setEndereco(novoEndereco);

            String novoTelefone = lerOuManter.apply("Telefone (" + funcionario.getTelefone() + ")");
            if (novoTelefone != null) funcionario.setTelefone(novoTelefone);

            String novoEmail = lerOuManter.apply("E-mail (" + funcionario.getEmail() + ")");
            if (novoEmail != null) funcionario.setEmail(novoEmail);

            String novoCpf = lerOuManter.apply("CPF (" + funcionario.getCpf() + ")");
            if (novoCpf != null) funcionario.setCpf(novoCpf);

            // Atualiza cargo com validação de enum
            System.out.print("Cargo atual (" + funcionario.getCargo() + ") [ENTER para manter]: ");
            String novoCargoStr = input.nextLine();
            if (!novoCargoStr.isEmpty()) {
                funcionario.setCargo(CargoFuncionario.valueOf(novoCargoStr.toUpperCase()));
            }

            // Atualiza salário com validação de número
            System.out.print("Salário atual (" + funcionario.getSalario() + ") [ENTER para manter]: ");
            String novoSalarioStr = input.nextLine();
            if (!novoSalarioStr.isEmpty()) {
                funcionario.setSalario(Double.parseDouble(novoSalarioStr));
            }

            System.out.println("\nDados atualizados com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Entrada inválida. Alterações de cargo ou salário não aplicadas.");
        } catch (Exception e) {
            System.out.println("Erro ao alterar funcionário: " + e.getMessage());
        }
    }

    /**
     * Remove um funcionário existente.
     * Solicita confirmação antes de remover o funcionário da lista.
     */
    public void remover() {
        System.out.print("Informe o ID do funcionário a ser removido: ");
        String idBusca = input.nextLine();

        Funcionario funcionarioRemover = listaFuncionario.stream()
                .filter(f -> f.getId().equals(idBusca))
                .findFirst()
                .orElse(null);

        if (funcionarioRemover == null) {
            System.out.println("Funcionário não encontrado!");
            return;
        }

        try {
            System.out.print("Tem certeza que deseja remover o funcionário " + funcionarioRemover.getNome() + "? (s/n): ");
            char confirmacao = input.next().charAt(0);
            input.nextLine(); // limpa buffer do Scanner

            if (confirmacao == 's' || confirmacao == 'S') {
                listaFuncionario.remove(funcionarioRemover);
                System.out.println("Funcionário removido com sucesso!");
            } else {
                System.out.println("Remoção cancelada.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Remoção cancelada.");
            input.nextLine(); // limpa buffer
        }
    }

    /**
     * Busca um funcionário pelo ID e exibe seus dados.
     */
    public void buscar() {
        System.out.print("Entre com o ID do funcionário: ");
        String id = input.nextLine();

        Funcionario funcionario = listaFuncionario.stream()
                .filter(f -> f.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (funcionario == null) {
            System.out.println("Funcionário não encontrado.");
        } else {
            System.out.println(funcionario);
        }
    }

    /**
     * Lista todos os funcionários cadastrados.
     */
    public void listar() {
        if (listaFuncionario.isEmpty()) {
            System.out.println("Nenhum funcionário cadastrado.");
            return;
        }

        System.out.println("\n=== Lista de Funcionários ===");
        listaFuncionario.forEach(f -> {
            System.out.println(f);
            System.out.println();
        });
    }

    /**
     * Lista barbeiros disponíveis em formato simplificado.
     * @param lista Lista de barbeiros a ser exibida
     */
    public static void listarBarbeirosSimplificado(List<Funcionario> lista) {
        System.out.println("\n=== Lista de Barbeiros Disponíveis ===");
        int i = 1;
        for (Funcionario f : lista) {
            System.out.println(i++ + ". " + f.getNome());
        }
    }

    /**
     * Retorna a lista de barbeiros cadastrados no sistema.
     * @return Lista de funcionários com cargo BARBEIRO
     */
    public List<Funcionario> getBarbeirosCadastrados() {
        return listaFuncionario.stream()
                .filter(f -> f.getCargo() == CargoFuncionario.BARBEIRO)
                .toList();
    }

    /**
     * Lista barbeiros cadastrados enumerados.
     */
    public void listarBarbeirosEnumerado() {
        List<Funcionario> lista = getBarbeirosCadastrados();
        if (lista.isEmpty()) {
            System.out.println("Nenhum barbeiro cadastrado.");
            return;
        }

        System.out.println("\n=== Lista de Barbeiros ===");
        int i = 1;
        for (Funcionario f : lista) {
            System.out.println(i++ + ". " + f.getNome());
        }
    }

    /**
     * Retorna a lista completa de funcionários.
     * @return lista de funcionários
     */
    public List<Funcionario> getListaFuncionario() {
        return listaFuncionario;
    }
}
