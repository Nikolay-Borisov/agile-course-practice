package ru.unn.agile.calculateSalary.Infrastructure;

import org.junit.Before;
import org.junit.Test;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import ru.unn.agile.calculateSalary.ViewModel.RegexMatcher;

public class RealLoggerTests {
    private static final String FILENAME = "./RealLoggerTests-lazarev-lab3.log";
    private RealLogger realLogger;

    @Before
    public void setUp() {
        realLogger = new RealLogger(FILENAME);
    }

    @Test
    public void createLoggerWithFilename() {
        assertNotNull(realLogger);
    }

    @Test
    public void createdLogFileWhenInitial() throws FileNotFoundException {
        FileReader fileReader = new FileReader(FILENAME);
        BufferedReader reader = new BufferedReader(fileReader);
    }

    @Test
    public void createLogMessage() {
        String example = "I'm test.";

        realLogger.textInLog(example);
        String message = realLogger.getLog().get(0);
        assertThat(message, RegexMatcher.matchesPattern(".*" + example + "$"));
    }

    @Test
    public void createMoreThanOneLogLine() {
        String[] fewMessages = {"I'm first test", "I'm second test"};

        realLogger.textInLog(fewMessages[0]);
        realLogger.textInLog(fewMessages[1]);

        List<String> messagesInLog = realLogger.getLog();
        assertThat(messagesInLog.get(0),
                   RegexMatcher.matchesPattern(".*" + fewMessages[0] + "$"));
        assertThat(messagesInLog.get(1),
                RegexMatcher.matchesPattern(".*" + fewMessages[1] + "$"));
    }

    @Test
    public void logIncludeDateAndTime() {
        String example = "I'm test";

        realLogger.textInLog(example);
        String message = realLogger.getLog().get(0);
        assertThat(message,
                   RegexMatcher.matchesPattern("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2} > .*"));
    }
}
