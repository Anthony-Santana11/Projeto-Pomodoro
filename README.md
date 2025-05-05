# Pomodoro Timer

Este é um projeto de um aplicativo de gerenciamento de tempo baseado na técnica Pomodoro. Ele permite que os usuários iniciem sessões de estudo, pausem, reiniciem e registrem suas sessões em um banco de dados.

## Funcionalidades

- **Gerenciamento de Usuários**:
  - Cadastro de novos usuários.
  - Busca de usuários existentes.
  - Atualização e exclusão de usuários.

- **Sessões de Estudo**:
  - Início de sessões de Pomodoro de 25 ou 50 minutos.
  - Pausa, reinício e término de sessões.
  - Registro automático de sessões no banco de dados.

- **Banco de Dados**:
  - Registro de usuários e sessões de estudo.
  - Listagem de usuários e sessões.

## Tecnologias Utilizadas

- **Linguagem**: Java
- **Gerenciador de Dependências**: Maven
- **Banco de Dados**: PostgreSQL
- **IDE**: IntelliJ IDEA

## Estrutura do Projeto

- `src/main/java/com/anthony/pomodoro/database`: Classes para conexão e manipulação do banco de dados.
- `src/main/java/com/anthony/pomodoro/menu`: Lógica de interação com o usuário.
- `src/main/java/com/anthony/pomodoro/timer`: Implementação do temporizador Pomodoro.
- `src/main/java/com/anthony/pomodoro/user`: Gerenciamento de usuários.

## Como Executar

1. **Pré-requisitos**:
   - Java 17 ou superior.
   - PostgreSQL configurado e rodando.
   - Maven instalado.

2. **Configuração do Banco de Dados**:
   - Crie um banco de dados chamado `pomodoro`.
   - Configure as credenciais no arquivo de conexão do banco de dados.

3. **Compilação e Execução**:
   - Clone o repositório.
   - Navegue até o diretório do projeto.
   - Execute o comando: `mvn clean install`.
   - Inicie o programa com: `java -jar target/pomodoro-timer.jar`.

## Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou enviar pull requests.

## Licença

Este projeto está licenciado sob a licença MIT. Consulte o arquivo `LICENSE` para mais detalhes.
