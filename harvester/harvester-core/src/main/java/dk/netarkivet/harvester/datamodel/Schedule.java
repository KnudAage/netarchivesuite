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
package dk.netarkivet.harvester.datamodel;

import java.io.Serializable;
import java.util.Date;

import dk.netarkivet.common.exceptions.ArgumentNotValid;
import dk.netarkivet.common.utils.Named;

/**
 * This class implements a schedule that can be either repeating or timed, depending on the subclass.
 * <p>
 * A schedule is a combination of a frequency, defining how often this schedule and at which points in time will trigger
 * an event , plus information about when to start and when to stop triggering events.
 * <p>
 * Methods are provided to check when the first event should happen, and to calculate the next events from the previous
 * event time.
 */
@SuppressWarnings({"serial"})
public abstract class Schedule implements Serializable, Named {

    /** Human readable name for the schedule. */
    protected String name;
    /** Any comments added by the user. */
    protected String comments;
    /** first run of job: date, time (hour:min:sec). May be null, meaning at any time */
    protected Date startDate;
    /** Frequency of runs, possibly with a time it should happen at. */
    protected Frequency frequency;
    /** Edition is used by the DAO to keep track of changes. */
    long edition = -1;
    /** ID autogenerated by DB, ignored otherwise. */
    private Long id;

    /**
     * Create a new schedule starting at a specific time and going on for an undefined time.
     *
     * @param startDate Time at which the schedule starts happening (though not necessarily the time of the first
     * event). May be null, meaning at any time.
     * @param frequency How frequently the events should happen
     * @param name The unique name of this schedule.
     * @param comments Comments entered by the user
     * @throws ArgumentNotValid if frequency, name or comments is null, or name is ""
     */
    protected Schedule(Date startDate, Frequency frequency, String name, String comments) {
        ArgumentNotValid.checkNotNullOrEmpty(name, "name");
        // startDate can be null in case of "start now"
        ArgumentNotValid.checkNotNull(frequency, "frequency");
        ArgumentNotValid.checkNotNull(comments, "comments");

        this.name = name;
        this.startDate = startDate;
        this.frequency = frequency;
        this.comments = comments;
    }

    /**
     * Get a new Schedule instance for a schedule that runs over a certain period.
     *
     * @param startDate The first date an event is allowed to happen. May be null, meaning at any time.
     * @param endDate The last date an event is allowed to happen. May be null meaning continue forever.
     * @param freq How frequently the events should happen.
     * @param name The name of this schedule.
     * @param comments Comments entered by the user
     * @return A new Schedule.
     * @throws ArgumentNotValid if frequency, name or comments is null, or name is ""
     */
    public static Schedule getInstance(Date startDate, Date endDate, Frequency freq, String name, String comments) {
        return new TimedSchedule(startDate, endDate, freq, name, comments);
    }

    /**
     * Get a new Schedule instance for a schedule that runs a certain number of times.
     *
     * @param startDate The first date an event is allowed to happen. May be null, meaning at any time.
     * @param repeats How many events should happen on this schedule.
     * @param freq How frequently the events should happen.
     * @param name The name of this schedule.
     * @param comments Comments entered by the user
     * @return A new Schedule.
     * @throws ArgumentNotValid if frequency, name or comments is null, or name is "" or repeats is 0 or negative
     */
    public static Schedule getInstance(Date startDate, int repeats, Frequency freq, String name, String comments) {
        return new RepeatingSchedule(startDate, repeats, freq, name, comments);
    }

    /**
     * Return the date at which the first event will happen.
     *
     * @param now The first time it can happen. Will be normalized to only second precision, milliseconds are set to 0.
     * @return The date of the first event to happen after startDate.
     * @throws ArgumentNotValid if now is null
     */
    public Date getFirstEvent(Date now) {
        ArgumentNotValid.checkNotNull(now, "now");

        now = new Date(now.getTime() / 1000 * 1000);
        if (startDate != null && now.before(startDate)) {
            return frequency.getFirstEvent(startDate);
        }
        return frequency.getFirstEvent(now);
    }

    /**
     * Return the date at which the next event will happen.
     *
     * @param lastEvent The time at which the previous event happened.
     * @param numPreviousEvents How many events have previously happened.
     * @return The date of the next event to happen or null for no more events.
     * @throws ArgumentNotValid if numPreviousEvents is negative
     */
    public abstract Date getNextEvent(Date lastEvent, int numPreviousEvents);

    /**
     * Get the name of this schedule.
     *
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the first possible date this schedule is allowed to run.
     *
     * @return The first possible date or null for any time
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Get the frequency defining how often and when this schedule should be run.
     *
     * @return The frequency
     */
    public Frequency getFrequency() {
        return frequency;
    }

    /**
     * Autogenerated equals.
     *
     * @param o The object to compare with
     * @return Whether objects are equal
     */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Schedule)) {
            return false;
        }

        final Schedule schedule = (Schedule) o;

        if (!comments.equals(schedule.comments)) {
            return false;
        }
        if (!frequency.equals(schedule.frequency)) {
            return false;
        }
        if (!name.equals(schedule.name)) {
            return false;
        }
        if (startDate != null ? !startDate.equals(schedule.startDate) : schedule.startDate != null) {
            return false;
        }

        return true;
    }

    /**
     * Autogenerated hashcode method.
     *
     * @return the hashcode
     */
    public int hashCode() {
        int result;
        result = name.hashCode();
        result = 29 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 29 * result + frequency.hashCode();
        result = 29 * result + comments.hashCode();
        return result;
    }

    /**
     * Returns any user-entered comments about this schedule.
     *
     * @return A string of free-form comments.
     */
    public String getComments() {
        return comments;
    }

    /**
     * Set the comments for the schedule.
     *
     * @param comments The new comments
     */
    public void setComments(String comments) {
        ArgumentNotValid.checkNotNull(comments, "comments");
        this.comments = comments;
    }

    /**
     * Get the edition number.
     *
     * @return The edition number
     */
    public long getEdition() {
        return edition;
    }

    /**
     * Set the edition number.
     *
     * @param theEdition The new edition
     */
    public void setEdition(long theEdition) {
        edition = theEdition;
    }

    /**
     * Get the ID of this schedule. Only for use by DBDAO
     *
     * @return the ID of this schedule
     */
    long getID() {
        return id;
    }

    /**
     * Set the ID of this schedule. Only for use by DBDAO
     *
     * @param id the new ID of this schedule
     */
    void setID(long id) {
        this.id = id;
    }

    /**
     * Check if this schedule has an ID set yet (doesn't happen until the DBDAO persists it).
     *
     * @return true if this schedule has an ID set yet
     */
    boolean hasID() {
        return id != null;
    }

}
