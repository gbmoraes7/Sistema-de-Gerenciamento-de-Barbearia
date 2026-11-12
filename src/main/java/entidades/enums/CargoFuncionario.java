package entidades.enums;

/**
 * Enumeração que representa os possíveis cargos de um funcionário dentro do sistema.
 * 
 * Utilizado na classe {@link entidades.Funcionario} para definir o cargo ocupado
 * por cada funcionário de forma segura e controlada.
 */
public enum CargoFuncionario {
    
    /** Funcionário responsável pelo atendimento aos clientes, como recepção ou suporte */
    ATENDENTE,
    
    /** Funcionário especializado em serviços de corte e cuidados com cabelo ou barba */
    BARBEIRO,
    
    /** Funcionário que exerce funções de supervisão ou gestão dentro da organização */
    GERENTE;
}
