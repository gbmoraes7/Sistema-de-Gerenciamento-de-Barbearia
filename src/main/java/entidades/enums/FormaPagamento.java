package entidades.enums;

/**
 * Representa as formas de pagamento disponíveis no sistema.
 *
 * <p>Esta enumeração é utilizada para registrar e controlar a forma como
 * uma {@link entidades.OrdemServico} foi paga. Cada constante representa
 * um método de pagamento aceito pela barbearia.</p>
 *
 * <p>As opções incluem:</p>
 * <ul>
 *   <li><b>CREDITO</b> — Pagamento via cartão de crédito.</li>
 *   <li><b>DEBITO</b> — Pagamento via cartão de débito.</li>
 *   <li><b>DINHEIRO</b> — Pagamento em espécie.</li>
 *   <li><b>PIX</b> — Pagamento via transferência instantânea PIX.</li>
 * </ul>
 *
 * @author Gabriel
 * @since 2025
 */
public enum FormaPagamento {
    
    /** Pagamento realizado por cartão de crédito. */
    CREDITO,
    
    /** Pagamento realizado por cartão de débito. */
    DEBITO,
    
    /** Pagamento realizado em dinheiro físico. */
    DINHEIRO,
    
    /** Pagamento realizado através do sistema PIX. */
    PIX;
}
