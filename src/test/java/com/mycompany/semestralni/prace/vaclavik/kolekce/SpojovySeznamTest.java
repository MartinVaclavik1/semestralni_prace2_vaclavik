package com.mycompany.semestralni.prace.vaclavik.kolekce;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * @author karel@simerda.cz
 */
public class SpojovySeznamTest<E> {

    /**
     * Testovací třída pro ověření implementace třídy SpojovySeznam
     */
    private static class TestClass {

        int a;

        public TestClass(int a) {
            this.a = a;
        }

        @Override
        public String toString() {
            return "T" + a;
        }

    }

    /**
     * *
     * Sada instancí testovací třídy pro ověření implementace třídy
     * SpojovySeznam
     */
    private final TestClass T1 = new TestClass(1);
    private final TestClass T2 = new TestClass(2);
    private final TestClass T3 = new TestClass(3);
    private final TestClass T4 = new TestClass(4);
    private final TestClass T5 = new TestClass(5);
    private final TestClass T6 = new TestClass(6);
    private final TestClass T7 = new TestClass(7);
    private final TestClass T8 = new TestClass(8);
    private final TestClass T9 = new TestClass(9);

    public SpojovySeznamTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // Ukázka jednoduchého testu
    @Test
    public void test_01_01_VlozPrvni() {
        try {
            SpojovySeznam<TestClass> instance = new SpojovySeznam<>();
            instance.vlozPrvni(T1);
            TestClass[] expected = new TestClass[]{T1};
            TestClass[] result = new TestClass[]{instance.dejPosledni()};//
            assertArrayEquals(expected, result);
        } catch (Exception ex) {
            fail();
        }
    }

    // Ukázka testu s vícenásobnou kontrolou stavu testované instance
    @Test
    public void test_01_02_VlozPrvni() {
        try {
            SpojovySeznam<TestClass> instance = new SpojovySeznam<>();
            instance.vlozPrvni(T1);
            instance.vlozPrvni(T2);
            TestClass[] result = {instance.dejPrvni(), instance.dejPosledni()};
            TestClass[] expected = {T2, T1};
            assertArrayEquals(expected, result);
        } catch (Exception ex) {
            fail();
        }
    }

    //
//  Ukázka testu s vyvoláním výjimky
    @Test(expected = NullPointerException.class)
    public void test_01_03_VlozPrvni() throws KolekceException {
        SpojovySeznam<TestClass> instance = new SpojovySeznam<>();
        instance.vlozPrvni(null);
        fail();
    }

    @Test
    public void test_01_04_VlozPrvni() {
        try {
            SpojovySeznam<TestClass> instance = new SpojovySeznam<>();
            instance.vlozPrvni(T1);
            instance.vlozPrvni(T2);
            instance.vlozPrvni(T3);
            instance.nastavPrvni();
            instance.dalsi();
            TestClass[] result = {instance.dejPrvni(), instance.dejAktualni(), instance.dejPosledni()};
            TestClass[] expected = {T3, T2, T1};
            assertArrayEquals(expected, result);
        } catch (Exception ex) {
            fail();
        }
    }

    @Test(expected = KolekceException.class)
    public void test_02_01_NastavPrvni() throws KolekceException {
        Seznam<TestClass> instance = new SpojovySeznam<>();
        instance.stream();
        instance.nastavPrvni();
        fail();
    }

    @Test
    public void test_02_02_NastavPrvni() {
        try {
            Seznam<TestClass> instance = new SpojovySeznam<>();
            instance.vlozPrvni(T1);
            instance.nastavPrvni();
            TestClass[] expected = {T1};
            TestClass[] result = {instance.dejAktualni()};
            assertArrayEquals(expected, result);
        } catch (KolekceException e) {
            fail();
        }
    }

    @Test(expected = KolekceException.class)
    public void test_03_01_NastavPosledni() throws KolekceException {
        Seznam<TestClass> instance = new SpojovySeznam<>();
        instance.stream();
        instance.nastavPosledni();
        fail();
    }

