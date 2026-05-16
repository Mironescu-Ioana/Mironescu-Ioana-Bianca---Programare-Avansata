package command;

import exception.CatalogException;

public interface Command {
    void execute() throws CatalogException;
}
