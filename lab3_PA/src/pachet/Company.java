package pachet;

import java.util.HashMap;
import java.util.Map;

public class Company implements Profile, Comparable<Company> {
    private String id;
    private String name;
    private int numberOfEmployees;
    private Map<Profile, String> relationships;

    public Company(String id, String name, int numberOfEmployees) {
        this.id = id;
        this.name = name;
        this.numberOfEmployees = numberOfEmployees;
        this.relationships = new HashMap<>();
    }

    @Override
    public String getId(){
        return id;
    }

    @Override
    public String getName(){
        return name;
    }

    public int getNumberOfEmployees() { return numberOfEmployees; }

    public void addRelationship(Profile profile2, String description)
    {
        relationships.put(profile2, description);
    }

    @Override
    public int getImportance()
    {
        return relationships.size();
    }

    @Override
    public int compareTo(Company other){
        return this.name.compareTo(other.name);
    }

    @Override
    public String toString(){
        return "Company: ID:"+id+", name: "+name+", number of employees: "+numberOfEmployees;
    }
}
