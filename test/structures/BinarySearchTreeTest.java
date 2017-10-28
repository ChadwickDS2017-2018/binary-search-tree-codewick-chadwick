package structures;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import config.Configuration;

public class BinarySearchTreeTest {

	private BinarySearchTree<Integer> tree;
	private static final int SPEED_TEST = 8;
	
	@Before
	public void setUp() throws Exception {
		tree = Configuration.createBinarySearchTree();
		assertNotNull("It looks like you did not set createBinarySearchTree in your configuration file.", tree);
	}

	@Test (timeout = 100)
	public void testSimpleAddSizeAndIsEmpty(){
		assertTrue("Fresh tree should be empty.", tree.isEmpty());
		assertEquals("Fresh tree should have size 0.", 0, tree.size());
		assertEquals("Add should return tree for convenience.", tree, tree.add(1));
		assertFalse("Tree should now be non-empty.", tree.isEmpty());
		assertEquals("Size should now be 1.", 1, tree.size());
		assertEquals("Add should return tree for convenience.", tree, tree.add(1));
		assertFalse("Tree should now be non-empty.", tree.isEmpty());
		assertEquals("Size should now be 2.", 2, tree.size());
		assertEquals("Add should return tree for convenience.", tree, tree.add(1));
		assertFalse("Tree should now be non-empty.", tree.isEmpty());
		assertEquals("Size should now be 3.", 3, tree.size());
		assertEquals("Add should return tree for convenience.", tree, tree.add(2));
		assertFalse("Tree should now be non-empty.", tree.isEmpty());
		assertEquals("Size should now be 4.", 4, tree.size());
	}
	
	@Test (timeout = 100)
	public void testSimpleAddAndContains() {
		assertFalse("Tree should not contain anything.", tree.contains(1));
		assertEquals("Add should return tree for convenience.", tree, tree.add(1));
		assertTrue("After add, contains should return true.", tree.contains(1));
		
		assertFalse("Tree should not contain 5.", tree.contains(5));
		assertEquals("Add should return tree for convenience.", tree, tree.add(5));
		
		assertTrue("After add, contains should return true.", tree.contains(5));
		
	}
	
	
	
	@Test (timeout = 1000)
	public void testRandomAddContains() {
		Random r = new Random(42);
		Set<Integer> valuesAdded = new HashSet<Integer>();
		for(int i = 0; i < SPEED_TEST; i++){
			assertEquals("Tree should have i elements in it.", i, tree.size());
			int next = r.nextInt();
			
			if(!valuesAdded.contains(next)){
				assertFalse("Tree should not contain this value yet.", tree.contains(next));
				valuesAdded.add(next);
			}
			
			assertEquals("Add should return tree for convenience.", tree, tree.add(next));
			assertTrue("After add, contains should return true.", tree.contains(next));
		}
	}
	
	@Test (timeout = 100, expected = NullPointerException.class)
	public void testAddNullPointer(){
		tree.add(null);
	}
	
	@Test (timeout = 100, expected = NullPointerException.class)
	public void testContainsNullPointer(){
		tree.contains(null);
	}
	
	
	@Test (timeout = 100)
	public void testSimpleAddRemoveAndSize() {
		assertEquals("Add should return tree for convenience.", tree, tree.add(1));
		assertEquals("Add should return tree for convenience.", tree, tree.add(5));
		assertEquals("Add should return tree for convenience.", tree, tree.add(5));
		assertEquals("Add should return tree for convenience.", tree, tree.add(5));
		
		assertEquals(4, tree.size());
		assertTrue(tree.remove(1));
		assertEquals(3, tree.size());
		assertFalse(tree.remove(1));
		assertEquals(3, tree.size());
		assertTrue(tree.remove(5));
		assertEquals(2, tree.size());
		assertTrue(tree.remove(5)); 
		assertEquals(1, tree.size());
		assertTrue(tree.remove(5));
		assertEquals(0, tree.size());
		assertFalse(tree.remove(5));
		assertTrue(tree.isEmpty());
	}
	
