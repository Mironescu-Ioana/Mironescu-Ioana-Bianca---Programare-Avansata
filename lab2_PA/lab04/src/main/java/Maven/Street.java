package Maven;

public class Street implements Comparable<Street> {
    private String name;
    private double length;
    private Intersection start;
    private Intersection end;

    public Street(String name, double length, Intersection start, Intersection end) {
        this.end = end;
        this.length = length;
        this.name = name;
        this.start = start;
    }

    public String getName() {
        return name;
    }

    public double getLength() {
        return length;
    }

    public Intersection getStart() {
        return start;
    }

    public Intersection getEnd() {
        return end;
    }

    @Override
    public int compareTo(Street other)
    {
        return Double.compare(this.length, other.length);
    }

    @Override
    public String toString()
    {
        return "Strada "+name+", dintre "+start+" si "+end;
    }
}
