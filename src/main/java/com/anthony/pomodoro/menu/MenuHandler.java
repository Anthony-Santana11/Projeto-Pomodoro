package com.anthony.pomodoro.menu;

import com.anthony.pomodoro.database.StudySessionsDAO;
import com.anthony.pomodoro.timer.PomodoroTimer;
import com.anthony.pomodoro.timer.TimerType;
import com.anthony.pomodoro.user.User;
import com.anthony.pomodoro.user.UserDAO;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.Date;
import java.util.Scanner;

public class MenuHandler {
    private final Connection conexao;
    private final Scanner scanner;
    private final UserDAO userDAO;
    private final StudySessionsDAO studySessionsDAO;
    private PomodoroTimer pomodoroTimer;
    private User currentUser;

    public MenuHandler(Connection conexao) {
        this.conexao = conexao;
        this.scanner = new Scanner(System.in);
        this.userDAO = new UserDAO();
        this.studySessionsDAO = new StudySessionsDAO();
    }

    public void verificarOuCadastrarUsuario() {
        System.out.print("Digite seu nome de usuário: ");
        String nome = scanner.nextLine();

        currentUser = userDAO.buscarUsuarioPorNome(nome);

        if (currentUser == null) {
            System.out.println("Usuário não encontrado. Vamos cadastrá-lo.");
            userDAO.inserirUsuario(nome);
            currentUser = userDAO.buscarUsuarioPorNome(nome);
            System.out.println("Usuário cadastrado com sucesso!");
        } else {
            System.out.println("Bem-vindo de volta, " + currentUser.getNome() + "!");
        }
    }

    private void exibirMenuPrincipal() {
        System.out.println("\n Menu Pomodoro ");
        System.out.println("1. Iniciar Pomodoro (25 minutos)");
        System.out.println("2. Iniciar Pomodoro (50 minutos)");
        System.out.println("3. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private void exibirMenuPomodoro() {
        System.out.println("\n Opções durante o Pomodoro ");
        System.out.println("1. Pausar Pomodoro");
        System.out.println("2. Reiniciar Pomodoro");
        System.out.println("3. Parar Pomodoro e salvar sessão");
        System.out.println("4. Voltar ao menu principal");
        System.out.println("5. Sair do programa");
        System.out.print("Escolha uma opção: ");
    }

    public void capturarEscolha() {
        int escolha;
        do {
            exibirMenuPrincipal();
            try {
                escolha = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número.");
                continue;
            }

            switch (escolha) {
                case 1 -> iniciarPomodoro(TimerType.POMODORO_25);
                case 2 -> iniciarPomodoro(TimerType.POMODORO_50);
                case 3 -> {
                    System.out.println("Saindo do programa...");
                    System.exit(0);
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        } while (true);
    }

    private void iniciarPomodoro(TimerType tipo) {
        pomodoroTimer = new PomodoroTimer(tipo);
        pomodoroTimer.iniciarPomodoro();

        int escolha;
        do {
            exibirMenuPomodoro();
            try {
                escolha = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número.");
                continue;
            }

            switch (escolha) {
                case 1 -> pausarPomodoro();
                case 2 -> reiniciarPomodoro();
                case 3 -> {
                    pararPomodoro();
                    return;
                }
                case 4 -> {
                    System.out.println("Voltando ao menu principal...");
                    return;
                }
                case 5 -> {
                    System.out.println("Saindo do programa...");
                    System.exit(0);
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        } while (isPomodoroAtivo());
    }

    private void pausarPomodoro() {
        if (isPomodoroAtivo()) {
            pomodoroTimer.pausarPomodoro();
        } else {
            System.out.println("Nenhum Pomodoro em andamento.");
        }
    }

    private void reiniciarPomodoro() {
        if (isPomodoroAtivo()) {
            pomodoroTimer.reiniciarPomodoro();
        } else {
            System.out.println("Nenhum Pomodoro em andamento.");
        }
    }

    private void pararPomodoro() {
        if (isPomodoroAtivo()) {
            int tempoEstudo = pomodoroTimer.getTempoEstudado();
            pomodoroTimer.pararPomodoro();

            int userId = obterIdUsuarioAtual();
            Date dataAtual = java.sql.Date.valueOf(LocalDate.now());

            studySessionsDAO.inserirEstudo(userId, dataAtual, tempoEstudo);
            System.out.println("Sessão de estudo registrada com sucesso!");
        } else {
            System.out.println("Nenhum Pomodoro em andamento.");
        }
    }

    private boolean isPomodoroAtivo() {
        return pomodoroTimer != null;
    }

    private int obterIdUsuarioAtual() {
        return currentUser.getId();
    }
}