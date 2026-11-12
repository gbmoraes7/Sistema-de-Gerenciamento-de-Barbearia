package entidades.enums;

/**
 * Enumeração que representa os possíveis status de um agendamento dentro do sistema.
 * 
 * Utilizado na classe {@link entidades.Agendamento} para controlar o estado de cada
 * agendamento de forma segura e consistente.
 */
public enum StatusAgendamento {
    
    /** Agendamento que foi cancelado pelo cliente ou pelo sistema */
    CANCELADO,
    
    /** Agendamento recém-criado ou confirmado, aguardando atendimento */
    AGENDADO,
    
    /** Agendamento que está em espera antes de ser atendido */
    EM_ESPERA,
    
    /** Agendamento que está sendo realizado no momento */
    EM_ATENDIMENTO,
    
    /** Agendamento finalizado com sucesso */
    CONCLUIDO;
}
