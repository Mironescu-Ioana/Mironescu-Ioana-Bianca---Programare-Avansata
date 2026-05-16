import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Main extends JFrame {
    private MazePanel canvas;
    private JSpinner rowSpinner, colSpinner;

    public Main()
    {
        setTitle("Create a Maze");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        canvas = new MazePanel();
        add(canvas, BorderLayout.CENTER);

        setupConfigPanel();
        setupControlPanel();
    }

    private void setupConfigPanel() {
        JPanel configPanel = new JPanel();

        configPanel.add(new JLabel("Rânduri:"));
        rowSpinner = new JSpinner(new SpinnerNumberModel(10, 2, 50, 1));
        configPanel.add(rowSpinner);

        configPanel.add(new JLabel("Coloane:"));
        colSpinner = new JSpinner(new SpinnerNumberModel(10, 2, 50, 1));
        configPanel.add(colSpinner);

        JButton drawButton = new JButton("Draw Cells");
        configPanel.add(drawButton);

        drawButton.addActionListener(e -> {
            int rows = (Integer) rowSpinner.getValue();
            int cols = (Integer) colSpinner.getValue();
            canvas.initGrid(rows, cols);
        });

        add(configPanel, BorderLayout.NORTH);
    }

    private void setupControlPanel() {
        JPanel controlPanel = new JPanel();

        JButton createBtn = new JButton("Create");

        JButton validateBtn = new JButton("Validate");
        JButton exportBtn = new JButton("Export PNG");
        JButton saveBtn = new JButton("Save");
        JButton loadBtn = new JButton("Load");

        JButton resetBtn = new JButton("Reset");
        JButton exitBtn = new JButton("Exit");


        controlPanel.add(createBtn);

        controlPanel.add(validateBtn);
        controlPanel.add(exportBtn);
        controlPanel.add(saveBtn);
        controlPanel.add(loadBtn);

        controlPanel.add(resetBtn);
        controlPanel.add(exitBtn);

        createBtn.addActionListener(e -> canvas.createRandomMaze());

        validateBtn.addActionListener(e -> {
            if(canvas.getGrid() != null) {
                boolean isValid = canvas.validatePath();
                if (isValid) JOptionPane.showMessageDialog(this, "Labirintul are o soluție!");
                else JOptionPane.showMessageDialog(this, "Labirintul nu are soluție.");
            }
        });

        exportBtn.addActionListener(e -> canvas.exportToPNG("maze.png"));

        saveBtn.addActionListener(e -> saveMaze());

        loadBtn.addActionListener(e -> loadMaze());

        resetBtn.addActionListener(e -> canvas.resetGrid());
        exitBtn.addActionListener(e -> System.exit(0));

        add(controlPanel, BorderLayout.SOUTH);
    }

    private void saveMaze() {
        if (canvas.getGrid() == null) return;
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("maze.ser"))) {
            out.writeObject(canvas.getGrid());
            out.writeInt(canvas.getRows());
            out.writeInt(canvas.getCols());
            JOptionPane.showMessageDialog(this, "Labirint salvat cu succes!");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Eroare la salvare!");
        }
    }

    private void loadMaze() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("maze.ser"))) {
            Cell[][] loadedGrid = (Cell[][]) in.readObject();
            int r = in.readInt();
            int c = in.readInt();
            canvas.setGrid(loadedGrid, r, c);
            rowSpinner.setValue(r);
            colSpinner.setValue(c);
            JOptionPane.showMessageDialog(this, "Labirint încărcat!");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Nu am găsit fișier de salvare sau eroare la citire.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main app = new Main();
            app.setLocationRelativeTo(null);
            app.setVisible(true);
        });
    }
}
