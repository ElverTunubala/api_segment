package models;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Calzada {
    private UUID id;
    private BigDecimal length;
    private UUID segmentId;

    // Constructor
    public Calzada(UUID id, BigDecimal length, UUID segmentId) {
        this.id = id;
        this.length = length;
        this.segmentId = segmentId;
    }

    // Default constructor (needed for JSON deserialization)
    public Calzada() {
        // Default constructor
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public UUID getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(UUID segmentId) {
        this.segmentId = segmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Calzada calzada = (Calzada) o;
        return Objects.equals(id, calzada.id) &&
                Objects.equals(length, calzada.length) &&
                Objects.equals(segmentId, calzada.segmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, length, segmentId);
    }

    @Override
    public String toString() {
        return "Calzada{" +
                "id=" + id +
                ", length=" + length +
                ", segmentId=" + segmentId +
                '}';
    }
}
