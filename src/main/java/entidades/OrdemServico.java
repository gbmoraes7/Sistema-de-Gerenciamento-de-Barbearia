package entidades;

import entidades.enums.FormaPagamento;
import entidades.enums.StatusAgendamento;
import entidades.enums.StatusOS;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import main.Sistema;

/**
 * Representa uma Ordem de Serviço (OS) emitida no sistema.
 *
 * <p>A OS pode ser originada de um agendamento ou criada manualmente com itens.
 * Armazena serviços, produtos, forma de pagamento, status e vinculação ao
 * cliente e funcionário.</p>
 *
 * <p>A classe também mantém um contador estático para gerar identificadores
 * únicos no formato "OS-XXX".</p>
 */
public class OrdemServico {

    /** Formato padrão para exibição de datas. */
    private final DateTimeFormatter formatoDataHora = DateTimeFormatter.ofPattern("dd/MM/yyyy às HH'h'mm");

    /** Identificador único da OS. */
    private final String id;

    /** Data e hora do registro da OS. */
    private LocalDateTime horaRegistro;

    /** Cliente vinculado à OS. */
    private final Cliente cliente;

    /** Funcionário responsável pela execução. */
    private Funcionario funcionario;

    /** Agendamento de origem, caso exista. */
    private Agendamento agendamento;

    /** Forma de pagamento utilizada. */
    private FormaPagamento formaPagamento;

    /** Status atual da OS. */
    private StatusOS statusOS;

    /** Contador estático para gerar IDs únicos. */
    private static int contador = 0;

    /** Lista de serviços associados à OS. */
    private final List<Servico> servicos = new ArrayList<>();

    /** Lista de itens (produtos) utilizados na OS. */
    private final List<Item> itens = new ArrayList<>();

    /** Entrada de dados pelo console. */
    private final Scanner input = new Scanner(System.in);


    /**
     * Construtor para OS oriunda de um agendamento.
     *
     * @param agendamento Agendamento associado (não pode ser nulo)
     *
     * @throws IllegalArgumentException caso o agendamento seja nulo
     */
    public OrdemServico(Agendamento agendamento) {
        if (agendamento == null) {
            throw new IllegalArgumentException("O agendamento não pode ser nulo.");
        }

        contador++;
        this.id = String.format("OS-%03d", contador);

        this.cliente = agendamento.getCliente();
        this.funcionario = agendamento.getFuncionario();
        this.agendamento = agendamento;
        this.statusOS = StatusOS.ABERTA;
        this.servicos.addAll(agendamento.getServicosDesejados());
        this.horaRegistro = LocalDateTime.now();
    }

