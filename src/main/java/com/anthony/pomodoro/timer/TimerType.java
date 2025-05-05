package com.anthony.pomodoro.timer;

public enum TimerType {
    POMODORO_25(25, 5),
    POMODORO_50(50, 10);

    private final int duracao;
    private final int intervalo;

    TimerType(int duracao, int intervalo) {
        this.duracao = duracao;
        this.intervalo = intervalo;
    }

    public int getDuracao() {
        return duracao;
    }

    public int getIntervalo() {
        return intervalo;
    }
}