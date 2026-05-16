package command;

import com.fasterxml.jackson.databind.ObjectMapper;
import exception.CatalogException;
import repository.Catalog;
import java.io.File;

public class SaveCommand implements Command {
    private Catalog catalog;
    private String path;

    public SaveCommand(Catalog catalog, String path) {
        this.catalog = catalog;
        this.path = path;
    }

    @Override
    public void execute() throws CatalogException
    {
        try{
            ObjectMapper mapper=new ObjectMapper();
            mapper.writeValue(new File(path), catalog);
            System.out.println("Catalogul a fost salvat in: "+path);
        } catch (Exception e) {
            throw new CatalogException("Eroare la salvarea fisierului JSON", e);
        }
    }
}
