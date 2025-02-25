import enums.RegionType;
import models.*;


public class Main {
    public static final String version = "1.1";

    public static World world = new World();

    public static void dataInitialize(){
        var russia = new Country("Россия");
        var moscow = new Region("Москва", RegionType.FEDERAL_CITY);
        moscow.cities.append(new City("Москва", 13000000));
        var spb = new Region("Санкт-Петербург", RegionType.FEDERAL_CITY);
        spb.cities.append(new City("Санкт-Петербург", 5600000));
        var tatarstan = new Region("Татарстан", RegionType.REPUBLIC);
        tatarstan.cities.append(new City("Казань", 1400000));
        tatarstan.cities.append(new City("Альметьевск", 170000));

        var saratov = new Region("Саратовская", RegionType.AREA);

        saratov.cities.append(
                        new City("Саратов", 830000),
                        new City("Балаково", 180000)
                );

        russia.regions.append(moscow,spb,tatarstan,saratov);

        russia.regions.append(
                new Region("Ямало-Ненецкий", RegionType.AUTONOMIC_DISTRICT)
        );

        var usa = new Country("Соединенные Штаты Америки (США)");
        var ny = new Region("Нью-йорк", RegionType.STATE);
        ny.cities.append(new City("Нью-Йорк", 8500000));
        var columbia = new Region("Колумбия", RegionType.DISTRICT);
        columbia.cities.append(new City("Вашингтон", 700000));
        var california = new Region("Калифорния", RegionType.STATE);
        california.cities.append(new City("Лос-Анджелес", 4000000));

        usa.regions.append(ny,columbia,california);
        world.countries.append(russia);
        world.countries.append(usa);

//        countries.add(usa);
    }

    public static void printCountries(){
        System.out.println(world.getString());
    }

    public static void main(String[] args) {
        System.out.println("Версия " + version );
        System.out.println("___________________________________________________________________________________________");
        dataInitialize();
        printCountries();




















    }
}
