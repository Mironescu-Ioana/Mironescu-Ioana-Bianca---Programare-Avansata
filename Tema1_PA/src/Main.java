public class Main {
    public static void main(String[] args)
    {
        if(args.length<2)
        {
            System.out.println("Argumente gresite!");
            return;
        }

        int n=Integer.parseInt(args[0]);
        String shape=args[1];

        long start=System.nanoTime();
        int[][] mat=imagine(n, shape);
        long end=System.nanoTime();
        long time=(end-start)/1000000;

        if(n<=100)
        {
            System.out.println(pretty(mat));
            System.out.println("Imaginea a fost generata in "+time+"ms.");
        }
        else
        {
            System.out.println("Imaginea este prea mare pentru a fi afisata!");
            System.out.println("Imaginea a fost generata in "+time+" ms.");
        }
    }

    private static int[][] imagine(int n, String shape)
    {
        int[][] mat=new int[n][n];

        if(shape.equals("rectangle"))
        {
            for(int i=0;i<n;i++)
                for(int j=0;j<n;j++)
                    mat[i][j]=255;

            int x=n/4;

            for(int i=x;i<n-x;i++)
                for(int j=x;j<n-x;j++)
                    mat[i][j]=0;

            int[] bounding=BoundingBox(mat, 0);
            for(int i=0;i<4;i++)
                System.out.println(bounding[i]);
        }
        else if(shape.equals("circle"))
        {
            int raza=n/3;
            int cx=n/2;
            int cy=n/2;

            for(int i=0;i<n;i++)
                for(int j=0;j<n;j++)
                {
                    if((j-cx)*(j-cx)+(i-cy)*(i-cy)<=raza*raza)
                        mat[i][j]=255;
                }

            int[] bounding=BoundingBox(mat, 255);
            for(int i=0;i<4;i++)
                System.out.println(bounding[i]);
        }
        else {
            System.out.println("Forma nerecunoscuta!");
        }

        return mat;
    }

    private static String pretty(int[][] mat)
    {
        StringBuilder m=new StringBuilder();

        for(int i=0;i<mat.length;i++)
        {
            for(int j=0;j<mat[i].length;j++)
            {
                if(mat[i][j]==0) m.append("\u2588\u2588");
                else if(mat[i][j]==255) m.append("\u2592\u2592");
                else m.append("  ");
            }
            m.append("\n");
        }
        return m.toString();
    }

    public static int[] BoundingBox(int[][] mat, int val)
    {
        int n=mat.length;
        int lmin=n;
        int lmax=-1;
        int cmin=n;
        int cmax=-1;

        boolean inauntru=false;

        for(int i=0;i<n;i++)
            for(int j=0;j<n;j++)
            {
                if(mat[i][j]==val)
                {
                    inauntru=true;

                    if(i<lmin) lmin=i;
                    if(i>lmax) lmax=i;
                    if(j<cmin) cmin=j;
                    if(j>cmax) cmax=j;
                }
            }
        if(!inauntru)
            return null;

        return new int[]{lmin, lmax, cmin, cmax};
    }
}
