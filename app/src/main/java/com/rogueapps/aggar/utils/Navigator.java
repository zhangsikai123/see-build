package com.rogueapps.aggar.utils;

/**
 * Created by Andrew Pachulio on 4/16/17.
 */
import android.util.Log;

import java.util.*;

/**
 * Created by zhangsikai on 4/14/17.
 */
public class Navigator {

    double[][]polygon;
    double[]user;
    double[][]entrances;
    public double[] selectedEntrance;
    public String direction;
    public Navigator(double[][]polygon,double[]user,double[][]entrances){
        this.polygon = polygon;this.user = user;this.entrances = entrances;
        selectedEntrance = new double[2];
        direction = "Forward";
    }
    public  void doCalculation(){
        // Determine closest entrance
        double[] selectedEntrance = null;
        Point[] selectedSplits = null;
        ArrayList<ArrayList<double[]>> selectedSplitPolys = null;
        polyDistance bestResult = null;
        boolean isSplit = true;
        String direction = "Forward";
        for (int i = 0; i < entrances.length; i++) {
            Point[] splits = splitPoints(polygon, entrances[i], user);

            if (splits==null) {
                isSplit = false;
                selectedEntrance = entrances[i];
                break;
            } else {
                ArrayList<ArrayList<double[]>>  splitPolygons = getSplitPolys(polygon, splits);
                polyDistance result = determineShortestPoly(splitPolygons.get(0), splitPolygons.get(1));
                if (bestResult == null || result.distance < bestResult.distance) {
                    selectedEntrance = entrances[i];
                    selectedSplits = splits;
                    selectedSplitPolys = splitPolygons;
                    bestResult = result;
                }
            }
        }
        if (isSplit) {
            double[][] res = new double[bestResult.poly.size()][2];
            for(int i=0;i<bestResult.poly.size();i++)res[i] = bestResult.poly.get(i);
            direction = getDirection(res, user, selectedEntrance);
        }
//        Log.d("distance:",""+convertLatLngToMeters(user,selectedEntrance));
//        Log.d("Entrance ",Arrays.toString(selectedEntrance));
        this.direction =  direction;
        this.selectedEntrance = selectedEntrance;
    }
    public void updateUser(double[] user){
        this.user = user;
    }

    private class Point{
        double[]pt;
        String type;
        int startIndex;
        int endIndex;
        public Point(double[]pt,String type,int stratIndex,int endIndex){
            this.pt = pt;this.type = type;this.startIndex = stratIndex;this.endIndex = endIndex;
        }
    }
    private class polyDistance{
        List<double[]>poly; double distance;
        public polyDistance(List<double[]>poly, double distance){
            this.distance = distance;
            this.poly = poly;
        }
    }
    // function to get intersection of two lines
// L1 = (p1, p2), L2 = (p3, p4)
    double[] intersection (double[]p1, double[]p2, double[]p3, double[]p4) {
        double x = ((p1[0]*p2[1] - p1[1]*p2[0]) * (p3[0] - p4[0]) - (p1[0] - p2[0]) * (p3[0]*p4[1] - p3[1]*p4[0])) /
                ((p1[0] - p2[0])*(p3[1] - p4[1]) - (p1[1] - p2[1]) * (p3[0] - p4[0]));

        double y = ((p1[0]*p2[1] - p1[1]*p2[0]) * (p3[1] - p4[1]) - (p1[1] - p2[1]) * (p3[0]*p4[1] - p3[1]*p4[0])) /
                ((p1[0] - p2[0])*(p3[1] - p4[1]) - (p1[1] - p2[1]) * (p3[0] - p4[0]));

        return new double[]{x,y};
    }

    // Function for determinate
    private double det(double[]a, double[]b, double[]c) {
        return ((b[0] - a[0]) * (c[1] - a[1]) - (b[1] - a[1]) * (c[0] - a[0]));
    }

