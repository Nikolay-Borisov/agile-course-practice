package ru.unn.agile.Queue.viewmodel;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import ru.unn.agile.Queue.model.Queue;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ViewModel {
    private ILogger logger;
    private final StringProperty  txtToAdd          = new SimpleStringProperty();
    private final StringProperty  state             = new SimpleStringProperty();
    private final StringProperty  element           = new SimpleStringProperty();
    private final BooleanProperty isAddingDisabled  = new SimpleBooleanProperty();
    private final Queue<Integer>  queue             = new Queue<Integer>();
    private final StringProperty logEntries = new SimpleStringProperty();

    private final InputChangeListener valueChangedListener = new InputChangeListener();

    private class InputChangeListener implements ChangeListener<String> {
        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
            String inputState = getInputState().toString();
            state.set(inputState);
            if (inputState == ViewState.BAD_INPUT.toString()) {
                log(ILogger.Level.ERR, "Incorrect item: " + newValue);
            } else {
                log(ILogger.Level.DBG, "Item to add: " + newValue);
            }
        }
    }

    public ViewModel() {
        txtToAdd.set("");
        setElementToEmpty();
        state.set(ViewState.AWAITING.toString());

        BooleanBinding canAdd = new BooleanBinding() {
            {
                super.bind(txtToAdd);
            }
            @Override
            protected boolean computeValue() {
                return getInputState() == ViewState.READY;
            }
        };
        isAddingDisabled.bind(canAdd.not());

        txtToAdd.addListener(valueChangedListener);
    }

    public void add() {
        if (isAddingDisabled.get()) {
            return;
        }

        Integer item = Integer.parseInt(getTxtToAdd());
        queue.add(item);
        updateElement();
        log(ILogger.Level.INFO, "Added " + getTxtToAdd());
    }

    public void remove() {
        try {
            Integer item = queue.remove();
            txtToAdd.set(item.toString());
            state.set(ViewState.OK.toString());
            log(ILogger.Level.INFO, "Remove " + item.toString());

        } catch (NoSuchElementException nsee) {
            state.set(ViewState.EMPTY.toString());
            log(ILogger.Level.ERR, "Unable to remove an item");
        }
        updateElement();
    }

    public boolean isQueueEmpty() {
        return queue.isEmpty();
    }

    public BooleanProperty isAddingDisabledProperty() {
        return isAddingDisabled;
    }

    public final boolean getIsAddingDisabled() {
        return isAddingDisabled.get();
    }

    public StringProperty stateProperty() {
        return state;
    }

    public final String getState() {
        return state.get();
    }

    public StringProperty elementProperty() {
        return element;
    }

    public final String getElement() {
        return element.get();
    }

    public StringProperty txtToAddProperty() {
        return txtToAdd;
    }

    public final String getTxtToAdd() {
        return txtToAdd.get();
    }

    public StringProperty logEntriesProperty() {
        return logEntries;
    }

    public final String getLogEntries() {
        return logEntries.get();
    }

    public void setLogger(final ILogger logger) {
        this.logger = logger;
    }

    public List<String> getLog() {
        if (logger != null) {
            return logger.getLog();
        }
        return new ArrayList<String>();
    }

    private void log(final ILogger.Level level, final String s) {
        if (logger == null) {
            return;
        }

        logger.log(level, s);
        fetchLogs();
    }

    private void fetchLogs() {
        if (logger == null) {
            return;
        }

        List<String> storedLog = logger.getLog();
        String logToView = "";
        for (String s : storedLog) {
            logToView += s + "\n";
        }
        logEntries.set(logToView);
    }

    private ViewState getInputState() {
        ViewState inputState = ViewState.READY;
        if (getTxtToAdd().isEmpty()) {
            inputState = ViewState.AWAITING;
            return inputState;
        }

        try {
            Integer.parseInt(getTxtToAdd());
        } catch (NumberFormatException nfe) {
            inputState = ViewState.BAD_INPUT;
        }
        return inputState;
    }

    private void updateElement() {
        if (isQueueEmpty()) {
            setElementToEmpty();
        } else {
            element.set(queue.element().toString());
        }
    }

    private void setElementToEmpty() {
        element.set("Empty");
    }
}

enum ViewState {
    AWAITING("Insert an item"),
    READY("Press add button"),
    BAD_INPUT("Incorrect input"),
    EMPTY("Queue is empty"),
    OK("Success");

    private final String name;

    private ViewState(final String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
