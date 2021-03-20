// A Route stores a route of cities and its total distance.
public class Route {
    public City[] route;
    public double distance;

    public Route(City[] route, double distance) {
        this.route = route;
        this.distance = distance;
    }

    public Route(Route r) {
        this.route = r.route.clone();
        this.distance = r.distance;
    }

    public String toString() {
        StringBuilder r = new StringBuilder();
        r.append(route[0].toString());
        for (int i = 1; i < route.length; i++) r.append(" => " + route[i].toString());
        return r.toString();
    }
}
