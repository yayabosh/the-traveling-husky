public class City {
    public final String name;
    public final double latitude;
    public final double longitude;
    public final int id;

    public City(String name, double latitude, double longitude, int id) {
        this.name      = name;
        this.latitude  = latitude;
        this.longitude = longitude;
        this.id        = id;
    }

    @Override
    public String toString() { return name; }

    public static City fromCsv(String line) {
        return fromCsv(line.split(","));
    }

    private static City fromCsv(String... values) {
        return new City(values[0], Double.parseDouble(values[1]), Double.parseDouble(values[2]), Integer.parseInt(values[3]));
    }
}
