package entidades;

import entidades.enums.StatusAgendamento;
import gestores.GestaoServico;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Representa um agendamento realizado no sistema.
 * <p>
 * Cada agendamento associa um cliente, um funcionário, uma data/hora
 * de execução e um conjunto de serviços desejados.
 * </p>
 * <p>
 * O ID é gerado automaticamente e é imutável após a criação,
 * garantindo integridade dos registros.
 * </p>
 */
public class Agendamento implements Comparator<Agendamento> {

    /** Identificador único do agendamento, imutável. */
    private final String id;

    /** Momento de início do agendamento. */
    private LocalDateTime dataHoraInicio;

    /** Status atual do agendamento. */
    private StatusAgendamento status;

    /** Cliente associado ao serviço agendado. */
    private Cliente cliente;

    /** Funcionário responsável pelo atendimento. */
    private Funcionario funcionario;

    /** Estação onde o atendimento será executado. */
    private EstacaoAtendimento estacaoAtendimento;

    /** Contador estático usado para gerar IDs exclusivos. */
    private static int contador = 0;

    /** Lista de serviços incluídos no agendamento. */
    private List<Servico> servicosDesejados = new ArrayList<>();

    /**
     * Constrói um novo agendamento completo.
     *
     * @param dataHoraInicio Data e hora do início.
     * @param cliente Cliente associado.
     * @param funcionario Funcionário responsável.
     * @param servicosDesejados Lista de serviços escolhidos.
     *
     * @throws IllegalArgumentException se algum parâmetro obrigatório for nulo.
     */
    public Agendamento(LocalDateTime dataHoraInicio,
                       Cliente cliente,
                       Funcionario funcionario,
                       List<Servico> servicosDesejados) {

        if (dataHoraInicio == null)
            throw new IllegalArgumentException("Data e hora do agendamento não podem ser nulas.");
        if (cliente == null)
            throw new IllegalArgumentException("Cliente não pode ser nulo.");
        if (funcionario == null)
            throw new IllegalArgumentException("Funcionário não pode ser nulo.");
        if (servicosDesejados == null || servicosDesejados.isEmpty())
            throw new IllegalArgumentException("A lista de serviços não pode ser nula ou vazia.");

        this.dataHoraInicio = dataHoraInicio;
        this.status = StatusAgendamento.AGENDADO;
        this.cliente = cliente;
        this.funcionario = funcionario;
        this.servicosDesejados = new ArrayList<>(servicosDesejados);

        contador++;
        this.id = String.format("AGEN-%03d", contador);
    }

    /**
     * Construtor alternativo usado para criar agendamentos apenas com status e cliente.
     *
     * @param status Estado inicial.
     * @param cliente Cliente associado.
     */
    public Agendamento(StatusAgendamento status, Cliente cliente) {
        if (status == null)
            throw new IllegalArgumentException("Status não pode ser nulo.");
        if (cliente == null)
            throw new IllegalArgumentException("Cliente não pode ser nulo.");

        this.status = StatusAgendamento.EM_ESPERA;
        this.cliente = cliente;

        contador++;
        this.id = String.format("AGEN-%03d", contador);
    }

    // -------------------------------------------------------------------------
    // Getters e Setters
    // -------------------------------------------------------------------------

    public EstacaoAtendimento getEstacaoAtendimento() {
        return estacaoAtendimento;
    }

    public void setEstacaoAtendimento(EstacaoAtendimento estacaoAtendimento) {
        this.estacaoAtendimento = estacaoAtendimento;
    }

    public List<Servico> getServicosDesejados() {
        return new ArrayList<>(servicosDesejados);
    }

    public void setServicosDesejados(List<Servico> servicosDesejados) {
        if (servicosDesejados == null || servicosDesejados.isEmpty())
            throw new IllegalArgumentException("A lista de serviços não pode ser vazia ou nula.");
        this.servicosDesejados = new ArrayList<>(servicosDesejados);
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getDataHoraInicio() {
        return dataHoraInicio;
    }

    /**
     * Calcula a duração total dos serviços do agendamento.
     *
     * @return Soma das durações.
     */
    public Duration getDuracaoServicos() {
        Duration total = Duration.ZERO;
        for (Servico s : servicosDesejados) {
            total = total.plus(s.getDuracao());
        }
        return total;
    }

    /**
     * Retorna o horário previsto para término do atendimento.
     */
    public LocalDateTime getDataHoraFim() {
        return dataHoraInicio.plus(getDuracaoServicos());
    }

    public void setDataHora(LocalDateTime dataHoraInicio) {
        if (dataHoraInicio == null)
            throw new IllegalArgumentException("Data e hora não podem ser nulas.");
        this.dataHoraInicio = dataHoraInicio;
    }

    public StatusAgendamento getStatus() {
        return status;
    }

    public void setStatus(StatusAgendamento status) {
        if (status == null)
            throw new IllegalArgumentException("Status não pode ser nulo.");
        this.status = status;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        if (cliente == null)
            throw new IllegalArgumentException("Cliente não pode ser nulo.");
        this.cliente = cliente;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        if (funcionario == null)
            throw new IllegalArgumentException("Funcionário não pode ser nulo.");
        this.funcionario = funcionario;
    }

    // -------------------------------------------------------------------------
    // Comparator
    // -------------------------------------------------------------------------

    /**
     * Compara dois agendamentos por data/hora.
     */
    @Override
    public int compare(Agendamento a1, Agendamento a2) {
        return a1.getDataHoraInicio().compareTo(a2.getDataHoraInicio());
    }

    // -------------------------------------------------------------------------
    // toString
    // -------------------------------------------------------------------------

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(id).append("\n");
        sb.append("------------------------------------------------------------\n");

        sb.append("Cliente: ").append(cliente.getNome())
                .append(" (ID: ").append(cliente.getId()).append(")\n");
        sb.append("CPF:      ").append(cliente.getCpf()).append("\n");
        sb.append("Telefone: ").append(cliente.getTelefone()).append("\n");

        sb.append("------------------------------------------------------------\n");

        sb.append("Funcionário: ").append(funcionario.getNome())
                .append(" (ID: ").append(funcionario.getId()).append(")\n");

        sb.append("Horário:  ")
                .append(dataHoraInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
                .append(" às ")
                .append(getDataHoraFim().format(DateTimeFormatter.ofPattern("HH:mm")))
                .append("\n");

        sb.append("Estação:  ").append(estacaoAtendimento)
                .append("       Status: ").append(status).append("\n");

        sb.append("------------------------------------------------------------\n");
        sb.append("Serviços:\n");

        for (Servico s : servicosDesejados) {
            sb.append("  ")
                    .append(s.getNome())
                    .append(" R$ ")
                    .append(String.format("%.2f", s.getPreco()))
                    .append("\n");
        }

        sb.append("\nTotal: R$ ")
                .append(String.format("%.2f", GestaoServico.getValorServicos(servicosDesejados)))
                .append("\n");

        return sb.toString();
    }
}
