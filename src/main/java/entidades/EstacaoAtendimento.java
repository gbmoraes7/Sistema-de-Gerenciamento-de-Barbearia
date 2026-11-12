package entidades;

/**
 * Representa uma estação de atendimento dentro do sistema.
 * 
 * <p>Uma estação de atendimento possui um número identificador e pode ser
 * classificada como completa ou simples, dependendo da disponibilidade de
 * equipamentos ou recursos.</p>
 */
public class EstacaoAtendimento {
    
    /** Número identificador da estação. Deve ser maior que zero. */
    private int numero;

    /** Indica se a estação é completa (true) ou simples (false). */
    private boolean estacaoCompleta;

    /**
     * Construtor da classe EstacaoAtendimento.
     *
     * @param numero Número identificador da estação (deve ser > 0)
     * @param estacaoCompleta Indica se a estação é completa
     * @throws IllegalArgumentException caso o número seja menor ou igual a zero
     */
    public EstacaoAtendimento(int numero, boolean estacaoCompleta) {
        setNumero(numero);
        setEstacaoCompleta(estacaoCompleta);
    }

    /**
     * Retorna a representação textual da estação,
     * indicando se é completa ou simples.
     *
     * @return String no formato:
     *         "X (completa)" ou "X (simples)"
     */
    @Override
    public String toString() {
        return estacaoCompleta
                ? numero + " (completa)"
                : numero + " (simples)";
    }

    /**
     * Obtém o número da estação.
     *
     * @return número identificador
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Define o número da estação.
     *
     * @param numero Novo número (deve ser maior que zero)
     * @throws IllegalArgumentException caso o número seja menor ou igual a zero
     */
    public void setNumero(int numero) {
        if (numero <= 0) {
            throw new IllegalArgumentException("O número da estação deve ser maior que zero.");
        }
        this.numero = numero;
    }

    /**
     * Verifica se a estação é completa.
     *
     * @return true se for completa, false se for simples
     */
    public boolean isEstacaoCompleta() {
        return estacaoCompleta;
    }

    /**
     * Define se a estação é completa ou simples.
     *
     * @param estacaoCompleta true para completa, false para simples
     */
    public void setEstacaoCompleta(boolean estacaoCompleta) {
        this.estacaoCompleta = estacaoCompleta;
    }
}
