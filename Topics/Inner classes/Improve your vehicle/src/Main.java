class Vehicle {

    private String name;

    // create constructor
    Vehicle(String name) {
        this.name = name;
    }

    class Engine {

        // add field horsePower
        // create constructor
        int horsePower;

        Engine(int horsePower) {
            this.horsePower = horsePower;
        }

        void start() {

            System.out.println("RRRrrrrrrr....");
        }

        // create method printHorsePower()
        void printHorsePower() {
            System.out.println("Vehicle " + Vehicle.this.name + " has " + this.horsePower + " horsepower.");
        }
    }
}

// this code should work
class EnjoyVehicle {

    public static void main(String[] args) {

        Vehicle vehicle = new Vehicle("Dixi");
        Vehicle.Engine engine = vehicle.new Engine(20);
        engine.printHorsePower();
    }
}