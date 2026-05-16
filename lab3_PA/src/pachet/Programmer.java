package pachet;

import java.time.LocalDate;

public class Programmer extends Person {
    private String codingLanguage;

    public Programmer(String id, String name, LocalDate birthDate, String hobby, String codingLanguage) {
        super(id, name, birthDate, hobby);
        this.codingLanguage = codingLanguage;
    }

    public String getCodingLanguage() {
        return codingLanguage;
    }

    @Override
    public String toString()
    {
        return "Programmer: "+super.getName()+", coding language: "+codingLanguage;
    }
}
