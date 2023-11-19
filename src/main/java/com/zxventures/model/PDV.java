package com.zxventures.model;

import com.bedatadriven.jackson.datatype.jts.serialization.GeometryDeserializer;
import com.bedatadriven.jackson.datatype.jts.serialization.GeometrySerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.Point;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.geolatte.geom.MultiPolygon;

import java.awt.geom.Point2D;

@Data
@Entity
@Table(name = "pdv")
public class PDV {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 30)
    @Column(name = "trading_name")
    private String tradingName;

    @NotNull
    @Size(max = 30)
    @Column(name = "owner_name")
    private String ownerName;

    @NotNull
    @Size(max = 30)
    @Column(name = "document")
    private String document;

    @NotNull
    @Column(name = "coverage_area")
    @JsonSerialize(using = GeometrySerializer.class)
    @JsonDeserialize(contentUsing = GeometryDeserializer.class)
    private MultiPolygon coverageArea;

    @NotNull
    @Column(name = "address")
    @JsonSerialize(using = GeometrySerializer.class)
    @JsonDeserialize(contentUsing = GeometryDeserializer.class)
    private Point address;

    public Double getDistance(double longitude, double latitude) {
        return this.calculateDistance(this.address.getY(), this.address.getX(), longitude, latitude);
    }

    private Double calculateDistance(double x1, double x2, double y1, double y2) {
        return Point2D.distance(x1, y1, x2, y2);
    }
}