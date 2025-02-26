import common.Files;
import common.Helper;
import enums.RegionType;
import models.*;

import java.io.File;
import java.util.Scanner;
import java.util.UUID;


public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static final String version = "1.4";
    public static final String countriesFile = getCurrentDir() + "\\countries.txt";
    public static final String regionsFile = getCurrentDir() + "\\regions.txt";
    public static final String citiesFile = getCurrentDir() + "\\cities.txt";
    public static World world = new World();

    Country countryPointer = null;

    Region regionPointer = null;

    City cityPointer = null;

    public static void initLocalStorage(){
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
        System.out.println(world.getInfo());
    }

    public static void saveAllToFileStorage(){
        var countiesSb = new StringBuilder();
        var regionsSb = new StringBuilder();
        var citiesSb = new StringBuilder();
        for (var country : world.countries.all()){
            countiesSb.append(Helper.unionStrings(country.packedStr())).append("\n");
            for (var region: country.regions.all()) {
                regionsSb.append(Helper.unionStrings(country.uuid.toString(),region.packedStr())).append("\n");
                for (var city: region.cities.all()){
                   citiesSb.append(Helper.unionStrings(region.uuid.toString(),city.packedStr())).append("\n");
                 }
            }
            Files.WriteToFile(countriesFile, countiesSb);
            Files.WriteToFile(regionsFile, regionsSb);
            Files.WriteToFile(citiesFile, citiesSb);
        }
        System.out.println("Запись данных выполнена.");
    }

    public static void readAllFromFileStorage(){
            var countriesList = Files.ReadFromFile(countriesFile);
            var regionsList = Files.ReadFromFile(regionsFile);
            var citiesList = Files.ReadFromFile(citiesFile);

            for (var countryRow : countriesList){
                var countryColumns = countryRow.split("\\|");
                var countryUUID = UUID.fromString(countryColumns[0]);
                var countryName = countryColumns[1];
                var country = new Country(countryUUID,countryName);
                world.countries.append(country);
            }

            for (var regionRow : regionsList){
                var regionColumns = regionRow.split("\\|");
                var countryUUID = UUID.fromString(regionColumns[0]);
                var regionUUID = UUID.fromString(regionColumns[1]);
                var regionName = regionColumns[2];
                var regionType =  RegionType.valueOf(regionColumns[3]);
                var country = world.countries.get(countryUUID);
                if (country == null) {
                    continue;
                }
                var region = new Region(regionUUID, regionName,regionType);
                country.regions.append(region);
            }

            for (var cityRow : citiesList){
                var cityColumns = cityRow.split("\\|");
                var regionUUID = UUID.fromString(cityColumns[0]);
                var cityUUID =  UUID.fromString(cityColumns[1]);
                var cityName = cityColumns[2];
                var population = 0;
                try
                {
                    population =  Integer.parseInt(cityColumns[3]);
                } catch (NumberFormatException e)  {
                    population = -1;
                }

                Region foundRegion = null;
                for (var country: world.countries.all()){
                    var region = country.regions.get(regionUUID);

                    if (region !=null){
                        foundRegion = region;
                        break;
                    }
                }

                if (foundRegion == null){
                    continue;
                }
                var city = new City(cityUUID,cityName, population);
                foundRegion.cities.append(city);
            }
    }

    public static void main(String[] args) {
        System.out.println("Версия " + version );
        System.out.println("Текущий каталог: " + getCurrentDir());
        System.out.println("___________________________________________________________________________________________");
        //initLocalStorage();
        //saveAllToFileStorage();
        readAllFromFileStorage();
        //printCountries();
        runWorkWithWorld();
    }

    public static String getCurrentDir(){
        String classPath = Main.class.getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .getPath();
        File classDir = new File(classPath).getParentFile();

        return classDir.getAbsolutePath();
    }


    public static void runWorkWithWorld(){
        while (true) {

            try {
                printMenu();
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        addCountry();
                        break;
                    case 2:
                        editCountry();
                        break;
                    case 3:
                        removeCountry();
                        break;
                    case 4:
                        searchCountry();
                        break;
                    case 5:
                        displayCountry();
                        break;
                    case 6:
                        displayFullInfo();
                        break;
                    case 7:
                        saveAllToFileStorage();
                        break;
                    case 8:
                        System.out.println("Выход из программы.");
                        return;
                    default:
                        System.out.println("Неверный выбор. Попробуйте снова.");
                }

            }catch (Exception e){
                System.out.println("Неверный выбор. Попробуйте снова.");
            }finally {
                scanner.nextLine();
            }

        }

    }

    // Вывод меню
    private static void printMenu() {
        System.out.println("\n--- Меню ---");
        System.out.println("1. Добавить страну");
        System.out.println("2. Редактировать страну");
        System.out.println("3. Удалить страну");
        System.out.println("4. Найти");
        System.out.println("5. Показать список стран");
        System.out.println("6. Показать полную информацию");
        System.out.println("7. Выполнить запись данных в файловое хранилище");
        System.out.println("8. Выход");
        System.out.print("Выберите действие: ");
    }

    // Добавление элемента
    private static void addCountry() {
        System.out.print("Введите наименование страны: ");
        String item = scanner.nextLine();
        var newUuid = UUID.randomUUID();
        world.countries.append(new Country(newUuid,item));
        if (world.countries.get(newUuid) != null){
            System.out.println("Страна добавлена: " + item);
        }else {
            System.out.println("Страна не добавлена");
        }

    }

    //Редактирование элемента
    private static void editCountry(){
        if (world.countries.ifEmpty()){
            System.out.println("Список стран пуст");
            return;
        }
        System.out.println("Страны:");
        System.out.println(world.countries.pointer());

        System.out.print("Введите номер для редактирования: ");
        String item = scanner.nextLine();

        try{
            var index = Integer.valueOf(item);
            index--;
            var country = world.countries.get(index);
            if (country != null){
                var name = country.name;
                System.out.print("Выбрана страна: " + name + ", внесите корректировку в название: ");
                var newName = scanner.nextLine();
                country.name = newName;
                System.out.println("Новое название: " + newName);
            }else{
                System.out.println("Не удалось определить страну");
            }

        } catch (Exception e){
            System.out.println("Ошибка редактирования: " + e.getMessage());
        }
    }

    // Удаление элемента
    private static void removeCountry() {
        if (world.countries.ifEmpty()){
            System.out.println("Список стран пуст");
            return;
        }
        System.out.println("Страны:");
        System.out.println(world.countries.pointer());

        System.out.print("Введите номер для удаления: ");
        String item = scanner.nextLine();

        try{
            var index = Integer.valueOf(item);
            index--;
            var country = world.countries.get(index);
            if (country != null){
                var id = country.uuid;
                var name = country.name;
                world.countries.remove(country);

                if (world.countries.get(id) == null){
                    System.out.println("Страна удалена: " + name);
                }
            }else{
                System.out.println("Не удалось определить страну");
            }

        } catch (Exception e){
            System.out.println("Ошибка удаления: " + e.getMessage());
        }
    }

    // Поиск элемента
    private static void searchCountry() {
        System.out.print("Введите элемент для поиска: ");
        String item = scanner.nextLine();
        var founded = world.countries.find(item);
        System.out.println("Результат поиска:");
        if (founded.all().isEmpty()){
            System.out.println("Ничего не найдено");
        }else {
                System.out.println(founded.pointer());
        }
    }

    private static void displayCountry(){
        System.out.println(world.countries.pointer());
    }

    // Отображение списка
    private static void displayFullInfo() {
        System.out.println(world.getInfo());
    }
}