	@Test (timeout = 50000000)
	public void testRandomAddRemoveAndSize() {
		Random r = new Random(42);
		List<Integer> valuesAdded = new LinkedList<Integer>();
		
		for(int i = 0; i < SPEED_TEST; i++){
			assertEquals("Tree should have i elements in it.", i, tree.size());
			int next = r.nextInt(SPEED_TEST);
			valuesAdded.add(next);
			assertEquals("Add should return tree for convenience.", tree, tree.add(next));
			assertTrue("After add, contains should return true.", tree.contains(next));
		}
		Iterator<Integer> itr = tree.iterator();
		
		while(itr.hasNext())
		    System.out.println(itr.next());
		assertEquals(SPEED_TEST, tree.size());
		for(Integer i : valuesAdded) {
		    System.out.println("Attempting to remove "+ i + "...");
		    System.out.println(tree.remove(i));
		    
			assertTrue("Could not remove previously added node.", tree.remove(i));
		}
		Iterator<Integer> itr2 = tree.iterator();
		while(itr2.hasNext()) {
            System.out.println(itr2.next());
		}
        
		assertEquals(0, tree.size());
		assertTrue(tree.isEmpty());		
	}
	
	private void assertIteratorContains(Iterator<Integer> itr, Integer ... elems){
		List<Integer> found = new LinkedList<Integer>();
		for(Integer e : elems){
			if(!itr.hasNext())
				fail("Expected iterator to produce " + Arrays.toString(elems) + " but produced " + found);
			Integer test = itr.next();
			found.add(test);
			if(!test.equals(e))
				fail("Expected iterator to produce " + Arrays.toString(elems) + " but start of iterator produced " + found);
		}
		
		if(itr.hasNext()){
			while(itr.hasNext())
				found.add(itr.next());
			fail("Expected iterator to produce " + Arrays.toString(elems) + " but produced " + found);
		}
		
	}
	
	@Test (timeout = 1000)
	public void testActualTreeRemove() {
		tree.add(50).add(49).add(47).add(48).add(51).add(58).add(57).add(59);
		assertTrue("Unable to remove node", tree.remove(59));
		Iterator<Integer> iter = tree.iterator();

		assertFalse(tree.contains(59));
		assertTrue(tree.size() == 7);
		
	}
	
	@Test (timeout = 100)
	public void testSimpleGetMinAndGetMax(){
		tree.add(4).add(2).add(1).add(3).add(5).add(6).add(7);
		assertEquals(new Integer(1), tree.getMinimum());
		assertEquals(new Integer(7), tree.getMaximum());
	}
	
	@Test (timeout = 1000)
	public void testRandomGetMinAndGetMax() {
		Random r = new Random(42);
		LinkedList<Integer> values = new LinkedList<Integer>();
		int currentMin = Integer.MAX_VALUE;
		int currentMax = Integer.MIN_VALUE;
		for(int i = 0; i < SPEED_TEST; i++){
			int next = r.nextInt();
			currentMin = Math.min(currentMin, next);
			currentMax = Math.max(currentMax, next);
			values.add(next);
			tree.add(next);
		}
		
		assertEquals(new Integer(currentMin), tree.getMinimum());
		assertEquals(new Integer(currentMax), tree.getMaximum());
		
	}
	
	@Test (timeout = 100, expected = IllegalStateException.class)
	public void testIllegalStateGetMin(){
		tree.getMinimum();
	}
	
	@Test (timeout = 100, expected = IllegalStateException.class)
	public void testIllegalStateGetMax(){
		tree.getMaximum();
	}
	
	@Test (timeout = 100)
	public void testSimpleIterable(){
		tree.add(4).add(2).add(1).add(3).add(5).add(6).add(7);
		LinkedList<Integer> values = new LinkedList<Integer>();
		values.add(1);
		values.add(2);
		values.add(3);
		values.add(4);
		values.add(5);
		values.add(6);
		values.add(7);
		for(Integer i : tree){
			Integer toCheck = values.remove();
			assertEquals(toCheck, i);
		}
		
	}
	
	@Test (timeout = 1000)
	public void testRandomIterator() {
		Random r = new Random(42);
		LinkedList<Integer> values = new LinkedList<Integer>();
		
		for(int i = 0; i < SPEED_TEST; i++){
			int next = r.nextInt();
			values.add(next);
			tree.add(next);
		}
		
		Collections.sort(values);

		for(Integer i : tree){
			Integer toCheck = values.remove();
			assertEquals(toCheck, i);
		}
				
	}
	
}
