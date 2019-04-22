package com.mapbox.turf;

import android.support.annotation.NonNull;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Geometry;
import com.mapbox.geojson.GeometryCollection;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.MultiLineString;
import com.mapbox.geojson.MultiPoint;
import com.mapbox.geojson.Point;
import com.mapbox.geojson.Polygon;
import com.mapbox.geojson.MultiPolygon;

import java.util.ArrayList;
import java.util.List;

/**
 * Class contains methods that are useful for getting all coordinates from a specific GeoJson
 * geometry.
 *
 * @see <a href="http://turfjs.org/docs/">Turf documentation</a>
 * @since 2.0.0
 */
public final class TurfMeta {

  private TurfMeta() {
    // Private constructor preventing initialization of this class
  }

  /**
   * Get all coordinates from a {@link Point} object, returning a {@code List} of Point objects.
   * If you have a geometry collection, you need to break it down to individual geometry objects
   * before using {@link #coordAll}.
   *
   * @param point any {@link Point} object
   * @return a {@code List} made up of {@link Point}s
   * @since 2.0.0
   */
  @NonNull
  public static List<Point> coordAll(@NonNull Point point) {
    List<Point> coords = new ArrayList<>();
    coords.add(point);
    return coords;
  }

  /**
   * Get all coordinates from a {@link LineString} object, returning a {@code List} of Point
   * objects. If you have a geometry collection, you need to break it down to individual geometry
   * objects before using {@link #coordAll}.
   *
   * @param lineString any {@link LineString} object
   * @return a {@code List} made up of {@link Point}s
   * @since 2.0.0
   */
  @NonNull
  public static List<Point> coordAll(@NonNull LineString lineString) {
    List<Point> coords = new ArrayList<>();
    coords.addAll(lineString.coordinates());
    return coords;
  }

  /**
   * Get all coordinates from a {@link MultiPoint} object, returning a {@code List} of Point
   * objects. If you have a geometry collection, you need to break it down to individual geometry
   * objects before using {@link #coordAll}.
   *
   * @param multiPoint any {@link MultiPoint} object
   * @return a {@code List} made up of {@link Point}s
   * @since 2.0.0
   */
  @NonNull
  public static List<Point> coordAll(@NonNull MultiPoint multiPoint) {
    List<Point> coords = new ArrayList<>();
    coords.addAll(multiPoint.coordinates());
    return coords;
  }

  /**
   * Get all coordinates from a {@link Polygon} object, returning a {@code List} of Point objects.
   * If you have a geometry collection, you need to break it down to individual geometry objects
   * before using {@link #coordAll}.
   *
   * @param polygon          any {@link Polygon} object
   * @param excludeWrapCoord whether or not to include the final coordinate of LinearRings that
   *                         wraps the ring in its iteration
   * @return a {@code List} made up of {@link Point}s
   * @since 2.0.0
   */
  @NonNull
  public static List<Point> coordAll(@NonNull Polygon polygon, boolean excludeWrapCoord) {
    List<Point> coords = new ArrayList<>();
    int wrapShrink = excludeWrapCoord ? 1 : 0;

    for (int i = 0; i < polygon.coordinates().size(); i++) {
      for (int j = 0; j < polygon.coordinates().get(i).size() - wrapShrink; j++) {
        coords.add(polygon.coordinates().get(i).get(j));
      }
    }
    return coords;
  }

  /**
   * Get all coordinates from a {@link MultiLineString} object, returning a {@code List} of Point
   * objects. If you have a geometry collection, you need to break it down to individual geometry
   * objects before using {@link #coordAll}.
   *
   * @param multiLineString any {@link MultiLineString} object
   * @return a {@code List} made up of {@link Point}s
   * @since 2.0.0
   */
  @NonNull
  public static List<Point> coordAll(@NonNull MultiLineString multiLineString) {
    List<Point> coords = new ArrayList<>();
    for (int i = 0; i < multiLineString.coordinates().size(); i++) {
      coords.addAll(multiLineString.coordinates().get(i));
    }
    return coords;
  }

