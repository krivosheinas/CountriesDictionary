import common.Files;
import common.Helper;
import enums.SourceType;
import extensions.SourceList;
import extensions.SourceName;
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

    public static Country countryPointer = null;

    public static Region regionPointer = null;

    public static City cityPointer = null;




    public static void initLocalStorage(){
        var russia = new Country("Россия");
        var moscow = new Region("Москва");
        moscow.cities.append(new City("Москва", 13000000));
        var spb = new Region("Санкт-Петербург");
        spb.cities.append(new City("Санкт-Петербург", 5600000));
        var tatarstan = new Region("Татарстан");
        tatarstan.cities.append(new City("Казань", 1400000));
        tatarstan.cities.append(new City("Альметьевск", 170000));
        var saratov = new Region("Саратовская");
        saratov.cities.append(
                        new City("Саратов", 830000),
                        new City("Балаково", 180000)
                );
        russia.regions.append(moscow,spb,tatarstan,saratov);
        russia.regions.append(
                new Region("Ямало-Ненецкий")
        );
        var usa = new Country("Соединенные Штаты Америки (США)");
        var ny = new Region("Нью-йорк");
        ny.cities.append(new City("Нью-Йорк", 8500000));
        var columbia = new Region("Колумбия");
        columbia.cities.append(new City("Вашингтон", 700000));
        var california = new Region("Калифорния");
        california.cities.append(new City("Лос-Анджелес", 4000000));
        usa.regions.append(ny,columbia,california);
        world.countries.append(russia);
        world.countries.append(usa);
    }

    public static void printCountries(){
        System.out.println(world.getInfo());
    }

    /**
     * Сохранение информации о внесенных странах, регионах и городах из памяти в файловое хранилище
     */
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

    /**
     * Загрузка в память из файлового хранилища данных о странах, регионах и городах
     */
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
                var country = world.countries.get(countryUUID);
                if (country == null) {
                    continue;
                }
                var region = new Region(regionUUID, regionName);
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

    /**
     * Главный метод программы
     * @param args
     */
    public static void main(String[] args) {

//        Charset charset = Charset.defaultCharset();
//        System.out.println("Текущая кодировка: " + charset.displayName());
//        System.out.println("Версия " + version );
//        System.out.println("Текущий каталог: " + getCurrentDir());
//        System.out.println("___________________________________________________________________________________________");
//        initLocalStorage();
//        saveAllToFileStorage();
          readAllFromFileStorage();
//        //printCountries();
          showCountryLevel();
    }

    /**
     * Функция получения текущей директории исполняемого файла
     * @return
     */
    public static String getCurrentDir(){
        String classPath = Main.class.getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .getPath();
        File classDir = new File(classPath).getParentFile();

        return classDir.getAbsolutePath();
    }

    /**
     * Вывод меню уровня Страны
     */
    private static void printCountryMenu() {
        System.out.println("\n--- Страны. Меню ---");
        System.out.println("1. Добавить страну");
        System.out.println("2. Редактировать страну");
        System.out.println("3. Удалить страну");
        System.out.println("4. Найти");
        System.out.println("5. Показать список стран");
        System.out.println("6. Показать полную информацию по странам и регионам");
        System.out.println("7. Выполнить запись данных в файловое хранилище");
        System.out.println("8. Выход");
        System.out.print("Выберите действие: ");
    }

    /**
     * Вывести меню действий для уровня Страны
     */
    public static void showCountryLevel(){
        while (true) {
            try {
                printCountryMenu();
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
                        displayPreview(SourceType.Country);
                        break;
                    case 6:
                        displayInfo(SourceType.Country);
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
                scanner.nextLine();
            }
        }
    }

    /**
     * Вывод меню уровня Регионы
     */
    private static void printRegionMenu() {
        System.out.println("\n--- Страна " + countryPointer.name + ". Редактирование ---");
        System.out.println("1. Изменить название страны");
        System.out.println("2. Добавить регион");
        System.out.println("3. Редактировать регион");
        System.out.println("4. Удалить регион");
        System.out.println("5. Вывести список регионов страны");
        System.out.println("6. Вывести полную информацию по регионам страны");
        System.out.println("7. Переход на уровень Страны");
        System.out.print("Выберите действие: ");
    }

    /**
     * Выводит меню действий для уровня Регионы
     */
    public static void showRegionLevel(){
        while (true) {
            try {
                printRegionMenu();
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        changeCountry();
                        break;
                    case 2:
                        addRegion();
                        break;
                    case 3:
                        changeRegion();
                         break;
                    case 4:
                        removeRegion();
                        break;
                    case 5:
                        displayPreview(SourceType.Region);
                        break;
                    case 6:
                        displayInfo(SourceType.Region);
                        break;
                    case 7:
                        System.out.println("Переход на уровень Страны.");
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

    /**
     * Вывод меню уровня Города
     */
    private static void printCityMenu() {
        System.out.println("\n--- Редактирование ---");
        System.out.println("1. Изменить название");
        System.out.println("2. Добавить город");
        System.out.println("3. Редактировать город");
        System.out.println("4. Удалить регион");
        System.out.println("5. Вывести список городов");
        System.out.println("6. Вывести полную информацию по городам региона");
        System.out.println("7. Отмена");
        System.out.print("Выберите действие: ");
    }

    /**
     * Выводит меню действий для уровня Города
     */
    public static void showCityLevel(){
        while (true) {
            try {
                printRegionMenu();
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
//                    case 1:
//                        addCountry();
//                        break;
//                    case 2:
//                        editCountry();
//                        break;
//                    case 3:
//                        removeCountry();
//                        break;
//                    case 4:
//                        searchCountry();
//                        break;
//                    case 5:
//                        displayCountry();
//                        break;
//                    case 6:
//                        displayFullInfo();
//                        break;
//                    case 7:
//                        saveAllToFileStorage();
//                        break;
                    case 8:
                        System.out.println("Выход из программы.");
                        return;
                    default:
                        System.out.println("Неверный выбор. Попробуйте снова.");
                }

            }catch (Exception e){
                System.out.println("Неверный выбор. Попробуйте снова.");
                scanner.nextLine();
            }
        }
    }

    /**
     * Добавление новой Страны
     */
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


    /**
     * Добавление нового Региона
     */
    private static void addRegion() {
        if (countryPointer == null){
            System.out.println("Страна не выбрана");
        }

        System.out.print("Введите наименование региона: ");
        String item = scanner.nextLine();
        var newUuid = UUID.randomUUID();
        countryPointer.regions.append(new Region(newUuid,item));
        if (countryPointer.regions.get(newUuid) != null){
            System.out.println("Регион добавлен: " + item);
        }else {
            System.out.println("Регион не добавлен");
        }
    }

    private static void editRegion(){

    }

    private static void removeRegion(){

        if (countryPointer == null){
            System.out.println("Страна не выбрана");
        }

        previewList(countryPointer.regions, "Страны");
        System.out.print("Введите номер для удаления: ");
        String item = scanner.nextLine();

        try{
            var index = Integer.valueOf(item);
            index--;
            var region = countryPointer.regions.get(index);
            if (region != null){
                var id = region.uuid;
                var name = region.name;
                countryPointer.regions.remove(region);

                if (countryPointer.regions.get(id) == null){
                    System.out.println("Регион удален: " + name);
                }
            }else{
                System.out.println("Не удалось определить регион");
            }

        } catch (Exception e){
            System.out.println("Ошибка удаления: " + e.getMessage());
        }
    }

    private static void changeRegion(){

    }

    private static <T extends SourceName> void previewList (SourceList<T> sourceList, String sourceName){
        if (sourceList.ifEmpty()){
            System.out.println("Список пуст");
            return;
        }
        System.out.println(sourceName + ":");
        System.out.println(sourceList.pointer());
    }


    private static void changeCountry(){
        System.out.print("Введите изменение в название: ");
        String item = scanner.nextLine();
        countryPointer.name = item;
        System.out.println("Новое название страны: " + item);
    }


    //Редактирование элемента
    private static void editCountry(){
        previewList(world.countries, "Страны");
        System.out.print("Введите номер для редактирования: ");
        String item = scanner.nextLine();

        try{
            var index = Integer.valueOf(item);
            index--;
            var country = world.countries.get(index);
            if (country != null){

                countryPointer = country;
                System.out.print("Выбрана страна: " + country.name);
                showRegionLevel();
            }else{
                System.out.println("Не удалось определить страну");
            }
        } catch (Exception e){
            System.out.println("Ошибка редактирования: " + e.getMessage());
        }
    }

    // Удаление элемента
    private static void removeCountry() {
        previewList(world.countries, "Страны");
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

    private static void displayPreview(SourceType sourceType){
        switch (sourceType){
            case Country:
                previewList(world.countries, "Страны");
                break;
            case Region:
                if (countryPointer == null){
                    break;
                }
                previewList(countryPointer.regions, "Регионы");
                break;
            case City:
                if (regionPointer == null){
                    break;
                }
                previewList(regionPointer.cities, "Города");
                break;
        }
    }

    // Отображение списка
    private static void displayInfo(SourceType sourceType) {
        switch (sourceType){
            case Country:
                System.out.println(world.getInfo());
                break;
            case Region:
                if (countryPointer == null){
                    break;
                }
                System.out.println(countryPointer.getInfo());
                return;
            case City:
                if (regionPointer == null){
                    break;
                }
                System.out.println(regionPointer.getInfo());
                break;
        }
    }
}


