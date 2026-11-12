package gestores;

import entidades.Agendamento;
import entidades.Cliente;
import entidades.Item;
import entidades.OrdemServico;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import main.Sistema;

/**
 * Classe responsável pela gestão de Ordens de Serviço (OS) no sistema.
 * 
 * Centraliza operações como cadastro, busca, listagem e acesso a OS,
 * mantendo uma lista interna de todas as Ordens de Serviço.
 */
public class GestaoOS {

    /** Scanner para leitura de dados do usuário */
    private final Scanner input = new Scanner(System.in);

    /** Lista que armazena todas as Ordens de Serviço */
    private final List<OrdemServico> listaOS = new ArrayList<>();

    // ====================== Métodos principais ======================

    /**
     * Retorna a lista de Ordens de Serviço cadastradas.
     * @return lista de Ordens de Serviço
     */
    public List<OrdemServico> getListaOS() {
        return listaOS;
    }

    /**
     * Cadastra uma Ordem de Serviço a partir de um agendamento existente.
     * @param agendamento Agendamento que originará a OS
     */
    public void cadastrar(Agendamento agendamento) {
        if (agendamento == null) {
            System.out.println("Agendamento inválido. Operação cancelada.");
            return;
        }

        try {
            OrdemServico ordemServico = new OrdemServico(agendamento);
            listaOS.add(ordemServico);
            System.out.println("\nOrdem de Serviço cadastrada com sucesso!");
            System.out.println(ordemServico);
            System.out.println();
            Sistema.getSistema().getMenus().submenuOperacoesOS(ordemServico);
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar Ordem de Serviço: " + e.getMessage());
        }
    }

    /**
     * Cadastra uma Ordem de Serviço a partir de uma venda na loja.
     */
    public void cadastrar() {
        try {
            Cliente cliente = Sistema.getSistema().getGestaoCliente().buscar();
            if (cliente == null) {
                System.out.println("Cliente inválido. Operação cancelada.");
                return;
            }

            List<Item> itens = Sistema.getSistema().getLoja().realizarVenda();
            if (itens == null || itens.isEmpty()) {
                System.out.println("Nenhum item selecionado. Operação cancelada.");
                return;
            }

            OrdemServico ordemServico = new OrdemServico(cliente, itens);
            listaOS.add(ordemServico);
            System.out.println("\nOrdem de Serviço cadastrada com sucesso!");
            System.out.println(ordemServico);
            System.out.println();
            Sistema.getSistema().getMenus().submenuOperacoesOS(ordemServico);
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar Ordem de Serviço: " + e.getMessage());
        }
    }

    /**
     * Busca uma Ordem de Serviço pelo ID informado pelo usuário.
     * @return Ordem de Serviço encontrada ou null se não existir
     */
    public OrdemServico buscarPorId() {
        System.out.print("Entre com o ID da Ordem de Serviço: ");
        String id = input.nextLine();

        OrdemServico ordemServico = listaOS.stream()
                .filter(os -> os.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (ordemServico == null) {
            System.out.println("OS não encontrada.");
        } else {
            System.out.println(ordemServico);
        }

        return ordemServico;
    }

    /**
     * Acessa uma Ordem de Serviço pelo ID e chama o submenu de operações.
     */
    public void acessar() {
        OrdemServico ordemServico = buscarPorId();
        if (ordemServico != null) {
            Sistema.getSistema().getMenus().submenuOperacoesOS(ordemServico);
        }
    }

    /**
     * Lista todas as Ordens de Serviço cadastradas.
     */
    public void listar() {
        System.out.println("\n=== Lista de Ordens de Serviço ===");
        if (listaOS.isEmpty()) {
            System.out.println("Nenhuma OS encontrada.");
            return;
        }

        for (OrdemServico os : listaOS) {
            System.out.println(os);
            System.out.println();
        }
    }
}
