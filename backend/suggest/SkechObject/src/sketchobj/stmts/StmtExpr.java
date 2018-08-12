package sketchobj.stmts;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import constraintfactory.ConstData;
import constraintfactory.ConstraintFactory;
import constraintfactory.ExternalFunction;
import sketchobj.core.Context;
import sketchobj.core.SketchObject;
import sketchobj.core.Type;
import sketchobj.expr.ExprConstant;
import sketchobj.expr.ExprFunCall;
import sketchobj.expr.Expression;

public class StmtExpr extends Statement {
	private Expression expr;

	public StmtExpr(Expression expr, int i) {
		this.expr = expr;
		expr.setParent(this);
		this.setLineNumber(i);;
	}

	public String toString() {
		return expr.toString() + ";";
	}
	/**
	 * If this stmt contains an expression that is a constant, it replaces the
	 * the ExprConst with an ExprFunCall to the name of a function with the same 
	 * name as the constant's name concatinated with index
	 * it then returns ConstData which wraps the info of the removed ExprConst
	*/
	@Override
	public ConstData replaceConst(int index) {
		List<SketchObject> toAdd = new ArrayList<SketchObject>();
		if (expr instanceof ExprConstant) {
			int value = ((ExprConstant) expr).getVal();
			Type t = ((ExprConstant) expr).getType();
			//replaces the ExprConst with a function call
			expr = new ExprFunCall("Const" + index, new ArrayList<Expression>());
			//returns an object that wraps all the info of the ExprConstant
			return new ConstData(t, toAdd, index + 1, value,null,this.getLineNumber());
		}
		return expr.replaceConst(index);
	}

	@Override
	public ConstData replaceConst_Exclude_This(int index,List<Integer> repair_range) {
		return new ConstData(null, new ArrayList<SketchObject>(), index, 0, null,this.getLineNumber());
	}
	
	@Override
	public Context buildContext(Context prectx) {
		Context postctx = new Context(prectx);
		prectx = new Context(prectx);
		postctx.setLinenumber(this.getLineNumber());
		prectx.setLinenumber(this.getLineNumber());
		
		this.setPostctx(new Context(postctx));
		this.setPrectx(new Context(prectx));
		return postctx;
	}

	@Override
	public Map<String, Type> addRecordStmt(StmtBlock parent, int index, Map<String, Type> m) {
		parent.stmts.set(index,
				new StmtBlock(ConstraintFactory.recordState(this.getPrectx().getLinenumber(), this.getPrectx().getAllVars()),this));
		m.putAll(this.getPostctx().getAllVars());
		return m;
	}

	@Override
	public  ConstData replaceLinearCombination(int index){
		return new ConstData(null, new ArrayList<SketchObject>(), index, 0, null,this.getLineNumber());
	}

	@Override
	public boolean isBasic() {
		return true;
	}

	@Override
	public List<ExternalFunction> extractExternalFuncs(List<ExternalFunction> externalFuncNames) {
		return expr.extractExternalFuncs(externalFuncNames);
	}

}
