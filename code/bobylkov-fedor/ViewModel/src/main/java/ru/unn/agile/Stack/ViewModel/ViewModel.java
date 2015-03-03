package ru.unn.agile.Stack.ViewModel;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import ru.unn.agile.Stack.Model.Stack;

import java.util.ArrayList;
import java.util.List;

public class ViewModel {
    private ILogger logger;

    private final ObjectProperty<ObservableList<String>> stackTable = new SimpleObjectProperty<>();
    private final StringProperty top = new SimpleStringProperty();
    private final StringProperty textToPush = new SimpleStringProperty();
    private final BooleanProperty isEmpty = new SimpleBooleanProperty();
    private final Stack<String> stack = new Stack<>();
    private final StringProperty logs = new SimpleStringProperty();

    public StringProperty logsProperty() {
        return logs;
    }
    public final String getLogs() {
        return logs.get();
    }

    public StringProperty topProperty() {
        return top;
    }
    public final String getTop() {
        return top.get();
    }

    public StringProperty textToPushProperty() {
        return textToPush;
    }
    public final String getTextToPush() {
        return textToPush.get();
    }
    public final void setTextToPush(final String text) {
        textToPush.set(text);
    }

    public ObjectProperty<ObservableList<String>> stackTableProperty() {
        return stackTable;
    }
    public final ObservableList<String> getStackTable() {
        return stackTable.get();
    }

    public BooleanProperty isEmptyProperty() {
        return isEmpty;
    }
    public final Boolean getIsEmpty() {
        return isEmpty.get();
    }
    public Boolean isPopButtonDisabled() {
        return getIsEmpty();
    }

    public ViewModel() {
        stack.addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(final Change<? extends String> c) {
                updateProperties();
                c.next();
                log("Stack size changed to: " + c.getList().size());
            }
        });
        textToPush.addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable,
                                final String oldValue,
                                final String newValue) {
                log("Text-To-Push changed to: " + newValue);
            }
        });
        top.addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable,
                                final String oldValue,
                                final String newValue) {
                log("Top changed to: " + newValue);
            }
        });

        updateProperties();
        textToPush.set("Push me!");
    }

    public void push() {
        log("Pushed: " + textToPush.get());
        stack.push(textToPush.get());
    }

    public void pop() {
        if (isPopButtonDisabled()) {
            return;
        }
        log("Popped: " + stack.top());
        stack.pop();
    }

    public void setLogger(final ILogger newLogger) {
        logger = newLogger;
    }

    public List<LogMessage> getLog() {
        if (logger != null) {
            return logger.getLog();
        }
        return new ArrayList<>();
    }

    private void log(final String message) {
        if (logger == null) {
            return;
        }
        logger.log(message);
        updateLogs();
    }

    private void updateLogs() {
        if (logger == null) {
            return;
        }
        List<LogMessage> fullLog = logger.getLog();
        String updatedLog = "";
        for (LogMessage message : fullLog) {
            updatedLog += message.toString() + "\n";
        }
        logs.set(updatedLog);
    }

    private void updateProperties() {
        isEmpty.set(stack.isEmpty());
        top.set(getIsEmpty() ? "" : stack.top());
        stackTable.set(FXCollections.observableList(stack.toList()));
    }
}
