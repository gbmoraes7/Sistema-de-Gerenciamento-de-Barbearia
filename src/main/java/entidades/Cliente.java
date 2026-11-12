package entidades;

/**
 * Representa um cliente do sistema.
 *
 * <p>
 * A classe {@code Cliente} estende {@link Pessoa}, herdando seus atributos
 * fundamentais (nome, endereço, telefone, e-mail e CPF) e adicionando a lógica
 * de geração automática de identificadores exclusivos.
 * </p>
 */
public class Cliente extends Pessoa {

    /**
     * Contador estático utilizado para gerar IDs únicos e sequenciais
     * para os clientes cadastrados.
     *
     * <p>O ID é gerado no formato {@code CLI-001}, {@code CLI-002}, etc.</p>
     */
    private static int contador = 0;

    /**
     * Constrói um novo cliente com todas as informações pessoais básicas.
     *
     * <p>
     * As informações fornecidas são validadas e encaminhadas ao construtor da
     * superclasse {@link Pessoa}. O ID é gerado automaticamente e não pode ser
     * alterado posteriormente.
     * </p>
     *
     * @param nome Nome completo do cliente — não pode ser nulo ou vazio.
     * @param endereco Endereço do cliente — não pode ser nulo ou vazio.
     * @param telefone Telefone de contato — não pode ser nulo ou vazio.
     * @param email Endereço de e-mail — não pode ser nulo ou vazio.
     * @param cpf CPF do cliente — não pode ser nulo ou vazio.
     *
     * @throws IllegalArgumentException se qualquer parâmetro for nulo ou inválido.
     */
    public Cliente(String nome, String endereco, String telefone, String email, String cpf) {
        super(validar(nome, "Nome"),
              validar(endereco, "Endereço"),
              validar(telefone, "Telefone"),
              validar(email, "E-mail"),
              validar(cpf, "CPF"));

        contador++;
        this.id = String.format("CLI-%03d", contador);
    }

    /**
     * Método utilitário para validação de campos obrigatórios.
     *
     * @param valor Valor a validar.
     * @param campo Nome do campo (para mensagem de erro).
     * @return O próprio valor, caso seja válido.
     * @throws IllegalArgumentException se o valor for nulo ou vazio.
     */
    private static String validar(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(campo + " não pode ser nulo ou vazio.");
        }
        return valor;
    }

    /**
     * Retorna uma representação textual amigável e organizada do cliente.
     *
     * @return String contendo os dados essenciais do cliente.
     */
    @Override
    public String toString() {
        return "ID: " + id + "\n"
                + "  Nome: " + nome + "\n"
                + "  Endereço: " + endereco + "\n"
                + "  Telefone: " + telefone + "\n"
                + "  Email: " + email + "\n"
                + "  CPF: " + cpf;
    }
}
