package entidades.enums;

/**
 * Representa os possíveis status de uma Ordem de Serviço (OS).
 * <p>
 * Cada status indica em qual etapa do processo a OS se encontra,
 * facilitando o controle e acompanhamento do fluxo de atendimento.
 * </p>
 */
public enum StatusOS {

    /**
     * Indica que a Ordem de Serviço foi criada, registrada no sistema,
     * mas ainda não teve nenhum processamento adicional.
     */
    ABERTA,

    /**
     * Indica que todos os procedimentos foram concluídos e a OS está
     * aguardando o pagamento por parte do cliente.
     */
    AGUARDANDO_PAGAMENTO,

    /**
     * Indica que a OS foi totalmente finalizada, incluindo o pagamento,
     * encerrando assim o fluxo de atendimento.
     */
    CONCLUIDA;
}
