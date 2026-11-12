package menus;

import entidades.Agendamento;
import entidades.OrdemServico;
import java.util.InputMismatchException;
import java.util.Scanner;
import main.Sistema;

/**
 * Classe responsável por exibir e controlar todos os menus do sistema da barbearia.
 * 
 * Contém métodos para menus principais, menus de gestão (clientes, funcionários,
 * produtos, serviços, agendamentos, ordens de serviço) e menus financeiros.
 */
public class Menus {

    /** Scanner para leitura de dados do usuário */
    private final Scanner input = new Scanner(System.in);

    /**
     * Exibe o menu principal do sistema e redireciona para os submenus.
     * Permite navegação contínua até o usuário escolher sair.
     */
    public void menuPrincipal() {
        int op = -1;

        do {
            System.out.println("\n==== BARBEARIA - MENU PRINCIPAL ====");
            System.out.println("1 - Gerenciar Agendamentos");
            System.out.println("2 - Gerenciar Ordens de Serviço");
            System.out.println("3 - Realizar Venda");
            System.out.println("4 - Gerenciar Clientes");
            System.out.println("5 - Gerenciar Produtos");
            System.out.println("6 - Gerenciar Serviços");
            System.out.println("7 - Gerenciar Funcionários");
            System.out.println("8 - Gerenciamento Financeiro");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");

            op = lerOpcao();

            switch (op) {
                case 1 -> gerenciarAgendamentos();
                case 2 -> gerenciarOrdemServico();
                case 3 -> Sistema.getSistema().getGestaoOS().cadastrar();
                case 4 -> gerenciarClientes();
                case 5 -> gerenciarProdutos();
                case 6 -> gerenciarServicos();
                case 7 -> gerenciarFuncionarios();
                case 8 -> gerenciarFinanceiro();
                case 0 -> System.out.println("\nEncerrando sistema...");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }

        } while (op != 0);
    }

    // ========================= MÉTODOS AUXILIARES =========================

    /**
     * Lê uma opção inteira do usuário com validação.
     * Evita que entradas inválidas quebrem o menu.
     *
     * @return opção válida digitada pelo usuário
     */
    private int lerOpcao() {
        int opcao = -1;
        while (true) {
            try {
                opcao = input.nextInt();
                input.nextLine(); // limpar buffer
                break;
            } catch (InputMismatchException e) {
                System.out.print("Entrada inválida! Digite um número: ");
                input.nextLine(); // limpar buffer
            }
        }
        return opcao;
    }

    // ========================= MENUS =========================

    /** Menu de gerenciamento de agendamentos */
    private void gerenciarAgendamentos() {
        int op;
        do {
            System.out.println("\n==== MENU AGENDAMENTOS ====");
            System.out.println("1 - Cadastrar Agendamento");
            System.out.println("2 - Listar Agendamentos");
            System.out.println("3 - Remover Agendamento");
            System.out.println("4 - Alterar Status Agendamento");
            System.out.println("5 - Buscar por ID");
            System.out.println("6 - Agendamentos Secundários (Pilha)");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            op = lerOpcao();

            switch (op) {
                case 1 -> Sistema.getSistema().getGestaoAgendamento().cadastrar();
                case 2 -> Sistema.getSistema().getGestaoAgendamento().listar();
                case 3 -> Sistema.getSistema().getGestaoAgendamento().remover();
                case 4 -> Sistema.getSistema().getGestaoAgendamento().alterarStatus();
                case 5 -> Sistema.getSistema().getGestaoAgendamento().buscarPorId();
                case 6 -> gerenciarAgendamentosSecundarios();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }

        } while (op != 0);
    }

    /** Submenu de agendamentos secundários (a implementar) */
    private void gerenciarAgendamentosSecundarios() {
        System.out.println("Funcionalidade em desenvolvimento...");
    }

    /** Menu de gerenciamento de Ordens de Serviço */
    private void gerenciarOrdemServico() {
        int op;
        do {
            System.out.println("\n==== MENU ORDEM DE SERVIÇO ====");
            System.out.println("1 - Acessar Ordem de Serviço");
            System.out.println("2 - Listar Ordens de Serviço");
            System.out.println("3 - Buscar Ordens de Serviço por ID");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            op = lerOpcao();

            switch (op) {
                case 1 -> Sistema.getSistema().getGestaoOS().acessar();
                case 2 -> Sistema.getSistema().getGestaoOS().listar();
                case 3 -> Sistema.getSistema().getGestaoOS().buscarPorId();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }

        } while (op != 0);
    }

    /** Menu de gerenciamento de clientes */
    private void gerenciarClientes() {
        int op;
        do {
            System.out.println("\n==== MENU CLIENTES ====");
            System.out.println("1 - Cadastrar Cliente");
            System.out.println("2 - Editar Cliente");
            System.out.println("3 - Excluir Cliente");
            System.out.println("4 - Listar Clientes");
            System.out.println("5 - Buscar Cliente");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            op = lerOpcao();

            switch (op) {
                case 1 -> Sistema.getSistema().getGestaoCliente().cadastrar();
                case 2 -> Sistema.getSistema().getGestaoCliente().alterar();
                case 3 -> Sistema.getSistema().getGestaoCliente().remover();
                case 4 -> Sistema.getSistema().getGestaoCliente().listar();
                case 5 -> Sistema.getSistema().getGestaoCliente().buscar();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }

        } while (op != 0);
    }

