package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class CarRepositoryTest {

    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        carRepository = new CarRepository();
    }

    @Test
    void testCreateAndFind() {
        Car car = new Car();
        car.setCarName("Toyota");
        car.setCarColor("Red");
        car.setCarQuantity(5);

        Car created = carRepository.create(car);

        assertNotNull(created.getCarId());
        assertEquals("Toyota", created.getCarName());
        assertEquals("Red", created.getCarColor());
        assertEquals(5, created.getCarQuantity());
    }

    @Test
    void testCreateWithExistingId() {
        Car car = new Car();
        car.setCarId("existing-id");
        car.setCarName("Honda");
        car.setCarColor("Blue");
        car.setCarQuantity(3);

        Car created = carRepository.create(car);

        assertEquals("existing-id", created.getCarId());
    }

    @Test
    void testFindAllEmpty() {
        Iterator<Car> iterator = carRepository.findAll();
        assertFalse(iterator.hasNext());
    }

    @Test
    void testFindAllWithMultipleCars() {
        Car car1 = new Car();
        car1.setCarName("Toyota");
        car1.setCarColor("Red");
        car1.setCarQuantity(5);
        carRepository.create(car1);

        Car car2 = new Car();
        car2.setCarName("Honda");
        car2.setCarColor("Blue");
        car2.setCarQuantity(3);
        carRepository.create(car2);

        Iterator<Car> iterator = carRepository.findAll();
        assertTrue(iterator.hasNext());
        iterator.next();
        assertTrue(iterator.hasNext());
        iterator.next();
        assertFalse(iterator.hasNext());
    }

    @Test
    void testFindByIdFound() {
        Car car = new Car();
        car.setCarName("Toyota");
        car.setCarColor("Red");
        car.setCarQuantity(5);
        Car created = carRepository.create(car);

        Car found = carRepository.findById(created.getCarId());

        assertNotNull(found);
        assertEquals(created.getCarId(), found.getCarId());
        assertEquals("Toyota", found.getCarName());
    }

    @Test
    void testFindByIdNotFound() {
        Car found = carRepository.findById("non-existent-id");
        assertNull(found);
    }

    @Test
    void testUpdate() {
        Car car = new Car();
        car.setCarName("Toyota");
        car.setCarColor("Red");
        car.setCarQuantity(5);
        Car created = carRepository.create(car);

        Car updatedCar = new Car();
        updatedCar.setCarName("Toyota Updated");
        updatedCar.setCarColor("Green");
        updatedCar.setCarQuantity(10);

        Car result = carRepository.edit(updatedCar);

        assertNotNull(result);
        assertEquals("Toyota Updated", result.getCarName());
        assertEquals("Green", result.getCarColor());
        assertEquals(10, result.getCarQuantity());
    }

    @Test
    void testUpdateNotFound() {
        Car updatedCar = new Car();
        updatedCar.setCarName("Ghost Car");
        updatedCar.setCarColor("Invisible");
        updatedCar.setCarQuantity(0);

        Car result = carRepository.edit(updatedCar);
        assertNull(result);
    }

    @Test
    void testDelete() {
        Car car = new Car();
        car.setCarName("Toyota");
        car.setCarColor("Red");
        car.setCarQuantity(5);
        Car created = carRepository.create(car);

        carRepository.delete(created.getCarId());

        Car found = carRepository.findById(created.getCarId());
        assertNull(found);
    }

    @Test
    void testDeleteNonExistent() {
        Car car = new Car();
        car.setCarName("Toyota");
        car.setCarColor("Red");
        car.setCarQuantity(5);
        carRepository.create(car);

        carRepository.delete("non-existent-id");

        Iterator<Car> iterator = carRepository.findAll();
        assertTrue(iterator.hasNext());
    }
}