    @Test
    public void test_03_02_NastavPosledni() {
        try {
            Seznam<TestClass> instance = new SpojovySeznam<>();
            instance.vlozPrvni(T1);
            instance.vlozPrvni(T2);
            instance.nastavPosledni();
            TestClass[] expected = {T1};
            TestClass[] result = {instance.dejAktualni()};
            assertArrayEquals(expected, result);
        } catch (KolekceException e) {
            fail();
        }
    }

    @Test
    public void test_04_02_NastavDalsi() {
        try {
            Seznam<TestClass> instance = new SpojovySeznam<>();
            instance.vlozPrvni(T1);
            instance.vlozPrvni(T2);
            instance.nastavPrvni();
            instance.dalsi();
            TestClass[] expected = {T1};
            TestClass[] result = {instance.dejAktualni()};
            assertArrayEquals(expected, result);
        } catch (KolekceException e) {
            fail();
        }
    }

    @Test(expected = KolekceException.class)
    public void test_04_01_NastavDalsi() throws KolekceException {
        Seznam<TestClass> instance = new SpojovySeznam<>();
        instance.dalsi();
        fail();
    }

    @Test
    public void test_05_01_JeDalsi() {
        try {
            Seznam<TestClass> instance = new SpojovySeznam<>();
            instance.vlozPrvni(T1);
            instance.nastavPrvni();
            boolean expected = false;
            boolean result = instance.jeDalsi();
            assertEquals(expected, result);
        } catch (KolekceException e) {
            fail();
        }
    }

    @Test
    public void test_06_01_VlozPosledni() {
        try {
            Seznam<TestClass> instance = new SpojovySeznam<>();
            instance.vlozPosledni(T1);
            instance.nastavPrvni();
            TestClass[] expected = {T1};
            TestClass[] result = {instance.dejAktualni()};
            assertArrayEquals(expected, result);
        } catch (KolekceException e) {
            fail();
        }
    }

    @Test
    public void test_06_02_VlozPosledni() {
        try {
            Seznam<TestClass> instance = new SpojovySeznam<>();
            instance.vlozPosledni(T1);
            instance.vlozPosledni(T2);
            instance.nastavPosledni();
            TestClass[] expected = {T2};
            TestClass[] result = {instance.dejAktualni()};
            assertArrayEquals(expected, result);
        } catch (KolekceException e) {
            fail();
        }
    }

    @Test(expected = NullPointerException.class)
    public void test_06_03_VlozPosledni() {

        Seznam<TestClass> instance = new SpojovySeznam<>();
        instance.vlozPosledni(null);
        fail();

    }