  /**
   * Get all coordinates from a {@link MultiPolygon} object, returning a {@code List} of Point
   * objects. If you have a geometry collection, you need to break it down to individual geometry
   * objects before using {@link #coordAll}.
   *
   * @param multiPolygon     any {@link MultiPolygon} object
   * @param excludeWrapCoord whether or not to include the final coordinate of LinearRings that
   *                         wraps the ring in its iteration
   * @return a {@code List} made up of {@link Point}s
   * @since 2.0.0
   */
  @NonNull
  public static List<Point> coordAll(@NonNull MultiPolygon multiPolygon, boolean excludeWrapCoord) {
    List<Point> coords = new ArrayList<>();
    int wrapShrink = excludeWrapCoord ? 1 : 0;

    for (int i = 0; i < multiPolygon.coordinates().size(); i++) {
      for (int j = 0; j < multiPolygon.coordinates().get(i).size(); j++) {
        for (int k = 0; k < multiPolygon.coordinates().get(i).get(j).size() - wrapShrink; k++) {
          coords.add(multiPolygon.coordinates().get(i).get(j).get(k));
        }
      }
    }
    return coords;
  }

  /**
   * Get all coordinates from a {@link Feature} object, returning a {@link List} of {@link Point}
   * objects.
   *
   * @param feature the {@link Feature} that you'd like to extract the Points from.
   * @return a {@code List} made up of {@link Point}s
   * @since 4.7.0
   */
  public static List<Point> coordAll(@NonNull Feature feature) {
    return coordAll(FeatureCollection.fromFeature(feature));
  }

  /**
   * Get all coordinates from a {@link FeatureCollection} object, returning a
   * {@link List} of {@link Point} objects.
   *
   * @param featureCollection the {@link FeatureCollection} that you'd like
   *                          to extract the Points from.
   * @return a {@code List} made up of {@link Point}s
   * @since 4.7.0
   */
  public static List<Point> coordAll(@NonNull FeatureCollection featureCollection) {
    List<Point> finalCoordsList = new ArrayList<>();
    for (Feature singleFeature : featureCollection.features()) {
      finalCoordsList.addAll(coordAll(singleFeature.geometry()));
    }
    return finalCoordsList;
  }

  /**
   * Get all coordinates from a {@link Geometry} object, returning a {@link List} of {@link Point}
   * objects.
   *
   * @param geometry the {@link Geometry} object to extract the {@link Point}s from
   * @return a {@code List} made up of {@link Point}s
   * @since 4.7.0
   */
  private static List<Point> coordAll(@NonNull Geometry geometry) {
    List<Point> finalCoordsList = new ArrayList<>();
    if (geometry instanceof Point) {
      finalCoordsList.addAll(TurfMeta.coordAll((Point) geometry));
    } else if (geometry instanceof MultiPoint) {
      for (Point singlePoint : TurfMeta.coordAll((MultiPoint) geometry)) {
        finalCoordsList.addAll(coordAll(singlePoint));
      }
    } else if (geometry instanceof LineString) {
      for (Point singlePoint : TurfMeta.coordAll((LineString) geometry)) {
        finalCoordsList.addAll(coordAll(singlePoint));
      }
    } else if (geometry instanceof MultiLineString) {
      for (Point singlePoint : TurfMeta.coordAll((MultiLineString) geometry)) {
        finalCoordsList.addAll(coordAll(singlePoint));
      }
    } else if (geometry instanceof Polygon) {
      for (Point singlePoint : TurfMeta.coordAll((Polygon) geometry, true)) {
        finalCoordsList.addAll(coordAll(singlePoint));
      }
    } else if (geometry instanceof MultiPolygon) {
      for (Point singlePoint : TurfMeta.coordAll((MultiPolygon) geometry, true)) {
        finalCoordsList.addAll(coordAll(singlePoint));
      }
    } else if (geometry instanceof GeometryCollection) {
      // recursive
      for (Geometry singleGeometry : ((GeometryCollection) geometry).geometries()) {
        finalCoordsList.addAll(coordAll(singleGeometry));
      }
    }
    return finalCoordsList;
  }

  /**
   * Unwrap a coordinate {@link Point} from a Feature with a Point geometry.
   *
   * @param obj any value
   * @return a coordinate
   * @see <a href="http://turfjs.org/docs/#getcoord">Turf getCoord documentation</a>
   * @since 3.2.0
   */
  public static Point getCoord(Feature obj) {
    if (obj.geometry() instanceof Point) {
      return (Point) obj.geometry();
    }
    throw new TurfException("A Feature with a Point geometry is required.");
  }
}
