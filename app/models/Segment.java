package models;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Segment {
    private UUID id;
    private String number;
    private BigDecimal length;
    private String directionNomenclature;

    // Constructor
    public Segment(UUID id, String number, BigDecimal length, String directionNomenclature) {
        this.id = id;
        this.number = number;
        this.length = length;
        this.directionNomenclature = directionNomenclature;
    }

    // Default constructor (needed for JSON deserialization)
    public Segment() {
        // Default constructor
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public String getDirectionNomenclature() {
        return directionNomenclature;
    }

    public void setDirectionNomenclature(String directionNomenclature) {
        this.directionNomenclature = directionNomenclature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Segment segment = (Segment) o;
        return Objects.equals(id, segment.id) &&
                Objects.equals(number, segment.number) &&
                Objects.equals(length, segment.length) &&
                Objects.equals(directionNomenclature, segment.directionNomenclature);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, length, directionNomenclature);
    }

    @Override
    public String toString() {
        return "Segment{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", length=" + length +
                ", directionNomenclature='" + directionNomenclature + '\'' +
                '}';
    }
}
