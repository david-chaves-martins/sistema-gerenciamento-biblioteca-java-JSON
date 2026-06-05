# Sistema de Gestão de Biblioteca com Autenticação de Perfis

Este é um sistema completo de gerenciamento de biblioteca física desenvolvido em **Java**, focado na aplicação prática dos pilares da Orientação a Objetos e na estruturação limpa de código.
**[Clique aqui para acessar a página inicial do sistema no navegador](https://test-data-tool--nynyx23.replit.app)



---

##  Funcionalidades

* **Controle de Acesso Dinâmico:** Fluxos de menus e permissões totalmente separados por perfis de acesso (Funcionários vs. Clientes) com validação de login por e-mail e senha.
* **Autocadastro de Usuários:** Menu inicial que permite que novos clientes ou administradores criem suas credenciais de forma independente.
* **Gestão de Acervo (CRUD):** Leitura, busca por termos, cadastro e edição de livros de forma otimizada via terminal.
* **Módulo de Movimentação:** Controle rigoroso de empréstimos e devoluções associados diretamente à carteirinha gerada para o cliente.

##  Diferenciais Técnicos (Arquitetura)

* **Polimorfismo e Herança:** Classes `Cliente` e `Funcionario` herdando de uma classe abstrata `Pessoa`.
* **Arquitetura Baseada em Interfaces:** Uso estratégico de contratos (`Leitura`, `Movimentar`, `Autenticavel`, `GerenciamentoAcervo`) para garantir o baixo acoplamento do sistema.
* **Persistência de Dados (JSON):** Integração com a biblioteca **Google Gson** para salvar e carregar as listas de dados automaticamente em arquivos locais `.json`.
* **Tratamento de Erros:** Fluxos blindados com blocos `try-catch` para evitar falhas por entradas inválidas no console.
