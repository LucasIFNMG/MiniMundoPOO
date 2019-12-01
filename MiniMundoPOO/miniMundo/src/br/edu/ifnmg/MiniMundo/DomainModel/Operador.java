/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.MiniMundo.DomainModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author pimen
 */
public class Operador {
    private int id;
    private String nome;
    private String cpf;
    private String login;
    private String senha;
    private List<String> telefones;

    private Pattern regex_cpf = 
    Pattern.compile("\\d{3}\\.?\\d{3}\\.?\\d{3}\\-?\\d{2}");
    
    public Operador() 
    {
        this.id = 0;
        this.nome = "";
        this.cpf = "00000000000";
        this.login = "lucas";
        this.senha = "123";  
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<String> tel) {
        this.telefones = telefones;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws ErroValidacaoException {
        if(nome.length() < 3)
            throw new ErroValidacaoException("Nome deve ter no mínimo 3 caracteres!");
        this.nome = nome;
    }

    public String getCpf() {
     return  cpf.substring(0, 3)+"."+
             cpf.substring(3, 6)+"."+
             cpf.substring(6, 9)+"-"+
             cpf.substring(9, 11);
    }

    public void setCpf(String cpf) throws ErroValidacaoException {
        Matcher m = regex_cpf.matcher(cpf);
        if(m.matches())
            this.cpf = cpf.replace(".", "").replace("-", "");
        else
            throw new ErroValidacaoException("CPF Inválido!");
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String user) {
        this.login = user;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public void addTelefone(String telefone){
    if(! this.telefones.contains(telefone))
        this.telefones.add(telefone);
    }
    
    public void removeTelefone(String telefone){
        if(this.telefones.contains(telefone))
            this.telefones.remove(telefone);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.id;
        hash = 59 * hash + Objects.hashCode(this.telefones);
        hash = 59 * hash + Objects.hashCode(this.nome);
        hash = 59 * hash + Objects.hashCode(this.cpf);
        hash = 59 * hash + Objects.hashCode(this.login);
        hash = 59 * hash + Objects.hashCode(this.senha);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Operador other = (Operador) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.cpf, other.cpf)) {
            return false;
        }
        if (!Objects.equals(this.login, other.login)) {
            return false;
        }
        if (!Objects.equals(this.senha, other.senha)) {
            return false;
        }
        if (!Objects.equals(this.telefones, other.telefones)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Operador{" + "id=" + id + ", tel=" + telefones + ", nome=" + nome + ", cpf=" + cpf + ", user=" + login + ", senha=" + senha + '}';
    }
    
    
    
}
