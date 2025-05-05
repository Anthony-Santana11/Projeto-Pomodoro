package com.anthony.pomodoro.timer;

public class PomodoroTimer {
    private final TimerType tipo;
    private boolean pausado;
    private boolean ativo;
    private int tempoEstudado; // em minutos simulados
    private Thread thread;

    public PomodoroTimer(TimerType tipo) {
        this.tipo = tipo;
        this.pausado = false;
        this.ativo = false;
        this.tempoEstudado = 0;
    }

    public void iniciarPomodoro() {
        if (ativo) {
            System.out.println("Já existe um Pomodoro em andamento.");
            return;
        }

        ativo = true;
        System.out.println("Pomodoro iniciado! Duração: " + tipo.getDuracao() + " minutos. Intervalo: " + tipo.getIntervalo() + " minutos.");

        thread = new Thread(() -> {
            int minutosPassados = 0;

            while (minutosPassados < tipo.getDuracao() && ativo) {
                try {
                    if (!pausado) {
                        Thread.sleep(60000);
                        minutosPassados++;
                        tempoEstudado++;
                        System.out.println("Minuto: " + minutosPassados + "/" + tipo.getDuracao());
                    } else {
                        Thread.sleep(500);
                    }
                } catch (InterruptedException e) {
                    System.out.println("Timer interrompido.");
                    Thread.currentThread().interrupt();
                    break;
                }
            }

            if (ativo && !pausado) {
                System.out.println("Pomodoro finalizado!");
            }

            ativo = false;
        });

        thread.start();
    }

    public void pausarPomodoro() {
        if (ativo && !pausado) {
            pausado = true;
            System.out.println("Pomodoro pausado.");
        }
    }

    public void reiniciarPomodoro() {
        if (ativo && pausado) {
            pausado = false;
            System.out.println("Pomodoro retomado.");
        }
    }

    public void pararPomodoro() {
        if (ativo) {
            ativo = false;
            if (thread != null && thread.isAlive()) {
                thread.interrupt();
            }
            System.out.println("Pomodoro parado.");
        }
    }

    public boolean isPausado() {
        return pausado;
    }

    public int getTempoEstudado() {
        return tempoEstudado;
    }

    public boolean isAtivo() {
        return ativo;
    }
}
