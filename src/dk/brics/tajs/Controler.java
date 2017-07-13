package dk.brics.tajs;

import dk.brics.tajs.analysis.FunctionCalls;
import dk.brics.tajs.analysis.Analysis;
import dk.brics.tajs.analysis.Solver;
import dk.brics.tajs.analysis.js.UserFunctionCalls;
import dk.brics.tajs.flowgraph.AbstractNode;
import dk.brics.tajs.flowgraph.FlowGraph;
import dk.brics.tajs.flowgraph.Function;
import dk.brics.tajs.lattice.ExecutionContext;
import dk.brics.tajs.lattice.ObjectLabel;
import dk.brics.tajs.lattice.State;
import dk.brics.tajs.lattice.Value;
import dk.brics.tajs.solver.BlockAndContext;
import dk.brics.tajs.solver.CallGraph;
import dk.brics.tajs.util.AnalysisException;

import java.lang.reflect.Array;
import java.util.*;

import static dk.brics.tajs.util.Collections.newSet;

/**
 * Created by scc_ubuntu on 5/31/17.
 */
public class Controler {

    private static  Collection<Function> liybryFunctions;
    private static Collection<Function> classFunctions;
    private static List<String> dependancyFunctionsNames = new ArrayList<>();
    private static Solver.SolverInterface c;
    private static Analysis a;


    public static <T> T fileAnalser(String [] args, boolean isFlowgraph){
    T fg = null;
    try {
        Main.initLogging();

        a = Main.init(args, null);
        if (a == null)
            System.exit(-1);
        Main.run(a);

        Main.reset();
        if (isFlowgraph) {
            fg = (T)a.getSolver().getFlowGraph();
        }else {
            fg = (T)a.getSolver().getAnalysisLatticeElement().getCallGraph();
        }
    } catch (AnalysisException e) {
        e.printStackTrace();
        System.exit(-2);
    }
    return fg;
}

public static void controller(String [] args){
    String libName = args[0];
    String fileName = args[1];
    String flowgraph = "-flowgraph";
    String callgraph = "-callgraph";

    //List<String> commonFunctionNames = new ArrayList<>();

    //liybryFunctions = Controler.<FlowGraph>fileAnalser(new String[]{libName,flowgraph},true).getFunctions();

    //classFunctions  = Controler.<FlowGraph>fileAnalser(new String[]{fileName,flowgraph}, true).getFunctions();



    CallGraph cg  = Controler.<CallGraph>fileAnalser(new String[]{callgraph, fileName, libName}, false);

    Set<Map.Entry<Function, Set<AbstractNode>>> teme = cg.getReverseEdgesIgnoreContexts().entrySet();

    for (Map.Entry<Function, Set<AbstractNode>> me : teme) {
        Function f = me.getKey();
        if (f.getName()!= null)
        dependancyFunctionsNames.add("function "+ f.getName());
        //System.out.println("..........lllllll......"+f.getName());
    }

    JSParser fileSearch = new JSParser();
    String[] fn = dependancyFunctionsNames.toArray(new String[dependancyFunctionsNames.size()]); // Functions Array
    try {
        fileSearch.parseFile("/home/scc_ubuntu/Documents/simple.js", fn); // Input File
        //System.out.println(classFunctions.toString());
    } catch ( Exception e){
        e.printStackTrace();
    }
}



}
