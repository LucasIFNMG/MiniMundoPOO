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

 public class ClienteRepositorio extends BancoDados
 {
    public ClienteRepositorio()
    {
        super();
    }

    public boolean Salvar(Cliente obj)
    {
        try {
            
            //Se ainda não existir nenhum registro, criar a tabela
            if(obj.getId() == 0)
            {
                PreparedStatement sql = this.getConexao()
                    .prepareStatement("insert into Clientes(nome, cpf, email, rua, numero, bairro) values (?, ?, ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                
                sql.setString(1, obj.getNome() );
                sql.setString(2, obj.getCpf().replace(".","").replace("-", "") );
                sql.setString(3, obj.getEmail() );
                sql.setString(4, obj.getRua() );
                sql.setInt(5, obj.getNumero() );
                sql.setString(6, obj.getBairro() );

                /**
                 * executeUpdate() = executa uma atualização/inserção/delete no banco
                 * executeQuery() = executa uma pesquisa no banco
                 */

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
            //Caso a tabela já esteja criada
            else
            {
                PreparedStatement sql = this.getConexao()
                    .prepareStatement("update Clientes set nome = ?, cpf = ?, email = ?, rua = ?, numero = ?, bairro = ? where id = ?)");

                    sql.setString(1, obj.getNome() );
                    sql.setString(2, obj.getCpf().replace(".","").replace("-", "") );
                    sql.setString(3, obj.getEmail() );
                    sql.setString(4, obj.getRua() );
                    sql.setInt(5, obj.getNumero() );
                    sql.setString(6, obj.getBairro() );
                    sql.setInt(7, obj.getId() );

                    if(sql.executeUpdate() > 0)
                    {
                        atualizarTelefones(obj);
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

    public void atualizarTelefones(Cliente cliente)
    {
        //"Apaga tudo e insere de novo"
        try {
            PreparedStatement sql = this.getConexao()
                .prepareStatement("delete from ClientesTelefones where cliente_id = ?");

            sql.setInt(1, cliente.getId() );

            String values = "";
            
            //Pegar todos os telefones do cliente
            for(String telefone : cliente.getTelefones() )
            {
                //Em caso de 1 ou mais números, inserir ", " no final de cada um
                if(values.length() > 0)
                    values += ", ";

                values += "("+cliente.getId()+",' " +telefone+" ')";
                
            }

            Statement sql2 = this.getConexao().createStatement();
            
            /**
             * executeUpdate() = executa uma atualização/inserção/delete no banco
             * executeQuery() = executa uma pesquisa no banco
            */
            sql2.executeUpdate("insert into ClientesTelefones(cliente_id, telefone VALUES " + values);

        } catch (SQLException ex) 
        {
            Logger.getLogger(AlunoRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //TODO public Cliente Abrir(int id){}
        



    }









 }