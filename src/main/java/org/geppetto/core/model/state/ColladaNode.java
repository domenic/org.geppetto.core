package org.geppetto.core.model.state;

import org.geppetto.core.model.state.visitors.IStateVisitor;

/**
 * Node use to define a collada object for visualization and serialization
 * 
 * @author  Jesus R. Martinez (jesus@metacell.us)
 *
 */
public class ColladaNode extends AVisualObjectNode{

	private String model;
	
	public String getModel() {
		return model;
	}

	@Override
	public boolean apply(IStateVisitor visitor) {
		return visitor.visitColladaNode(this);
	}

	public void setModel(String model) {
		this.model = model;
	}

}
