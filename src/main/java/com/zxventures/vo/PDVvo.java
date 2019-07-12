package com.zxventures.vo;

import java.io.Serializable;

import com.bedatadriven.jackson.datatype.jts.serialization.GeometryDeserializer;
import com.bedatadriven.jackson.datatype.jts.serialization.GeometrySerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;

import lombok.Getter;
import lombok.Setter;

public class PDVvo implements Serializable {

	private static final long serialVersionUID = -6980068772655099560L;

	@Getter
	@Setter
	private Long id;

	@Getter
	@Setter
	private String tradingName;

	@Getter
	@Setter
	private String ownerName;

	@Getter
	@Setter
	private String document;

	@Getter
	@Setter
	@JsonSerialize(using = GeometrySerializer.class)
	@JsonDeserialize(contentUsing = GeometryDeserializer.class)
	private MultiPolygon coverageArea;

	@Getter
	@Setter
	@JsonSerialize(using = GeometrySerializer.class)
	@JsonDeserialize(contentUsing = GeometryDeserializer.class)
	private Point address;

}
