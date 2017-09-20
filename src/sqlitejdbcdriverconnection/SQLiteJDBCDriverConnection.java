package sqlitejdbcdriverconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteJDBCDriverConnection {
    
    public Connection connect() {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:academico.bd";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            
            //System.out.println("A conexão com o banco de dados ocorreu com sucesso!");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } 
        
        return conn;
    }
    
    public void criaTabela (Connection conn) {
        
        String sql = "CREATE TABLE IF NOT EXISTS pessoa (";
                sql += "	id integer PRIMARY KEY,";
                sql += "	nome text NOT NULL";
                sql += ");";
        
        try {
                Statement stmt = conn.createStatement();
                
            // cria uma tabela
            stmt.execute(sql);
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void insereDados (Connection conn, int id, String nome) {
        String sql = "INSERT INTO pessoa VALUES(?,?)";
 
        try {
                PreparedStatement pstmt = conn.prepareStatement(sql); 
                
                pstmt.setInt(1, id);
                pstmt.setString(2, nome);
                
                pstmt.executeUpdate();
                
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void selecionaDados (Connection conn) {
        
        String sql = "SELECT id, nome "
                     + "FROM pessoa;";
        
        try {
            
            Statement comandoSql = conn.createStatement();
            
            ResultSet rs  = comandoSql.executeQuery(sql);
            
            // loop no resultado
            while (rs.next()) {
                System.out.println(rs.getInt("id") +  "\t" + 
                                   rs.getString("nome"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        
        SQLiteJDBCDriverConnection bd = new SQLiteJDBCDriverConnection();
        
        Connection conn = bd.connect();
        
        bd.criaTabela(conn);
        
        bd.insereDados(conn, 0, "Fulano de Tal");
        
        bd.insereDados(conn, 1, "Beltrano");
        
        bd.insereDados(conn, 2, "Sicrano");
        
        bd.selecionaDados (conn);
    }
    
}
