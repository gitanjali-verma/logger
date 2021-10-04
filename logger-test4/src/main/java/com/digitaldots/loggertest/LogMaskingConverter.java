package com.digitaldots.loggertest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.core.pattern.PatternConverter;

@Plugin(name = "LogMaskingConverter", category = PatternConverter.CATEGORY)
@ConverterKeys({ "logmasking" })
public class LogMaskingConverter extends LogEventPatternConverter {

    private static final String NAME = "logmasking";

//    private static final String CREDIT_CARD_REGEX = "((?:(?:\\d{4}[- ]){3}\\d{4}|\\d{16}))(?![\\d])";
    private static final String CREDIT_CARD_REGEX = "([0-9]{4})[0-9]{0,9}([0-9]{4})";
    private static final Pattern CREDIT_CARD_PATTERN = Pattern.compile(CREDIT_CARD_REGEX);
    private static final String CAREDIT_CARD_REPLACEMENT_REGEX = "XXXXXXXXXXXXXXXX";

    private static final String CVV_REGEX = "([0-9]{3})";
    private static final Pattern CVV_PATTERN = Pattern.compile(CVV_REGEX);
    private static final String CVV_REPLACEMENT_REGEX = "+++";

    private static final String SSN_REGEX = "([0-9]{9})";
//    private static final String SSN_REGEX = "^(?!666|000|9\\d{2})\\d{3}-(?!00)\\d{2}-(?!0{4})\\d{4}$";
    private static final Pattern SSN_PATTERN = Pattern.compile(SSN_REGEX);
    private static final String SSN_REPLACEMENT_REGEX = "*********";

    private LogMaskingConverter(String[] options) {
        super(NAME, NAME);
    }

    public static LogMaskingConverter newInstance(final String[] options) {
        return new LogMaskingConverter(options);
    }

    @Override
    public void format(LogEvent event, StringBuilder outputMessage) {
        String message = event.getMessage().getFormattedMessage();
        String maskedMessage = message;
        if (event.getMarker() != null && LoggingMarkers.JSON.getName().equals(event.getMarker().getName())) {
            try {
                maskedMessage = mask(message);
            } catch (Exception e) {
                maskedMessage = message;
            }
        }
        outputMessage.append(maskedMessage);
    }

    private String mask(String message) {
        Matcher matcher = null;
        StringBuffer buffer = new StringBuffer();

        if (message.contains("CREDIT_CARD")) {
            matcher = CREDIT_CARD_PATTERN.matcher(message);
            maskMatcher(matcher, buffer, CAREDIT_CARD_REPLACEMENT_REGEX);
            message = buffer.toString();
        } else if (message.contains("SSN")) {
            matcher = SSN_PATTERN.matcher(message);
            maskMatcher(matcher, buffer, SSN_REPLACEMENT_REGEX);
            message = buffer.toString();
        } else if (message.contains("CVV")) {
            matcher = CVV_PATTERN.matcher(message);
            maskMatcher(matcher, buffer, CVV_REPLACEMENT_REGEX);
            message = buffer.toString();
        }
        return message;
    }

    private StringBuffer maskMatcher(Matcher matcher, StringBuffer buffer, String maskStr) {
        while (matcher.find()) {
            matcher.appendReplacement(buffer, maskStr);
        }
        matcher.appendTail(buffer);
        return buffer;
    }
}