import common.Files;
import enums.RegionType;
import models.*;


public class Main {
    public static final String version = "1.2";
    public static final String countriesPath = "e:\\Projects_Java\\countries.txt";
    public static final String regionsPath = "e:\\Projects_Java\\regions.txt";
    public static final String citiesList = "e:\\Projects_Java\\cities.txt";

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
    }

    public static void printCountries(){
        System.out.println(world.getString());
    }

    public static void SaveAll(){
        var countiesSb = new StringBuilder();
        var regionsSb = new StringBuilder();
        var citiesSb = new StringBuilder();
        var countryIndex = 0;
        for (var country : world.countries.all()){
            var regionIndex = 0;
            countiesSb.append(String.format("%s|%s\n",countryIndex,country.convertToString()));
            for (var region: country.regions.all()) {
                var cityIndex = 0;
                regionsSb.append(String.format("%s|%s|%s\n",regionIndex,countryIndex,region.convertToString()));
                for (var city: region.cities.all()){
                   citiesSb.append(String.format("%s|%s|%s|%s\n",cityIndex,regionIndex,countryIndex,city.convertToString()));
                   cityIndex++;
                 }
                regionIndex++;
            }
            countryIndex++;
            Files.WriteToFile("e:\\Projects_Java\\countries.txt", countiesSb);
            Files.WriteToFile("e:\\Projects_Java\\regions.txt", regionsSb);
            Files.WriteToFile("e:\\Projects_Java\\cities.txt", citiesSb);

        }
    }

    public static void ReadAll(){
            var countriesList = Files.ReadFromFile("e:\\Projects_Java\\countries.txt");
            var regionsList = Files.ReadFromFile("e:\\Projects_Java\\regions.txt");
            var citiesList = Files.ReadFromFile("e:\\Projects_Java\\cities.txt");

            for (var countryRow : countriesList){
                var countryColumns = countryRow.split("\\|");
                var countryIndex = Integer.parseInt(countryColumns[0]);
                var countryName = countryColumns[1];
                var country = new Country(countryName);
                world.countries.addByIndex(countryIndex, country);
            }

            for (var regionRow : regionsList){
                var regionColumns = regionRow.split("\\|");
                var regionIndex = Integer.parseInt(regionColumns[0]);
                var countryIndex = Integer.parseInt(regionColumns[1]);
                var regionName = regionColumns[2];
                var regionType =  RegionType.valueOf(regionColumns[3]);
                var country = world.countries.getByIndex(countryIndex);
                if (country == null) {
                    continue;
                }
                var region = new Region(regionName,regionType);
                country.regions.addByIndex(regionIndex,region);
            }

            for (var cityRow : citiesList){
                var cityColumns = cityRow.split("\\|");
                var cityIndex =  Integer.parseInt(cityColumns[0]);
                var regionIndex = Integer.parseInt(cityColumns[1]);
                var countryIndex = Integer.parseInt(cityColumns[2]);
                var cityName = cityColumns[3];
                var population = 0;
                try
                {
                    population =  Integer.parseInt(cityColumns[4]);
                } catch (NumberFormatException e)  {
                    population = -1;
                }

                var country = world.countries.getByIndex(countryIndex);
                if (country ==null){
                    continue;
                }

                var region = country.regions.getByIndex(regionIndex);

                if (region == null){
                    continue;
                }
                var city = new City(cityName, population);
                region.cities.addByIndex(cityIndex, city);
            }
    }


    public static void main(String[] args) {
        System.out.println("Версия " + version );
        System.out.println("___________________________________________________________________________________________");
        //dataInitialize();
        //SaveAll();
        ReadAll();
        printCountries();
    }
}
