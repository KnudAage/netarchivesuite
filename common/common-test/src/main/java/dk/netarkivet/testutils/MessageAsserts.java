/*
 * #%L
 * Netarchivesuite - common - test
 * %%
 * Copyright (C) 2005 - 2017 The Royal Danish Library, 
 *             the National Library of France and the Austrian National Library.
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 2.1 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-2.1.html>.
 * #L%
 */

package dk.netarkivet.testutils;

import dk.netarkivet.common.distribute.NetarkivetMessage;
import junit.framework.TestCase;

/**
 * Assertions on JMS/Netarkivet messages
 */

public class MessageAsserts {
    /**
     * Assert that a message is ok, and if not, print out the error message.
     *
     * @param s A string explaining what was expected to happen, e.g. "Get message on existing file should reply ok"
     * @param msg The message to check the status of
     */
    public static void assertMessageOk(String s, NetarkivetMessage msg) {
        if (!msg.isOk()) {
            TestCase.fail(s + ": " + msg.getErrMsg());
        }
    }
}
