package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CarControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CarService carService;

    @InjectMocks
    private CarController carController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(carController).build();
    }

    @Test
    void testCreateCarPage() throws Exception {
        mockMvc.perform(get("/car/createCar"))
                .andExpect(status().isOk())
                .andExpect(view().name("CreateCar"))
                .andExpect(model().attributeExists("car"));
    }

    @Test
    void testCreateCarPost() throws Exception {
        Car car = new Car();
        car.setCarId("car-001");
        car.setCarName("Toyota");
        car.setCarColor("Red");
        car.setCarQuantity(5);

        when(carService.create(any(Car.class))).thenReturn(car);

        mockMvc.perform(post("/car/createCar")
                        .param("carId", "car-001")
                        .param("carName", "Toyota")
                        .param("carColor", "Red")
                        .param("carQuantity", "5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("listCar"));

        verify(carService, times(1)).create(any(Car.class));
    }

    @Test
    void testCarListPage() throws Exception {
        Car car1 = new Car();
        car1.setCarId("car-001");
        car1.setCarName("Toyota");
        car1.setCarColor("Red");
        car1.setCarQuantity(5);

        Car car2 = new Car();
        car2.setCarId("car-002");
        car2.setCarName("Honda");
        car2.setCarColor("Blue");
        car2.setCarQuantity(3);

        List<Car> carList = new ArrayList<>();
        carList.add(car1);
        carList.add(car2);

        when(carService.findAll()).thenReturn(carList);

        mockMvc.perform(get("/car/listCar"))
                .andExpect(status().isOk())
                .andExpect(view().name("CarList"))
                .andExpect(model().attributeExists("cars"));

        verify(carService, times(1)).findAll();
    }

    @Test
    void testEditCarPage() throws Exception {
        Car car = new Car();
        car.setCarId("car-001");
        car.setCarName("Toyota");
        car.setCarColor("Red");
        car.setCarQuantity(5);

        when(carService.findById("car-001")).thenReturn(car);

        mockMvc.perform(get("/car/editCar/car-001"))
                .andExpect(status().isOk())
                .andExpect(view().name("EditCar"))
                .andExpect(model().attributeExists("car"));

        verify(carService, times(1)).findById("car-001");
    }

    @Test
    void testEditCarPost() throws Exception {
        Car car = new Car();
        car.setCarId("car-001");
        car.setCarName("Toyota Updated");
        car.setCarColor("Green");
        car.setCarQuantity(10);

        when(carService.edit(any(Car.class))).thenReturn(car);

        mockMvc.perform(post("/car/editCar")
                        .param("carId", "car-001")
                        .param("carName", "Toyota Updated")
                        .param("carColor", "Green")
                        .param("carQuantity", "10"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("listCar"));

        verify(carService, times(1)).edit(any(Car.class));
    }

    @Test
    void testDeleteCar() throws Exception {
        doNothing().when(carService).delete("car-001");

        mockMvc.perform(post("/car/deleteCar")
                        .param("carId", "car-001"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("listCar"));

        verify(carService, times(1)).delete("car-001");
    }
}