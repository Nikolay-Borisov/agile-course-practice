package ru.unn.agile.Huffman.model;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
public class HuffmanTest {
    @Test
    public void canFindTroubleWithBuildTreeWithWrongMessage() {
        String str = "";
        Huffman[] huffmans = Huffman.buildTree(str);
        assertNull(huffmans);
    }
    @Test
     public void canBildTreeWithMessage() {
        String str = "some message";
        Huffman[] huffmans = Huffman.buildTree(str);
        assertNotNull(huffmans);
    }
    @Test
    public void canBildTreeWithOneSimbol() {
        String str = "s";
        Huffman[] huffmans = Huffman.buildTree(str);
        assertNotNull(huffmans);
    }
    @Test
    public void canBildCorrectTreeWithMessage() {
        String str = "some message";
        Huffman[] huffmans = Huffman.buildTree(str);
        boolean res = Huffman.checkNode(huffmans, str.length());
        assertTrue(res);
    }
    @Test
    public void canBildCodeWithMessage() {
        String str = "some message";
        Huffman[] huffmans = Huffman.buildTree(str);
        String s = "";
        String[] st = new String[256];
        boolean res = Huffman.buildCode(st, huffmans, s, str.length(), -1);
        assertTrue(res);
    }
    @Test
    public void canWriteTree() {
        String str = "some message";
        Huffman[] huffmans = Huffman.buildTree(str);
        String s = "";
        String[] st = new String[256];
        s = Huffman.writeTree(huffmans, str.length());
        assertNotNull(s);
    }
}
