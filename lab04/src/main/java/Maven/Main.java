package Maven;

import com.github.javafaker.Faker;
import org.graph4j.Graph;
import org.graph4j.Edge;
import org.graph4j.GraphBuilder;
import org.graph4j.spanning.WeightedSpanningTreeIterator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.Collection;

public class Main {
    public static void main(String[] args)
    {
        Faker faker=new Faker();

        List<Intersection> inters=IntStream.rangeClosed(1, 4)
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


        System.out.println("Cei mai ieftini k arbori din oras:");

        Map<Intersection, Integer> nrIntersection=new HashMap<>();
        int id=0;
        for(Intersection node : myCity.getIntersections())
        {
            nrIntersection.put(node, id++);
        }

        Graph graph=GraphBuilder.vertexRange(0, myCity.getIntersections().size()-1).buildGraph();

        for(Street street : myCity.getStreets())
        {
            int u=nrIntersection.get(street.getStart());
            int v=nrIntersection.get(street.getEnd());

            graph.addEdge(u, v, street.getLength());
        }

        WeightedSpanningTreeIterator spanningTreeIterator=new WeightedSpanningTreeIterator(graph);

        int k=5;
        int i=1;

        while(i<=k && spanningTreeIterator.hasNext())
        {
            Collection<Edge> edges=spanningTreeIterator.next();

            double totalCost=0;
            for(Edge  edge : edges)
            {
                totalCost+=graph.getEdgeWeight(edge);
            }
            System.out.println("Solutia "+i+", cost total: "+totalCost);
            i++;
        }
    }
}
