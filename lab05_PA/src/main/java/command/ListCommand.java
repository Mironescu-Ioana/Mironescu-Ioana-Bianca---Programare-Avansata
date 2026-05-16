package command;
import repository.Catalog;

public class ListCommand implements Command {
    private Catalog catalog;

    public ListCommand(Catalog catalog) {
        this.catalog = catalog;
    }

    @Override
    public void execute()
    {
        catalog.print();
    }
}
