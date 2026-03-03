package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarServiceImpl carService;

    private Car car;

    @BeforeEach
    void setUp() {
        car = new Car();
        car.setCarId("car-001");
        car.setCarName("Toyota");
        car.setCarColor("Red");
        car.setCarQuantity(5);
    }

    @Test
    void testCreateCar() {
        when(carRepository.create(any(Car.class))).thenReturn(car);

        Car created = carService.create(car);

        assertNotNull(created);
        assertEquals("car-001", created.getCarId());
        assertEquals("Toyota", created.getCarName());
        verify(carRepository, times(1)).create(car);
    }

    @Test
    void testFindAll() {
        List<Car> carList = new ArrayList<>();
        carList.add(car);

        Car car2 = new Car();
        car2.setCarId("car-002");
        car2.setCarName("Honda");
        car2.setCarColor("Blue");
        car2.setCarQuantity(3);
        carList.add(car2);

        Iterator<Car> iterator = carList.iterator();
        when(carRepository.findAll()).thenReturn(iterator);

        List<Car> result = carService.findAll();

        assertEquals(2, result.size());
        assertEquals("Toyota", result.get(0).getCarName());
        assertEquals("Honda", result.get(1).getCarName());
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testFindAllEmpty() {
        List<Car> emptyList = new ArrayList<>();
        when(carRepository.findAll()).thenReturn(emptyList.iterator());

        List<Car> result = carService.findAll();

        assertTrue(result.isEmpty());
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(carRepository.findById("car-001")).thenReturn(car);

        Car found = carService.findById("car-001");

        assertNotNull(found);
        assertEquals("car-001", found.getCarId());
        assertEquals("Toyota", found.getCarName());
        verify(carRepository, times(1)).findById("car-001");
    }

    @Test
    void testFindByIdNotFound() {
        when(carRepository.findById("non-existent")).thenReturn(null);

        Car found = carService.findById("non-existent");

        assertNull(found);
        verify(carRepository, times(1)).findById("non-existent");
    }

    @Test
    void testUpdate() {
        Car updatedCar = new Car();
        updatedCar.setCarName("Toyota Updated");
        updatedCar.setCarColor("Green");
        updatedCar.setCarQuantity(10);

        when(carRepository.edit(any(Car.class))).thenReturn(updatedCar);

        Car result = carService.findById(updatedCar.getCarId());

        assertNotNull(result);
        assertEquals("Toyota Updated", result.getCarName());
        assertEquals("Green", result.getCarColor());
        verify(carRepository, times(1)).edit(any(Car.class));
    }

    @Test
    void testDeleteCarById() {
        doNothing().when(carRepository).delete("car-001");

        carService.delete("car-001");

        verify(carRepository, times(1)).delete("car-001");
    }
}