package entidades;

/**
 * Representa uma despesa financeira do sistema.
 * 
 * <p>Uma despesa possui uma descrição e um valor monetário associado.
 * Essa classe é simples e utilizada principalmente para registrar
 * gastos da empresa ou do sistema de gestão.</p>
 */
public class Despesa {
    
    /** Descrição da despesa, explicando sua finalidade. */
    private String descricao;

    /** Valor monetário da despesa. Deve ser maior ou igual a zero. */
    private double valor;

    /**
     * Construtor da classe Despesa.
     * 
     * @param descricao Descrição da despesa (não pode ser nula ou vazia)
     * @param valor Valor da despesa (não pode ser negativo)
     * @throws IllegalArgumentException caso a descrição seja inválida
     *         ou o valor seja negativo
     */
    public Despesa(String descricao, double valor) {
        setDescricao(descricao);
        setValor(valor);
    }

    /**
     * Obtém a descrição da despesa.
     * 
     * @return Descrição textual da despesa
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Define a descrição da despesa.
     * 
     * @param descricao Nova descrição (não pode ser nula ou vazia)
     * @throws IllegalArgumentException caso a descrição seja inválida
     */
    public void setDescricao(String descricao) {
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("A descrição da despesa não pode ser vazia ou nula.");
        }
        this.descricao = descricao;
    }

    /**
     * Obtém o valor da despesa.
     * 
     * @return Valor monetário associado
     */
    public double getValor() {
        return valor;
    }

    /**
     * Define o valor da despesa.
     * 
     * @param valor Novo valor da despesa (deve ser >= 0)
     * @throws IllegalArgumentException caso o valor seja negativo
     */
    public void setValor(double valor) {
        if (valor < 0) {
            throw new IllegalArgumentException("O valor da despesa não pode ser negativo.");
        }
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Despesa: " + descricao + " | Valor: R$ " + String.format("%.2f", valor);
    }
}
