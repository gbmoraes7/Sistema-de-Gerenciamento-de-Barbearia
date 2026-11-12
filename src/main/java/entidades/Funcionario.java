package entidades;

import ControlePonto.Ponto;
import entidades.enums.CargoFuncionario;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa um funcionário do sistema.
 *
 * <p>A classe herda de {@link Usuario} e adiciona atributos específicos,
 * como cargo, salário e lista de registros de ponto.</p>
 */
public class Funcionario extends Usuario {

    /**
     * Contador estático utilizado para gerar IDs únicos para cada funcionário
     * no formato "FUNC-XXX".
     */
    private static int contador = 0;

    /**
     * Cargo ocupado pelo funcionário, definido pelo enum {@link CargoFuncionario}.
     */
    private CargoFuncionario cargo;

    /**
     * Lista de registros de ponto do funcionário.
     */
    private List<Ponto> pontos;

    /**
     * Salário atual do funcionário.
     */
    private Double salario;

    /**
     * Construtor da classe Funcionario.
     *
     * <p>Inicializa os dados básicos herdados de {@link Usuario},
     * define cargo, salário, permissões e gera automaticamente um ID único.</p>
     *
     * @param nome Nome completo do funcionário
     * @param endereco Endereço do funcionário
     * @param telefone Telefone de contato
     * @param email E-mail do funcionário
     * @param cpf CPF do funcionário
     * @param cargo Cargo ocupado pelo funcionário
     * @param salario Valor do salário atual (deve ser maior que zero)
     *
     * @throws IllegalArgumentException caso cargo seja nulo ou salário inválido
     */
    public Funcionario(String nome, String endereco, String telefone,
                       String email, String cpf,
                       CargoFuncionario cargo, Double salario) {

        super(nome, endereco, telefone, email, cpf);

        if (cargo == null) {
            throw new IllegalArgumentException("O cargo do funcionário não pode ser nulo.");
        }

        if (salario == null || salario <= 0) {
            throw new IllegalArgumentException("O salário deve ser maior que zero.");
        }

        this.cargo = cargo;
        this.salario = salario;

        this.senha = "funcionario123";
        this.permissao = false;

        contador++; // ❗ Era obrigatório, mas não estava sendo incrementado
        this.id = String.format("FUNC-%03d", contador);

        this.pontos = new ArrayList<>();
    }

    // ---------------- GETTERS E SETTERS ----------------

    /**
     * Retorna o cargo do funcionário.
     *
     * @return Cargo atual
     */
    public CargoFuncionario getCargo() {
        return cargo;
    }

    /**
     * Define um novo cargo para o funcionário.
     *
     * @param cargo Novo cargo
     * @throws IllegalArgumentException caso o cargo seja nulo
     */
    public void setCargo(CargoFuncionario cargo) {
        if (cargo == null) {
            throw new IllegalArgumentException("O cargo do funcionário não pode ser nulo.");
        }
        this.cargo = cargo;
    }

    /**
     * Retorna o salário atual do funcionário.
     *
     * @return Salário atual
     */
    public Double getSalario() {
        return salario;
    }

    /**
     * Define um novo valor para o salário.
     *
     * @param salario Novo salário (deve ser maior que zero)
     * @throws IllegalArgumentException caso o salário seja inválido
     */
    public void setSalario(Double salario) {
        if (salario == null || salario <= 0) {
            throw new IllegalArgumentException("O salário deve ser maior que zero.");
        }
        this.salario = salario;
    }

    /**
     * Retorna a lista de registros de ponto do funcionário.
     *
     * @return Lista de pontos
     */
    public List<Ponto> getPontos() {
        return pontos;
    }

    /**
     * Adiciona um registro de ponto ao funcionário.
     *
     * @param ponto Registro de ponto
     * @throws IllegalArgumentException caso o ponto seja nulo
     */
    public void adicionarPonto(Ponto ponto) {
        if (ponto == null) {
            throw new IllegalArgumentException("O ponto a ser adicionado não pode ser nulo.");
        }
        pontos.add(ponto);
    }

    // ---------------- TO STRING ----------------

    @Override
    public String toString() {
        return "ID: " + id + "\n"
                + "  Nome: " + nome + "\n"
                + "  Endereço: " + endereco + "\n"
                + "  Telefone: " + telefone + "\n"
                + "  Email: " + email + "\n"
                + "  CPF: " + cpf + "\n"
                + "  Cargo: " + cargo + "\n"
                + "  Salário: " + salario;
    }
}
