public interface Autenticavel {
    Funcionario buscarFuncionarioPorLogin(String email, String senha);
    Cliente buscarClientePorLogin(String email, String senha);
}