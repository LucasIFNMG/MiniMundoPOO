/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.MiniMundo.DomainModel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author pimen
 */
public class Compra 
{
    private int id;
    private int quantidade;
    private Date data;
    private BigDecimal valorTotal;
    private BigDecimal precoCompra;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getPrecoCompra() {
        return precoCompra;
    }

    public void setPrecoCompra(BigDecimal precoCompra) {
        this.precoCompra = precoCompra;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.id;
        hash = 37 * hash + this.quantidade;
        hash = 37 * hash + Objects.hashCode(this.data);
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
        final Compra other = (Compra) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.quantidade != other.quantidade) {
            return false;
        }
        if (!Objects.equals(this.data, other.data)) {
            return false;
        }
        if (!Objects.equals(this.valorTotal, other.valorTotal)) {
            return false;
        }
        if (!Objects.equals(this.precoCompra, other.precoCompra)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "Compra{" + "id=" + id + ", quantidade=" + quantidade + ", data=" + data + ", valorTotal=" + valorTotal + ", precoCompra=" + precoCompra + '}';
    }
     
}
