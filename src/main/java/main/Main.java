package main;

import java.util.Scanner;

/**
 * Classe principal do sistema.
 * 
 * Inicia a execução do sistema de gestão, incluindo clientes, funcionários,
 * produtos, serviços, agendamentos e ordens de serviço.
 */
public class Main {

    /**
     * Ponto de entrada da aplicação.
     * 
     * Inicializa o sistema e chama o método executar() da classe Sistema.
     * @param args argumentos de linha de comando (não utilizados)
     */
    public static void main(String[] args) {
        // Uso de try-with-resources para garantir fechamento do Scanner
        try (Scanner input = new Scanner(System.in)) {
            Sistema.getSistema().executar();
        } catch (Exception e) {
            System.out.println("Ocorreu um erro durante a execução do sistema: " + e.getMessage());
        }
    }
}
