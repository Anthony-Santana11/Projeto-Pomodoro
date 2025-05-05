package com.anthony.pomodoro.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudySessionsDAO {
    private Connection conexao;

    public void inicializarConexao () {
       this.conexao =  DataBaseConnections.getConexao();
    }

    public void inserirEstudo(int userId, java.util.Date data, int tempoEstudo) {
        if (userId <= 0) {
            System.out.println("Erro: userId inválido. Não é possível inserir o estudo.");
            return;
        }

        java.sql.Date sqlDate = new java.sql.Date(data.getTime());
        System.out.println("Inserindo estudo com userId: " + userId + ", data: " + sqlDate + ", tempo: " + tempoEstudo);

        if (conexao == null) {
            System.out.println("Erro: Conexão não estabelecida.");
            return;
        }

        String sql = "INSERT INTO study_sessions (user_id, study_date, minutes) VALUES (?, ?, ?)";

        try {
            conexao.setAutoCommit(true);
            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setInt(1, userId);
                stmt.setDate(2, sqlDate);
                stmt.setInt(3, tempoEstudo);

                int linhasAfetadas = stmt.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println(" Sessão de estudo salva com sucesso no banco!");
                    conexao.commit();
                } else {
                    System.out.println(" Nenhuma linha foi inserida.");
                    conexao.rollback();
                }
            } catch (SQLException e) {
                conexao.rollback();
                System.out.println(" Erro ao salvar sessão de estudo: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("Erro de transação: " + e.getMessage());
        } finally {
            try {
                conexao.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void atualizarEstudo (int user_id, java.sql.Date study_date, int minutes) {
        String sql = "UPDATE study_sessions SET study_date = ?, minutes = ? WHERE user_id = ?";
        java.sql.Date sqlDate = new java.sql.Date(study_date.getTime());
        try {

            inicializarConexao();
            var statement = conexao.prepareStatement(sql);
            statement.setDate(1, sqlDate);
            statement.setInt(2, minutes);
            statement.setInt(3, user_id);
            statement.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deletarEstudo (int user_id) {
        String sql = "DELETE FROM study_sessions WHERE user_id = ?";
        try {

            inicializarConexao();
            var statement = conexao.prepareStatement(sql);
            statement.setInt(1, user_id);
            statement.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void listarEstudos () {
        String sql = "SELECT * FROM study_sessions";
        try {

            inicializarConexao();
            var statement = conexao.prepareStatement(sql);
            var resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int user_id = resultSet.getInt("user_id");
               java.sql.Date study_date = resultSet.getDate("study_date");
                int minutes = resultSet.getInt("minues");
                System.out.println("ID: " + user_id + ", Data: " + study_date + ", Tempo: " + minutes);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void listarEstudosID () {
        String sql = "SELECT id FROM study_sessions";
        try {

            inicializarConexao();
            var statement = conexao.prepareStatement(sql);
            var resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int user_id = resultSet.getInt("id");
                System.out.println("ID: " + user_id);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

