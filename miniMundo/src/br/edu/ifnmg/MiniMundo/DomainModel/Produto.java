/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.MiniMundo.DomainModel;

import java.util.Objects;

/**
 *
 * @author pimen
 */
public class Produto {
    private int id;
    private String descricao;
    private float precoCompra;
    private float precoVenda;
    private String uniVenda;
    private String uniCompra;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getPrecoCompra() {
        return precoCompra;
    }

    public void setPrecoCompra(float precoCompra) {
        this.precoCompra = precoCompra;
    }

    public float getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(float precoVenda) {
        this.precoVenda = precoVenda;
    }

    public String getUniVenda() {
        return uniVenda;
    }

    public void setUniVenda(String uniVenda) {
        this.uniVenda = uniVenda;
    }

    public String getUniCompra() {
        return uniCompra;
    }

    public void setUniCompra(String uniCompra) {
        this.uniCompra = uniCompra;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + this.id;
        hash = 17 * hash + Objects.hashCode(this.descricao);
        hash = 17 * hash + Float.floatToIntBits(this.precoCompra);
        hash = 17 * hash + Float.floatToIntBits(this.precoVenda);
        hash = 17 * hash + Objects.hashCode(this.uniVenda);
        hash = 17 * hash + Objects.hashCode(this.uniCompra);
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
        final Produto other = (Produto) obj;
        if (this.id != other.id) {
            return false;
        }
        if (Float.floatToIntBits(this.precoCompra) != Float.floatToIntBits(other.precoCompra)) {
            return false;
        }
        if (Float.floatToIntBits(this.precoVenda) != Float.floatToIntBits(other.precoVenda)) {
            return false;
        }
        if (!Objects.equals(this.descricao, other.descricao)) {
            return false;
        }
        if (!Objects.equals(this.uniVenda, other.uniVenda)) {
            return false;
        }
        if (!Objects.equals(this.uniCompra, other.uniCompra)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Produto{" + "id=" + id + ", descricao=" + descricao + ", precoCompra=" + precoCompra + ", precoVenda=" + precoVenda + ", uniVenda=" + uniVenda + ", uniCompra=" + uniCompra + '}';
    }
    
    
    
    
}
