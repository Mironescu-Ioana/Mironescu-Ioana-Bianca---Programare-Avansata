package pachet;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Person implements Profile, Comparable<Person>  {
    private String id;
    private String name;
    private LocalDate birthDate;
    private String hobby;
    private Map<Profile, String> relationships;

    public Person(String id, String name, LocalDate birthDate, String hobby) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.hobby = hobby;
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getHobby() {
        return hobby;
    }

    public void addRelationship(Profile profile2, String description)
    {
        relationships.put(profile2, description);
    }

    public Map<Profile, String> getRelationships() {
        return relationships;
    }

    @Override
    public int getImportance()
    {
        return relationships.size();
    }

    @Override
    public int compareTo(Person other){
        return this.name.compareTo(other.name);
    }

    @Override
    public String toString(){
        return "Person: ID: "+id+", Birth date: "+birthDate+", Hobby: "+hobby+", name: "+name;
    }
}
