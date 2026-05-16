package pachet;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class NetworkVerifierTest {
    @Test
    public void testReteaLiniaraGasestePunctulDinMijloc()
    {
        NetworkVerifier<String> verifier=new NetworkVerifier<>();
        Map<String, List<String>> network=new HashMap<>();

        network.put("A", Arrays.asList("B"));
        network.put("B", Arrays.asList("A", "C"));
        network.put("C", Arrays.asList("B"));

        Set<String> pcteArticulatie=verifier.findArticulationPoints(network);

        assertEquals(1, pcteArticulatie.size());
        assertTrue(pcteArticulatie.contains("B"));
    }

    @Test
    public void testReteaCircularaNuArePuncteDeArticulatie()
    {
        NetworkVerifier<String> verifier=new NetworkVerifier<>();
        Map<String, List<String>> network=new HashMap<>();

        network.put("A", Arrays.asList("B", "C"));
        network.put("B", Arrays.asList("A", "C"));
        network.put("C", Arrays.asList("A", "B"));

        Set<String> pcteArticulatie=verifier.findArticulationPoints(network);

        assertTrue(pcteArticulatie.isEmpty());
    }

    @Test
    public void testGrupuriUniteDeUnSingurNodGasestePuntea()
    {
        NetworkVerifier<String> verifier=new NetworkVerifier<>();
        Map<String, List<String>> network=new HashMap<>();

        network.put("A", Arrays.asList("B", "X"));
        network.put("B", Arrays.asList("A", "X"));
        network.put("X", Arrays.asList("A", "B", "C", "D"));
        network.put("C", Arrays.asList("D", "X"));
        network.put("D", Arrays.asList("C", "X"));

        Set<String> pcteArticulatie=verifier.findArticulationPoints(network);

        assertEquals(1, pcteArticulatie.size());
        assertTrue(pcteArticulatie.contains("X"));
    }
}
