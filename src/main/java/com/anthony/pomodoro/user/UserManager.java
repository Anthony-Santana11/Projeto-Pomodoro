package com.anthony.pomodoro.user;
import java.util.List;

public class UserManager {
        private final UserDAO userDAO;

        public UserManager() {
            this.userDAO = new UserDAO();
        }

        public void adicionarUsuario(String nome) {
            userDAO.inserirUsuario(nome);
        }

        public void atualizarUsuario(int id, String nome) {
            userDAO.atualizarNome(id, nome);
        }

        public void removerUsuario(int id) {
            userDAO.deletarUsuario(id);
        }

        public List<User> listarTodosUsuarios() {
            return userDAO.listarUsuarios();
        }

        public List<User> listarUsuariosPorID() {
            return userDAO.listarUsuariosID();
        }
    }
