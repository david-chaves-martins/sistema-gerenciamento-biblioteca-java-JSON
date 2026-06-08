import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Scanner;

public class BibliotecaService implements Leitura, Movimentar, GerenciamentoAcervo, GerenciamentoPessoas, Autenticavel {

    Scanner sc = new Scanner(System.in);
    ArrayList<Livros> listaLivros = new ArrayList<>();
    ArrayList<Cliente> listaClientes = new ArrayList<>();
    ArrayList<Funcionario> listaFuncionarios = new ArrayList<>();

    public BibliotecaService() {
        carregarDados();
    }

    private void salvarDados() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try {
            FileWriter writerLivros = new FileWriter("livros.json");
            gson.toJson(listaLivros, writerLivros);
            writerLivros.close();

            FileWriter writerClientes = new FileWriter("clientes.json");
            gson.toJson(listaClientes, writerClientes);
            writerClientes.close();

            FileWriter writerFuncionarios = new FileWriter("funcionarios.json");
            gson.toJson(listaFuncionarios, writerFuncionarios);
            writerFuncionarios.close();

        } catch (Exception e) {
            System.out.println("Erro ao salvar os dados no disco: " + e.getMessage());
        }
    }

    private void carregarDados() {
        Gson gson = new Gson();
        File arquivoLivros = new File("livros.json");
        File arquivoClientes = new File("clientes.json");
        File arquivoFuncionarios = new File("funcionarios.json");

        try {
            if (arquivoLivros.exists()) {
                Type tipoListaLivros = new TypeToken<ArrayList<Livros>>() {
                }.getType();
                listaLivros = gson.fromJson(new FileReader(arquivoLivros), tipoListaLivros);
                if (listaLivros == null) {
                    listaLivros = new ArrayList<>();
                }
            } else {
                listaLivros.add(new Livros("O Senhor dos Anéis", "J.R.R. Tolkien", "29/07/1954"));
            }

            if (arquivoClientes.exists()) {
                Type tipoListaClientes = new TypeToken<ArrayList<Cliente>>() {
                }.getType();
                listaClientes = gson.fromJson(new FileReader(arquivoClientes), tipoListaClientes);
                if (listaClientes == null) {
                    listaClientes = new ArrayList<>();
                }
            } else {
                listaClientes.add(new Cliente("João", "111.222.333-44", 25, "Silva", "joao@email.com", "senha123"));
            }

            if (arquivoFuncionarios.exists()) {
                Type tipoListaFuncionarios = new TypeToken<ArrayList<Funcionario>>() {
                }.getType();
                listaFuncionarios = gson.fromJson(new FileReader(arquivoFuncionarios), tipoListaFuncionarios);
                if (listaFuncionarios == null) {
                    listaFuncionarios = new ArrayList<>();
                }
            } else {
                listaFuncionarios.add(new Funcionario("Maria", "999.888.777-66", 30, "Souza", "maria@biblioteca.com", "admin", "Bibliotecária"));
            }

            atualizarContadores();
            salvarDados();

        } catch (Exception e) {
            System.out.println("Erro ao carregar os dados: " + e.getMessage());
        }
    }

    private void atualizarContadores() {
        int maiorIdPessoa = -1;

        for (Cliente cliente : listaClientes) {
            if (cliente.getId() > maiorIdPessoa) {
                maiorIdPessoa = cliente.getId();
            }
        }

        for (Funcionario funcionario : listaFuncionarios) {
            if (funcionario.getId() > maiorIdPessoa) {
                maiorIdPessoa = funcionario.getId();
            }
        }

        Pessoa.setContadorId(maiorIdPessoa + 1);

        int maiorIdLivro = 0;

        for (Livros livro : listaLivros) {
            if (livro.getId() > maiorIdLivro) {
                maiorIdLivro = livro.getId();
            }
        }

        Livros.setContadorId(maiorIdLivro + 1);
    }



    @Override
    public void listarLivros() {

        for (Livros listaLivro : listaLivros) {
            System.out.println("Livros:" + listaLivro.getTitulo());
            System.out.println("Autor:" + listaLivro.getAutor());
            System.out.println("Disponibilidade:" + (listaLivro.isStatus() ? "Disponível" : "Indisponivel"));
            System.out.println("----------------");
        }
    }

    @Override
    public void emprestarLivro() {
        while (true) {
            System.out.print("Digite o código da carteirinha do cliente (ex: CART-1): ");
            String carteirinhaDigitada = sc.nextLine().trim();

            Cliente clienteAchado = null;
            for (Cliente c : listaClientes) {
                if (c.getCarteirinha().equalsIgnoreCase(carteirinhaDigitada)) {
                    clienteAchado = c;
                    break;
                }
            }

            if (clienteAchado == null) {
                System.out.println(" Cliente não encontrado! Cadastro obrigatório antes do empréstimo.");
                return;
            }
            System.out.print("Digite o titulo que deseja emprestado (ou 'sair' para cancelar): ");
            String tituloDigitado = sc.nextLine();
            if (tituloDigitado.equalsIgnoreCase("sair")) {
                System.out.println("Sistema encerrado.");
                return;
            }
            for (Livros livro : listaLivros) {
                if (livro.getTitulo().equalsIgnoreCase(tituloDigitado)) {
                    if (livro.isStatus()) {
                        livro.setStatus(false);
                        clienteAchado.getLivrosEmprestados().add(livro);
                        System.out.println("Livro " + livro.getTitulo() + " emprestado com sucesso para " + clienteAchado.getNome() + "!");

                        salvarDados(); // CHAMA O JSON AQUI!
                        return;
                    } else {
                        System.out.println("Livro " + livro.getTitulo() + " já esta emprestado");
                        return;
                    }
                }
            }
            System.out.println("Livro não encontrado, tente novamente!");
        }
    }

    @Override
    public void procurarLivro() {
        while (true) {
            System.out.print("Digite o titulo que deseja procurar: ");
            String tituloDigitado = sc.nextLine();
            if (tituloDigitado.equalsIgnoreCase("sair")) {
                System.out.println("Sistema encerrado.");
                return;
            }
            for (Livros listaLivro : listaLivros) {
                if (listaLivro.getTitulo().equalsIgnoreCase(tituloDigitado)) {
                    System.out.println("Livro encontrado com sucesso!");
                    System.out.println("Livro: " + listaLivro.getTitulo());
                    System.out.println("Autor: " + listaLivro.getAutor());
                    System.out.println("Disponibilidade: " + listaLivro.isStatus());
                    return;
                }
            }
            System.out.println("Livro não encontrado. Digite 'sair' para voltar ou tente outro título.");
        }
    }

    @Override
    public void devolverLivro() {
        while (true) {
            System.out.print("Digite o código da carteirinha do cliente que está devolvendo: ");
            String carteirinhaDigitada = sc.nextLine().trim();

            Cliente clienteAchado = null;
            for (Cliente c : listaClientes) {
                if (c.getCarteirinha().equalsIgnoreCase(carteirinhaDigitada)) {
                    clienteAchado = c;
                    break;
                }
            }

            if (clienteAchado == null) {
                System.out.println(" Carteirinha não encontrada.");
                return;
            }
            System.out.print("Digite o titulo que deseja devolver (ou 'sair' para cancelar): ");
            String tituloDigitado = sc.nextLine();

            if (tituloDigitado.equalsIgnoreCase("sair")) {
                System.out.println("Sistema encerrado.");
                return;
            }
            for (Livros listaLivro : listaLivros) {
                if (listaLivro.getTitulo().equalsIgnoreCase(tituloDigitado)) {
                    if (!listaLivro.isStatus()) {
                        boolean livroPertenceAoCliente = clienteAchado.getLivrosEmprestados()
                                .removeIf(l -> l.getTitulo().equalsIgnoreCase(tituloDigitado));

                        if (!livroPertenceAoCliente) {
                            System.out.println("Este livro não está emprestado para " + clienteAchado.getNome() + ".");
                            return;
                        }

                        listaLivro.setStatus(true);

                        System.out.println("Livro " + listaLivro.getTitulo() + " devolvido com sucesso por " + clienteAchado.getNome() + "!");

                        salvarDados(); // CHAMA O JSON AQUI!
                    } else {
                        System.out.println("Livro " + listaLivro.getTitulo() + " já está na biblioteca!");
                    }

                    return;
                }
            }
            System.out.println("Livro procurado não existe na biblioteca, tente novamente!");
        }
    }

    @Override
    public void cadastrarLivro() {
        System.out.println("Qual o titulo do livro que deseja cadastrar?!");
        String nome = sc.nextLine();

        System.out.println("Qual autor do livro que deseja cadastrar?!");
        String autor = sc.nextLine();

        for (Livros livro : listaLivros) {
            if (livro.getTitulo().equalsIgnoreCase(nome) && livro.getAutor().equalsIgnoreCase(autor)) {
                System.out.println("Este livro já está cadastrado no acervo.");
                return;
            }
        }

        java.time.LocalDate dataValida = null;
        String anoPublicacao = ""; // Aqui vai ficar a data formatada final (ex: 10/09/2007)

        while (dataValida == null) {
            System.out.print("Qual a data/ano de publicação do livro? (ex: 10092007 ou 10/09/2007): ");
            String entrada = sc.nextLine().trim();

            java.time.format.DateTimeFormatter formatoSemBarra = java.time.format.DateTimeFormatter.ofPattern("ddMMyyyy");
            java.time.format.DateTimeFormatter formatoComBarra = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");

            try {
                if (entrada.contains("/")) {
                    dataValida = java.time.LocalDate.parse(entrada, formatoComBarra);
                } else {
                    dataValida = java.time.LocalDate.parse(entrada, formatoSemBarra);
                }

                anoPublicacao = dataValida.format(formatoComBarra);

            } catch (java.time.format.DateTimeParseException e) {
                System.out.println("Data inválida! Digite 8 números ou no formato DD/MM/AAAA.");
            }
        }

        Livros novoLivro = new Livros(nome, autor, anoPublicacao);

        novoLivro.setStatus(true);

        listaLivros.add(novoLivro);

        System.out.println("\n Livro \"" + nome + "\" cadastrado com sucesso com a data: " + anoPublicacao);

        salvarDados(); // CHAMA O JSON AQUI!
    }

    @Override
    public void editarLivro() {

        System.out.print("Qual o título do livro que deseja editar? ");
        String tituloBusca = sc.nextLine();

        for (Livros livro : listaLivros) {
            if (livro.getTitulo().equalsIgnoreCase(tituloBusca)) {
                System.out.println("Livro encontrado: " + livro.getTitulo() + " (Autor: " + livro.getAutor() + ")" + " Data: " + livro.getAnoPublicacao());
                System.out.println("--- Digite os novos dados ---");

                System.out.print("Qual é o NOVO título? ");
                String novoTitulo = sc.nextLine();

                System.out.print("Qual é o NOVO autor? ");
                String novoAutor = sc.nextLine();

                java.time.LocalDate dataValida = null;
                String novaData = "";

                java.time.format.DateTimeFormatter formatoSemBarra = java.time.format.DateTimeFormatter.ofPattern("ddMMyyyy");
                java.time.format.DateTimeFormatter formatoComBarra = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");

                while (dataValida == null) {
                    System.out.print("Qual a NOVA data de publicação? (ex: 10092007 ou 10/09/2007): ");
                    String entrada = sc.nextLine().trim();

                    try {
                        if (entrada.contains("/")) {
                            dataValida = java.time.LocalDate.parse(entrada, formatoComBarra);
                        } else {
                            dataValida = java.time.LocalDate.parse(entrada, formatoSemBarra);
                        }
                        novaData = dataValida.format(formatoComBarra);

                    } catch (java.time.format.DateTimeParseException e) {
                        System.out.println("Data inválida! Digite 8 números ou no formato DD/MM/AAAA.");
                    }
                }

                // Agora atualizamos o objeto que achamos na lista com os 3 dados novos!
                livro.setTitulo(novoTitulo);
                livro.setAutor(novoAutor);
                livro.setAnoPublicacao(novaData); // Usando a novaData bonitinha e validada

                System.out.println("\n Livro editado com sucesso!");

                salvarDados(); // CHAMA O JSON AQUI!
                return;
            }
        }

        System.out.println(" Livro não encontrado na biblioteca.");
    }

    @Override
    public void cadastrarCliente() {

        System.out.print("Qual o nome do cliente? ");
        String nome = sc.nextLine();

        System.out.print("Qual o sobrenome do cliente? ");
        String sobrenome = sc.nextLine();

        System.out.print("Qual o CPF do cliente? ");
        String cpf = sc.nextLine();

        System.out.print("Qual a idade do cliente? ");
        int idade = 0;
        try {
            idade = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Idade inválida. O cadastro prosseguirá com idade 0.");
        }

        System.out.print("Qual o email do cliente? ");
        String email = sc.nextLine();

        for (Cliente cliente : listaClientes) {
            if (cliente.getCpf().equalsIgnoreCase(cpf)) {
                System.out.println("Já existe um cliente cadastrado com este CPF.");
                return;
            }
            if (cliente.getEmail().equalsIgnoreCase(email)) {
                System.out.println("Já existe um cliente cadastrado com este email.");
                return;
            }
        }

        System.out.print("Crie uma senha para o cliente: ");
        String senha = sc.nextLine();

        // Criando o objeto Cliente (ID, carteirinha e lista vazia são gerados sozinhos!)
        Cliente novoCliente = new Cliente(nome, cpf, idade, sobrenome, email, senha);

        // Salvando o cliente na nossa lista
        listaClientes.add(novoCliente);

        System.out.println("\n Cliente \"" + nome + " " + sobrenome + "\" cadastrado com sucesso!");
        System.out.println(" A carteirinha gerada para este cliente é: " + novoCliente.getCarteirinha());

        salvarDados(); // CHAMA O JSON AQUI!
    }

    @Override
    public void cadastrarFuncionario() {
        System.out.println("\n=== REGISTO DE NOVO FUNCIONÁRIO ===");

        System.out.print("Qual o nome do funcionário? ");
        String nome = sc.nextLine();

        System.out.print("Qual o sobrenome do funcionário? ");
        String sobrenome = sc.nextLine();

        System.out.print("Qual o CPF (ou NIF) do funcionário? ");
        String cpf = sc.nextLine();

        System.out.print("Qual a idade do funcionário? ");
        int idade = 0;
        try {
            idade = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Idade inválida. O registo prosseguirá com idade 0.");
        }

        System.out.print("Qual o email do funcionário? ");
        String email = sc.nextLine();

        for (Funcionario funcionario : listaFuncionarios) {
            if (funcionario.getCpf().equalsIgnoreCase(cpf)) {
                System.out.println("Já existe um funcionário cadastrado com este CPF.");
                return;
            }
            if (funcionario.getEmail().equalsIgnoreCase(email)) {
                System.out.println("Já existe um funcionário cadastrado com este email.");
                return;
            }
        }

        System.out.print("Crie uma senha para o funcionário: ");
        String senha = sc.nextLine();

        System.out.print("Qual o cargo do funcionário (ex: Bibliotecário, Atendente, Gerente)? ");
        String cargo = sc.nextLine();

        // Cria o objeto Funcionario (O ID e a Matrícula são gerados sozinhos!)
        Funcionario novoFuncionario = new Funcionario(nome, cpf, idade, sobrenome, email, senha, cargo);

        // Guarda o funcionário na nossa lista
        listaFuncionarios.add(novoFuncionario);

        System.out.println("\n Funcionário \"" + nome + " " + sobrenome + "\" registado com sucesso!");
        System.out.println(" A matrícula gerada para este funcionário é: " + novoFuncionario.getMatricula());

        salvarDados(); // CHAMA O JSON AQUI!
    }

    @Override
    public Funcionario buscarFuncionarioPorLogin(String email, String senha) {
        for (Funcionario f : listaFuncionarios) {
            if (f.getEmail().equalsIgnoreCase(email) && f.getSenha().equals(senha)) {
                return f;
            }
        }
        return null;
    }

    @Override
    public Cliente buscarClientePorLogin(String email, String senha) {
        for (Cliente c : listaClientes) {
            if (c.getEmail().equalsIgnoreCase(email) && c.getSenha().equals(senha)) {
                return c;
            }
        }
        return null;
    }
}
