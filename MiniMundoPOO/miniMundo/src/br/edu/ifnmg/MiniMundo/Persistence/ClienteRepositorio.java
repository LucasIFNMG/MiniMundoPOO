package br.edu.ifnmg.MiniMundo.Persistence;

import br.edu.ifnmg.MiniMundo.DomainModel.Cliente;
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
            Logger.getLogger(ClienteRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Cliente Abrir(int id)
    {
        try{

            PreparedStatement sql = this.getConexao()
                    .prepareStatement("select * from Clientes where id = ?");

            sql.setInt(1, id);

            ResultSet resultado = sql.executeQuery();

            resultado.next();

            Cliente cliente = new Cliente();

            try
            {
                cliente.setId( resultado.getInt("id") );
                cliente.setNome( resultado.getString("nome") );
                cliente.setCpf( resultado.getString("cpf") );
                abrirTelefones(cliente);
                cliente.setRua( resultado.getString("rua") );
                cliente.setNumero( resultado.getInt("numero") );
                cliente.setBairro( resultado.getString("bairro") );
            }

            catch(Exception ex)
            {
                cliente = null;
            }

            return cliente;

        }

        catch(SQLException ex)
        {
            System.out.println(ex.getMessage() );
        }

        return null;

    }

    public void abrirTelefones(Cliente cliente)
    {
        try
        {
            PreparedStatement sql = this.getConexao()
                    .prepareStatement("select telefone from ClientesTelefones where cliente_id = ?");
            
            sql.setInt(1, cliente.getId() );

            ResultSet resultado = sql.executeQuery();

            while(resultado.next() )
            {
                cliente.addTelefone(resultado.getString("telefone") );
            }
            
        } catch (SQLException ex)
        {
            Logger.getLogger(ClienteRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean Apagar(Cliente obj)
    {
        try{
            PreparedStatement sql = this.getConexao()
                    .prepareStatement("delete from Clientes where id = ?");
            
            sql.setInt(1, obj.getId() );

            if(sql.executeUpdate() > 0)
                return true;
            else
                return false;

        } catch (SQLException ex)
        {
            System.out.println(ex.getMessage() );
        }
        return false;
    }
    
    public List<Cliente> Buscar(Cliente filtro)
    {
        try
        {
            String where = "";

            if(filtro != null)
            {
                if(filtro.getNome() != null && !filtro.getNome().isEmpty() )
                    where+= "nome like '%"+filtro.getNome() + "%'";

                if(filtro.getCpf() != null && !filtro.getCpf().isEmpty() &&
                        !"000.000.000-00".equals(filtro.getCpf() ) )
                        {
                            if(where.length() > 0)
                                where +=" and ";
                            where += "cpf = ''"+filtro.getCpf().replace(".", "").replace("-", "") + "'";

                        }
            }

            String consulta = "select * from Clientes";

            if(where.length() > 0)
                consulta += " where " + where;

            PreparedStatement sql = this.getConexao()
                    .prepareStatement(consulta);

            ResultSet resultado = sql.executeQuery();

            List<Cliente> clientes = new ArrayList<>();

            while(resultado.next() )
            {
                Cliente cliente = new Cliente();

                try
                {
                    cliente.setId( resultado.getInt("id") );
                    cliente.setNome( resultado.getString("nome") );
                    cliente.setCpf( resultado.getString("cpf") );
                    abrirTelefones(cliente);
                    cliente.setRua( resultado.getString("rua") );
                    cliente.setNumero( resultado.getInt("numero") );
                    cliente.setBairro( resultado.getString("bairro") );
                } catch(Exception ex)
                {
                    cliente = null;
                }

                clientes.add(cliente);
            }
            return clientes;
            

        } catch (SQLException ex)
        {
            System.out.println(ex.getMessage() );
        } 

        return null;





    }



}
