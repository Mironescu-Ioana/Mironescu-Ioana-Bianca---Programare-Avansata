package pachet;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SocialNetwork {
    private List<Profile> profiles;

    public SocialNetwork(List<Profile> profiles) {
        this.profiles = new ArrayList<>();
    }

    public void addProfile(Profile newProfile)
    {
        boolean gasit=false;
        for(Profile p : profiles)
        {
            if(p.getId().equals(newProfile.getId()))
            {
                gasit=true;
                break;
            }
        }

        if(!gasit)
        {
            profiles.add(newProfile);
            System.out.println("Profilul "+newProfile.getName()+" a fost adaugat.");
        }
        else
            System.out.println("Eroare: Profilul "+newProfile.getName()+" exista deja.");
    }

    public void printByImportance()
    {
        List<Profile> finalList = new ArrayList<>(profiles);
        finalList.sort(Comparator.comparing(Profile::getImportance).reversed());

        System.out.println("Membri sortati dupa importanta: ");

        for(Profile p : finalList)
        {
            System.out.println(p.toString()+", Importanta: "+p.getImportance());
        }
    }
}
