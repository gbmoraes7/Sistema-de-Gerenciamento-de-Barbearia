package entidades;

import java.time.Duration;

/**
 * Representa um serviço oferecido pela barbearia.
 *
 * <p>Um serviço possui informações como nome, preço, duração e se é um
 * serviço completo ou simples. Cada instância recebe automaticamente um
 * identificador único no formato {@code SERV-XXX}, gerado sequencialmente.</p>
 *
 * @author Gabriel
 * @since 2025
 */
public class Servico {
    
    /**
     * Identificador único do serviço.
     *
     * <p>O valor é gerado automaticamente no formato {@code SERV-XXX},
     * onde {@code XXX} é um número sequencial com três dígitos.</p>
     */
    private String id;

    /** Nome comercial do serviço. */
    private String nome;

    /** Preço cobrado pelo serviço. */
    private Double preco;
    
    /**
     * Indica se o serviço é considerado completo.
     *
     * <p>Serviços completos podem seguir regras específicas no sistema,
     * como duração diferenciada ou composição de múltiplas etapas.</p>
     */
    private boolean servicoCompleto;

    /** Duração do serviço representada por {@link Duration}. */
    private Duration duracao;

    /**
     * Contador estático utilizado para gerar IDs únicos.
     *
     * <p>A cada serviço criado, esse contador é incrementado e usado
     * para formar o ID da instância.</p>
     */
    private static int contador = 0;
    
    /**
     * Constrói um novo serviço com nome, preço, duração e indicação de completude.
     *
     * <p>A duração é fornecida em minutos e convertida para um objeto
     * {@link Duration}. O ID da instância é gerado automaticamente.</p>
     *
     * @param nome nome do serviço (não deve ser nulo)
     * @param preco preço do serviço
     * @param duracao duração do serviço em minutos
     * @param servicoCompleto indica se o serviço é completo
     */
    public Servico(String nome, Double preco, int duracao, boolean servicoCompleto) {
        this.nome = nome;
        this.preco = preco;
        this.duracao = Duration.ofMinutes(duracao);
        this.servicoCompleto = servicoCompleto;
        contador++;
        this.id = String.format("SERV-%03d", contador);
    }

    /**
     * Retorna se o serviço é completo.
     *
     * @return {@code true} se for serviço completo, {@code false} caso contrário.
     */
    public boolean isServicoCompleto() {
        return servicoCompleto;
    }

    /**
     * Define se o serviço é completo.
     *
     * @param servicoCompleto novo valor de completude.
     */
    public void setServicoCompleto(boolean servicoCompleto) {
        this.servicoCompleto = servicoCompleto;
    }

    /**
     * Obtém a duração do serviço.
     *
     * @return objeto {@link Duration} contendo a duração.
     */
    public Duration getDuracao() {
        return duracao;
    }

    /**
     * Altera a duração do serviço.
     *
     * @param duracao nova duração (não deve ser nula)
     */
    public void setDuracao(Duration duracao) {
        this.duracao = duracao;
    }

    /**
     * Retorna o identificador único deste serviço.
     *
     * @return ID no formato {@code SERV-XXX}
     */
    public String getId() {
        return id;
    }

    /**
     * Retorna o nome do serviço.
     *
     * @return nome do serviço
     */
    public String getNome() {
        return nome;
    }

    /**
     * Atualiza o nome do serviço.
     *
     * @param nome novo nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o preço do serviço.
     *
     * @return valor do serviço
     */
    public Double getPreco() {
        return preco;
    }

    /**
     * Atualiza o preço do serviço.
     *
     * @param preco novo valor
     */
    public void setPreco(Double preco) {
        this.preco = preco;
    }
    
    /**
     * Retorna uma representação simplificada do serviço.
     *
     * <p>Útil para exibições compactas em listagens.</p>
     *
     * @return string contendo nome, preço e duração em minutos
     */
    public String toStringSimplificado() {
        return nome 
                + " | "
                + preco
                + " | " 
                + duracao.toMinutes()
                + " minutos";
    }
    
    /**
     * Retorna uma representação detalhada do serviço.
     *
     * @return string formatada contendo todos os atributos do serviço.
     */
    @Override
    public String toString() {
        String tipoServico = servicoCompleto ? "Serviço Completo" : "Serviço Simples";
        
        return "ID: " + id + "\n"
                + "  Nome: " + nome + "\n"
                + "  Preço: " + preco + "\n"
                + "  Duração: " + duracao.toMinutes() + " minutos\n"
                + "  " + tipoServico;
    }
}
