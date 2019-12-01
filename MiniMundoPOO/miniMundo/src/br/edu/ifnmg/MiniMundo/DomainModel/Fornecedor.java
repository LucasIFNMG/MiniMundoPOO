/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.MiniMundo.DomainModel;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author pimen
 */
public class Fornecedor {
    private int id;
    private String razaoSocial;
    private String cnpj;
    private String email;
    private String rua;
    private int numero;
    private String bairro;
    private String cidade;
    private Estado estado;
    
    //40.376.505 /   0001-70
    
    //112.663.856-06
    
    private Pattern regex_cnpj = 
        Pattern.compile("/^\\d{2}\\.\\d{3}\\.\\d{3}\\/\\d{4}\\-\\d{2}$/");
    
    public Fornecedor() 
    {
        this.id = 0;
        this.razaoSocial = "";
        this.cnpj = "40.376.505/0001-70";
        this.email = "forn@abc.com";
        this.rua = "A";
        this.bairro = "Centro";
        this.numero = 0;
    }

    public int getId() {
        return id;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCnpj() 
    {
        return cnpj.substring(0, 2)+"."+
               cnpj.substring(2, 5)+"."+
               cnpj.substring(5, 8)+"/"+
               cnpj.substring(8,12)+"-"+
               cnpj.substring(12, 14);
    }

    public void setCnpj(String cnpj) throws ErroValidacaoException {
        Matcher m = regex_cnpj.matcher(cnpj);
        if(m.matches())
            this.cnpj = cnpj.replace(".", "").replace("-", "").replace("/", "");
        else
            throw new ErroValidacaoException("CNPJ Inválido!");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.id;
        hash = 79 * hash + Objects.hashCode(this.cnpj);
        hash = 79 * hash + Objects.hashCode(this.email);
        hash = 79 * hash + Objects.hashCode(this.razaoSocial);
        hash = 79 * hash + Objects.hashCode(this.rua);
        hash = 79 * hash + this.numero;
        hash = 79 * hash + Objects.hashCode(this.bairro);
        hash = 79 * hash + Objects.hashCode(this.cidade);
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
        final Fornecedor other = (Fornecedor) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.numero != other.numero) {
            return false;
        }
        if (!Objects.equals(this.cnpj, other.cnpj)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.razaoSocial, other.razaoSocial)) {
            return false;
        }
        if (!Objects.equals(this.rua, other.rua)) {
            return false;
        }
        if (!Objects.equals(this.bairro, other.bairro)) {
            return false;
        }
        if (!Objects.equals(this.cidade, other.cidade)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Fornecedor{" + "id=" + id + ", cnpj=" + cnpj + ", email=" + email + ", razaoSocial=" + razaoSocial + ", rua=" + rua + ", numero=" + numero + ", bairro=" + bairro + ", cidade=" + cidade + '}';
    }
    
    
    
}
