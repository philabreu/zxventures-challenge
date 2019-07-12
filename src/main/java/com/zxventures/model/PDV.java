package com.zxventures.model;

import java.awt.geom.Point2D;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.bedatadriven.jackson.datatype.jts.serialization.GeometryDeserializer;
import com.bedatadriven.jackson.datatype.jts.serialization.GeometrySerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pdv")
@NoArgsConstructor
@AllArgsConstructor
public class PDV implements Serializable {

	private static final long serialVersionUID = 1644420518370889181L;

	@Id
	@Getter
	@Setter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Getter
	@Setter
	@Column(name = "trading_name")
	private String tradingName;

	@NotNull
	@Getter
	@Setter
	@Column(name = "owner_name")
	private String ownerName;

	@NotNull
	@Getter
	@Setter
	@Column(name = "document")
	private String document;

	@NotNull
	@Getter
	@Setter
	@Column(name = "coverage_area")
	@JsonSerialize(using = GeometrySerializer.class)
	@JsonDeserialize(contentUsing = GeometryDeserializer.class)
	private MultiPolygon coverageArea;

	@NotNull
	@Getter
	@Setter
	@Column(name = "address")
	@JsonSerialize(using = GeometrySerializer.class)
	@JsonDeserialize(contentUsing = GeometryDeserializer.class)
	private Point address;

	public Double getDistance(double longitude, double latitude) {
		return this.calculateDistance(this.address.getY(), this.address.getX(), longitude, latitude);
	}

	private Double calculateDistance(double y1, double x1, double y2, double x2) {
		return Point2D.distance(x1, y1, x2, y2);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PDV other = (PDV) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
