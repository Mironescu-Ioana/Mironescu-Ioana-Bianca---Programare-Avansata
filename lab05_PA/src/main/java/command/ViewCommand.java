package command;

import exception.CatalogException;
import model.Resource;
import repository.Catalog;

public class ViewCommand implements Command {
    private Catalog catalog;
    private Resource resource;

    public ViewCommand(Catalog catalog, Resource resource) {
        this.catalog = catalog;
        this.resource = resource;
    }

    @Override
    public void execute() throws CatalogException
    {
        catalog.openResource(resource);
    }
}
