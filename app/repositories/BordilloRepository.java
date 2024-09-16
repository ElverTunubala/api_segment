package repositories;

import javax.inject.Inject;
import play.db.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import models.Bordillo;
import javax.inject.Singleton;

@Singleton
public class BordilloRepository {

    private final Database db;

    @Inject
    public BordilloRepository(Database db) {
        this.db = db;
    }

    // Encuentra todos los bordillos
    public List<Bordillo> findAll() {
        List<Bordillo> bordillos = new ArrayList<>();
        String sql = "SELECT * FROM Bordillo";

        // Utiliza withConnection para manejar automáticamente la conexión
        db.withConnection(conn -> {
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Bordillo bordillo = new Bordillo();
                    bordillo.setId(UUID.fromString(rs.getString("id")));
                    bordillo.setLength(rs.getBigDecimal("length"));
                    bordillo.setSegmentId(UUID.fromString(rs.getString("segment_id")));
                    bordillos.add(bordillo);
                }
            }
            return bordillos;
        });
        return bordillos;
    }

    // Encuentra un bordillo por ID
    public Bordillo findById(UUID id) {
        String sql = "SELECT * FROM Bordillo WHERE id = ?";
        return db.withConnection(conn -> {
            Bordillo bordillo = null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setObject(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        bordillo = new Bordillo();
                        bordillo.setId(UUID.fromString(rs.getString("id")));
                        bordillo.setLength(rs.getBigDecimal("length"));
                        bordillo.setSegmentId(UUID.fromString(rs.getString("segment_id")));
                    }
                }
            }
            return bordillo;
        });
    }

    // Guarda un nuevo bordillo
    public void save(Bordillo bordillo) { 
        String sql = "INSERT INTO Bordillo (length, segment_id) VALUES (?, ?)";
    
        db.withConnection(conn -> {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setBigDecimal(1, bordillo.getLength());
                stmt.setObject(2, bordillo.getSegmentId(), java.sql.Types.OTHER);
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException("Error al guardar el bordillo", e);
            }
            return null;
        });
    }
    
    // Actualiza un bordillo existente
    public void update(Bordillo bordillo) {
        String sql = "UPDATE Bordillo SET length = ?, segment_id = ? WHERE id = ?";
        db.withConnection(conn -> {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                // Establece los parámetros del PreparedStatement
                stmt.setBigDecimal(1, bordillo.getLength());
                stmt.setObject(2, bordillo.getSegmentId()); //devuelve un UUID
                stmt.setObject(3, bordillo.getId()); //devuelve un UUID

                // Ejecuta la actualización
                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated == 0) {
                    System.out.println("No se encontró el bordillo con el ID especificado.");
                }
            } catch (SQLException e) {
                // Maneja cualquier error que ocurra al ejecutar la actualización
                System.err.println("Error al actualizar el bordillo: " + e.getMessage());
            }
            return null;
        });
    }


    // Elimina un bordillo por ID
    public void delete(UUID id) {
        String sql = "DELETE FROM Bordillo WHERE id = ?";
        db.withConnection(conn -> {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setObject(1, id);
                stmt.executeUpdate();
            }
            return null;
        });
    }
}
