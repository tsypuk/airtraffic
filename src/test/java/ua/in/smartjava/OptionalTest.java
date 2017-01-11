package ua.in.smartjava;

import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class OptionalTest {

    @Test
    public void testWagen() {
        Car wagon = new Car(null, "empty");
        Assert.assertEquals(0, getPorshenDiameter(wagon));
    }

    @Test
    public void testCarEngine() {
        Car mercedes = new Car(new Engine(null), "Mercedes");
        Assert.assertEquals(0, getPorshenDiameter(mercedes));
    }

    @Test
    public void testCarEnginePorshen() {
        Car porsche = new Car(new Engine(new Porshen(22)), "Porshe");
        Assert.assertEquals(22, getPorshenDiameter(porsche));
    }

    private int getPorshenDiameter(Car car) {
        return Optional.ofNullable(car)
                .flatMap(Car::getEngine)
                .flatMap(Engine::getPorshen)
                .map(Porshen::getDiameter)
                .orElse(0);
    }

    @Test
    public void testBmw() {
        Car bmw = new Car(new Engine(new Porshen(32)), "BMW");
        Assert.assertEquals(32, getPorshenDiameterOfBmwOnly(bmw));
    }

    @Test
    public void testNotBmw() {
        Car fiat = new Car(new Engine(new Porshen(16)), "FIAT");
        Assert.assertEquals(0, getPorshenDiameterOfBmwOnly(fiat));
    }

    private int getPorshenDiameterOfBmwOnly(Car car) {
        return Optional.ofNullable(car)
                .filter(vehicle -> "BMW".equals(vehicle.getName()))
                .flatMap(Car::getEngine)
                .flatMap(Engine::getPorshen)
                .map(Porshen::getDiameter)
                .orElse(0);
    }

    class Car {
        Engine engine;
        String name;

        public Car(Engine engine, String name) {
            this.engine = engine;
            this.name = name;
        }

        public Optional<Engine> getEngine() {
            return Optional.ofNullable(engine);
        }

        public String getName() {
            return name;
        }
    }

    class Porshen {
        int diameter;

        public Porshen(int diameter) {
            this.diameter = diameter;
        }

        public int getDiameter() {
            return diameter;
        }
    }

    class Engine {
        Porshen porshen;

        public Engine(Porshen porshen) {
            this.porshen = porshen;
        }

        public Optional<Porshen> getPorshen() {
            return Optional.ofNullable(porshen);
        }

    }
}