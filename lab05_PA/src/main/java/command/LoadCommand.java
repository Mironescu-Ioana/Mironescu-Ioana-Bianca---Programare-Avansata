package command;

import com.fasterxml.jackson.databind.ObjectMapper;
import exception.CatalogException;
import repository.Catalog;
import java.io.File;

public class LoadCommand implements Command {
    private Catalog catalog;
    private String path;

    public LoadCommand(Catalog catalog, String path) {
        this.catalog = catalog;
        this.path = path;
    }

    @Override
    public void execute() throws CatalogException
    {
        try{
            ObjectMapper mapper=new ObjectMapper();

            Catalog newCatalog=mapper.readValue(new File(path), Catalog.class);

            this.catalog.setResources(newCatalog.getResources());

            System.out.println("Catalogul a obtinut resurse din: "+path);
        } catch (Exception e) {
            throw new CatalogException("Eroare la incarcarea fisierului JSON", e);
        }
    }
}
