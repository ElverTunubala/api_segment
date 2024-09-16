package repositories;

import models.Segment;
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
public class SegmentRepository {

    private final Database db;

    @Inject
    public SegmentRepository(Database db) {
        this.db = db;
    }

    // Lista todos los segmentos
    public List<Segment> findAll() {
        List<Segment> segments = new ArrayList<>();
        String sql = "SELECT * FROM Segment";

        try {
            db.withConnection(conn -> {
                try (PreparedStatement stmt = conn.prepareStatement(sql);
                     ResultSet rs = stmt.executeQuery()) {

                    while (rs.next()) {
                        Segment segment = new Segment();
                        segment.setId(UUID.fromString(rs.getString("id")));
                        segment.setNumber(rs.getString("number"));
                        segment.setLength(rs.getBigDecimal("length"));
                        segment.setDirectionNomenclature(rs.getString("direction_nomenclature"));
                        segments.add(segment);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException("Error al obtener los segmentos", e);
                }
                return null;
            });
        } catch (Exception e) {
            e.printStackTrace(); // Maneja el error de manera adecuada
        }

        return segments;
    }

    // Crea un nuevo segmento
    public void save(Segment segment) {
        String sql = "INSERT INTO Segment (number, length, direction_nomenclature) VALUES (?, ?, ?)";

        try {
            db.withConnection(conn -> {
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    
                    stmt.setString(1, segment.getNumber());
                    stmt.setBigDecimal(2, segment.getLength());
                    stmt.setString(3, segment.getDirectionNomenclature());
                    stmt.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException("Error al guardar el segmento", e);
                }
                return null;
            });
        } catch (Exception e) {
            e.printStackTrace(); // Maneja el error de manera adecuada
        }
    }

    // Muestra un segmento por ID
    public Segment findById(UUID id) {
        String sql = "SELECT * FROM Segment WHERE id = ?";
        Segment[] segment = {null}; // Usamos un array para asignar dentro del lambda

        try {
            db.withConnection(conn -> {
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setObject(1, id);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            segment[0] = new Segment();
                            segment[0].setId(UUID.fromString(rs.getString("id")));
                            segment[0].setNumber(rs.getString("number"));
                            segment[0].setLength(rs.getBigDecimal("length"));
                            segment[0].setDirectionNomenclature(rs.getString("direction_nomenclature"));
                        }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException("Error al obtener el segmento por ID", e);
                }
                return null;
            });
        } catch (Exception e) {
            e.printStackTrace(); // Maneja el error de manera adecuada
        }

        return segment[0];
    }

    // Actualiza un segmento existente
    public void update(Segment segment) {
        String sql = "UPDATE Segment SET number = ?, length = ?, direction_nomenclature = ? WHERE id = ?";

        try {
            db.withConnection(conn -> {
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, segment.getNumber());
                    stmt.setBigDecimal(2, segment.getLength());
                    stmt.setString(3, segment.getDirectionNomenclature());
                    stmt.setObject(4, segment.getId());

                    int rowsAffected = stmt.executeUpdate();
                    if (rowsAffected == 0) {
                        System.out.println("Segment not found");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException("Error al actualizar el segmento", e);
                }
                return null;
            });
        } catch (Exception e) {
            e.printStackTrace(); // Maneja el error de manera adecuada
        }
    }

    // Elimina un segmento por ID
    public void delete(UUID id) {
        String sql = "DELETE FROM Segment WHERE id = ?";

        try {
            db.withConnection(conn -> {
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setObject(1, id);
                    int rowsAffected = stmt.executeUpdate();
                    if (rowsAffected == 0) {
                        System.out.println("Segment not found");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException("Error al eliminar el segmento", e);
                }
                return null;
            });
        } catch (Exception e) {
            e.printStackTrace(); // Maneja el error de manera adecuada
        }
    }
}
