package ru.unn.agile.Triangle.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.Triangle.Model.Triangle.Operation;

import static org.junit.Assert.*;

public class ViewModelTest {
    private ViewModel viewModel;

    public void setExternalViewModel(final ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Before
    public void setUp() {
        if (viewModel == null) {
            viewModel = new ViewModel(new TestLogger());
        }
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canSetDefaultValues() {
        assertEquals("", viewModel.aXProperty().get());
        assertEquals("", viewModel.aYProperty().get());
        assertEquals("", viewModel.bXProperty().get());
        assertEquals("", viewModel.bYProperty().get());
        assertEquals("", viewModel.cXProperty().get());
        assertEquals("", viewModel.cYProperty().get());
        assertEquals(Operation.PERIMETER, viewModel.operationProperty().get());
        assertEquals("", viewModel.valuesProperty().get());
        assertEquals(CurrentStatus.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsWaitingWhenComputeWithEmptyFields() {
        viewModel.compute();

        assertEquals(CurrentStatus.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsReadyWhenFieldsAreFill() {
        setTestData();

        assertEquals(CurrentStatus.READY.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canReportBadFormat() {
        viewModel.aXProperty().set("bad");

        assertEquals(CurrentStatus.BAD_FORMAT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsWaitingIfNotEnoughCorrectData() {
        viewModel.aXProperty().set("0");

        assertEquals(CurrentStatus.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void computeButtonIsDisabledInitially() {
        assertTrue(viewModel.computationDisabledProperty().get());
    }

    @Test
    public void computeButtonIsDisabledWhenFormatIsBad() {
        setTestData();
        viewModel.aXProperty().set("bad");

        assertTrue(viewModel.computationDisabledProperty().get());
    }

    @Test
    public void computeButtonIsDisabledWithIncompleteInput() {
        viewModel.aXProperty().set("1");

        assertTrue(viewModel.computationDisabledProperty().get());
    }

    @Test
    public void computeButtonIsEnabledWithCorrectInput() {
        setTestData();

        assertFalse(viewModel.computationDisabledProperty().get());
    }

    @Test
    public void canSetPerimeterOperation() {
        viewModel.operationProperty().set(Operation.PERIMETER);

        assertEquals(Operation.PERIMETER, viewModel.operationProperty().get());
    }

    @Test
    public void perimeterIsDefaultOperation() {
        assertEquals(Operation.PERIMETER, viewModel.operationProperty().get());
    }

    @Test
    public void canSetSuccessMessage() {
        setTestData();

        viewModel.compute();

        assertEquals(CurrentStatus.SUCCESS.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canSetBadFormatMessage() {
        viewModel.aXProperty().set("bad");

        assertEquals(CurrentStatus.BAD_FORMAT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsReadyWhenSetProperData() {
        setTestData();

        assertEquals(CurrentStatus.READY.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void perimeterComputationHasCorrectValues() {
        viewModel.aXProperty().set("2");
        viewModel.aYProperty().set("2");
        viewModel.bXProperty().set("4");
        viewModel.bYProperty().set("4");
        viewModel.cXProperty().set("4");
        viewModel.cYProperty().set("2");

        viewModel.compute();

        assertEquals("6.83", viewModel.valuesProperty().get());
    }

    @Test
    public void lengthsComputationHasCorrectValues() {
        viewModel.aXProperty().set("1");
        viewModel.aYProperty().set("2");
        viewModel.bXProperty().set("1");
        viewModel.bYProperty().set("4");
        viewModel.cXProperty().set("2");
        viewModel.cYProperty().set("2");
        viewModel.operationProperty().set(Operation.LENGTHS);

        viewModel.compute();

        assertEquals("2.00 2.24 1.00", viewModel.valuesProperty().get());
    }

    @Test
    public void spaceComputationHasCorrectValues() {
        viewModel.aXProperty().set("1");
        viewModel.aYProperty().set("1");
        viewModel.bXProperty().set("1");
        viewModel.bYProperty().set("3");
        viewModel.cXProperty().set("2");
        viewModel.cYProperty().set("1");
        viewModel.operationProperty().set(Operation.SPACE);

        viewModel.compute();

        assertEquals("1.00", viewModel.valuesProperty().get());
    }

    @Test
    public void anglesComputationHasCorrectValues() {
        viewModel.aXProperty().set("1");
        viewModel.aYProperty().set("1");
        viewModel.bXProperty().set("1");
        viewModel.bYProperty().set("4");
        viewModel.cXProperty().set("4");
        viewModel.cYProperty().set("1");
        viewModel.operationProperty().set(Operation.ANGLES);

        viewModel.compute();

        assertEquals("0.00 0.71 0.71", viewModel.valuesProperty().get());
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotCreateViewModelWithNullLogger() {
        new ViewModel(null);
    }

    @Test
    public void logIsEmptyAfterConstruction() {
        String records = viewModel.getRecords();

        assertTrue(records.isEmpty());
    }

    @Test
    public void recordIsLoggedAfterComputation() {
        setTestData();
        viewModel.compute();
        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*" + LogMessages.COMPUTE_PRESSED + ".*"));
    }

    @Test
    public void correctRecordIsLoggedAfterComputation() {
        setTestData();

        viewModel.compute();
        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*" + viewModel.aXProperty().get()
                + ".*" + viewModel.aYProperty().get()
                + ".*" + viewModel.bXProperty().get()
                + ".*" + viewModel.bYProperty().get()
                + ".*" + viewModel.cXProperty().get()
                + ".*" + viewModel.cYProperty().get()
                + ".*"));
    }

    @Test
    public void inputRecordFormatIsCorrect() {
        setTestData();

        viewModel.compute();

        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*Points: "
                + "Ax = " + viewModel.aXProperty().get()
                + ", Ay = " + viewModel.aYProperty().get()
                + ", Bx = " + viewModel.bXProperty().get()
                + ", By = " + viewModel.bYProperty().get()
                + ", Cx = " + viewModel.cXProperty().get()
                + ", Cy = " + viewModel.cYProperty().get()
                + ".*"));
    }

    @Test
    public void operationInfoIsRecorded() {
        setTestData();

        viewModel.compute();

        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*Perimeter.*"));
    }

    @Test
    public void canRecordSeveralTimes() {
        setTestData();

        viewModel.compute();
        viewModel.compute();

        assertEquals(2, viewModel.getLog().size());
    }

    @Test
    public void recordIsLoggedAfterOperationChanged() {
        setTestData();

        viewModel.onOperationChanged(Operation.PERIMETER, Operation.SPACE);
        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*" + LogMessages.OPERATION_CHANGED + " Space.*"));
    }

    @Test
    public void recordIsNotLoggedIfOperationWasNotChanged() {
        viewModel.onOperationChanged(Operation.PERIMETER, Operation.PERIMETER);

        assertEquals(0, viewModel.getLog().size());
    }

    @Test
    public void argumentsAreCorrectlyLogged() {
        setTestData();

        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);
        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*" + LogMessages.INPUT_FINISHED
                + "Input points: \\("
                + viewModel.aXProperty().get() + ", "
                + viewModel.aYProperty().get() + "\\), "
                + "\\(" + viewModel.bXProperty().get() + ", "
                + viewModel.bYProperty().get() + "\\), "
                + "\\(" + viewModel.cXProperty().get() + ", "
                + viewModel.cYProperty().get() + "\\)."));
    }

    @Test
    public void recordWouldNotBeAddedIfComputeButtonDisabled() {
        viewModel.compute();

        assertTrue(viewModel.getLog().isEmpty());
    }

    @Test
    public void doNotRecordSameParametersTwice() {
        viewModel.aXProperty().set("12");
        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);
        viewModel.aXProperty().set("12");
        viewModel.onFocusChanged(Boolean.TRUE, Boolean.FALSE);

        assertEquals(1, viewModel.getLog().size());
    }

    private void setTestData() {
        viewModel.aXProperty().set("1");
        viewModel.aYProperty().set("2");
        viewModel.bXProperty().set("3");
        viewModel.bYProperty().set("4");
        viewModel.cXProperty().set("3");
        viewModel.cYProperty().set("2");
    }
}
