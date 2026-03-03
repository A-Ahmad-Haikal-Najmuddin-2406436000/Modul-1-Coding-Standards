package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {

    private Car car;

    @BeforeEach
    void setUp() {
        car = new Car();
        car.setCarId("car-001");
        car.setCarName("Toyota Avanza");
        car.setCarColor("Silver");
        car.setCarQuantity(10);
    }

    @Test
    void testGetCarId() {
        assertEquals("car-001", car.getCarId());
    }

    @Test
    void testGetCarName() {
        assertEquals("Toyota Avanza", car.getCarName());
    }

    @Test
    void testGetCarColor() {
        assertEquals("Silver", car.getCarColor());
    }

    @Test
    void testGetCarQuantity() {
        assertEquals(10, car.getCarQuantity());
    }

    @Test
    void testSetCarId() {
        car.setCarId("car-002");
        assertEquals("car-002", car.getCarId());
    }

    @Test
    void testSetCarName() {
        car.setCarName("Honda Jazz");
        assertEquals("Honda Jazz", car.getCarName());
    }

    @Test
    void testSetCarColor() {
        car.setCarColor("Black");
        assertEquals("Black", car.getCarColor());
    }

    @Test
    void testSetCarQuantity() {
        car.setCarQuantity(20);
        assertEquals(20, car.getCarQuantity());
    }
}