    // p1 and p2 define the line created by user coordinate and polygon center
// function finds the points where to create split in polygon
    private Point[] splitPoints (double[][]polygon,double[] entrance,double[] user) {
        Point[] pts = new Point[2];
        ArrayList<Point> sortedCheck = new ArrayList<>();
        int ptsPointer = 0;
        // Get possible intersections
        for (int i = 0; i < polygon.length; i++) {
            double[] a = polygon[i];
            double[] b = i == 0? polygon[polygon.length - 1] : polygon[i - 1];
            double[] c = intersection(entrance, user, a, b);
            if (boundsCheck(a, b, c)) {
                pts[ptsPointer++] = new Point(c,"intersection",i,i == 0 ? polygon.length - 1 : i - 1);
                // Silly check to prevent issues of this pt and entrance not being the exact same
                if (distance(c, entrance) > 0.0001) {
                    sortedCheck.add(new Point(c,"intersection",i,i == 0 ? polygon.length - 1 : i - 1));
                }
            }
        }

        // Check if entrance is actually in view
        // Would happen if entrance and user next to each other when sorted
        sortedCheck.add(new Point(entrance,"entrance",0,0));
        sortedCheck.add(new Point(user,"user",0,0));
        Collections.sort(sortedCheck, new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                if(o1.pt[0] - o2.pt[0]>0)return 1;
                else return -1;
            }
        });

        String prevType = sortedCheck.get(0).type;
        boolean sameSide = false;
        for (int i = 1; i < sortedCheck.size(); i++) {
            String tempType = sortedCheck.get(i).type;
            if (prevType == "entrance" &&  tempType == "user" || prevType == "user" &&  tempType == "entrance") {
                sameSide = true;
                break;
            }
            prevType = tempType;
        }

        if (sameSide) {
            return null;
        } else {
            return pts;
        }
    }

    // Only handles splitting into two
    private  ArrayList<ArrayList<double[]>> getSplitPolys (double[][]polygon, Point[] splits) {
        ArrayList<ArrayList<double[]>> res = new ArrayList<>();
        ArrayList<double[]> polyA = new ArrayList<>();
        polyA.add(splits[1].pt);
        polyA.add(splits[0].pt);
        for (int i = splits[0].endIndex + 1; i < splits[1].startIndex; i++) {
            polyA.add(polygon[i]);
        }

        if (splits[0].endIndex + 1 > polygon.length - 1) {
            for (int i = 0; i < splits[1].startIndex; i++) {
                polyA.add(polygon[i]);
            }
        }

        ArrayList<double[]> polyB = new ArrayList<>();
        polyB.add(splits[0].pt);
        polyB.add(splits[1].pt);
        for (int i = splits[1].endIndex + 1; i < polygon.length; i++) {
            polyB.add(polygon[i]);
        }
        for (int i = 0; i < splits[0].startIndex; i++) {
            polyB.add(polygon[i]);
        }
        res.add(polyA);res.add(polyB);
        return res;
    }

    // Determines which poly has smaller distance
    private polyDistance determineShortestPoly (ArrayList<double[]>polyA, ArrayList<double[]>polyB) {
        double aDistance = 0;
        for (int i = 0; i < polyA.size(); i++) {
            double[] a = polyA.get(i);
            double[] b = i == 0 ? polyA.get(polyA.size() - 1) : polyA.get(i - 1);
            aDistance += distance(a, b);
        }

        double bDistance = 0;
        for (int i = 0; i < polyB.size(); i++) {
            double[] a = polyB.get(i);
            double[] b = i == 0 ? polyB.get(polyB.size() - 1) : polyB.get(i-1);
            bDistance += distance(a, b);
        }

        return aDistance < bDistance ? new polyDistance(polyA,aDistance):new polyDistance(polyA,aDistance);
    }

    private String getDirection (double[][]poly, double[]user, double[]entrance) {
        int t = 0;
        for (int i = 0; i < poly.length; i++) {
            t += det(poly[i], user, entrance);
        }
        return t < 0 ? "Left" : "Right";
    }

    private boolean boundsCheck (double[]a, double[]b, double[]c) {
        boolean inBounds = true;
        double x1 = a[0] > b[0] ? b[0] : a[0];
        double x2 = a[0] > b[0] ? a[0] : b[0]; // larger x
        double y1 = a[1] > b[1] ? b[1] : a[1];
        double y2 = a[1] > b[1] ? a[1] : b[1]; // larger y
        return x1 < c[0] && c[0] < x2 && y1 < c[1] && c[1] < y2;
    }

    // Determine distance between two pts
    private  double distance(double[]p1, double[]p2) {
        double[] diff = new double[]{p2[0] - p1[0], p2[1] - p1[1]};
        return Math.sqrt(diff[0] * diff[0] + diff[1] * diff[1]);
    }

    private double toRadians(double d) {
        return d * Math.PI / 180;
    }

    // http://www.movable-type.co.uk/scripts/latlong.html
    public  double convertLatLngToMeters (double[]ptA, double[]ptB) {
        double R = 6371e3; // metres
        double lat1 = toRadians(ptA[0]);
        double lat2 = toRadians(ptB[0]);
        double deltaLat = toRadians((ptB[0]-ptA[0]));
        double deltaLng = toRadians((ptB[1]-ptA[1]));

        double a = Math.sin(deltaLat/2) * Math.sin(deltaLat/2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(deltaLng/2) * Math.sin(deltaLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return R * c;
    }
}
