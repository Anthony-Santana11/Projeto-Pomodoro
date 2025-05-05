package com.anthony.pomodoro;

import com.anthony.pomodoro.database.DataBaseConnections;
import com.anthony.pomodoro.menu.MenuHandler;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        System.out.println("Bem-vindo ao Pomodoro Timer!");

        try (Connection conexao = DataBaseConnections.conectar()) {
            if (conexao == null) {
                System.out.println("Conexão com o banco de dados estabelecida com sucesso!");
                MenuHandler menuHandler = new MenuHandler(conexao);
                menuHandler.verificarOuCadastrarUsuario();
                menuHandler.capturarEscolha();
            } else {
                System.out.println("Falha ao conectar ao banco de dados.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }

        System.out.println("Programa encerrado. Até a próxima!");
    }
}