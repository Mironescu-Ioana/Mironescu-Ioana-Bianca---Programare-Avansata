package Maven;

import com.github.javafaker.Faker;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.YenShortestPathIterator;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args)
    {
        Faker faker=new Faker();

        List<Intersection> inters=IntStream.rangeClosed(1, 10)
                .mapToObj(i -> new Intersection(faker.address().cityName()+" Intersection"))
                .collect(Collectors.toList());

        System.out.println("Au aparut intersectiile: "+inters);

        List<Street> streets=new ArrayList<>();

        streets.add(new Street(faker.address().streetName(), 7, inters.get(0), inters.get(1)));
        streets.add(new Street(faker.address().streetName(), 10, inters.get(1), inters.get(2)));
        streets.add(new Street(faker.address().streetName(), 12, inters.get(1), inters.get(3)));
        streets.add(new Street(faker.address().streetName(), 4, inters.get(2), inters.get(3)));

        System.out.println("Strazile inainte de sortare:");

        for(Street s : streets)
            System.out.println(s);

        streets.sort(Comparator.comparing(Street::getLength));

        System.out.println("Strazile sortate dupa lungime:");

        for(Street s : streets)
            System.out.println(s);

        Set<Intersection> intersectionSet=new HashSet<>();
        //Am adaugat toate intersectiile in set
        intersectionSet.addAll(inters);
        System.out.println("Initial set-ul va avea dimensiunea "+intersectionSet.size());

        Intersection nou=new Intersection(inters.get(0).getName());

        if(intersectionSet.add(nou))
            System.out.println("Am reusit sa adaug duplicatul v4!");
        else
            System.out.println("Dimensiunea setului: "+intersectionSet.size());



        City myCity=new City("Zootopia", inters, streets);

        List<Street> straziTarget=myCity.getTargetStreets(5.0);

        System.out.println("Strazile buna: ");
        straziTarget.forEach(System.out::println);




        Graph<Intersection, DefaultWeightedEdge> graph=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

        for(Intersection node : myCity.getIntersections())
            graph.addVertex(node);

        for(Street street : myCity.getStreets())
        {
            DefaultWeightedEdge edge=graph.addEdge(street.getStart(), street.getEnd());
            if(edge != null)
                graph.setEdgeWeight(edge, street.getLength());
        }

        Intersection startNode=inters.get(0);
        Intersection endNode=inters.get(3);
        System.out.println("Drumul de cost minim de la "+startNode.getName()+" la "+endNode.getName());

        YenShortestPathIterator<Intersection, DefaultWeightedEdge> ShortestPaths=
                new YenShortestPathIterator<>(graph, startNode, endNode);

        int k=4;
        for(int i=1;i<=k && ShortestPaths.hasNext();i++)
        {
            GraphPath<Intersection, DefaultWeightedEdge> path=ShortestPaths.next();
            System.out.println("Traseul "+i+", Cost total: "+path.getWeight()+", :"+path.getVertexList());
        }
    }
}
