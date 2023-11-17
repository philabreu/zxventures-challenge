package com.zxventures.model;

import com.bedatadriven.jackson.datatype.jts.serialization.GeometryDeserializer;
import com.bedatadriven.jackson.datatype.jts.serialization.GeometrySerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.awt.geom.Point2D;
import java.io.Serializable;

@Entity
@Table(name = "pdv")
public class PDV implements Serializable {
    private static final long serialVersionUID = 1644420518370889181L;

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

    public PDV(Long id, String tradingName, String ownerName, String document,
               MultiPolygon coverageArea, Point address) {
        this.id = id;
        this.tradingName = tradingName;
        this.ownerName = ownerName;
        this.document = document;
        this.coverageArea = coverageArea;
        this.address = address;
    }

    public PDV() {
    }

    public Double getDistance(double longitude, double latitude) {
        return this.calculateDistance(this.address.getY(), this.address.getX(), longitude, latitude);
    }

    private Double calculateDistance(double y1, double x1, double y2, double x2) {
        return Point2D.distance(x1, y1, x2, y2);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTradingName() {
        return tradingName;
    }

    public void setTradingName(String tradingName) {
        this.tradingName = tradingName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public MultiPolygon getCoverageArea() {
        return coverageArea;
    }

    public void setCoverageArea(MultiPolygon coverageArea) {
        this.coverageArea = coverageArea;
    }

    public Point getAddress() {
        return address;
    }

    public void setAddress(Point address) {
        this.address = address;
    }
}
