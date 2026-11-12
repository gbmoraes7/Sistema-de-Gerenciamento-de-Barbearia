package entidades;

/**
 * Representa uma pessoa genérica dentro do sistema.
 * <p>
 * Esta classe abstrata serve como superclasse para entidades específicas,
 * como {@link Cliente} e {@link Funcionario}, centralizando atributos comuns
 * como nome, endereço, telefone, e-mail e CPF.
 * </p>
 *
 * <p>
 * Como é abstrata, não pode ser instanciada diretamente, devendo ser estendida
 * por subclasses concretas que definirão comportamentos e identificadores
 * específicos, como IDs automáticos.
 * </p>
 *
 * ### Responsabilidades principais:
 * <ul>
 *   <li>Fornecer a estrutura comum a todas as pessoas do sistema</li>
 *   <li>Centralizar atributos compartilhados</li>
 *   <li>Fornecer getters e setters padronizados</li>
 * </ul>
 */
public abstract class Pessoa {

    /** Identificador único da pessoa (definido pelas subclasses). */
    protected String id;

    /** Nome completo da pessoa. */
    protected String nome;

    /** Endereço residencial ou comercial da pessoa. */
    protected String endereco;

    /** Número de telefone para contato. */
    protected String telefone;

    /** Endereço de e-mail utilizado para comunicação. */
    protected String email;

    /** CPF da pessoa. */
    protected String cpf;

    /**
     * Construtor da classe Pessoa.
     * <p>
     * Este construtor é acessível apenas pelas subclasses, que devem chamar
     * {@code super(...)} para inicializar os atributos comuns.
     * </p>
     *
     * @param nome     Nome completo da pessoa
     * @param endereco Endereço residencial ou comercial
     * @param telefone Telefone de contato
     * @param email    Endereço de e-mail válido
     * @param cpf      CPF da pessoa
     */
    public Pessoa(String nome, String endereco, String telefone, String email, String cpf) {
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
        this.cpf = cpf;
    }

    /**
     * Retorna o identificador único da pessoa.
     *
     * @return ID no formato definido pela subclasse (ex.: "CLI-001")
     */
    public String getId() {
        return id;
    }

    /**
     * Define o identificador único da pessoa.
     * <p>
     * Deve ser utilizado somente por subclasses que geram IDs automaticamente.
     * </p>
     *
     * @param id Identificador único da pessoa
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Retorna o nome completo da pessoa.
     *
     * @return Nome completo
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define um novo nome para a pessoa.
     *
     * @param nome Nome completo atualizado
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o endereço atual da pessoa.
     *
     * @return Endereço residencial ou comercial
     */
    public String getEndereco() {
        return endereco;
    }

    /**
     * Define um novo endereço para a pessoa.
     *
     * @param endereco Novo endereço atualizado
     */
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    /**
     * Retorna o telefone de contato da pessoa.
     *
     * @return Telefone cadastrado
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     * Atualiza o telefone de contato da pessoa.
     *
     * @param telefone Novo telefone cadastrado
     */
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /**
     * Retorna o e-mail cadastrado da pessoa.
     *
     * @return Endereço de e-mail
     */
    public String getEmail() {
        return email;
    }

    /**
     * Atualiza o e-mail cadastrado da pessoa.
     *
     * @param email Novo endereço de e-mail
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retorna o CPF da pessoa.
     *
     * @return CPF informado
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * Altera o CPF cadastrado da pessoa.
     *
     * @param cpf Novo CPF da pessoa
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
