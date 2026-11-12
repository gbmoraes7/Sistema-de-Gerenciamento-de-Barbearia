package login;

import entidades.Administrador;
import entidades.Usuario;
import java.util.InputMismatchException;
import java.util.Scanner;
import main.Sistema;

/**
 * Classe responsável pelo processo de login no sistema.
 * 
 * Permite autenticar usuários (funcionários ou administrador) e alterar
 * senhas temporárias.
 */
public class Login {

    /** Scanner para leitura de dados do usuário */
    private final Scanner input = new Scanner(System.in);

    /**
     * Realiza o login de um usuário.
     * Solicita usuário e senha, valida e permite troca de senha temporária.
     * 
     * @return true se login for bem-sucedido, false caso contrário
     */
    public boolean realizarLogin() {
        try {
            System.out.print("Usuário: ");
            String login = input.nextLine();

            // Busca usuário entre funcionários
            Usuario usuario = Sistema.getSistema()
                    .getGestaoFuncionario()
                    .getListaFuncionario()
                    .stream()
                    .filter(u -> u.getUsuario().equals(login))
                    .findFirst()
                    .orElse(null);

            // Verifica se é o administrador
            if (usuario == null && login.equals(Administrador.getInstance().getUsuario())) {
                usuario = Administrador.getInstance();
            }

            if (usuario != null) {
                System.out.print("Senha: ");
                String senha = input.nextLine();

                if (usuario.getSenha().equals(senha)) {
                    // Permite trocar senha temporária
                    if (senha.equals("admin123") || senha.equals("funcionario123")) {
                        trocarSenha(usuario);
                    }
                    return true;
                } else {
                    System.out.println("Senha incorreta.");
                    return false;
                }
            } else {
                System.out.println("Usuário não encontrado.");
                return false;
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida.");
            input.nextLine(); // limpa buffer do Scanner
            return false;
        } catch (Exception e) {
            System.out.println("Erro no login: " + e.getMessage());
            return false;
        }
    }

    /**
     * Permite ao usuário alterar sua senha.
     * Não permite que a nova senha seja igual à antiga.
     * 
     * @param usuario usuário que deseja alterar a senha
     */
    public void trocarSenha(Usuario usuario) {
        try {
            System.out.print("Insira a nova senha: ");
            String novaSenha = input.nextLine();

            while (usuario.getSenha().equals(novaSenha)) {
                System.out.print("A nova senha não pode ser igual à antiga.\nInsira a nova senha: ");
                novaSenha = input.nextLine();
            }

            usuario.setSenha(novaSenha);
            System.out.println("Senha alterada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao alterar a senha: " + e.getMessage());
        }
    }
}
