package br.edu.ifnmg.MiniMundo.Persistence;

import br.edu.ifnmg.MiniMundo.DomainModel.Produto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LucasIFNMG
 */

 public class ProdutoRepositorio extends BancoDados
 {

     public ProdutoRepositorio()
     {
         super();
     }

    public boolean Salvar(final Produto obj)
    {
        try 
        {
            if(obj.getId() == 0)
            {
                PreparedStatement sql = this.getConexao()
                    .prepareStatement("insert into Produtos(descricao, precoCompra, precoVenda, uniVenda, uniCompra) values (?, ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                
                sql.setString(1, obj.getDescricao() );
                sql.setBigDecimal(2, obj.getPrecoCompra() );
                sql.setBigDecimal(3, obj.getPrecoVenda() );
                sql.setString(4, obj.getUniVenda() );
                sql.setString(5, obj.getUniCompra() );
                
                if(sql.executeUpdate() > 0)
                {
                    ResultSet chave = sql.getGeneratedKeys();
                    chave.next();
                    obj.setId(chave.getInt(1) );
                    
                    return true;
                }

                else
                    return false;
            }

            else
            {
                PreparedStatement sql = this.getConexao()
                    .prepareStatement("update Produtos set descricao = ?, precoCompra = ?, precoVenda = ?, uniVenda = ?, uniCompra = ?, where id = ?)");

                sql.setString(1, obj.getDescricao() );
                sql.setBigDecimal(2, obj.getPrecoCompra() );
                sql.setBigDecimal(3, obj.getPrecoVenda() );
                sql.setString(4, obj.getUniVenda() );
                sql.setString(5, obj.getUniCompra() );
                sql.setInt(6, obj.getId() );
                
                if(sql.executeUpdate() > 0)
                {
                     return true;
                }
                else
                    return false;

            }

        } catch (SQLException ex)
        {
            System.out.println(ex.getMessage() );
        }

        return false;
    }
    
    public Produto Abrir(int id) throws SQLException
    {
        try
        {
            PreparedStatement sql = this.getConexao()
                .prepareStatement("select * from Produtos where id = ?");

            sql.setInt(1, id);

            ResultSet resultado = sql.executeQuery();

            resultado.next();

            Produto produto = new Produto();

            try
            {
                produto.setDescricao(resultado.getString("descricao") );
                produto.setPrecoCompra(resultado.getBigDecimal("precoCompra") );
                produto.setPrecoVenda(resultado.getBigDecimal("precoVenda") );
                produto.setUniVenda(resultado.getString("uniVenda") );
                produto.setUniCompra(resultado.getString("uniCompra") );                
            }

            catch(Exception ex)
            {
                produto = null;
            }

            return produto;

        }

        catch(SQLException ex)
        {
            System.out.println(ex.getMessage() );
        }

        return null;

    }

    public boolean Apagar(Produto obj)
    {
        try
        {
            PreparedStatement sql = this.getConexao()
                .prepareStatement("delete from Produtos where id = ?");

            sql.setInt(1, obj.getId() );

            if(sql.executeUpdate() > 0)
                return true;
            else
                return false;

        }   catch (SQLException ex)
        {
            System.out.println(ex.getMessage() );
        }
        return false;

    }

    public List<Produto> Buscar(Produto filtro)
    {
        try
        {
            String where = "";

            if(filtro != null)
            {
                if(filtro.getDescricao() != null && !filtro.getDescricao().isEmpty() )
                    where+= "nome like '%"+filtro.getDescricao() + "%'";
                
            }

        String consulta = "select * from Produtos";

        if(where.length() > 0)
            consulta += " where " + where;

        PreparedStatement sql = this.getConexao()
            .prepareStatement(consulta);

        ResultSet resultado = sql.executeQuery();

        List<Produto> produtos = new ArrayList<>();

        while(resultado.next() )
        {
            Produto produto = new Produto();

            try
            {
                produto.setDescricao(resultado.getString("descricao") );
                produto.setPrecoCompra(resultado.getBigDecimal("precoCompra") );
                produto.setPrecoVenda(resultado.getBigDecimal("precoVenda") );
                produto.setUniVenda(resultado.getString("uniVenda") );
                produto.setUniCompra(resultado.getString("uniCompra") ); 

            } catch(Exception ex)
            {
                produto = null;
            }

            produtos.add(produto);

        }

        return produtos;

    } catch (SQLException ex)
    {
        System.out.println(ex.getMessage() );
    }

    return null;

    }

}
                