/*
 * #%L
 * Netarchivesuite - harvester
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
package dk.netarkivet.harvester.heritrix3;

import dk.netarkivet.common.utils.ApplicationUtils;

/**
 * This application controls the Heritrix3 harvester which does the actual harvesting, and is also responsible for
 * uploading the harvested data to the ArcRepository.
 *
 * @see HarvestControllerServer
 */
public class HarvestControllerApplication {

    /**
     * Runs the HarvestController Application. Settings are read from config files.
     *
     * @param args an empty array
     */
    public static void main(String[] args) {
        ApplicationUtils.startApp(HarvestControllerServer.class, args);
    }

}
