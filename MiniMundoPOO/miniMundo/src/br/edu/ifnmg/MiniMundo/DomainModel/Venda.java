/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.MiniMundo.DomainModel;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author pimen
 */
public class Venda {
    private int id;
    private float precoVenda;
    private Date data;
    private int quant;
    private float valorTotal;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(float precoVenda) {
        this.precoVenda = precoVenda;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getQuant() {
        return quant;
    }

    public void setQuant(int quant) {
        this.quant = quant;
    }

    public float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + this.id;
        hash = 59 * hash + Float.floatToIntBits(this.precoVenda);
        hash = 59 * hash + Objects.hashCode(this.data);
        hash = 59 * hash + this.quant;
        hash = 59 * hash + Float.floatToIntBits(this.valorTotal);
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
        final Venda other = (Venda) obj;
        if (this.id != other.id) {
            return false;
        }
        if (Float.floatToIntBits(this.precoVenda) != Float.floatToIntBits(other.precoVenda)) {
            return false;
        }
        if (this.quant != other.quant) {
            return false;
        }
        if (Float.floatToIntBits(this.valorTotal) != Float.floatToIntBits(other.valorTotal)) {
            return false;
        }
        if (!Objects.equals(this.data, other.data)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Venda{" + "id=" + id + ", precoVenda=" + precoVenda + ", data=" + data + ", quant=" + quant + ", valorTotal=" + valorTotal + '}';
    }
    
    
}
