package utilities;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;

/** An abstract class for the users local settings. Includes the language and date/time information. */
public abstract class CurrentSession {
    private static LocalDateTime dateTime;

    /** Get the local time zone ID.
     * @return a ZoneId the local zone id. */
    public static ZoneId getZone(){
        ZoneId zone = ZoneId.systemDefault();
        return zone;
    }

    /** Gets the language string of the default language.
     * @return the string of the current default language. */
    public static String getLangString(){
        String lang = Locale.getDefault().getDisplayLanguage();
        return lang;
    }

    /** Gets the language code string of the default language.
     * @return the languge code of the current default language. */
    public static String getLangCode(){
        String langC = Locale.getDefault().getLanguage();
        return langC;
    }

}
