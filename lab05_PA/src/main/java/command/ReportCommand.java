package command;

import exception.CatalogException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import repository.Catalog;
import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class ReportCommand implements Command {
    private Catalog catalog;

    public ReportCommand(Catalog catalog) {
        this.catalog = catalog;
    }

    @Override
    public void execute() throws CatalogException {
        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
            cfg.setDirectoryForTemplateLoading(new File("templates"));
            Template template = cfg.getTemplate("report.ftl");

            Map<String, Object> data = new HashMap<>();
            data.put("resources", catalog.getResources());

            File htmlFile = new File("report_catalog.html");
            Writer out = new FileWriter(htmlFile);
            template.process(data, out);
            out.close();

            System.out.println("Raport generat!");
            Desktop.getDesktop().open(htmlFile);
        } catch (Exception e) {
            throw new CatalogException("Eroare la generarea HTML!", e);
        }
    }
}
