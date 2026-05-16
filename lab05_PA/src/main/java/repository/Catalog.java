package repository;

import exception.CatalogException;
import model.Resource;

import java.awt.Desktop;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class Catalog {
    private List<Resource> resources=new ArrayList<>();

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public void addResource(Resource resource)
    {
        resources.add(resource);
        System.out.println("Am adaugat resursa: "+resource.getTitle());
    }

    public void print()
    {
        System.out.println("Catalog:");
        for(Resource r : resources)
            System.out.println(r);
    }

    public void openResource(Resource resource) throws CatalogException
    {
        try
        {
            if(!Desktop.isDesktopSupported())
                throw new CatalogException("Nu suport clasa Desktop!");

            Desktop desktop=Desktop.getDesktop();
            String location=resource.getLocation();

            if(location.startsWith("https://") || location.startsWith("https://"))
            {
                desktop.browse(new URI(location));
                System.out.println("S-a deschis in browser: "+resource.getTitle());
            }
            else
            {
                File file=new File(location);
                if(!file.exists())
                    throw new CatalogException("Fisierul nu exista aici: "+location);
                desktop.open(file);
                System.out.println("Fisier deshis: "+resource.getTitle());
            }
        } catch(Exception e)
        {
            throw new CatalogException("Eroare la deschiderea resursei: "+resource.getTitle(), e);
        }
    }
}
