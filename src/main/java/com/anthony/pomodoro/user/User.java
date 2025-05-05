package com.anthony.pomodoro.user;

public class User {
    private String nome;
    private int id;

    public User(String nome){
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio");
        }
        this.nome = nome;
    }

    public User(String nome, int id){
    }

    public String getNome() {
        return nome;
    }


    public void setNome(String nome) {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio");
        }
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "nome='" + nome + '\'' +
                '}';
    }


}
