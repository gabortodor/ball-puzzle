package helper;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Duration;
import org.apache.commons.lang3.time.DurationFormatUtils;

/**
 * A class implementing a basic stopwatch function, using Javafx.
 */
public class Stopwatch {

    private LongProperty seconds = new SimpleLongProperty();
    private StringProperty hhmmss = new SimpleStringProperty();
    private Timeline timeline;

    /**
     * Creates a {@code Stopwatch} object.
     */
    public Stopwatch() {
        timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            seconds.set(seconds.get() + 1);
        }), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        hhmmss.bind(Bindings.createStringBinding(() -> formatSeconds(seconds.get()), seconds));
    }

    /**
     * {@return the secondProperty linked to the seconds attribute}
     */
    public LongProperty secondsProperty() {
        return seconds;
    }

    /**
     * {@return the seconds elapsed since the start}
     */
    public long getSeconds() {
        return seconds.get();
    }

    /**
     * {@return a property to access the time elapsed in {@code hh:mm:ss} format}
     */
    public StringProperty hhmmssProperty() {
        return hhmmss;
    }

    /**
     * Starts the stopwatch.
     */
    public void start() {
        timeline.play();
    }

    /**
     * Stops the stopwatch.
     */
    public void stop() {
        timeline.pause();
    }

    /**
     * Resets the stopwatch.
     */
    public void reset() {
        seconds.set(0);
    }

    /**
     * {@return the given seconds in {@code hh:mm:ss} format}
     *
     * @param seconds the seconds which will be converted to the String format
     */
    public static String formatSeconds(long seconds){
        return DurationFormatUtils.formatDuration(seconds * 1000, "HH:mm:ss");
    }

}
