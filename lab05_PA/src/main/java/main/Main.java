package main;

import command.*;
import exception.CatalogException;
import exception.InvalidResourceException;
import model.Resource;
import repository.Catalog;

public class Main {
    public static void main(String[] args)
    {
        try{
            Catalog catalog=new Catalog();

            Resource knuth = new Resource(
                    "knuth67",
                    "The Art of Computer Programming",
                    "d:/books/programming/tacp.ps",
                    1967,
                    "Donald E. Knuth"
            );

            Resource jvms = new Resource(
                    "jvm25",
                    "The Java Virtual Machine Specification",
                    "https://docs.oracle.com/javase/specs/jvms/se25/html/index.html",
                    2025,
                    "Tim Lindholm & others"
            );

            catalog.addResource(knuth);
            catalog.addResource(jvms);
//
//            catalog.print();
//
//            catalog.openResource(jvms);
//            catalog.openResource(knuth);

            Command comandaList=new ListCommand(catalog);
            comandaList.execute();

            Command comandaSave=new SaveCommand(catalog, "catalog_info.json");
            comandaSave.execute();

            Catalog catalogFromFile=new Catalog();

            Command comandaLoad=new LoadCommand(catalogFromFile, "catalog_info.json");
            comandaLoad.execute();

            System.out.println("Catalog nou:");
            Command comandaListNoua=new ListCommand(catalogFromFile);
            comandaListNoua.execute();

            Command comandaReport=new ReportCommand(catalog);
            comandaReport.execute();

            ViewCommand view = new ViewCommand(catalog, jvms);
            view.execute();

        } catch (InvalidResourceException| CatalogException e)
        {
            System.err.println("Eroare! "+e.getMessage());
        }
    }
}
