import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.*;

public class MazePanel extends JPanel{
    private int rows=0;
    private int cols=0;
    private Cell[][] grid;
    private final int CELL_SIZE=30;

    public MazePanel()
    {

        setBackground(new Color(152, 251, 152));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (grid == null) return;

                int startX = (getWidth() - (cols * CELL_SIZE)) / 2;
                int startY = (getHeight() - (rows * CELL_SIZE)) / 2;

                int mouseX = e.getX() - startX;
                int mouseY = e.getY() - startY;

                int c = mouseX / CELL_SIZE;
                int r = mouseY / CELL_SIZE;

                if (r >= 0 && r < rows && c >= 0 && c < cols) {
                    int lx = mouseX % CELL_SIZE;
                    int ly = mouseY % CELL_SIZE;

                    int distTop = ly;
                    int distBottom = CELL_SIZE - ly;
                    int distLeft = lx;
                    int distRight = CELL_SIZE - lx;

                    int min = Math.min(Math.min(distTop, distBottom), Math.min(distLeft, distRight));

                    if (min == distTop) {
                        grid[r][c].top = !grid[r][c].top;
                        if (r > 0) grid[r - 1][c].bottom = grid[r][c].top;
                    } else if (min == distBottom) {
                        grid[r][c].bottom = !grid[r][c].bottom;
                        if (r < rows - 1) grid[r + 1][c].top = grid[r][c].bottom;
                    } else if (min == distLeft) {
                        grid[r][c].left = !grid[r][c].left;
                        if (c > 0) grid[r][c - 1].right = grid[r][c].left;
                    } else {
                        grid[r][c].right = !grid[r][c].right;
                        if (c < cols - 1) grid[r][c + 1].left = grid[r][c].right;
                    }
                    repaint();
                }
            }
        });
    }

    public void initGrid(int rows, int cols)
    {
        this.rows=rows;
        this.cols=cols;
        grid=new Cell[rows][cols];

        for(int i=0;i<rows;i++)
            for(int j=0;j<cols;j++)
                grid[i][j]=new Cell(i, j);
        repaint();
    }

    public void createRandomMaze() {
        if (grid==null) return;
        Random rand=new Random();

        for(int i=0;i<rows;i++) {
            for(int j=0;j<cols;j++) {
                if (rand.nextBoolean()) {
                    grid[i][j].top = false;
                    if (i > 0)
                        grid[i - 1][j].bottom = false;

                }

                if (rand.nextBoolean()) {
                    grid[i][j].right = false;
                    if (j < cols - 1) {
                        grid[i][j + 1].left = false;
                    }
                }

                if (rand.nextBoolean()) {
                    grid[i][j].bottom = false;
                    if (i < rows - 1) {
                        grid[i + 1][j].top = false;
                    }
                }

                if (rand.nextBoolean()) {
                    grid[i][j].left = false;
                    if (j > 0) {
                        grid[i][j - 1].right = false;
                    }
                }
            }
        }
        repaint();
    }

    public void resetGrid() {
        this.rows=0;
        this.cols=0;
        this.grid=null;
        repaint();
    }

    public boolean validatePath()
    {
        if (grid == null) return false;

        for(int i=0; i<rows; i++)
            for(int j=0; j<cols; j++)
                grid[i][j].isPath = false;

        Cell start = grid[0][0];
        Cell end = grid[rows - 1][cols - 1];

        Map<Cell, Cell> parentMap = new HashMap<>();
        Queue<Cell> queue = new LinkedList<>();
        Set<Cell> visited = new HashSet<>();

        queue.add(start);
        visited.add(start);

        boolean found = false;

        while (!queue.isEmpty()) {
            Cell curr = queue.poll();
            if (curr == end) {
                found = true;
                break;
            }

            int r = curr.row, c = curr.col;
            //verif vecini(daca nu are perete si nu e vizitat)
            if (!curr.top && r > 0 && !visited.contains(grid[r - 1][c])) {
                visited.add(grid[r - 1][c]);
                queue.add(grid[r - 1][c]);
                parentMap.put(grid[r - 1][c], curr);
            }
            if (!curr.bottom && r < rows - 1 && !visited.contains(grid[r + 1][c])) {
                visited.add(grid[r + 1][c]);
                queue.add(grid[r + 1][c]);
                parentMap.put(grid[r + 1][c], curr);
            }
            if (!curr.left && c > 0 && !visited.contains(grid[r][c - 1])) {
                visited.add(grid[r][c - 1]);
                queue.add(grid[r][c - 1]);
                parentMap.put(grid[r][c - 1], curr);
            }
            if (!curr.right && c < cols - 1 && !visited.contains(grid[r][c + 1])) {
                visited.add(grid[r][c + 1]);
                queue.add(grid[r][c + 1]);
                parentMap.put(grid[r][c + 1], curr);
            }
        }

        if (found) {
            Cell curr = end;
            while (curr != null) {
                curr.isPath = true;
                curr = parentMap.get(curr);
            }
            repaint();
            return true;
        }

        repaint();
        return false;
    }

    public void exportToPNG(String filePath) {
        BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        this.paintAll(g2d);
        g2d.dispose();
        try {
            ImageIO.write(image, "png", new File(filePath));
            JOptionPane.showMessageDialog(this, "Imagine salvată cu succes!");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Eroare la salvarea imaginii.");
        }
    }

    public Cell[][] getGrid() { return grid; }
    public void setGrid(Cell[][] newGrid, int rows, int cols) {
        this.grid = newGrid;
        this.rows = rows;
        this.cols = cols;
        repaint();
    }
    public int getRows() { return rows; }
    public int getCols() { return cols; }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (grid==null) return;

        Graphics2D g2d=(Graphics2D) g;
        g2d.setStroke(new BasicStroke(2));


        int startX=(getWidth()-(cols*CELL_SIZE))/2;
        int startY=(getHeight()-(rows*CELL_SIZE))/2;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int x = startX + j * CELL_SIZE;
                int y = startY + i * CELL_SIZE;

                if (grid[i][j].isPath) {
                    g2d.setColor(Color.YELLOW);
                } else if (i==0 && j==0) {
                    g2d.setColor(Color.GREEN); // Start
                } else if (i==rows-1 && j==cols-1) {
                    g2d.setColor(Color.RED); // End
                } else {
                    g2d.setColor(new Color(200, 162, 200));
                }

                g2d.fillRect(x, y, CELL_SIZE, CELL_SIZE);

                //g2d.setColor(new Color(200, 162, 200));

                g2d.setColor(Color.BLACK);
                if (grid[i][j].top)    g2d.drawLine(x, y, x + CELL_SIZE, y);
                if (grid[i][j].bottom) g2d.drawLine(x, y + CELL_SIZE, x + CELL_SIZE, y + CELL_SIZE);
                if (grid[i][j].left)   g2d.drawLine(x, y, x, y + CELL_SIZE);
                if (grid[i][j].right)  g2d.drawLine(x + CELL_SIZE, y, x + CELL_SIZE, y + CELL_SIZE);
            }
        }
    }
}
