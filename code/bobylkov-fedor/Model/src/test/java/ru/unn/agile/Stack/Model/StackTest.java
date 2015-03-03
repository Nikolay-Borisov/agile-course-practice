package ru.unn.agile.Stack.Model;

import javafx.collections.ListChangeListener;
import org.junit.Before;
import org.junit.Test;

import java.util.EmptyStackException;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertArrayEquals;

public class StackTest {
    private Stack<Object> stack;

    @Before
    public void setUpTest() {
        stack = new Stack<>();
    }

    @Test
    public void isEmptyStackEmpty() {
        assertTrue(stack.isEmpty());
    }

    @Test
    public void isNotEmptyStackNotEmpty() {
        stack.push(1);
        assertFalse(stack.isEmpty());
    }

    @Test
    public void isStackEmptyAfterPop() {
        stack.push(1);
        stack.pop();
        assertTrue(stack.isEmpty());
    }

    @Test (expected = EmptyStackException.class)
    public void cannotPopFromEmptyStack() {
        stack.pop();
    }

    @Test
    public void canPopFromNotEmptyStack() {
        stack.push(1);
        assertEquals(1, stack.pop());
    }

    @Test
    public void canPush() {
        stack.push(1);
        assertStackEquals(new Object[] {1}, stack);
    }

    @Test (expected = EmptyStackException.class)
    public void cannotGetTopInEmptyStack() {
        stack.top();
    }

    @Test
    public void canGetTopInNotEmptyStack() {
        stack.push(1);
        assertEquals(1, stack.top());
    }

    @Test
    public void isTopCorrectAfterPush() {
        stack.push(1);
        assertEquals(1, stack.top());
        stack.push(2);
        assertEquals(2, stack.top());
    }

    @Test
    public void isTopCorrectAfterPop() {
        stack.push(1);
        stack.push(2);

        assertEquals(2, stack.top());
        stack.pop();
        assertEquals(1, stack.top());
    }

    @Test
    public void isStackOrderCorrect() {
        stack.push(1);
        stack.push(2);
        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());
    }

    @Test
    public void canObjectStackContainDifferentTypeElements() {
        stack.push(1);
        stack.push("str");
        assertStackEquals(new Object[] {1, "str"}, stack);
    }

    @Test
    public void canCreateSpecificTypeStack() {
        Stack<String> stringStack = new Stack<>();
        stringStack.push("str");
        assertStackEquals(new Object[] {"str"}, stringStack);
    }

    @Test
    public void canCopyStackFromStack() {
        stack.push(1);
        stack.push(2);

        Stack<Object> stackFromStack = new Stack<>(stack);
        assertStackEquals(new Object[] {1, 2}, stackFromStack);
    }

    @Test
    public void canCopyStackFromList() {
        stack.push(1);
        stack.push(2);

        Stack<Object> stackFromList = new Stack<>(stack.toList());
        assertStackEquals(new Object[] {1, 2}, stackFromList);
    }

    @Test
    public void isOriginalStackCorrectAfterCopyingFromStack() {
        stack.push(1);
        stack.push(2);

        Stack<Object> stackFromStack = new Stack<>(stack);
        assertStackEquals(new Object[] {1, 2}, stack);
    }

    @Test
    public void isOriginalStackCorrectAfterCopyingFromList() {
        stack.push(1);
        stack.push(2);

        Stack<Object> stackFromList = new Stack<>(stack.toList());
        assertStackEquals(new Object[] {1, 2}, stack);
    }

    @Test
    public void canConvertStackToList() {
        stack.push(1);
        stack.push(2);

        List<Object> list = stack.toList();
        assertArrayEquals(new Object[] {1, 2}, list.toArray());
    }

    @Test
    public void canConvertEmptyStackToList() {
        List<Object> list = stack.toList();
        assertArrayEquals(new Object[] {}, list.toArray());
    }

    @Test
    public void canConvertSpecificTypeStackToSameTypeList() {
        Stack<String> stringStack = new Stack<>();
        stringStack.push("str");

        List<String> stringList = stringStack.toList();
        assertArrayEquals(new Object[] {"str"}, stringList.toArray());
    }

    @Test
    public void isStackNotCorruptedAfterCallingToList() {
        stack.push(1);
        stack.push(2);

        List<Object> list = stack.toList();
        list.clear();

        assertArrayEquals(new Object[] {}, list.toArray());
        assertStackEquals(new Object[] {1, 2}, stack);
    }

    @Test
    public void canAddListener() {
        stack.addListener(new ListChangeListener<Object>() {
            @Override
            public void onChanged(final Change<?> c) {
                assertArrayEquals(c.getList().toArray(), stack.toList().toArray());
            }
        });

        stack.push(1);
        stack.push(2);
    }

    @Test
    public void canRemoveListener() {
        ListChangeListener<Object> listener = new ListChangeListener<Object>() {
            @Override
            public void onChanged(final Change<?> c) {
                fail("Listener was removed, but its change method was called.");
            }
        };
        stack.addListener(listener);

        stack.removeListener(listener);
        stack.push(1);
    }

    private void assertStackEquals(final Object[] expected, final Stack actual) {
        assertArrayEquals(expected, actual.toList().toArray());
    }
}
