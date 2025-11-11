package io.github.aplaraujo.locadora_estudo;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseTest {
    static Connection connection;

    @BeforeAll
    static void setUpDatabase() throws  Exception {
        // Abrir a conexão
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
        // Criar tabela de usuários
        connection.createStatement().execute("CREATE TABLE users (id INT, name VARCHAR)");
    }

    @BeforeEach
    void insertUserTest() throws Exception{
        connection.createStatement().execute("INSERT INTO users (id, name) VALUES (1, 'José')");
    }

    @Test
    // @Disabled // Teste desabilitado
    void testUserExists() throws Exception {
        var result = connection.createStatement().executeQuery("SELECT * FROM users WHERE id = 1");
        Assertions.assertTrue(result.next());
    }

    @AfterAll
    static void closeDatabase() throws Exception{
        connection.close();
    }
}
