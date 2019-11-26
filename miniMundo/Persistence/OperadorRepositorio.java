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

 public class OperadorRepositorio extends BancoDados
 {
     public OperadorRepositorio()
     {
         super();
     }

     public boolean Salvar(Operador obj)
     {
        try
        {
            if(obj.getId() == 0)
            {
                PreparedStatement sql = this.getConexao()
                    .prepareStatement("insert into Operadores(nome, cpf, login, senha) values (?, ?, ?, ?)",
                         Statement.RETURN_GENERATED_KEYS);

                sql.setString(1, obj.getNome() );
                sql.setString(2, obj.getCpf().replace(".","").replace("-", "") );
                sql.setString(3, obj.getUser() );
                sql.setString(4, obj.getSenha() );

                if(sql.executeUpdate() > 0)
                {
                    ResultSet chave = sql.getGeneratedKeys();
                    chave.next();
                    obj.setId(chave.getInt(1) );

                    atualizarTelefones(obj);

                    return true;

                }
                else
                    return false;
                
                }
                
                else
                {
                    PreparedStatement sql = this.getConexao()
                        .prepareStatement("update Operadores set nome = ?, cpf = ?, user = ?, senha = ? where id = ?)");

                    sql.setString(1, obj.getNome() );
                    sql.setString(2, obj.getCpf().replace(".","").replace("-", "") );
                    sql.setString(3, obj.getUser() );
                    sql.setString(4, obj.getSenha() );
                    sql.setId(5, obj.getId() );

                    if(sql.executeUpdate() > 0)
                    {
                        atualizarTelefones(obj);
                        return true;
                    }
                    else
                        return false;

                }

            } catch(SQLException ex)
            {
                System.out.println(ex.getMessage() );
            }

            return false;

        }

        public void atualizarTelefones(Operadores operador)
        {

            try
            {
                PreparedStatement sql = this.getConexao()
                    .prepareStatement("delete from OperadoresTelefones where operador_id = ?");
                
                sql.setInt(1, operador.getId() );

                String values = "";

                for(String telefone: operador.getTelefones() )
                {
                    if(values.length() > 0)
                        values += ", ";

                    values += "("+cliente.getId()+",'" +telfone+" ')";

                }

                Statement sql2 = this.getConexao().createStatement();

            } catch (SQLException ex)
            {
                Logger.getLogger(OperadorRepositorio.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

}
