package Maven;

import java.util.List;
import java.util.stream.Collectors;

public class City {
    private String name;
    private List<Intersection> intersections;
    private List<Street> streets;

    public City(String name, List<Intersection> intersections, List<Street> streets) {
        this.intersections = intersections;
        this.name = name;
        this.streets = streets;
    }

    public List<Intersection> getIntersections() {
        return intersections;
    }

    public List<Street> getStreets() {
        return streets;
    }

    public List<Street> getTargetStreets(double lmin)
    {
        return streets.stream()
                .filter(street -> street.getLength()>lmin)
                .filter(street -> countJoinedStreets(street)>=3)
                .collect(Collectors.toList());
    }

    private long countJoinedStreets(Street myStreet)
    {
        return streets.stream()
                .filter(street -> !street.equals(myStreet))
                .filter(street -> street.getStart().equals(myStreet.getStart()) ||
                                        street.getStart().equals(myStreet.getEnd()) ||
                                        street.getEnd().equals(myStreet.getStart()) ||
                                        street.getEnd().equals(myStreet.getEnd())).count();
    }
}
