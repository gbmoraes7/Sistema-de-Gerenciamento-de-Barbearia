package entidades;

/**
 * Representa o administrador do sistema.
 * <p>
 * Esta classe implementa o padrão Singleton, garantindo que apenas uma
 * instância do administrador exista durante toda a execução do sistema.
 * O administrador herda os atributos e comportamentos básicos da classe {@link Usuario},
 * porém possui permissões elevadas.
 * </p>
 *
 * <p><b>Observação:</b> os valores padrão (nome, endereço, CPF e email) são
 * definidos para representar um administrador genérico do sistema.</p>
 *
 * @author Gabriel
 */
public class Administrador extends Usuario {

    /**
     * Instância única da classe (Singleton).
     */
    private static Administrador instancia;

    /**
     * Construtor privado para impedir múltiplas instâncias.
     * <p>
     * Define valores padrões para o administrador e ativa a permissão total.
     * A senha padrão é "admin123".
     * </p>
     */
    private Administrador() {
        super("Administrador do Sistema",
                "Endereço não especificado",
                "00000000000",
                "admin@sistema.com",
                "000.000.000-00");

        this.senha = "admin123";
        this.permissao = true;
    }

    /**
     * Retorna a instância única do administrador.
     * <p>
     * Caso ainda não exista, cria uma nova instância. Implementa o padrão Singleton.
     * </p>
     *
     * @return instância única de {@code Administrador}.
     * @throws IllegalStateException caso ocorra erro inesperado ao criar o administrador.
     */
    public static Administrador getInstance() {
        if (instancia == null) {
            try {
                instancia = new Administrador();
            } catch (Exception e) {
                throw new IllegalStateException("Falha ao inicializar o administrador do sistema.", e);
            }
        }
        return instancia;
    }
}
