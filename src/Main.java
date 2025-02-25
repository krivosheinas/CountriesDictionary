import enums.RegionType;
import models.*;

import java.util.ArrayList;
import java.util.Arrays;


public class Main {
    public static World world = new World();

    public static void dataInitialize(){
        var russia = new Country("Россия");
        var moscow = new Region("Москва", RegionType.FEDERAL_CITY);
        moscow.addSubject(new City("Москва", 13000000));
        var spb = new Region("Санкт-Петербург", RegionType.FEDERAL_CITY);
        spb.addSubject(new City("Санкт-Петербург", 5600000));
        var tatarstan = new Region("Татарстан", RegionType.REPUBLIC);
        tatarstan.addSubject(new City("Казань", 1400000));
        tatarstan.addSubject(new City("Альметьевск", 170000));

        var saratov = new Region("Саратовская", RegionType.AREA);

        saratov.addSubjects(
                new ArrayList<>(Arrays.asList(
                        new City("Саратов", 830000),
                        new City("Балаково", 180000)
                ))
        );

        russia.addSubjects(new ArrayList<>(Arrays.asList(moscow,spb,tatarstan,saratov)));

        russia.addSubject(
                new Region("Ямало-Ненецкий", RegionType.AUTONOMIC_DISTRICT)
        );

        var usa = new Country("Соединенные Штаты Америки (США)");
        var ny = new Region("Нью-йорк", RegionType.STATE);
        ny.addSubject(new City("Нью-Йорк", 8500000));
        var columbia = new Region("Колумбия", RegionType.DISTRICT);
        columbia.addSubject(new City("Вашингтон", 700000));
        var california = new Region("Калифорния", RegionType.STATE);
        california.addSubject(new City("Лос-Анджелес", 4000000));

        usa.addSubjects(new ArrayList<>(Arrays.asList(ny,columbia,california)));
        world.addSubject(russia);
        world.addSubject(usa);

          russia.sortDesc();

          for (var region : russia.getSubjects()){
              region.sortDesc();
          }

//        countries.add(usa);
    }

    public static void printCountries(){
        System.out.println(world.getString());
    }

    public static void main(String[] args) {
        dataInitialize();
        printCountries();




















    }
}
