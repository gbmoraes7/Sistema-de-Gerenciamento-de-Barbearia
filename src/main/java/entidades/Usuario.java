package entidades;

/**
 * Representa um usuário do sistema.
 *
 * <p>A classe {@code Usuario} herda de {@link Pessoa} e adiciona informações
 * específicas de autenticação, como nome de usuário, senha e permissões.
 * O nome de usuário é automaticamente definido como o CPF informado.</p>
 *
 * <p>Esta classe serve como base para perfis específicos, como
 * administradores ou funcionários com níveis distintos de acesso.</p>
 *
 * @author Gabriel
 * @since 2025
 */
public class Usuario extends Pessoa {
   
    /**
     * Nome de usuário utilizado no login.
     *
     * <p>Por padrão, o valor é definido automaticamente como o CPF informado na criação
     * do usuário.</p>
     */
    private String usuario;

    /**
     * Senha utilizada para autenticação no sistema.
     *
     * <p>Este atributo é protegido para permitir acesso por subclasses,
     * como {@code Administrador}.</p>
     */
    protected String senha;

    /**
     * Indica se o usuário possui permissões administrativas.
     *
     * <p>Usuários com permissão podem executar funções restritas dentro do sistema.</p>
     */
    protected boolean permissao;

    /**
     * Construtor da classe Usuario.
     *
     * <p>Inicializa os dados pessoais herdados de {@link Pessoa} e define o nome de
     * usuário como o próprio CPF do usuário.</p>
     *
     * @param nome     nome completo do usuário
     * @param endereco endereço do usuário
     * @param telefone telefone de contato
     * @param email    e-mail do usuário
     * @param cpf      CPF, que também será usado como nome de usuário
     */
    public Usuario(String nome, String endereco, String telefone, String email, String cpf) {
        super(nome, endereco, telefone, email, cpf);
        this.usuario = cpf;
    }

    /**
     * Retorna o nome de usuário utilizado no login.
     *
     * @return nome de usuário (normalmente o CPF)
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Retorna a senha cadastrada do usuário.
     *
     * @return senha do usuário
     */
    public String getSenha() {
        return senha;
    }

    /**
     * Atualiza a senha do usuário.
     *
     * @param senha nova senha a ser configurada
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * Informa se o usuário possui permissões administrativas.
     *
     * @return {@code true} se tiver permissão administrativa, {@code false} caso contrário
     */
    public boolean isPermissao() {
        return permissao;
    }
}
