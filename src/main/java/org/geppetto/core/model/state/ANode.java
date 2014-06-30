/*******************************************************************************
 * The MIT License (MIT)
 * 
 * Copyright (c) 2011, 2013 OpenWorm.
 * http://openworm.org
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the MIT License
 * which accompanies this distribution, and is available at
 * http://opensource.org/licenses/MIT
 *
 * Contributors:
 *     	OpenWorm - http://openworm.org/people.html
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights 
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell 
 * copies of the Software, and to permit persons to whom the Software is 
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, 
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR 
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE 
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/
package org.geppetto.core.model.state;

import org.geppetto.core.model.state.visitors.IVisitable;

/**
 * Parent Node, use for serialization. 
 * 
 * @author matteocantarelli
 * 
 */
public abstract class ANode implements IVisitable
{
	protected ANode _parent;
	protected String _name;
	protected String _metatype;

	public enum SUBTREE
	{
		MODEL_TREE,
		VISUALIZATION_TREE,
		WATCH_TREE,
		TIME_STEP
	}
	
	/**
	 * Different types of nodes
	 * 
	 * @author  Jesus R. Martinez (jesus@metacell.us)
	 *
	 */
	public enum MetaTypes{
		EntityNode("Entity"), AspectNode("Aspect"), AspectTreeNode("AspectNode"),
		ParameterNode("Parameter"), AVisualNode("Visual Object"), StateVariableNode("State Variable"),
		TextMetadataNode("Text"), TimeNode("Time"), URLMetadataNode("URL");

		private final String text;
		
		private MetaTypes(final String text) {
			this.text = text;
		}
		
		@Override
		public String toString() {
			return text;
		}
	}
	public String getMetaType() {
		return _metatype;
	}

	public void setMetaType(String _metaType) {
		this._metatype = _metaType;
	}

	public ANode(String name)
	{
		super();
		_name = name;
		_parent = null;
	}
	
	public ANode(){
		
	}

	public void setName(String name){
		this._name = name;
	}
	
	public String getName()
	{
		return _name;
	}

	/**
	 * @return the next sibling of this node
	 */
	public ANode nextSibling()
	{
		if(!(getParent() instanceof ACompositeStateNode))
		{
			return null;
		}
		else
		{
			int currentIndex = ((ACompositeStateNode) getParent()).getChildren().indexOf(this);
			if(((ACompositeStateNode) getParent()).getChildren().size() < currentIndex + 2)
			{
				return null;
			}
			else
			{
				return ((ACompositeStateNode) getParent()).getChildren().get(currentIndex + 1);
			}
		}
	}

	public ANode getParent()
	{
		return _parent;
	}

	public boolean isArray()
	{
		return _name.contains("[");
	}
	
	public String getFullName()
	{
		StringBuffer fullName = new StringBuffer();
		ANode iterateState = this;
		while(iterateState != null)
		{
			if(iterateState._parent != null && !iterateState._name.equals(SUBTREE.MODEL_TREE.toString()) && !iterateState._name.equals(SUBTREE.WATCH_TREE.toString()))
			{
				if(!fullName.toString().isEmpty())
				{
					fullName.insert(0, ".");
				}
				fullName.insert(0, iterateState._name);
			}
			iterateState = iterateState._parent;
		}
		return fullName.toString();
	}

}
