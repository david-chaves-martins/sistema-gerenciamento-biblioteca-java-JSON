import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BibliotecaService biblioteca = new BibliotecaService();
        int perfil = -1;

        do {
            System.out.println("\n==================================");
            System.out.println("   BEM-VINDO AO SISTEMA DA BIBLIOTECA   ");
            System.out.println("==================================");
            System.out.println("Selecione uma opção:");
            System.out.println("1 - Sou Funcionário (Administrador)");
            System.out.println("2 - Sou Cliente (Usuário)");
            System.out.println("3 - Cadastrar-se (Criar Nova Conta)");
            System.out.println("0 - Sair do Sistema");
            System.out.print("Escolha uma opção: ");

            try {
                perfil = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(" Por favor, digite um número válido.");
                continue;
            }

            switch (perfil) {
                case 1:
                    menuFuncionario(sc, biblioteca);
                    break;
                case 2:
                    menuCliente(sc, biblioteca);
                    break;
                case 3:
                    menuCadastro(sc, biblioteca);
                    break;
                case 0:
                    System.out.println("Obrigado por usar o sistema da Biblioteca! Até logo.");
                    break;
                default:
                    System.out.println(" Opção inválida! Tente novamente.");
                    break;
            }

        } while (perfil != 0);

        sc.close();
    }

    private static void menuCadastro(Scanner sc, BibliotecaService biblioteca) {
        System.out.println("\n==================================");
        System.out.println("          CRIAR NOVA CONTA        ");
        System.out.println("==================================");
        System.out.println("Escolha o tipo de perfil que deseja criar:");
        System.out.println("1 - Criar conta como Cliente (Usuário)");
        System.out.println("2 - Criar conta como Funcionário (Administrador)");
        System.out.println("0 - Voltar ao Menu Principal");
        System.out.print("Escolha uma opção: ");

        int opcao = -1;
        try {
            opcao = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println(" Por favor, digite um número válido.");
            return;
        }

        switch (opcao) {
            case 1:
                biblioteca.cadastrarCliente();
                break;
            case 2:
                biblioteca.cadastrarFuncionario();
                break;
            case 0:
                System.out.println("Voltando ao menu principal...");
                break;
            default:
                System.out.println(" Opção inválida!");
                break;
        }
    }

    private static void menuFuncionario(Scanner sc, BibliotecaService biblioteca) {
        System.out.println("\n==================================");
        System.out.println("       LOGIN DE FUNCIONÁRIO       ");
        System.out.println("==================================");
        System.out.print("Digite o seu Email: ");
        String email = sc.nextLine();
        System.out.print("Digite a sua Senha: ");
        String senha = sc.nextLine();

        Funcionario funcionarioLogado = biblioteca.buscarFuncionarioPorLogin(email, senha);

        if (funcionarioLogado == null) {
            System.out.println("  Email ou Senha incorretos! Acesso negado.");
            return;
        }

        int opcao = -1;
        do {
            System.out.println("\n--- PAINEL DO FUNCIONÁRIO ---");
            System.out.println("Utilizador atual: " + funcionarioLogado.getNome() + " (" + funcionarioLogado.getCargo() + ")");
            System.out.println("1 - Cadastrar Novo Cliente");
            System.out.println("2 - Cadastrar Novo Funcionário");
            System.out.println("3 - Cadastrar Livro no Acervo");
            System.out.println("4 - Editar Dados de um Livro");
            System.out.println("5 - Realizar Empréstimo para Cliente");
            System.out.println("6 - Realizar Devolução de Livro");
            System.out.println("7 - Listar Todo o Acervo");
            System.out.println("8 - Procurar Livro");
            System.out.println("0 - Voltar ao Menu Principal (Logout)");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido.");
                continue;
            }

            switch (opcao) {
                case 1: biblioteca.cadastrarCliente(); break;
                case 2: biblioteca.cadastrarFuncionario(); break;
                case 3: biblioteca.cadastrarLivro(); break;
                case 4: biblioteca.editarLivro(); break;
                case 5: biblioteca.emprestarLivro(); break;
                case 6: biblioteca.devolverLivro(); break;
                case 7: biblioteca.listarLivros(); break;
                case 8: biblioteca.procurarLivro(); break;
                case 0: System.out.println("Saindo do painel do funcionário..."); break;
                default: System.out.println(" Opção inválida!"); break;
            }
        } while (opcao != 0);
    }

    private static void menuCliente(Scanner sc, BibliotecaService biblioteca) {
        System.out.println("\n==================================");
        System.out.println("         LOGIN DE CLIENTE         ");
        System.out.println("==================================");
        System.out.print("Digite o seu Email: ");
        String email = sc.nextLine();
        System.out.print("Digite a sua Senha: ");
        String senha = sc.nextLine();

        Cliente clienteLogado = biblioteca.buscarClientePorLogin(email, senha);

        if (clienteLogado == null) {
            System.out.println("  Email ou Senha incorretos! Acesso negado.");
            return;
        }

        int opcao = -1;
        do {
            System.out.println("\n--- PAINEL DO CLIENTE ---");
            System.out.println("Bem-vindo(a), " + clienteLogado.getNome() + " | Carteirinha: " + clienteLogado.getCarteirinha());
            System.out.println("1 - Ver Catálogo de Livros");
            System.out.println("2 - Procurar Livro Específico");
            System.out.println("0 - Voltar ao Menu Principal (Logout)");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido.");
                continue;
            }

            switch (opcao) {
                case 1: biblioteca.listarLivros(); break;
                case 2: biblioteca.procurarLivro(); break;
                case 0: System.out.println("Saindo do painel do cliente..."); break;
                default: System.out.println(" Opção inválida!"); break;
            }
        } while (opcao != 0);
    }
}