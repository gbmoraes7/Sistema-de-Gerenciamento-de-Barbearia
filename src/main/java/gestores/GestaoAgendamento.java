package gestores;

import entidades.Agendamento;
import entidades.Cliente;
import entidades.EstacaoAtendimento;
import entidades.Funcionario;
import entidades.Servico;
import entidades.enums.StatusAgendamento;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import main.Sistema;

/**
 * Classe responsável pela gestão dos agendamentos no sistema.
 * 
 * Esta classe centraliza operações de criação, listagem, remoção e alteração
 * de status de agendamentos, mantendo uma lista interna de todos os agendamentos realizados.
 */
public class GestaoAgendamento {

    /** Scanner para leitura de dados do usuário */
    private final Scanner input = new Scanner(System.in);

    /** Formatos de data e hora para entrada e exibição */
    private final DateTimeFormatter formatoInserirHora = DateTimeFormatter.ofPattern("HH:mm");
    private final DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HHhmm");
    private final DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /** Lista que armazena todos os agendamentos do sistema */
    private final List<Agendamento> agendamentos = new ArrayList<>();

    /**
     * Realiza um novo agendamento solicitando dados do cliente, serviços, 
     * data e hora do atendimento.
     * 
     * Inclui verificação de disponibilidade de estações e funcionários.
     * Trata exceções de entrada de dados inválidos.
     */
    public void cadastrar() {
        try {
            System.out.println("\n=== Realizar Novo Agendamento ===");

            System.out.print("CPF do cliente: ");
            String cpfCliente = input.nextLine();
            Cliente cliente = Sistema.getSistema()
                    .getGestaoCliente()
                    .getListaCliente()
                    .stream()
                    .filter(c -> c.getCpf().equals(cpfCliente))
                    .findFirst()
                    .orElse(null);

            if (cliente == null) {
                System.out.println("Cliente não encontrado!");
                return;
            }
            System.out.println("Cliente " + cliente.getNome() + " selecionado.");

            List<Servico> servicosDesejados = new ArrayList<>();
            Sistema.getSistema().getGestaoServico().listarEnumerado();
            char resp = 's';
            boolean estacaoCompleta = false;

            while (resp == 's' || resp == 'S') {
                try {
                    System.out.print("Selecione o serviço desejado (número): ");
                    int posicao = input.nextInt() - 1;
                    input.nextLine();
                    Servico servicoDesejado = Sistema.getSistema().getGestaoServico().getListaServicos().get(posicao);
                    servicosDesejados.add(servicoDesejado);
                    System.out.println(servicoDesejado.getNome() + " adicionado.");
                    if (servicoDesejado.isServicoCompleto()) estacaoCompleta = true;

                    System.out.print("Deseja selecionar mais um serviço (s/n)? ");
                    resp = input.nextLine().charAt(0);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Serviço inválido. Tente novamente.");
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Digite um número.");
                    input.nextLine(); // limpar buffer
                }
            }

            Duration duracao = GestaoServico.getDuracaoServicos(servicosDesejados);
            System.out.println("Duração total do atendimento: " + duracao.toMinutes() + " minutos.");
            System.out.println("Estação de atendimento: " + (estacaoCompleta ? "Completa" : "Simples"));

            LocalDate data = null;
            LocalTime horario = null;
            EstacaoAtendimento estacaoDisponivel = null;

            while (estacaoDisponivel == null) {
                try {
                    System.out.print("Insira a data desejada (DD/MM/AAAA): ");
                    data = LocalDate.parse(input.nextLine(), formatoData);

                    listarAgendamentosSimplificado(data);

                    System.out.print("Escolha um horário (HH:MM): ");
                    horario = LocalTime.parse(input.nextLine(), formatoInserirHora);

                    estacaoDisponivel = verificarDisponibilidade(LocalDateTime.of(data, horario), duracao, estacaoCompleta);
                    if (estacaoDisponivel == null) System.out.println("Este horário está indisponível! Escolha outro.");
                } catch (DateTimeParseException e) {
                    System.out.println("Formato de data ou hora inválido. Tente novamente.");
                }
            }

            System.out.println("O atendimento será realizado na Estação #" + estacaoDisponivel + ".");

            List<Agendamento> conflitos = verificarDisponibilidadeAgendamento(LocalDateTime.of(data, horario), duracao);
            List<Funcionario> barbeirosDisponiveis = retornarBarbeirosDisponiveis(conflitos);

            GestaoFuncionario.listarBarbeirosSimplificado(barbeirosDisponiveis);

            Funcionario funcionario = null;
            while (funcionario == null) {
                try {
                    System.out.print("Insira o número do Barbeiro: ");
                    int resposta = input.nextInt() - 1;
                    input.nextLine();
                    funcionario = barbeirosDisponiveis.get(resposta);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Barbeiro inválido. Escolha novamente.");
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Digite um número.");
                    input.nextLine();
                }
            }

            Agendamento agendamento = new Agendamento(LocalDateTime.of(data, horario), cliente, funcionario, servicosDesejados);
            agendamentos.add(agendamento);

            System.out.println("\nAgendamento criado com sucesso!");
            System.out.println(agendamento);

        } catch (Exception e) {
            System.out.println("Erro inesperado ao criar agendamento: " + e.getMessage());
        }
    }

