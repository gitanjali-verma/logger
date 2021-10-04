package com.digitaldots.loggertest;

import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class LoggingMarkers {
    static final Marker JSON = MarkerManager.getMarker("JSON-MASK");
    static final Marker XML = MarkerManager.getMarker("XML-MASK");
}