    /**
     * Construtor para OS criada diretamente com produtos.
     *
     * @param cliente Cliente vinculado (não pode ser nulo)
     * @param itens Lista de itens utilizados (não pode ser nula)
     *
     * @throws IllegalArgumentException caso cliente ou itens sejam inválidos
     */
    public OrdemServico(Cliente cliente, List<Item> itens) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo.");
        }
        if (itens == null) {
            throw new IllegalArgumentException("A lista de itens não pode ser nula.");
        }

        contador++;
        this.id = String.format("OS-%03d", contador);
        this.cliente = cliente;
        this.itens.addAll(itens);
        this.horaRegistro = LocalDateTime.now();
    }

    // -------------------- GETTERS --------------------

    public String getId() {
        return id;
    }

    public static int getContador() {
        return contador;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public Agendamento getAgendamento() {
        return agendamento;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    // -------------------- MÉTODOS DE MANIPULAÇÃO --------------------

    /**
     * Adiciona serviços à OS com base em seleção do usuário.
     * <p>Utiliza listagem de serviços pelo sistema.</p>
     *
     * @throws IndexOutOfBoundsException se o usuário selecionar serviço inexistente
     */
    public void adicionarServicosOS() {

        Sistema.getSistema().getGestaoServico().listarEnumerado();
        System.out.print("Selecione o serviço desejado: ");

        char resp = 's';
        while (resp == 's') {

            try {
                int posicao = input.nextInt() - 1;
                List<Servico> lista = Sistema.getSistema().getGestaoServico().getListaServicos();

                if (posicao < 0 || posicao >= lista.size()) {
                    System.out.println("Posição inválida. Nenhum serviço adicionado.");
                } else {
                    Servico servicoDesejado = lista.get(posicao);
                    servicos.add(servicoDesejado);
                    System.out.println(servicoDesejado.getNome() + " adicionado.");
                }

            } catch (Exception e) {
                System.out.println("Entrada inválida. Tente novamente.");
                input.nextLine();
            }

            System.out.println("Deseja selecionar mais um serviço (s/n)? ");
            resp = input.next().charAt(0);
            input.nextLine();
        }
    }

    /**
     * Solicita ao sistema a realização de uma venda e adiciona os itens retornados à OS.
     */
    public void adicionarProdutosOS() {
        List<Item> lista = Sistema.getSistema().getLoja().realizarVenda();

        if (lista != null) {
            itens.addAll(lista);
        }
    }

    /**
     * Soma o valor total dos serviços associados à OS.
     *
     * @return total em reais
     */
    public double totalServicos() {
        double total = 0.0;

        for (Servico servico : servicos) {
            total += servico.getPreco();
        }

        return total;
    }

    /**
     * Calcula o total dos produtos asociados à OS.
     *
     * @return valor total
     */
    public double totalProdutos() {
        double total = 0.0;

        for (Item item : itens) {
            total += item.valorTotal();
        }

        return total;
    }

    /**
     * Calcula o total da OS, considerando descontos para cancelamento de agendamento.
     *
     * @return total a pagar
     */
    public double totalOS() {
        if (agendamento != null && agendamento.getStatus() == StatusAgendamento.CANCELADO) {
            return 0.35 * (totalProdutos() + totalServicos());
        }
        return totalProdutos() + totalServicos();
    }

    /**
     * Permite selecionar a forma de pagamento da OS.
     */
    public void definirFormaPagamentoOS() {

        int op;
        do {
            System.out.println("Qual será a forma de pagamento? ");
            System.out.println("1. CREDITO ");
            System.out.println("2. DEBITO ");
            System.out.println("3. DINHEIRO ");
            System.out.println("4. PIX ");

            op = input.nextInt();
            input.nextLine();

            switch (op) {
                case 1 -> formaPagamento = FormaPagamento.CREDITO;
                case 2 -> formaPagamento = FormaPagamento.DEBITO;
                case 3 -> formaPagamento = FormaPagamento.DINHEIRO;
                case 4 -> formaPagamento = FormaPagamento.PIX;
                default -> System.out.println("Opção inválida.");
            }

        } while (formaPagamento == null);

        System.out.println("Forma de pagamento " + formaPagamento.name() + " definida.");
    }

    /**
     * Altera o status atual da OS.
     */
    public void alterarStatusOS() {

        int op;
        do {
            System.out.println("Para qual status deseja alterar? ");
            System.out.println("1. ABERTA ");
            System.out.println("2. AGUARDANDO PAGAMENTO ");
            System.out.println("3. CONCLUIDA ");

            op = input.nextInt();
            input.nextLine();

            switch (op) {
                case 1 -> statusOS = StatusOS.ABERTA;
                case 2 -> statusOS = StatusOS.AGUARDANDO_PAGAMENTO;
                case 3 -> statusOS = StatusOS.CONCLUIDA;
                default -> System.out.println("Opção inválida.");
            }

        } while (statusOS == null);

        System.out.println("Status alterado para: " + statusOS.name());
    }


    // -------------------- REPRESENTAÇÃO --------------------

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("---- Ordem de Serviço ----\n");
        sb.append("ID: ").append(id)
                .append("   Registro: ")
                .append(horaRegistro.format(formatoDataHora))
                .append("\n\n");

        sb.append("Cliente: ");
        sb.append(cliente != null ? cliente.getNome() : "N/A").append("\n");

        sb.append("Barbeiro: ");
        sb.append(funcionario != null ? funcionario.getNome() : "N/A").append("\n");

        if (agendamento != null) {
            sb.append("Agendamento: ").append(agendamento.getId())
                    .append("   Status: ").append(agendamento.getStatus().name())
                    .append("\n");
        }

        sb.append("Forma de Pagamento: ")
                .append(formaPagamento != null ? formaPagamento.name() : "Não definida")
                .append("\n\n");

        if (!servicos.isEmpty()) {
            sb.append("Serviços:\n");
            for (Servico s : servicos) {
                sb.append("  - ").append(s.getNome())
                        .append(" (R$ ").append(String.format("%.2f", s.getPreco())).append(")\n");
            }
            sb.append("Subtotal Serviços: R$ ").append(String.format("%.2f", totalServicos())).append("\n\n");
        }

        if (!itens.isEmpty()) {
            sb.append("Produtos:\n");
            for (Item i : itens) {
                sb.append("  - ").append(i.getQuantidade()).append("x ")
                        .append(i.getProduto().getNome())
                        .append("  (R$ ").append(String.format("%.2f", i.valorTotal())).append(")\n");
            }
            sb.append("Subtotal Produtos: R$ ").append(String.format("%.2f", totalProdutos())).append("\n\n");
        }

        sb.append("TOTAL OS: R$ ").append(String.format("%.2f", totalOS())).append("\n");

        return sb.toString();
    }
}
