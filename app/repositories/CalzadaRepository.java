package repositories;

import models.Calzada;
import play.db.Database;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Singleton
public class CalzadaRepository {

    private final Database db; //para conectarme a la base de datos

    @Inject
    public CalzadaRepository(Database db) {
        this.db = db; // Asigna Database a la variable
    }

    // Encuentra todas las calzadas
    public List<Calzada> findAll() {
        List<Calzada> calzadas = new ArrayList<>();
        String sql = "SELECT * FROM Calzada";

        try {
            db.withConnection(conn -> {
                try (PreparedStatement stmt = conn.prepareStatement(sql);
                     ResultSet rs = stmt.executeQuery()) {

                    while (rs.next()) {
                        Calzada calzada = new Calzada();
                        calzada.setId(UUID.fromString(rs.getString("id")));
                        calzada.setLength(rs.getBigDecimal("length"));
                        calzada.setSegmentId(UUID.fromString(rs.getString("segment_id")));
                        calzadas.add(calzada);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException("Error al obtener las calzadas", e);
                }
                return null;
            });
        } catch (Exception e) {
            e.printStackTrace(); // Maneja el error de manera adecuada
        }

        return calzadas;
    }

    // Encuentra una calzada por ID
    public Calzada findById(UUID id) {
        String sql = "SELECT * FROM Calzada WHERE id = ?";
        Calzada[] calzada = {null}; // Usamos un array para asignar dentro del lambda

        try {
            db.withConnection(conn -> {
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setObject(1, id);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            calzada[0] = new Calzada();
                            calzada[0].setId(UUID.fromString(rs.getString("id")));
                            calzada[0].setLength(rs.getBigDecimal("length"));
                            calzada[0].setSegmentId(UUID.fromString(rs.getString("segment_id")));
                        }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException("Error al obtener la calzada por ID", e);
                }
                return null;
            });
        } catch (Exception e) {
            e.printStackTrace(); 
        }

        return calzada[0];
    }

    // Guarda una nueva calzada
    public void save(Calzada calzada) {
        String sql = "INSERT INTO Calzada (length, segment_id) VALUES (?, ?)";

        try {
            db.withConnection(conn -> {
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setBigDecimal(1, calzada.getLength());
                    stmt.setObject(2, calzada.getSegmentId());
                    stmt.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException("Error al guardar la calzada", e);
                }
                return null;
            });
        } catch (Exception e) {
            e.printStackTrace(); // Maneja el error de manera adecuada
        }
    }

    // Actualiza una calzada existente
    public void update(Calzada calzada) {
        String sql = "UPDATE Calzada SET length = ?, segment_id = ? WHERE id = ?";

        try {
            db.withConnection(conn -> {
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setBigDecimal(1, calzada.getLength());
                    stmt.setObject(2, calzada.getSegmentId());
                    stmt.setObject(3, calzada.getId());
                    stmt.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException("Error al actualizar la calzada", e);
                }
                return null;
            });
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }

    // Elimina una calzada por ID
    public void delete(UUID id) {
        String sql = "DELETE FROM Calzada WHERE id = ?";

        try {
            db.withConnection(conn -> {
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setObject(1, id);
                    stmt.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException("Error al eliminar la calzada", e);
                }
                return null;
            });
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }
}
