package logutils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class LogEntry {
    long timestamp;
    String log;

    LogEntry(long timestamp, String log) {
        this.timestamp = timestamp;
        this.log = log;
    }
}

class SplitLogEntry {
    long timestamp;
    int key;
    int size;
    int page;
    String message;
    String originalLog;

    SplitLogEntry(long timestamp, int key, int size, int page, String message, String originalLog) {
        this.timestamp = timestamp;
        this.key = key;
        this.size = size;
        this.page = page;
        this.message = message;
        this.originalLog = originalLog;
    }
}

public class LogProcessor {
    public static void main(String[] args) {
        List<LogEntry> normalLogEntries = new ArrayList<>();
        List<SplitLogEntry> splitLogEntries = new ArrayList<>();
        String filePath = "All-Messages-search-result.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
            String line;
            Pattern timestampPattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}\\.\\d{3}");
            Pattern keyPattern = Pattern.compile("seq(-)?(\\d+)-size-(\\d+)-page-(\\d+)");
            Pattern messagePattern = Pattern.compile("seq(-)?(\\d+)-size-(\\d+)-page-(\\d+):\\s(.+)$");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

            while ((line = reader.readLine()) != null) {
                Matcher timestampMatcher = timestampPattern.matcher(line);
                if (timestampMatcher.find()) {
                    String timestampStr = timestampMatcher.group();
                    try {
                        long timestamp = dateFormat.parse(timestampStr).getTime();
                        Matcher keyMatcher = keyPattern.matcher(line);
                        if (keyMatcher.find()) {
                            int key = Integer.parseInt(keyMatcher.group(2));
                            int size = Integer.parseInt(keyMatcher.group(3));
                            int page = Integer.parseInt(keyMatcher.group(4));
                            Matcher messageMatcher = messagePattern.matcher(line);
                            if (messageMatcher.find()) {
                                String message = messageMatcher.group(5);
                                splitLogEntries.add(new SplitLogEntry(timestamp, key, size, page, message, line));
                            }
                        } else {
                            normalLogEntries.add(new LogEntry(timestamp, line));
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        splitLogEntries.sort(Comparator.comparingLong((SplitLogEntry e) -> e.timestamp)
                .thenComparingInt(e -> e.key)
                .thenComparingInt(e -> e.page));

        Map<Integer, List<SplitLogEntry>> groupedSplitLogEntries = new HashMap<>();
        for (SplitLogEntry entry : splitLogEntries) {
            groupedSplitLogEntries.computeIfAbsent(entry.key, k -> new ArrayList<>()).add(entry);
        }

        for (List<SplitLogEntry> entries : groupedSplitLogEntries.values()) {
            if (entries.size() < 2) {
                normalLogEntries.add(new LogEntry(entries.get(0).timestamp, entries.get(0).originalLog));
            } else {
                entries.sort(Comparator.comparingInt(e -> e.page));
                StringBuilder finalLog = new StringBuilder(entries.get(0).originalLog);
                for (SplitLogEntry entry : entries.subList(1, entries.size())) {
                    finalLog.append(entry.message);
                }
                normalLogEntries.add(new LogEntry(entries.get(0).timestamp, finalLog.toString()));
            }
        }

        normalLogEntries.sort(Comparator.comparingLong(e -> e.timestamp));

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("sorted_log_entries.csv"), StandardCharsets.UTF_8)) {
            for (LogEntry entry : normalLogEntries) {
                writer.write(entry.log + System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

