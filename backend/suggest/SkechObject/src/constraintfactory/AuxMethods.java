package constraintfactory;

import java.util.ArrayList;
import java.util.List;

import jsonast.*;
import sketchobj.core.*;
import sketchobj.expr.*;
import sketchobj.stmts.*;

public class AuxMethods {

	
	static public List<Expression> extractArguments(Traces traces, int targetindex){
		List<Expression> result = new ArrayList<>();
		
		List<Trace> tracelist = traces.getTraces();
		//the trace with a call statement
		Trace callTrace = null;
		//searchs starting at the target index until it finds a trace
		//with a call statement
		for(int i = targetindex; i >=0; i--){
			if(tracelist.get(i).getEvent().equals("call")){
				callTrace = tracelist.get(i);
			break;
			}
		}
		//gets a list of local variables at the trace with a call stmt
		List<Var> args = callTrace.getLocals().getVar();
		//gets a list of heap  variables at the trace with a call stmt
		List<Var> heapObjs = callTrace.getHeap().getVar();
		
		for(Var v: args){
			//if its an int
			if(v.getType() == 0){
				result.add(new ExprString(v.getValue().toString()));
			}
			//if its a ref variable, search for the value in the heap
			if(v.getType() == 1){
				Integer heapIndex = v.getValue();
				for(Var h: heapObjs){
					if(h.getName().equals(heapIndex.toString())){
						result.add(new ExprString(h.getListasString()));
						break;
					}
				}
				
			}
			if(v.getType() == 3){
				System.out.println("error");
			}
		}
		
		
		return result;
	}
	
	static public String listToString(List l){
		String result = "[" + l.get(0).toString();
		for(int i = 1; i < l.size(); i++){
			result += "," +l.get(i);
		}
		return result + "]";
		
	}
}
