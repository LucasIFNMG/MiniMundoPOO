package br.edu.ifnmg.MiniMundo.Persistence;

import br.edu.ifnmg.POO.DomainModel.Cliente;
import br.edu.ifnmg.POO.DomainModel.Sexo;
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

    public boolean Salvar(Produto obj)
    {
        try 
        {
            if(obj.getId() == 0)
            {
                PreparedStatement sql = this.getConexao()
                    .prepareStatement("insert into Produtos(nome, cpf, email, rua, numero, bairro) values (?, ?, ?, ?, ?, ?"),
                        Statement.RETURN_GENERATED_KEYS);
                
                sql.setString(1, obj.getNome() );
                sql.setString(2, obj.getCpf().replace(".","").replace("-", "") );
                sql.setString(3, obj.getEmail() );
                sql.setString(4, obj.getRua() );
                sql.setInt(5, obj.getNumero() );
                sql.setString(6, obj.getBairro() );

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
                    .prepareStatement("update Produtos set nome = ?, cpf = ?, email = ?, rua = ?, numero = ?, bairro = ? where id = ?)");

                sql.setString(1, obj.getNome() );
                sql.setString(2, obj.getCpf().replace(".","").replace("-", "") );
                sql.setString(3, obj.getEmail() );
                sql.setString(4, obj.getRua() );
                sql.setInt(5, obj.getNumero() );
                sql.setString(6, obj.getBairro() );
                sql.setInt(7, obj.getId() );

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

        public Produto Abrir(int id)
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
                    produto.setId( resultado.getInt("id") );
                    produto.setNome( resultado.getString("nome") );
                    produto.setCpf( resultado.getString("cpf") );
                    produto.setRua( resultado.getString("rua") );
                    produto.setNumero( resultado.getInt("numero") );
                    produto.setBairro( resultado.getString("bairro") );

                }

                catch(Exception ex)
                {
                    produto = null;
                }

                return produto;

            }

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
                if(filtro.getNome() != null && !filtro.getNome().isEmpty() )
                    where+= "nome like '%"+filtro.getNome() + "%'";
                
                if(filtro.getCpf() != null && !filtro.getCpf().isEmpty() &&
                    !"000.000.000-00".equals(filtro.getCpf() ))
                {
                    if(where.length() > 0)
                        where +=" and ";
                    where += "cpf = ''"+filtro.getCpf().replace(".", "").replace("-", "")+ "'";
                }
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

                produto.setId( resultado.getInt("id") );
                produto.setNome( resultado.getString("nome") );
                produto.setCpf( resultado.getString("cpf") );
                produto.setRua( resultado.getString("rua") );
                produto.setNumero( resultado.getInt("numero") );
                produto.setBairro( resultado.getString("bairro") );

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
                