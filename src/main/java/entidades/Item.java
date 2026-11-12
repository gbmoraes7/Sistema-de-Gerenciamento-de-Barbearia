package entidades;

/**
 * Representa um item composto por um produto e sua quantidade.
 * 
 * <p>Essa classe é utilizada em operações como vendas, listagens de compras
 * ou controle de estoque, permitindo calcular o valor total baseado na
 * quantidade informada.</p>
 */
public class Item {

    /**
     * Produto associado ao item.
     */
    private Produto produto;

    /**
     * Quantidade do produto neste item.
     */
    private int quantidade;

    /**
     * Construtor da classe Item.
     *
     * @param produto Produto associado ao item (não pode ser nulo)
     * @param quantidade Quantidade desejada (deve ser maior que zero)
     * 
     * @throws IllegalArgumentException se o produto for nulo ou a quantidade for inválida
     */
    public Item(Produto produto, int quantidade) {
        if (produto == null) {
            throw new IllegalArgumentException("O produto não pode ser nulo.");
        }
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }

        this.produto = produto;
        this.quantidade = quantidade;
    }

    /**
     * Calcula o valor total correspondente a este item.
     *
     * @return Valor total (preço do produto × quantidade)
     */
    public double valorTotal() {
        return produto.getPreco() * quantidade;
    }

    /**
     * Retorna uma representação textual do item.
     *
     * @return String contendo a quantidade e a representação do produto
     */
    @Override
    public String toString() {
        return quantidade + " unid. " + produto;
    }

    /**
     * Retorna o produto associado.
     *
     * @return Produto referente ao item
     */
    public Produto getProduto() {
        return produto;
    }

    /**
     * Retorna a quantidade do item.
     *
     * @return Quantidade do produto
     */
    public int getQuantidade() {
        return quantidade;
    }
}
