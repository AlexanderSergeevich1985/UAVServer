/**The MIT License (MIT)
 Copyright (c) 2018 by AleksanderSergeevich
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:
 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */
package uav.Utils;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DateTimeHelper {
    private static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy HH:mm:ss Z";

    public static class StopWatch {
        private Instant start;
        private Instant stop;

        private void StopWatch() {}

        public void start() {
            this.start = Instant.now();
        }

        public void stop() {
            this.stop = Instant.now();
        }

        public void reset() {
            this.start = Instant.now();
            this.stop = null;
        }

        public Long getTimeMeasurement(@Nonnull final TimeUnit units) {
            if(this.stop == null) return 0l;
            Duration measurement = Duration.between(start, stop);
            switch(units) {
                case DAYS:
                    return measurement.toDays();
                case HOURS:
                    return measurement.toHours();
                case MINUTES:
                    return measurement.toMinutes();
                case SECONDS:
                    return measurement.getSeconds();
                case MILLISECONDS:
                    return measurement.toMillis();
                case NANOSECONDS:
                    return measurement.toNanos();
            }
            return 0l;
        }

        public static StopWatch getInstance() {
            StopWatch timer = new StopWatch();
            timer.start();
            return timer;
        }
    }

    public static Long timeUnitConvert(@Nonnull final TimeUnit from, @Nonnull final TimeUnit to, @Nonnull final Long duration) {
        if(from.equals(to)) return duration;
        switch(to) {
            case DAYS:
                return from.toDays(duration);
            case HOURS:
                return from.toHours(duration);
            case MINUTES:
                return from.toMinutes(duration);
            case SECONDS:
                return from.toSeconds(duration);
            case MILLISECONDS:
                return from.toMillis(duration);
            case MICROSECONDS:
                return from.toMicros(duration);
            case NANOSECONDS:
                return from.toNanos(duration);
        }
        return duration;
    }

    @Deprecated
    public static Long diffBetweenTwoDate(@Nonnull final Date firstDate, @Nonnull final Date secondDate) {
        return Math.abs(secondDate.getTime() - firstDate.getTime());
    }

    public static Long diffBetweenTwoDate(@Nonnull final ZonedDateTime firstDate, @Nonnull final ZonedDateTime secondDate) {
        return Duration.between(firstDate, secondDate).toMillis();
    }

    public static Long diffBetweenTwoDateType(@Nonnull final ZonedDateTime firstDate, @Nonnull final ZonedDateTime secondDate, @Nonnull final TimeUnit units) {
        long diff = diffBetweenTwoDate(firstDate, secondDate);
        if(units.equals(TimeUnit.MILLISECONDS)) return diff;
        switch(units) {
            case DAYS:
                return TimeUnit.MILLISECONDS.toDays(diff);
            case HOURS:
                return TimeUnit.MILLISECONDS.toHours(diff);
            case MINUTES:
                return TimeUnit.MILLISECONDS.toMinutes(diff);
            case SECONDS:
                return TimeUnit.MILLISECONDS.toSeconds(diff);
            case MICROSECONDS:
                return TimeUnit.MILLISECONDS.toMicros(diff);
            case NANOSECONDS:
                return TimeUnit.MILLISECONDS.toNanos(diff);
        }
        return diff;
    }

    public static LocalDateTime getLocalCurrentTime() {
        return LocalDateTime.now();
    }

    public static LocalDateTime getLocalDateTime(@Nonnull String ldt, @Nonnull String pattern) {
        return LocalDateTime.parse(ldt, DateTimeFormatter.ofPattern(pattern));
    }

    @Nullable
    public static ZonedDateTime getCurrentTimeWithTimeZone(final String zoneId) {
        if(zoneId == null || zoneId.isEmpty()) return null;
        return ZonedDateTime.now(ZoneId.of(zoneId));
    }

    public static ZoneId getLocalZoneId() {
        return ZoneId.systemDefault();
    }

    public static ZoneId calcZoneIdByOffset(@Nonnull final int timeOffset, @Nonnull final TimeUnit units) throws DateTimeException {
        ZoneOffset offset = null;
        switch(units) {
            case HOURS:
                offset = ZoneOffset.ofHours(timeOffset);
                break;
            case MINUTES:
                offset = ZoneOffset.ofHoursMinutes(timeOffset / 60, timeOffset % 60);
                break;
            case SECONDS:
                offset = ZoneOffset.ofTotalSeconds(timeOffset);
                break;
            case MICROSECONDS:
                offset = ZoneOffset.ofTotalSeconds((int)TimeUnit.SECONDS.convert(timeOffset, units));
                break;
            case NANOSECONDS:
                offset = ZoneOffset.ofTotalSeconds((int)TimeUnit.SECONDS.convert(timeOffset, units));
                break;
            default:
                throw new DateTimeException("Time units isn't correct");
        }
        return ZoneId.ofOffset("UTC", offset);
    }

    @Nullable
    public static ZonedDateTime addIntervalToDate(@Nullable final ZonedDateTime startDate, long ammountToAdd, @Nonnull final TemporalUnit units) {
        if(startDate == null || units == null) return null;
        return startDate.plus(ammountToAdd, units);
    }

    @Nullable
    public static String zdtToString(@Nullable final ZonedDateTime zdt, @Nullable final String pattern) {
        if(zdt == null) return null;
        if(pattern == null || pattern.isEmpty())
            return zdt.format(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT));
        else
            return zdt.format(DateTimeFormatter.ofPattern(pattern));
    }

    @Nullable
    public static ZonedDateTime stringToZDT(@Nullable final String zdt) throws DateTimeParseException {
        if(zdt == null || zdt.isEmpty()) return null;
        return ZonedDateTime.parse(zdt);
    }

    public static Timestamp zdtToTimestamp(@Nonnull final ZonedDateTime zdt) throws IllegalArgumentException {
        return Timestamp.from(zdt.toInstant());
    }

    public static ZonedDateTime timestampToZDT(@Nonnull final Timestamp timestamp, final @Nullable ZoneId zoneId) throws IllegalArgumentException {
        return zoneId != null ? timestamp.toLocalDateTime().atZone(zoneId) : timestamp.toLocalDateTime().atZone(ZoneId.systemDefault());
    }
}
