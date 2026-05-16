package pachet;

import java.time.LocalDate;

public class Designer extends Person{
    private int yearsOfExperience;

    public Designer(String id, String name, LocalDate birthDate, String hobby, int yearsOfExperience) {
        super(id, name, birthDate, hobby);
        this.yearsOfExperience = yearsOfExperience;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    @Override
    public String toString()
    {
        return "Designer: "+super.getName()+", having "+yearsOfExperience+" years of experience.";
    }
}
