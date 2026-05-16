package pachet;

import java.time.LocalDate;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        SocialNetwork network = new SocialNetwork(new ArrayList<>());

        Person zinaida = new Person("P1", "Zinaida", LocalDate.of(1998, 5, 20), "Citit");
        Company pizzaNico = new Company("C1", "PizzaNico", 15);

        Programmer ioana = new Programmer("P2", "Ioana", LocalDate.of(2001, 3, 15), "Gaming", "Java");
        Designer warda = new Designer("P3", "Warda", LocalDate.of(1999, 11, 10), "Pictura", 3);
        Company techCorp = new Company("C2", "TechCorp", 150);

        ioana.addRelationship(techCorp, "Software Developer");
        ioana.addRelationship(zinaida, "Prietene");
        ioana.addRelationship(warda, "Colege de echipa");

        zinaida.addRelationship(pizzaNico, "Client fidel");
        zinaida.addRelationship(ioana, "Prietene");

        pizzaNico.addRelationship(zinaida, "Client VIP");

        System.out.println("--- Adaugare profiluri in retea ---");
        network.addProfile(zinaida);
        network.addProfile(pizzaNico);
        network.addProfile(ioana);
        network.addProfile(warda);
        network.addProfile(techCorp);

        network.addProfile(ioana);

        System.out.println("\n--- Afisare rezultate ---");
        network.printByImportance();
    }
}