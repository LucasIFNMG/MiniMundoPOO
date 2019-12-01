package br.edu.ifnmg.MiniMundo.Persistence;

import br.edu.ifnmg.MiniMundo.DomainModel.Operador;
import br.edu.ifnmg.MiniMundo.DomainModel.Estado;
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

     public boolean Salvar(final Operador obj)
     {
        try
        {
            if(obj.getId() == 0)
            {
                final PreparedStatement sql = this.getConexao()
                    .prepareStatement("insert into Operadores(nome, cpf, login, senha) values (?, ?, ?, ?)",
                         Statement.RETURN_GENERATED_KEYS);

                sql.setString(1, obj.getNome() );
                sql.setString(2, obj.getCpf().replace(".","").replace("-", "") );
                sql.setString(3, obj.getLogin() );
                sql.setString(4, obj.getSenha() );

                if(sql.executeUpdate() > 0)
                {
                    final ResultSet chave = sql.getGeneratedKeys();
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
                    final PreparedStatement sql = this.getConexao()
                        .prepareStatement("update Operadores set nome = ?, cpf = ?, login = ?, senha = ? where id = ?)");

                    sql.setString(1, obj.getNome() );
                    sql.setString(2, obj.getCpf().replace(".","").replace("-", "") );
                    sql.setString(3, obj.getLogin() );
                    sql.setString(4, obj.getSenha() );
                    sql.setInt(5, obj.getId() );

                    if(sql.executeUpdate() > 0)
                    {
                        atualizarTelefones(obj);
                        return true;
                    }
                    else
                        return false;

                }

            } catch(final SQLException ex)
            {
                System.out.println(ex.getMessage() );
            }

            return false;

        }

        public void atualizarTelefones(final Operador operador)
        {

            try
            {
                final PreparedStatement sql = this.getConexao()
                    .prepareStatement("delete from OperadoresTelefones where operador_id = ?");
                
                sql.setInt(1, operador.getId() );

                String values = "";

                for(final String telefone: operador.getTelefones() )
                {
                    if(values.length() > 0)
                        values += ", ";

                    values += "("+operador.getId()+",'" +telefone+" ')";

                }

                final Statement sql2 = this.getConexao().createStatement();

            } catch (final SQLException ex)
            {
                Logger.getLogger(OperadorRepositorio.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    public Operador Abrir(final int id)
    {
        try
        {
            final PreparedStatement sql = this.getConexao().prepareStatement("select * from Operadores where id = ?");

            sql.setInt(1, id);

            final ResultSet resultado = sql.executeQuery();

            resultado.next();

            Operador operador = new Operador();

            try
            {
                operador.setId(resultado.getInt("id"));
                operador.setNome(resultado.getString("nome"));
                operador.setCpf(resultado.getString("cpf"));
                abrirTelefones(operador);
                
            } catch(final Exception ex)
            {
                operador = null;
            }

            return operador;

        } catch(final SQLException ex)
        {
            System.out.println(ex.getMessage() );
        }

        return null;

    }

    public void abrirTelefones(final Operador operador)
    {
        try
        {
            final PreparedStatement sql = this.getConexao()
                .prepareStatement("select telefone from OperadoresTelefones where operador_id = ?");

            sql.setInt(1, operador.getId() );

            final ResultSet resultado = sql.executeQuery();

            while(resultado.next() )
            {
                operador.addTelefone(resultado.getString("telefone"));
            }

        } catch(final SQLException ex)
        {
            Logger.getLogger(OperadorRepositorio.class.getName()).log(Level.SEVERE, null, ex);    
        }
    }

    public boolean Apagar(final Operador obj)
    {
        try
        {
            final PreparedStatement sql = this.getConexao().prepareStatement
                ("delete from Operadores where id = ?");

            sql.setInt(1, obj.getId() );

            if(sql.executeUpdate() > 0)
                return true;
            else
                return false;

        } catch(final SQLException ex)
        {
            System.out.println(ex.getMessage());
        }

        return false;

    }

    public List<Operador> Buscar(final Operador filtro)
    {
        try
        {
            String where = "";

            if(filtro != null)
            {
                if(filtro.getNome() != null && !filtro.getNome().isEmpty())
                    where += "nome like '%" + filtro.getNome() + "%'";

                if(filtro.getCpf() != null && !filtro.getCpf().isEmpty()
                    && !"000.000.000-00".equals(filtro.getCpf()))
                    {
                        if(where.length() > 0)
                            where += " and ";
                        where += "cpf = ''" + filtro.getCpf().replace(".", "").replace("-", "");


                    }

            }

           String consulta = "select * from Operadores";

           if(where.length() > 0)
           {
               consulta += " where " + where;
           }

           final PreparedStatement sql = this.getConexao()
                .prepareStatement(consulta);

            final ResultSet resultado = sql.executeQuery();

            final List<Operador> operadores = new ArrayList<>();

            while(resultado.next() )
            {
                Operador operador = new Operador();

                try
                {
                    operador.setId(resultado.getInt("id"));
                    operador.setNome(resultado.getString("nome"));
                    operador.setCpf(resultado.getString("cpf"));
                    abrirTelefones(operador);

                } catch(final Exception ex)
                {
                    operador = null;
                }

                operadores.add(operador);

            }

            return operadores;

        } catch(final SQLException ex)
        {
            System.out.println(ex.getMessage() );
        }

        return null;

    }
}
