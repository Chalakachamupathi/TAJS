package dk.brics.tajs;

import dk.brics.tajs.analysis.Analysis;
import dk.brics.tajs.flowgraph.AbstractNode;
import dk.brics.tajs.flowgraph.Function;
import dk.brics.tajs.solver.CallGraph;
import dk.brics.tajs.util.AnalysisException;
import java.util.*;

/**
 * Created by scc_ubuntu on 5/31/17.
 */
public class Controller {

    private List<String> dependancyFunctionsNames = new ArrayList<>();


    private <T> T fileAnalser(String [] args, boolean isFlowgraph){
        T fg = null;
        Analysis a;
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

    public void controller(String [] args){
        String libName = args[0];
        String fileName = args[1];
        String callgraph = "-callgraph";


        CallGraph cg  = Controller.this.<CallGraph>fileAnalser(new String[]{callgraph, fileName, libName}, false);

        Set<Map.Entry<Function, Set<AbstractNode>>> teme = cg.getReverseEdgesIgnoreContexts().entrySet();

        for (Map.Entry<Function, Set<AbstractNode>> me : teme) {
            Function f = me.getKey();
            if (f.getName()!= null)
                this.dependancyFunctionsNames.add("function "+ f.getName());
        }

        JSParser fileSearch = new JSParser();
        String[] fn = this.dependancyFunctionsNames.toArray(new String[this.dependancyFunctionsNames.size()]); // Functions Array
        try {
            fileSearch.parseFile("/home/scc_ubuntu/Documents/simple.js", fn); // Input File
        } catch ( Exception e){
            e.printStackTrace();
        }
    }



}
