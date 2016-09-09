/*******************************************************************************
 * Copyright (c) 2014 Felix Kutzner.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Felix Kutzner - initial implementation.
 ******************************************************************************/

package tools.vitruv.domains.emf.monitorededitor.test.utils;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class BasicTestCase {

    private static boolean hasLoggerBeenInitialized = false;
    protected Logger LOGGER = Logger.getLogger(BasicTestCase.class);

    public BasicTestCase() {
        synchronized (BasicTestCase.class) {
            if (!hasLoggerBeenInitialized) {
                org.apache.log4j.BasicConfigurator.configure();
                hasLoggerBeenInitialized = true;
                Logger.getRootLogger().setLevel(Level.ALL);
            }
        }
    }

}
