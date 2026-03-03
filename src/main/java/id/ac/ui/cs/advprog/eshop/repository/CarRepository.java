package id.ac.ui.cs.advprog.eshop.repository;
import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Repository
public class CarRepository implements BaseRepository<Car, String>{
    static int id = 0;

    private List<Car> carData = new ArrayList<>();

    @Override
    public Car create(Car car) {
        if (car.getCarId() == null) {
            UUID uuid = UUID.randomUUID();
            car.setCarId(uuid.toString());
        }
        carData.add(car);
        return car;
    }
    @Override
    public Iterator<Car> findAll() {
        return carData.iterator();
    }
    @Override
    public Car findById(String id) {
        for (Car car : carData) {
            if (car.getCarId().equals(id)) {
                return car;
            }
        }
        return null;
    }

    @Override
    public Car edit(Car updatedCar) {
        Car existingCar = findById(updatedCar.getCarId());

        if (existingCar != null) {
            existingCar.setCarName(updatedCar.getCarName());
            existingCar.setCarName(updatedCar.getCarColor());
            existingCar.setCarQuantity(updatedCar.getCarQuantity());
            return existingCar;
        }
        return null; // Handle the case where the car is not found
    }
    @Override
    public void delete(String id) {
        carData.removeIf(car -> car.getCarId().equals(id));
    }
}