    /** Menu de gerenciamento de funcionários */
    private void gerenciarFuncionarios() {
        int op;
        do {
            System.out.println("\n==== MENU FUNCIONÁRIOS ====");
            System.out.println("1 - Cadastrar Funcionário");
            System.out.println("2 - Editar Funcionário");
            System.out.println("3 - Excluir Funcionário");
            System.out.println("4 - Listar Funcionários");
            System.out.println("5 - Buscar Funcionário");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            op = lerOpcao();

            switch (op) {
                case 1 -> Sistema.getSistema().getGestaoFuncionario().cadastrar();
                case 2 -> Sistema.getSistema().getGestaoFuncionario().alterar();
                case 3 -> Sistema.getSistema().getGestaoFuncionario().remover();
                case 4 -> Sistema.getSistema().getGestaoFuncionario().listar();
                case 5 -> Sistema.getSistema().getGestaoFuncionario().buscar();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }

        } while (op != 0);
    }

    /** Menu de gerenciamento de produtos */
    private void gerenciarProdutos() {
        int op;
        do {
            System.out.println("\n==== MENU PRODUTOS ====");
            System.out.println("1 - Cadastrar Produto");
            System.out.println("2 - Excluir Produto");
            System.out.println("3 - Listar Produtos");
            System.out.println("4 - Buscar Produto");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            op = lerOpcao();

            switch (op) {
                case 1 -> Sistema.getSistema().getGestaoProduto().cadastrar();
                case 2 -> Sistema.getSistema().getGestaoProduto().remover();
                case 3 -> Sistema.getSistema().getGestaoProduto().listar();
                case 4 -> Sistema.getSistema().getGestaoProduto().buscar();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }

        } while (op != 0);
    }

    /** Menu de gerenciamento de serviços */
    private void gerenciarServicos() {
        int op;
        do {
            System.out.println("\n==== MENU SERVIÇOS ====");
            System.out.println("1 - Cadastrar Serviço");
            System.out.println("2 - Excluir Serviço");
            System.out.println("3 - Listar Serviços");
            System.out.println("4 - Buscar Serviço");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            op = lerOpcao();

            switch (op) {
                case 1 -> Sistema.getSistema().getGestaoServico().cadastrar();
                case 2 -> Sistema.getSistema().getGestaoServico().remover();
                case 3 -> Sistema.getSistema().getGestaoServico().listar();
                case 4 -> Sistema.getSistema().getGestaoServico().buscar();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }

        } while (op != 0);
    }

    /** Menu de gerenciamento financeiro (funcionalidades a implementar) */
    private void gerenciarFinanceiro() {
        int op;
        do {
            System.out.println("\n==== MENU FINANCEIRO ====");
            System.out.println("1 - Registrar Despesa");
            System.out.println("2 - Listar Despesas");
            System.out.println("3 - Emitir Relatório Diário");
            System.out.println("4 - Balanço Mensal");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            op = lerOpcao();

            switch (op) {
                case 1 -> registrarDespesa();
                case 2 -> listarDespesas();
                case 3 -> relatorioDiario();
                case 4 -> balancoMensal();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida.");
            }

        } while (op != 0);
    }

    private void registrarDespesa() {
        System.out.println("Funcionalidade em desenvolvimento...");
    }

    private void listarDespesas() {
        System.out.println("Funcionalidade em desenvolvimento...");
    }

    private void relatorioDiario() {
        System.out.println("Funcionalidade em desenvolvimento...");
    }

    private void balancoMensal() {
        System.out.println("Funcionalidade em desenvolvimento...");
    }

    /**
     * Submenu de operações específicas para uma Ordem de Serviço.
     * Permite adicionar produtos/serviços, definir pagamento e alterar status.
     *
     * @param OS Ordem de Serviço a ser manipulada
     */
    public void submenuOperacoesOS(OrdemServico OS) {
        int opcao;
        do {
            System.out.println("\n===== MENU DA ORDEM DE SERVIÇO =====");
            System.out.println("1 - Adicionar Serviços à OS");
            System.out.println("2 - Adicionar Produtos à OS");
            System.out.println("3 - Definir Forma de Pagamento");
            System.out.println("4 - Alterar Status da OS");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");

            opcao = lerOpcao();

            switch (opcao) {
                case 1 -> OS.adicionarServicosOS();
                case 2 -> OS.adicionarProdutosOS();
                case 3 -> OS.definirFormaPagamentoOS();
                case 4 -> OS.alterarStatusOS();
                case 0 -> System.out.println("Retornando...");
                default -> System.out.println("Opção inválida! Tente novamente.");
            }

        } while (opcao != 0);
    }
}
