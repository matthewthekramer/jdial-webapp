package sketchobj.core;
import java.util.Collection;
import java.util.Collections;

import constraintfactory.ConstData;

public class TypeStructRef extends Type{
    private String name;
    private final boolean isUnboxed;
    public TypeStructRef(String name, boolean isUnboxed) {
        this.name = name;
        this.isUnboxed = isUnboxed;
    }
    public String getName()
    {
        return name;
    }
    public boolean isStruct () { return true; }
    
    public Collection<Type> getBaseTypes() {
        return Collections.singletonList((Type) this);
    }
	public boolean isUnboxed() {
		return isUnboxed;
	}
	@Override
	public ConstData replaceLinearCombination(int index) {
		// TODO Auto-generated method stub
		return null;
	}
}
