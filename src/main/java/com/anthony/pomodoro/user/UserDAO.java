package com.anthony.pomodoro.user;

import com.anthony.pomodoro.database.DataBaseConnections;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private  Connection conexao;

    public UserDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public UserDAO() {
        inicializarConexao();
    }

    public void inicializarConexao () {
        if (this.conexao == null) {
            this.conexao = DataBaseConnections.getConexao();
        }
        if (this.conexao == null) {
            throw new RuntimeException("Conexão não inicializada.");
        }
    }



    public void inserirUsuario (String nome) {
        String sql = "INSERT INTO users (nome) VALUES (?)";
        try {

            inicializarConexao();
            var statement = conexao.prepareStatement(sql);
            statement.setString(1, nome);
            statement.executeUpdate();
            System.out.println("Dados inseridos com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro ao inserir dados: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void atualizarNome (int id, String nome) {
        String sql = "UPDATE users SET nome = ? WHERE id = ?";
        try {

            inicializarConexao();
            var statement = conexao.prepareStatement(sql);
            statement.setString(1, nome);
            statement.setInt(2, id);
            statement.executeUpdate();
            System.out.println("Dados atualizados com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro ao atualizar dados: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void deletarUsuario (int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try {

            inicializarConexao();
            var statement = conexao.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("Dados deletados com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro ao deletar dados: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public User buscarUsuarioPorNome(String nome) {
        String sql = "SELECT id ,nome FROM public.users WHERE nome= ?";
        try {
            inicializarConexao();
            PreparedStatement statement = conexao.prepareStatement(sql);
            statement.setString(1, nome);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nomeUsuario = resultSet.getString("nome");
                return new User(nomeUsuario, id); // Certifique-se que existe esse construtor
            }

        } catch (Exception e) {
            System.err.println("Erro ao buscar usuário: " + e.getMessage());
        }
        return null;
    }

    public List<User> listarUsuarios() {
        String sql = "SELECT * FROM users";
        List<User> usuarios = new ArrayList<>();

        try (Connection conexao = DataBaseConnections.getConexao();
             var statement = conexao.prepareStatement(sql);
             var resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                // Obtém o nome diretamente do ResultSet
                String nome = resultSet.getString("nome");
                User usuario = new User(nome);
                usuarios.add(usuario);
            }

            System.out.println("Dados listados com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro ao listar dados: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return usuarios;
    }

    public List<User> listarUsuariosID () {

        String sql = "SELECT id FROM users";
        List<User> usuarios = new ArrayList<>();

        try (Connection conexao = DataBaseConnections.getConexao();
             var statement = conexao.prepareStatement(sql);
             var resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                User usuario = new User(resultSet.getString("nome"));
                usuarios.add(usuario);
            }

            System.out.println("IDs listados com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro ao listar IDs: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return usuarios;
    }
    public class CadastroUsuario {
        public void cadastrarUsuario(String nome) {
            if (nome == null || nome.trim().isEmpty()) {
                System.out.println("Nome inválido. O cadastro não pode ser realizado.");
                return;
            }

            try {
                inserirUsuario(nome);
                System.out.println("Usuário cadastrado com sucesso!");
            } catch (Exception e) {
                System.err.println("Erro ao cadastrar usuário: " + e.getMessage());
            }
        }
    }
}