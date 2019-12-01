/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.MiniMundo.Persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author lucas
 */
public class BancoDados {
    private Connection conexao;

    public BancoDados()
    {
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");

            conexao = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/MiniMundo","lucas","33238032ana");
        
        } 
        catch (ClassNotFoundException ex) 
        {
            System.out.println("Driver do banco de dados não foi encontrado!");
        }
        catch(SQLException ex)
        {
            System.out.println("ERRO: Verifique os dados de conexão com o Banco de Dados");
            System.out.println(ex.getMessage() );
        }

        
    }

    public Connection getConexao()
    {
        return conexao;
    }

}