    @Test
    public void test_06_04_VlozPosledni() {
        try {
            SpojovySeznam<TestClass> instance = new SpojovySeznam<>();
            instance.vlozPosledni(T1);
            instance.vlozPosledni(T2);
            instance.vlozPosledni(T3);
            instance.nastavPrvni();
            instance.dalsi();
            TestClass[] result = {instance.dejPrvni(), instance.dejAktualni(), instance.dejPosledni()};
            TestClass[] expected = {T1, T2, T3};
            assertArrayEquals(expected, result);
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void test_07_01_VlozZaAktualni() {
        try {
            Seznam<TestClass> instance = new SpojovySeznam<>();
            instance.vlozPosledni(T1);
            instance.vlozPosledni(T2);
            instance.nastavPrvni();
            instance.vlozZaAktualni(T3);
            TestClass[] expected = {T1, T3, T2};
            TestClass[] result = {instance.dejPrvni(), instance.dejZaAktualnim(), instance.dejPosledni()};
            assertArrayEquals(expected, result);
        } catch (KolekceException e) {
            fail();
        }
    }

    @Test(expected = KolekceException.class)
    public void test_07_02_VlozZaAktualni() throws KolekceException {
        Seznam<TestClass> instance = new SpojovySeznam<>();
        instance.vlozZaAktualni(T1);
        fail();
    }

    @Test(expected = NullPointerException.class)
    public void test_07_03_VlozZaAktualni() throws NullPointerException, KolekceException {

        Seznam<TestClass> instance = new SpojovySeznam<>();
        instance.vlozPosledni(T1);
        instance.nastavPosledni();
        instance.vlozZaAktualni(null);
        fail();

    }

    @Test
    public void test_07_04_VlozZaAktualni() {
        try {
            Seznam<TestClass> instance = new SpojovySeznam<>();
            instance.vlozPosledni(T1);
            instance.vlozPosledni(T2);
            instance.nastavPosledni();
            instance.vlozZaAktualni(T3);
            TestClass[] expected = {T1, T3, T3};
            TestClass[] result = {instance.dejPrvni(), instance.dejZaAktualnim(), instance.dejPosledni()};
            assertArrayEquals(expected, result);
        } catch (KolekceException e) {
            fail();
        }
    }

    @Test
    public void test_08_01_DejPrvni() {
        try {
            Seznam<TestClass> instance = new SpojovySeznam<>();
            instance.vlozPosledni(T1);
            instance.vlozPosledni(T2);
            TestClass[] expected = {T1};
            TestClass[] result = {instance.dejPrvni()};
            assertArrayEquals(expected, result);
        } catch (KolekceException e) {
            fail();
        }
    }

    @Test(expected = KolekceException.class)
    public void test_08_02_DejPrvni() throws KolekceException {

        Seznam<TestClass> instance = new SpojovySeznam<>();
        instance.dejPrvni();
        fail();
    }

    @Test
    public void test_09_01_DejPosledni() {
        try {
            Seznam<TestClass> instance = new SpojovySeznam<>();
            instance.vlozPosledni(T1);
            instance.vlozPosledni(T2);
            TestClass[] expected = {T2};
            TestClass[] result = {instance.dejPosledni()};
            assertArrayEquals(expected, result);
        } catch (KolekceException e) {
            fail();
        }
    }

    @Test(expected = KolekceException.class)
    public void test_09_02_DejPosledni() throws KolekceException {
        Seznam<TestClass> instance = new SpojovySeznam<>();
        instance.dejPosledni();
        fail();
    }

    @Test(expected = KolekceException.class)
    public void test_10_01_DejZaAktualnim() throws KolekceException {
        Seznam<TestClass> instance = new SpojovySeznam<>();
        instance.dejZaAktualnim();
        fail();
    }

    @Test(expected = KolekceException.class)
    public void test_11_01_OdeberPrvni() throws KolekceException {
        Seznam<TestClass> instance = new SpojovySeznam<>();
        instance.odeberPrvni();
        fail();
    }

    @Test
    public void test_11_02_OdeberPrvni() {
        try {
            Seznam<TestClass> instance = new SpojovySeznam<>();
            instance.vlozPrvni(T1);
            instance.vlozPrvni(T2);
            instance.odeberPrvni();
            TestClass[] expected = {T1};
            TestClass[] result = {instance.dejPrvni()};
            assertArrayEquals(expected, result);
        } catch (KolekceException e) {
            fail();
        }
    }

    @Test
    public void test_11_03_OdeberPrvni() {
        try {
            Seznam<TestClass> instance = new SpojovySeznam<>();
            instance.vlozPrvni(T1);
            instance.vlozPrvni(T2);
            instance.nastavPrvni();
            instance.odeberPrvni();
            int expected = 1;
            int result = instance.size();
            assertEquals(expected, result);
        } catch (KolekceException e) {
            fail();
        }
    }

    @Test(expected = KolekceException.class)
    public void test_12_01_OdeberPosledni() throws KolekceException {
        Seznam<TestClass> instance = new SpojovySeznam<>();
        instance.odeberPosledni();
        fail();
    }

    @Test
    public void test_12_02_OdeberPosledni() {
        try {
            Seznam<TestClass> instance = new SpojovySeznam<>();
            instance.vlozPrvni(T1);
            instance.vlozPrvni(T2);
            instance.odeberPosledni();
            TestClass[] expected = {T2};
            TestClass[] result = {instance.dejPosledni()};
            assertArrayEquals(expected, result);
        } catch (KolekceException e) {
            fail();
        }
    }

    @Test
    public void test_12_03_OdeberPosledni() {
        try {
            Seznam<TestClass> instance = new SpojovySeznam<>();
            instance.vlozPrvni(T1);
            instance.vlozPrvni(T2);
            instance.nastavPosledni();
            instance.odeberPosledni();
            int expected = 1;
            int result = instance.size();
            assertEquals(expected, result);
        } catch (KolekceException e) {
            fail();
        }
    }

    @Test
    public void test_12_04_OdeberPosledni() {
        try {
            Seznam<TestClass> instance = new SpojovySeznam<>();
            instance.vlozPrvni(T2);
            instance.vlozPrvni(T1);
            instance.vlozPosledni(T3);
            instance.vlozPosledni(T4);
            instance.vlozPosledni(T5);
            instance.odeberPosledni();
            instance.odeberPosledni();
            TestClass[] expected = {T3};
            TestClass[] result = {instance.dejPosledni()};
            assertArrayEquals(expected, result);
        } catch (KolekceException e) {
            fail();
        }
    }

    @Test
    public void test_12_05_OdeberPosledni() {
        try {
            Seznam<TestClass> instance = new SpojovySeznam<>();
            instance.vlozPrvni(T2);
            instance.odeberPosledni();
            int expected = 0;
            int result = instance.size();
            assertEquals(expected, result);
        } catch (KolekceException e) {
            fail();
        }
    }

    @Test(expected = KolekceException.class)
    public void test_13_01_OdeberZaAktualnim() throws KolekceException {
        Seznam<TestClass> instance = new SpojovySeznam<>();
        instance.odeberZaAktualnim();
        fail();
    }

    @Test
    public void test_13_02_OdeberZaAktualnim() {
        try {
            Seznam<TestClass> instance = new SpojovySeznam<>();
            instance.vlozPrvni(T1);
            instance.vlozPosledni(T2);
            instance.nastavPrvni();
            instance.odeberZaAktualnim();
            TestClass[] expected = {T1};
            TestClass[] result = {instance.dejPosledni()};
            assertArrayEquals(expected, result);
        } catch (KolekceException e) {
            fail();
        }
    }

    @Test
    public void test_13_03_OdeberZaAktualnim() {
        try {
            Seznam<TestClass> instance = new SpojovySeznam<>();
            instance.vlozPrvni(T1);
            instance.vlozPosledni(T2);
            instance.vlozPosledni(T3);
            instance.nastavPrvni();
            instance.odeberZaAktualnim();
            TestClass[] expected = {T1, T3};
            TestClass[] result = {instance.dejPrvni(), instance.dejPosledni()};
            assertArrayEquals(expected, result);
        } catch (KolekceException e) {
            fail();
        }
    }

    @Test(expected = KolekceException.class)
    public void test_14_01_OdeberAktualni() throws KolekceException {
        Seznam<TestClass> instance = new SpojovySeznam<>();
        instance.odeberAktualni();
        fail();
    }

    @Test
    public void test_14_02_OdeberAktualni() {
        try {
            Seznam<TestClass> instance = new SpojovySeznam<>();
            instance.vlozPrvni(T1);
            instance.vlozPosledni(T2);
            instance.nastavPrvni();
            instance.odeberAktualni();
            TestClass[] expected = {T2};
            TestClass[] result = {instance.dejPrvni()};
            assertArrayEquals(expected, result);
        } catch (KolekceException e) {
            fail();
        }
    }

    @Test
    public void test_14_03_OdeberAktualni() {
        try {
            Seznam<TestClass> instance = new SpojovySeznam<>();
            instance.vlozPrvni(T1);
            instance.vlozPosledni(T2);
            instance.vlozPosledni(T3);
            instance.vlozPosledni(T4);
            instance.nastavPrvni();
            instance.dalsi();
            instance.dalsi();
            instance.odeberAktualni();
            instance.nastavPrvni();
            instance.dalsi();
            TestClass[] expected = {T1, T2, T4};
            TestClass[] result = {instance.dejPrvni(), instance.dejAktualni(), instance.dejPosledni()};
            assertArrayEquals(expected, result);
        } catch (KolekceException e) {
            fail();
        }
    }

    @Test
    public void test_14_04_OdeberAktualni() {
        try {
            Seznam<TestClass> instance = new SpojovySeznam<>();
            instance.vlozPrvni(T1);
            instance.vlozPosledni(T2);
            instance.nastavPrvni();
            instance.odeberAktualni();
            TestClass[] expected = {T2};
            TestClass[] result = {instance.dejPrvni()};
            assertArrayEquals(expected, result);
        } catch (KolekceException e) {
            fail();
        }
    }

    @Test
    public void test_14_05_OdeberAktualni() {
        try {
            Seznam<TestClass> instance = new SpojovySeznam<>();
            instance.vlozPrvni(T1);
            instance.vlozPosledni(T2);
            instance.nastavPosledni();
            instance.odeberAktualni();
            TestClass[] expected = {T1};
            TestClass[] result = {instance.dejPrvni()};
            assertArrayEquals(expected, result);
        } catch (KolekceException e) {
            fail();
        }
    }

    @Test
    public void test_15_01_Zrus() throws KolekceException {
        Seznam<TestClass> instance = new SpojovySeznam<>();
        instance.vlozPrvni(T1);
        instance.vlozPosledni(T2);
        instance.vlozPosledni(T3);
        instance.zrus();
        int expected = 0;
        int result = instance.size();
        assertEquals(expected, result);
    }

    @Test
    public void test_16_01_Itereator() {
        Seznam<TestClass> instance = new SpojovySeznam<>();
        instance.vlozPrvni(T1);
        instance.vlozPosledni(T2);
        instance.vlozPosledni(T3);
        Iterator<E> iterator = (Iterator<E>) instance.iterator(); //instacne.iterator();
        assertTrue(iterator.hasNext());
    }

    @Test
    public void test_16_02_Itereator() {
        Seznam<TestClass> instance = new SpojovySeznam<>();
        instance.vlozPrvni(T1);
        instance.vlozPosledni(T2);
        instance.vlozPosledni(T3);
        Iterator<E> iterator = (Iterator<E>) instance.iterator();
        iterator.next();
        TestClass expected = T2;
        TestClass actual = (TestClass) iterator.next();
        assertEquals(expected, actual);
    }

    @Test
    public void test_16_03_Itereator() {
        Seznam<TestClass> instance = new SpojovySeznam<>();
        instance.vlozPrvni(T1);
        Iterator<E> iterator = (Iterator<E>) instance.iterator();
        assertEquals(T1, iterator.next());
    }

    @Test(expected = NoSuchElementException.class)
    public void test_16_04_Itereator() {
        Seznam<TestClass> instance = new SpojovySeznam<>();
        Iterator<E> iterator = (Iterator<E>) instance.iterator();
        iterator.next();
        fail();
    }

    @Test
    public void test_16_05_Itereator() {
        Seznam<TestClass> instance = new SpojovySeznam<>();
        instance.vlozPosledni(T9);
        instance.vlozPosledni(T8);
        Iterator<E> iterator = (Iterator<E>) instance.iterator();
        assertEquals(T9, iterator.next());
    }

    @Test(expected = KolekceException.class)
    public void test_17_01_DejAktualni() throws KolekceException {
        Seznam<TestClass> instance = new SpojovySeznam<>();
        instance.dejAktualni();
        fail();
    }

    @Test(expected = KolekceException.class)
    public void test_17_02_DejAktualni() throws KolekceException {
        Seznam<TestClass> instance = new SpojovySeznam<>();
        instance.vlozPrvni(T9);
        instance.dejAktualni();
        fail();
    }

    @Test
    public void test_17_03_DejAktualni() throws KolekceException {
        Seznam<TestClass> instance = new SpojovySeznam<>();
        instance.vlozPosledni(T2);
        instance.vlozPosledni(T1);
        instance.vlozPosledni(T3);
        instance.nastavPrvni();
        instance.dalsi();
        instance.dalsi();
        assertEquals(T3, instance.dejAktualni());
    }

    @Test
    public void test_17_04_DejAktualni() throws KolekceException {
        Seznam<TestClass> instance = new SpojovySeznam<>();
        instance.vlozPosledni(T1);
        instance.vlozPosledni(T2);
        instance.vlozPosledni(T3);
        instance.vlozPosledni(T4);
        instance.vlozPosledni(T5);
        instance.vlozPosledni(T6);
        instance.nastavPrvni();
        instance.odeberAktualni();
        instance.nastavPrvni();
        assertEquals(T2, instance.dejAktualni());
    }
}
