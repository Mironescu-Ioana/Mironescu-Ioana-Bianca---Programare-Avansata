package pachet;

import java.util.*;

public class NetworkVerifier<T> {
    private int time;

    private Map<T, Integer> foundTime;
    private Map<T, Integer> prevTime;
    private Map<T, T> parent;
    private Set<T> visited;
    private Set<T> articulationPoints;

    public NetworkVerifier()
    {
        this.foundTime=new HashMap<>();
        this.prevTime=new HashMap<>();
        this.parent=new HashMap<>();
        this.visited=new HashSet<>();
        this.articulationPoints=new HashSet<>();
        this.time=0;
    }

    public Set<T> findArticulationPoints(Map<T, List<T>> network)
    {
        foundTime.clear();
        prevTime.clear();
        parent.clear();
        visited.clear();
        articulationPoints.clear();
        time=0;

        for(T node : network.keySet())
            if(!visited.contains(node))
                dfs(node, network);

        return articulationPoints;
    }

    private void dfs(T u, Map<T, List<T>> network)
    {
        visited.add(u);
        time++;
        foundTime.put(u, time);
        prevTime.put(u, time);

        int countChildren=0;

        List<T> neighbors=network.getOrDefault(u, new ArrayList<>());

        for (T v : neighbors)
        {
            if(!visited.contains(v))
            {
                countChildren++;
                parent.put(v, u);

                dfs(v, network);

                prevTime.put(u, Math.min(prevTime.get(u), prevTime.get(v)));

                if(parent.get(u)==null && countChildren>1)
                    articulationPoints.add(u);

                if(parent.get(u)!=null && prevTime.get(v)>=foundTime.get(u))
                    articulationPoints.add(u);


            }
            else if(!v.equals(parent.get(u)))
                prevTime.put(u, Math.min(prevTime.get(u), foundTime.get(v)));
        }
    }
}
