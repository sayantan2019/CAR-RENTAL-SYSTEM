import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

class Car {
    private String carId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvl;
    
    public Car(String carId, String brand, String model, double basePricePerDay) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvl = true;
    }
    
    public String getCarId() {
        return carId;
    }
    
    public String getBrand() {
        return brand;
    }
    
    public String getModel() {
        return model;
    }
    
    public double calculatePrice(int rentalDays) {
        return basePricePerDay * rentalDays;
    }
    
    public boolean isAvl() {
        return isAvl;
    }
    
    public void rent() {
        isAvl = false;
    }
    
    public void returnCar() {
        isAvl = true;
    }
}

class Customer {
    private String customerId; 
    private String name;
    
    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }
    
    public String getCustomerId() {
        return customerId;
    }
    
    public String getName() {
        return name;
    }
}

class Rental {
    private Car car;
    private Customer customer;
    private int days;
    
    public Rental(Car car, Customer customer, int days) {
        this.car = car;
        this.customer = customer;
        this.days = days;
    }
    
    public Car getCar() {
        return car;
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public int getDays() {
        return days;
    }
}

class CarRentalSystem {
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;
    
    public CarRentalSystem() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }
    
    public void addCar(Car car) {
        cars.add(car);
    }
    
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }
    
    public void rentCar(Car car, Customer customer, int days) {
        if (car.isAvl()) {
            car.rent();
            rentals.add(new Rental(car, customer, days));
        } else {
            System.out.println("Car is not available for rent.");
        }
    }
    
    public void returnCar(Car car) {
        car.returnCar();
        Rental rentalToRemove = null;
        for (Rental rental : rentals) {
            if (rental.getCar() == car) {
                rentalToRemove = rental;
                break;
            }
        }
        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);
            System.out.println("Car return successful.");
        } else {
            System.out.println("Car was not rented.");
        }
    }
    
    public void menu() {
        Scanner sc = new Scanner(System.in);
        
        while (true) {
            System.out.println("Car Rental System");
            System.out.println("1. Rent a car");
            System.out.println("2. Return a car");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            
            int ch = sc.nextInt();
            sc.nextLine(); // Consume newline
            
            if (ch == 1) {
                System.out.println("\nRent a car\n");
                System.out.print("Enter your name: ");
                String customerName = sc.nextLine();
                
                System.out.println("\nAvailable Cars:");
                for (Car car : cars) {
                    if (car.isAvl()) {
                        System.out.println(car.getCarId() + " - " + car.getBrand() + " " + car.getModel());
                    }
                }
                
                System.out.print("Enter the car ID you want to rent: ");
                String carId = sc.nextLine();
                
                System.out.print("Enter the number of days for rental: ");
                int rentalDays = sc.nextInt();
                sc.nextLine(); // Consume newline
                
                Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName);
                addCustomer(newCustomer);
                
                Car selectedCar = null;
                for (Car car : cars) {
                    if (car.getCarId().equals(carId) && car.isAvl()) {
                        selectedCar = car;
                        break;
                    }
                }
                
                if (selectedCar != null) {
                    double totalPrice = selectedCar.calculatePrice(rentalDays);
                    System.out.println(".....Rental Information......");
                    System.out.printf("Customer ID: %s%n", newCustomer.getCustomerId());
                    System.out.printf("Customer Name: %s%n", newCustomer.getName());
                    System.out.printf("Car: %s %s%n", selectedCar.getBrand(), selectedCar.getModel());
                    System.out.printf("Rental Days: %d%n", rentalDays);
                    System.out.printf("Total Price: $%.2f%n", totalPrice);
                    
                    System.out.print("Confirm rental (Y/N): ");
                    String confirm = sc.nextLine();
                    
                    if (confirm.equalsIgnoreCase("Y")) {
                        rentCar(selectedCar, newCustomer, rentalDays);
                        System.out.println("Car rented successfully.");
                    } else {
                        System.out.println("Rental cancelled.");
                    }
                    
                } else {
                    System.out.println("Invalid car ID or car not available.");
                }
                
            } else if (ch == 2) {
                System.out.println("\nReturn a car\n");
                System.out.print("Enter the car ID you want to return: ");
                String carId = sc.nextLine();
                
                Car carToReturn = null;
                for (Car car : cars) {
                    if (car.getCarId().equals(carId) && !car.isAvl()) {
                        carToReturn = car;
                        break;
                    }
                }
                
                if (carToReturn != null) {
                    Customer customer = null;
                    for (Rental rental : rentals) {
                        if (rental.getCar() == carToReturn) {
                            customer = rental.getCustomer();
                            break;
                        }
                    }
                    if (customer != null) {
                        returnCar(carToReturn);
                        System.out.println("Car returned successfully by " + customer.getName());
                    } else {
                        System.out.println("Rental information is missing.");
                    }
                    
                } else {
                    System.out.println("Invalid car ID or car not rented.");
                }
                
            } else if (ch == 3) {
                break;
            } else {
                System.out.println("Invalid choice, please enter a valid choice.");
            }
        }
        
        System.out.println("Thanks for using Car Rental System.");
        sc.close();
    }
}

public class Main {
    public static void main(String[] args) {
        CarRentalSystem rSystem = new CarRentalSystem();
         
        Car car1 = new Car("C001", "TOYOTA", "CAMRY", 60.0);
        Car car2 = new Car("C002", "HONDA", "ACCORD", 70.0);
        Car car3 = new Car("C003", "MAHINDRA", "THAR", 150.0);
        
        rSystem.addCar(car1);
        rSystem.addCar(car2);
        rSystem.addCar(car3);
         
        rSystem.menu();
    }
}
