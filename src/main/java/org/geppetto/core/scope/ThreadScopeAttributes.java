/*
 * Copyright 2007-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.geppetto.core.scope;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Thread scope attributes.
 * 
 * @author David Winterfeldt
 */
public class ThreadScopeAttributes {
        
    protected final Map<String, Object> hBeans = new HashMap<String, Object>();
    protected final Map<String, Runnable> hRequestDestructionCallbacks = new LinkedHashMap<String, Runnable>();

    public ThreadScopeAttributes(){
    }
    /**
     * Gets bean <code>Map</code>.
     */
    protected final Map<String, Object> getBeanMap() {
        return hBeans;
    }

    /**
     * Register the given callback as to be executed after request completion.
     * 
     * @param   name        The name of the bean.
     * @param   callback    The callback of the bean to be executed for destruction.
     */
    protected final void registerRequestDestructionCallback(String name, Runnable callback) {
        
        hRequestDestructionCallbacks.put(name, callback);
    }

    /**
     * Clears beans and processes all bean destruction callbacks.
     */
    protected final void clear() {
        processDestructionCallbacks();
        
        hBeans.clear();   
    }

    /**
     * Processes all bean destruction callbacks.
     */
    private final void processDestructionCallbacks() {
        for (String name: hRequestDestructionCallbacks.keySet()) {
            Runnable callback = hRequestDestructionCallbacks.get(name);
            
            System.out.println("Performing destruction callback for '" + name + "' bean" + 
                     " on thread '" + Thread.currentThread().getName() + "'.");
            
            callback.run();
        }
        
        hRequestDestructionCallbacks.clear();
    }
	public void updateHBeands(String name, Object value) {
		this.hBeans.put(name, value);
	}

}