    /**
     * Lista todos os agendamentos existentes no sistema.
     * Caso a lista esteja vazia, exibe uma mensagem informando.
     */
    public void listar() {
        System.out.println("\n=== Lista de Agendamentos ===");
        if (agendamentos.isEmpty()) {
            System.out.println("Nenhum agendamento encontrado.");
            return;
        }
        agendamentos.forEach(System.out::println);
    }

    /**
     * Altera o status de um agendamento selecionado pelo usuário.
     * Inclui tratamento para entradas inválidas.
     */
    public void alterarStatus() {
        Agendamento agendamento = buscarPorId();
        if (agendamento == null) return;

        int op = 0;
        do {
            try {
                System.out.println("Para qual status deseja alterar?");
                System.out.println("1. CANCELADO");
                System.out.println("2. EM ATENDIMENTO");
                System.out.println("3. CONCLUIDO");
                op = input.nextInt();
                input.nextLine();

                switch (op) {
                    case 1 -> {
                        agendamento.setStatus(StatusAgendamento.CANCELADO);
                        Sistema.getSistema().getGestaoOS().cadastrar(agendamento);
                    }
                    case 2 -> agendamento.setStatus(StatusAgendamento.EM_ATENDIMENTO);
                    case 3 -> {
                        agendamento.setStatus(StatusAgendamento.CONCLUIDO);
                        Sistema.getSistema().getGestaoOS().cadastrar(agendamento);
                    }
                    default -> System.out.println("Opção inválida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Digite um número.");
                input.nextLine();
            }
        } while (op != 1 && op != 2 && op != 3);
    }

    // ====================== Outros métodos ======================

    /**
     * Lista os agendamentos de uma data específica de forma simplificada.
     * @param data Data dos agendamentos
     */
    public void listarAgendamentosSimplificado(LocalDate data) {
        List<Agendamento> agendamentosDia = filtroAgendados().stream()
                .filter(x -> x.getDataHoraInicio().toLocalDate().equals(data))
                .sorted(Comparator.comparing(Agendamento::getDataHoraInicio).thenComparing(Agendamento::getDataHoraFim))
                .collect(Collectors.toList());

        if (agendamentosDia.isEmpty()) return;

        System.out.println("\n=== Agendamentos em " + data.format(formatoData) + " ===");
        for (Agendamento x : agendamentosDia) {
            System.out.println(x.getDataHoraInicio().format(formatoHora)
                    + " às "
                    + x.getDataHoraFim().format(formatoHora)
                    + " | "
                    + x.getEstacaoAtendimento()
                    + " | "
                    + x.getCliente().getNome());
        }
    }

    /**
     * Remove um agendamento pelo ID informado pelo usuário.
     * Inclui confirmação antes da remoção e tratamento de entradas inválidas.
     */
    public void remover() {
        System.out.print("Informe o ID do agendamento a ser removido: ");
        String idBusca = input.nextLine();

        Agendamento agendamentoRemover = agendamentos.stream()
                .filter(ag -> ag.getId().equals(idBusca))
                .findFirst()
                .orElse(null);

        if (agendamentoRemover == null) {
            System.out.println("Agendamento não encontrado!");
            return;
        }

        System.out.print("Tem certeza que deseja remover o agendamento do " + agendamentoRemover.getCliente().getNome() + "? (s/n): ");
        char confirmacao = input.nextLine().charAt(0);

        if (confirmacao == 's' || confirmacao == 'S') {
            agendamentos.remove(agendamentoRemover);
            System.out.println("Agendamento removido com sucesso!");
        } else {
            System.out.println("Remoção cancelada.");
        }
    }

    /**
     * Busca um agendamento pelo ID informado pelo usuário.
     * @return Agendamento encontrado ou null se não existir
     */
    public Agendamento buscarPorId() {
        System.out.print("Entre com o ID do Agendamento: ");
        String id = input.nextLine();

        Agendamento agendamento = agendamentos.stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
        if (agendamento == null) {
            System.out.println("Agendamento não encontrado.");
            return null;
        }

        System.out.println(agendamento);
        return agendamento;
    }

    /**
     * Filtra apenas os agendamentos com status AGENDADO.
     * @return Lista de agendamentos agendados
     */
    public List<Agendamento> filtroAgendados() {
        return agendamentos.stream().filter(x -> x.getStatus() == StatusAgendamento.AGENDADO).collect(Collectors.toList());
    }

    /**
     * Verifica a disponibilidade das estações de atendimento para um horário e duração.
     * @param horario Data e hora do início do agendamento
     * @param duracao Duração do agendamento
     * @param estacaoCompleta True se o serviço exige estação completa
     * @return Estação disponível ou null se não houver
     */
    public EstacaoAtendimento verificarDisponibilidade(LocalDateTime horario, Duration duracao, boolean estacaoCompleta) {
        List<Agendamento> lista = filtroAgendados().stream()
                .filter(x -> x.getDataHoraInicio().toLocalDate().equals(horario.toLocalDate()))
                .collect(Collectors.toList());

        int controlador = 0;
        for (Agendamento x : lista) {
            if (x.getDataHoraInicio().isBefore(horario.plus(duracao)) && horario.isBefore(x.getDataHoraFim())) {
                if (estacaoCompleta) {
                    System.out.println("Estação #1 está indisponível.");
                    return null;
                } else {
                    controlador += x.getEstacaoAtendimento().getNumero();
                }
            }
        }

        if (estacaoCompleta) return Sistema.getSistema().getEstacoes()[0];
        else if (controlador == 3) return Sistema.getSistema().getEstacoes()[1];
        else if (controlador == 2) return Sistema.getSistema().getEstacoes()[2];
        else {
            System.out.println("Estações #2 e #3 estão indisponíveis.");
            return null;
        }
    }

    /**
     * Verifica conflitos de horário para um agendamento.
     * @param horario Data e hora do início
     * @param duracao Duração do agendamento
     * @return Lista de agendamentos que conflitam
     */
    public List<Agendamento> verificarDisponibilidadeAgendamento(LocalDateTime horario, Duration duracao) {
        List<Agendamento> lista = filtroAgendados().stream()
                .filter(x -> x.getDataHoraInicio().toLocalDate().equals(horario.toLocalDate()))
                .collect(Collectors.toList());

        List<Agendamento> conflitos = new ArrayList<>();
        for (Agendamento x : lista) {
            if (x.getDataHoraInicio().isBefore(horario.plus(duracao)) && horario.isBefore(x.getDataHoraFim())) {
                conflitos.add(x);
            }
        }
        return conflitos;
    }

    /**
     * Retorna a lista de barbeiros disponíveis, excluindo os que têm conflito.
     * @param conflitos Lista de agendamentos que conflitam
     * @return Lista de funcionários disponíveis
     */
    public List<Funcionario> retornarBarbeirosDisponiveis(List<Agendamento> conflitos) {
        List<Funcionario> lista = new ArrayList<>(Sistema.getSistema().getGestaoFuncionario().getBarbeirosCadastrados());
        for (Agendamento agendamento : conflitos) {
            lista.remove(agendamento.getFuncionario());
        }
        return lista;
    }
}
