package entidades;

/**
 * Representa um produto disponível para venda ou utilização no sistema.
 * <p>
 * Cada produto possui nome, preço, quantidade em estoque e um identificador
 * único gerado automaticamente no formato {@code "PROD-XXX"}.
 * </p>
 *
 * ### Responsabilidades:
 * <ul>
 *   <li>Controlar informações essenciais sobre um produto</li>
 *   <li>Gerenciar o estoque (adição e baixa)</li>
 *   <li>Manter IDs únicos através de um contador interno</li>
 * </ul>
 */
public class Produto {

    /**
     * Contador estático utilizado para gerar IDs únicos para cada produto.
     * O valor é incrementado a cada nova instância criada.
     */
    private static int contador = 0;

    /** Identificador único do produto no formato {@code "PROD-XXX"}. */
    private String id;

    /** Nome do produto. */
    private String nome;

    /** Preço unitário do produto. */
    private Double preco;

    /** Quantidade disponível em estoque. */
    private Integer quantidade;

    /**
     * Construtor da classe Produto.
     * <p>
     * Inicializa os atributos básicos e gera automaticamente um identificador
     * sequencial baseado no contador estático.
     * </p>
     *
     * @param nome       Nome do produto
     * @param preco      Preço unitário do produto
     * @param quantidade Quantidade inicial disponível em estoque
     * @throws IllegalArgumentException caso preço ou quantidade sejam inválidos
     */
    public Produto(String nome, Double preco, Integer quantidade) {

        if (preco == null || preco < 0)
            throw new IllegalArgumentException("Preço do produto não pode ser negativo ou nulo.");

        if (quantidade == null || quantidade < 0)
            throw new IllegalArgumentException("Quantidade inicial não pode ser negativa.");

        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;

        contador++;
        this.id = String.format("PROD-%03d", contador);
    }

    /** @return ID único do produto. */
    public String getId() {
        return id;
    }

    /** @return Nome do produto. */
    public String getNome() {
        return nome;
    }

    /**
     * Atualiza o nome do produto.
     *
     * @param nome Novo nome a ser atribuído
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /** @return Preço unitário do produto. */
    public Double getPreco() {
        return preco;
    }

    /**
     * Atualiza o preço do produto.
     *
     * @param preco Novo preço
     * @throws IllegalArgumentException caso o preço seja negativo
     */
    public void setPreco(Double preco) {
        if (preco == null || preco < 0)
            throw new IllegalArgumentException("Preço não pode ser negativo ou nulo.");
        this.preco = preco;
    }

    /** @return Quantidade atual em estoque. */
    public Integer getQuantidade() {
        return quantidade;
    }

    /**
     * Adiciona uma quantidade ao estoque.
     *
     * @param quantidade Quantidade a ser adicionada
     * @throws IllegalArgumentException se a quantidade for negativa
     */
    public void adicionarQuantidade(int quantidade) {
        if (quantidade < 0)
            throw new IllegalArgumentException("Quantidade adicionada não pode ser negativa.");

        this.quantidade += quantidade;
    }

    /**
     * Reduz a quantidade do produto em estoque.
     *
     * @param quantidade Quantidade a ser baixada
     * @throws IllegalArgumentException se a quantidade for negativa ou maior do que o estoque
     */
    public void baixarQuantidade(int quantidade) {
        if (quantidade < 0)
            throw new IllegalArgumentException("Quantidade a baixar não pode ser negativa.");

        if (quantidade > this.quantidade)
            throw new IllegalArgumentException("Quantidade insuficiente em estoque.");

        this.quantidade -= quantidade;
    }

    @Override
    public String toString() {
        return "ID: " + id + "\n"
                + "  Nome: " + nome + "\n"
                + "  Preço: " + preco + "\n"
                + "  Quantidade: " + quantidade;
    }
}
