package com.digitaldots.loggertest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;

@Plugin(name = "logmask", category = "Converter")
@ConverterKeys("cm")
class LogMaskingConverter extends LogEventPatternConverter {
    private static final String NAME = "cm";
//    private static final String JSON_REPLACEMENT_REGEX = "$1: ****";
//    private static final String JSON_KEYS = "'ssn', 'private', 'creditCard'".join("|");
//    private static final Pattern JSON_PATTERN = Pattern.compile(JSON_KEYS + "}): ([^]+)");

    private static final String JSON_REPLACEMENT_REGEX = "*********";
    private static final String SSN_REGEX = "([0-9]{9})";
    private static final Pattern JSON_PATTERN = Pattern.compile(SSN_REGEX);

    LogMaskingConverter(String[] options) {
        super(NAME, NAME);
    }

    static LogMaskingConverter newInstance(final String[] options) {
        return new LogMaskingConverter(options);
    }

    @Override
    public void format(LogEvent event, StringBuilder outputMessage) {
        System.out.println("+++++++++++++++++++++++++");
        String message = event.getMessage().getFormattedMessage();
        String maskedMessage = message;

        if (event.getMarker().getName() == LoggingMarkers.JSON.getName()) {
            try {
                maskedMessage = mask(message);
            } catch (Exception e) {
                maskedMessage = message; // Although if this fails, it may be better to not log the message
            }
        }
        outputMessage.append(maskedMessage);
    }

    private String mask(String message) {
        System.out.println("+++++++++++++++++++++++++");
        StringBuffer buffer = new StringBuffer();
        Matcher matcher = JSON_PATTERN.matcher(message);
        while (matcher.find()) {
            matcher.appendReplacement(buffer, JSON_REPLACEMENT_REGEX);
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

}