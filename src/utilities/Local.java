package utilities;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.TimeZone;

/** An abstract class for the users local settings. */
public abstract class Local {
    private static LocalDateTime dateTime;

    /** Get the local time zone ID.
     * @return a ZoneId the local zone id. */
    public static ZoneId getZone(){
        ZoneId zone = ZoneId.systemDefault();
        return zone;
    }

    public static String getLangString(){
        String lang = Locale.getDefault().getDisplayLanguage();
        return lang;
    }

    public static String getLangCode(){
        String langC = Locale.getDefault().getLanguage();
        return langC;
    }

}
