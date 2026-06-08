# Sistema de Gestão de Biblioteca com Autenticação de Perfis

Sistema de gerenciamento de biblioteca física desenvolvido em **Java**, com foco na aplicação prática de Orientação a Objetos, autenticação por perfil e persistência de dados em JSON.

[Ver interface gráfica demonstrativa no Replit](https://test-data-tool--nynyx23.replit.app)
---

## Funcionalidades

- **Controle de acesso por perfil:** menus e permissões separados para funcionários e clientes, com validação de login por e-mail e senha.
- **Autocadastro de usuários:** permite que novos clientes ou funcionários criem suas credenciais pelo menu inicial.
- **Gestão de acervo:** cadastro, listagem, busca e edição de livros via terminal.
- **Empréstimos e devoluções:** controle de movimentações associado à carteirinha do cliente.
- **Persistência local:** dados salvos automaticamente em arquivos `.json`.

## Diferenciais técnicos

- **Herança e abstração:** classes `Cliente` e `Funcionario` herdando da classe abstrata `Pessoa`.
- **Interfaces:** uso de contratos como `Leitura`, `Movimentar`, `Autenticavel`, `GerenciamentoAcervo` e `GerenciamentoPessoas`.
- **Persistência com Gson:** integração com a biblioteca **Google Gson** para salvar e carregar dados automaticamente.
- **Validações:** tratamento de entradas inválidas e verificações para evitar cadastros duplicados.
