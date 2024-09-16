package models;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Bordillo {
    private UUID id;
    private BigDecimal length;
    private UUID segmentId;

    // Constructor
    public Bordillo(UUID id, BigDecimal length, UUID segmentId) {
        this.id = id;
        this.length = length;
        this.segmentId = segmentId;
    }

    // Default constructor (needed for JSON deserialization)
    public Bordillo() {
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
        Bordillo bordillo = (Bordillo) o;
        return Objects.equals(id, bordillo.id) &&
                Objects.equals(length, bordillo.length) &&
                Objects.equals(segmentId, bordillo.segmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, length, segmentId);
    }

    @Override
    public String toString() {
        return "Bordillo{" +
                "id=" + id +
                ", length=" + length +
                ", segmentId=" + segmentId +
                '}';
    }
}
