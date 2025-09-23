package com.uce.sedral.services;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;

public class GeomUtils {

    private static final GeometryFactory geometry = new GeometryFactory();

    public static Point crearPuntoUTM(double este, double norte, int srid) {
        Point punto = geometry.createPoint(new Coordinate(este, norte));
        punto.setSRID(srid);
        return punto;
    }

    public static LineString crearLineaUTM(Point inicio, Point fin, int srid) {
        Coordinate[] coords = new Coordinate[]{inicio.getCoordinate(), fin.getCoordinate()};
        LineString lineString = geometry.createLineString(coords);
        lineString.setSRID(srid);
        return lineString;
    }
}
