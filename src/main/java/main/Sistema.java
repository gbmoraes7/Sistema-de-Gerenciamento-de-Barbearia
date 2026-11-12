package main;

import entidades.Administrador;
import entidades.EstacaoAtendimento;
import entidades.Funcionario;
import entidades.OrdemServico;
import entidades.Usuario;
import gestores.GestaoAgendamento;
import gestores.GestaoCliente;
import gestores.GestaoFuncionario;
import gestores.GestaoOS;
import gestores.GestaoProduto;
import gestores.GestaoServico;
import login.Login;
import menus.Menus;

/**
 * Classe principal que representa o sistema da barbearia.
 * 
 * Implementa o padrão Singleton, garantindo que exista apenas uma instância
 * do sistema. Centraliza os principais módulos: login, menus, gestão de
 * clientes, funcionários, produtos, serviços, agendamentos, ordens de serviço
 * e estações de atendimento.
 */
public class Sistema {
    
    /** Instância única do sistema */
    private static Sistema sistema;
    
    /** Gerenciador de login */
    private static Login login;

    /** Usuário atualmente logado */
    private Usuario usuario;

    /** Menus do sistema */
    private final Menus menus;

    /** Loja para vendas de produtos */
    private final Loja loja;

    /** Gestão de agendamentos */
    private final GestaoAgendamento gestaoAgendamento;

    /** Gestão de ordens de serviço */
    private final GestaoOS gestaoOS;

    /** Gestão de clientes */
    private final GestaoCliente gestaoCliente;

    /** Gestão de funcionários */
    private final GestaoFuncionario gestaoFuncionario;

    /** Gestão de produtos */
    private final GestaoProduto gestaoProduto;

    /** Gestão de serviços */
    private final GestaoServico gestaoServico;

    /** Array de estações de atendimento */
    private final EstacaoAtendimento[] estacoes;
    
    /**
     * Construtor privado que inicializa todos os módulos do sistema.
     * 
     * Implementa Singleton para garantir instância única.
     */
    private Sistema() {
        this.login = new Login();
        this.menus = new Menus();
        this.loja = new Loja();
        this.gestaoAgendamento = new GestaoAgendamento();
        this.gestaoOS = new GestaoOS();
        this.gestaoCliente = new GestaoCliente();
        this.gestaoFuncionario = new GestaoFuncionario();
        this.gestaoProduto = new GestaoProduto();
        this.gestaoServico = new GestaoServico();
        this.estacoes = new EstacaoAtendimento[3];
        inicializarEstacoes();
    }
    
    /**
     * Executa o sistema, iniciando o fluxo de login e depois mostrando o menu principal.
     */
    public void executar() {
        Administrador.getInstance();
        
        boolean logado = false;
        while (!logado) {
            try {
                logado = login.realizarLogin();
            } catch (Exception e) {
                System.out.println("Erro durante o login: " + e.getMessage());
            }
        }        
        
        menus.menuPrincipal();
    }

    /** @return Usuário atualmente logado */
    public Usuario getUsuario() {
        return usuario;
    }
    
    /** @return Gestão de agendamentos */
    public GestaoAgendamento getGestaoAgendamento() {
        return gestaoAgendamento;
    }

    /** @return Gestão de clientes */
    public GestaoCliente getGestaoCliente() {
        return gestaoCliente;
    }

    /** @return Menus do sistema */
    public Menus getMenus() {
        return menus;
    }

    /** @return Loja para vendas */
    public Loja getLoja() {
        return loja;
    }

    /** @return Gestão de funcionários */
    public GestaoFuncionario getGestaoFuncionario() {
        return gestaoFuncionario;
    }

    /** @return Gestão de produtos */
    public GestaoProduto getGestaoProduto() {
        return gestaoProduto;
    }

    /** @return Gestão de serviços */
    public GestaoServico getGestaoServico() {
        return gestaoServico;
    }

    /** @return Gestão de ordens de serviço */
    public GestaoOS getGestaoOS() {
        return gestaoOS;
    }

    /** @return Array de estações de atendimento */
    public EstacaoAtendimento[] getEstacoes() {
        return estacoes;        
    }
    
    /**
     * Retorna a instância única do Sistema.
     * 
     * @return Sistema
     */
    public static Sistema getSistema() {
        if (sistema == null)
            sistema = new Sistema();
        return sistema;
    }
    
    /**
     * Inicializa as estações de atendimento.
     * A primeira estação é marcada como disponível por padrão.
     */
    private void inicializarEstacoes() {
        estacoes[0] = new EstacaoAtendimento(1, true);
        estacoes[1] = new EstacaoAtendimento(2, false);
        estacoes[2] = new EstacaoAtendimento(3, false);
    }  
    
    /**
     * Retorna o número de instâncias de Ordens de Serviço criadas.
     * 
     * @return total de OS
     */
    public static int getInstanciasOS() {
        return OrdemServico.getContador();
    }
}
