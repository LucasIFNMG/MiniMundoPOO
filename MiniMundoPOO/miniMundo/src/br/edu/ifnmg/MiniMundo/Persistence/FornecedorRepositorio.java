
package br.edu.ifnmg.MiniMundo.Persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import br.edu.ifnmg.MiniMundo.DomainModel.Estado;
import br.edu.ifnmg.MiniMundo.DomainModel.Fornecedor;

public class FornecedorRepositorio extends BancoDados
{
    public FornecedorRepositorio()
    {
        super();
    }

    public boolean Salvar (Fornecedor obj)
    {
        try
        {

            if(obj.getId() == 0)
            {
                PreparedStatement sql = this.getConexao()
                    .prepareStatement("insert into Fornecedores(razaoSocial, cnpj, email, rua, numero, bairro, cidade, estado) values (?, ?, ?, ?, ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                
                sql.setString(1, obj.getRazaoSocial() );
                sql.setString(2, obj.getCnpj() );
                sql.setString(3, obj.getEmail() );
                sql.setString(4, obj.getRua() );
                sql.setInt(5, obj.getNumero() );
                sql.setString(6, obj.getBairro() );
                sql.setString(7, obj.getCidade() );
                sql.setString(8, obj.getEstado().name() );

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

    public Fornecedor Abrir(int id)
    {
        try
        {
            PreparedStatement sql = this.getConexao()
                .prepareStatement("select * from Fornecedores where id = ?");
            
            sql.setInt(1, id);

            ResultSet resultado = sql.executeQuery();

            resultado.next();

            Fornecedor fornecedor = new Fornecedor();

            try
            {

                fornecedor.setId(resultado.getInt("id") );
                fornecedor.setRazaoSocial(resultado.getString("razaoSocial"));
                fornecedor.setCnpj(resultado.getString("cnpj"));
                fornecedor.setEmail(resultado.getString("email"));
                fornecedor.setRua(resultado.getString("rua"));
                fornecedor.setNumero(resultado.getInt("numero"));
                fornecedor.setBairro(resultado.getString("bairro"));
                fornecedor.setCidade(resultado.getString("cidade"));
                fornecedor.setEstado(Estado.valueOf(resultado.getString("estado")));
                
            } catch(Exception ex)
            {
                fornecedor = null;
            }

            return fornecedor;

        } catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }

        return null;

    }

}

