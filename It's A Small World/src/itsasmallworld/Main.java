package itsasmallworld;

import java.awt.Point;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Point points[] = new Point[100];
        Point points2[] = new Point[100];
        Random rand = new Random();
        for (int i = 0; i < 100; i++) {
            int x = rand.nextInt(100);
            int y = rand.nextInt(100);
            x *= (int) Math.pow(-1, rand.nextInt());
            y *= (int) Math.pow(-1, rand.nextInt());
            points[i] = new Point(x, y);
            points2[i] = new Point(x, y);
        }
        KDTree tree = buildKDTree(points2, 0);
        for (Point p : points) {
            Point nearest[] = nearest(p, tree, new Point[3], 0);
            System.out.print("(" + p.x + ", " + p.y + ")" + " ");
            printNearest(nearest);
        }
    }

    static void printNearest(Point p[]) {
        for (Point pp : p) {
            System.out.print("(" + pp.x + ", " + pp.y + ")");
        }
        System.out.println();
    }

    static Point[] nearest(Point p, KDTree tree, Point nearest[], int depth) {
        if (tree == null) {
            return nearest;
        }
        if (!p.equals(tree.root)) {
            if (nearest[0] == null || getDistance(p, tree.root) < getDistance(p, nearest[0])) {
                nearest[2] = nearest[1];
                nearest[1] = nearest[0];
                nearest[0] = tree.root;
            } else if (nearest[1] == null || getDistance(p, tree.root) < getDistance(p, nearest[1])) {
                nearest[2] = nearest[1];
                nearest[1] = tree.root;
            } else if (nearest[2] == null || getDistance(p, tree.root) < getDistance(p, nearest[2])) {
                nearest[2] = tree.root;
            }
        }
        nearest(p, tree.left, nearest, depth + 1);
        nearest(p, tree.right, nearest, depth + 1);
        return nearest;
    }

    static KDTree buildKDTree(Point points[], int depth) {
        if (points.length == 0) {
            return null;
        } else {
            final int axis = depth % 2;
            Arrays.sort(points, new Comparator() {

                public int compare(Object t, Object t1) {
                    return getCoord((Point) t, axis) - getCoord((Point) t1, axis);
                }
            });
            int median = points.length / 2;
            return new KDTree(points[median],
                    buildKDTree(Arrays.copyOfRange(points, 0, median), depth + 1),
                    buildKDTree(Arrays.copyOfRange(points, median + 1, points.length), depth + 1),
                    depth);
        }
    }

    static int getCoord(Point p, int axis) {
        if (axis == 0) {
            return p.x;
        } else {
            return p.y;
        }
    }

    static double getDistance(Point a, Point b) {
        return a.distance(b);
    }
}

class KDTree extends Object {

    Point root;
    KDTree left;
    KDTree right;
    int depth;

    KDTree(Point root, KDTree left, KDTree right, int depth) {
        this.root = root;
        this.left = left;
        this.right = right;
        this.depth = depth;
    }

    @Override
    public String toString() {
        String ret = depth + " " + root.toString() + "\n";
        if (left != null) {
            ret += " " + left.toString();
        }
        if (right != null) {
            ret += " " + right.toString();
        }
        return ret;
    }
}
