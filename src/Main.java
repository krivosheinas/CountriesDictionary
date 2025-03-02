import common.Files;
import common.Helper;
import enums.SourceType;
import extensions.SourceList;
import extensions.SourceName;
import models.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    public static final String countriesFile = getCurrentDir() + "\\countries.txt";
    public static final String regionsFile = getCurrentDir() + "\\regions.txt";
    public static final String citiesFile = getCurrentDir() + "\\cities.txt";
    public static World world = new World();
    public static Country countryPointer = null;
    public static Region regionPointer = null;
    public static boolean isNeedToLoadMainMenu = false;


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
     * @param args Аргументы программы
     */
    public static void main(String[] args) {
          readAllFromFileStorage();
          countryActions();
    }

    /**
     * Функция получения текущей директории исполняемого файла
     * @return Полный путь до текущего каталога
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
        System.out.println("\n***** Страны. Главное меню *****");
        System.out.println("1. Добавить страну");
        System.out.println("2. Редактировать страну");
        System.out.println("3. Удалить страну");
        System.out.println("4. Поиск");
        System.out.println("5. Показать список стран");
        System.out.println("6. Показать детализированную информацию по странам и регионам");
        System.out.println("7. Выполнить запись данных в файловое хранилище");
        System.out.println("8. Выход");
        System.out.print("Выберите действие: ");
    }

    /**
     * Вывести меню действий для уровня Страны
     */
    public static void countryActions(){
        while (true) {
            isNeedToLoadMainMenu = false;
            countryPointer = null;
            regionPointer = null;
            try {
                printCountryMenu();
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1 -> addCountry();
                    case 2 -> editCountry();
                    case 3 -> removeCountry();
                    case 4 -> deepSearch();
                    case 5 -> displayPreview(SourceType.Country);
                    case 6 -> displayInfo(SourceType.Country);
                    case 7 -> saveAllToFileStorage();
                    case 8 -> {
                        System.out.println("Выход из программы.");
                        return;
                    }
                    default -> System.out.println("Неверный выбор. Попробуйте снова.");
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
        System.out.println("\n***** Режим редактирования страны *****");
        System.out.println("Текущая страна: " + countryPointer.name);
        System.out.println("1. Изменить название страны");
        System.out.println("2. Добавить регион");
        System.out.println("3. Редактировать регион");
        System.out.println("4. Удалить регион");
        System.out.println("5. Вывести список регионов текущей страны");
        System.out.println("6. Вывести детализированную информацию по регионам текущей страны");
        System.out.println("7. Переход в Главное меню");
        System.out.print("Выберите действие: ");
    }

    /**
     * Выводит меню действий для уровня Регионы
     */
    public static void regionActions(){
        while (true) {
            regionPointer = null;
            try {
                printRegionMenu();
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> changeCountry();
                    case 2 -> addRegion();
                    case 3 -> editRegion();
                    case 4 -> removeRegion();
                    case 5 -> displayPreview(SourceType.Region);
                    case 6 -> displayInfo(SourceType.Region);
                    case 7 -> {
                        System.out.println("Переход в Главное меню");
                        return;
                    }
                    default -> System.out.println("Неверный выбор. Попробуйте снова.");
                }

            }catch (Exception e){
                System.out.println("Неверный выбор. Попробуйте снова.");
                scanner.nextLine();
            }

            if (isNeedToLoadMainMenu){
                return;
            }
        }
    }

    /**
     * Вывод меню уровня Города
     */
    private static void printCityMenu() {
        System.out.println("\n***** Режим редактирования региона *****");
        System.out.println("Текущий регион: " + regionPointer.name);
        System.out.println("1. Изменить название региона");
        System.out.println("2. Добавить город");
        System.out.println("3. Редактировать город");
        System.out.println("4. Удалить город");
        System.out.println("5. Вывести список городов текущего региона");
        System.out.println("6. Вывести детализированную информацию по городам текущего региона");
        System.out.println("7. Переход на предыдущий уровень");
        System.out.println("8. Переход в Главное меню");
        System.out.print("Выберите действие: ");
    }

    /**
     * Выводит меню действий для уровня Города
     */
    public static void cityActions(){
        while (true) {
            try {
                printCityMenu();
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1 -> changeRegion();
                    case 2 -> addCity();
                    case 3 -> editCity();
                    case 4 -> removeCity();
                    case 5 -> displayPreview(SourceType.City);
                    case 6 -> displayInfo(SourceType.City);
                    case 7 -> {
                        System.out.println("Переход на предыдущий уровень.");
                        return;
                    }
                    case 8 -> {
                        System.out.println("Переход в Главное меню");
                        isNeedToLoadMainMenu = true;
                        return;
                    }
                    default -> System.out.println("Неверный выбор. Попробуйте снова.");
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
        System.out.print("Введите название страны: ");
        String name = scanner.nextLine();
        var uuid = UUID.randomUUID();
        world.countries.append(new Country(uuid,name));
        if (world.countries.get(uuid) != null){
            System.out.println("Страна добавлена: " + name);
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
        System.out.print("Введите название региона: ");
        String name = scanner.nextLine();
        var uuid = UUID.randomUUID();
        countryPointer.regions.append(new Region(uuid,name));
        if (countryPointer.regions.get(uuid) != null){
            System.out.println("Регион добавлен: " + name);
        }else {
            System.out.println("Регион не добавлен");
        }
    }

    /**
     * Добавление нового Города
     */
    private static void addCity() {
        if (regionPointer == null){
            System.out.println("Регион не выбран");
        }
        System.out.print("Введите название города: ");
        String name = scanner.nextLine();
        var population = 0;
        while (true){
            System.out.print("Введите численность населения: ");
            try{
                population = scanner.nextInt();
                break;
            }catch (Exception e){
                System.out.println("Введено некорректное значение");
                scanner.nextLine();
            }
        }
        var uuid = UUID.randomUUID();
        regionPointer.cities.append(new City(uuid, name, population));
        if (regionPointer.cities.get(uuid) != null){
            System.out.println("Город добавлен: " + name);
        }else {
            System.out.println("Город не добавлен");
        }
    }

    /**
     * Комплексное дедактирование Региона
     */
    private static void editRegion(){
        if (countryPointer == null){
            System.out.println("Страна не выбрана");
        }
        previewList(countryPointer.regions, "Регионы");
        if (countryPointer.regions.all().isEmpty()){
            return;
        }
        System.out.print("Введите номер для редактирования: ");
        String item = scanner.nextLine();
        try{
            var index = Integer.parseInt(item);
            if (index <=0 || index > countryPointer.regions.all().size()){
                throw new Exception("Не существующий индекс");
            }
            index--;
            var region = countryPointer.regions.get(index);
            if (region != null){

                regionPointer = region;
                cityActions();
            }else{
                System.out.println("Не удалось определить регион");
            }
        } catch (Exception e){
            System.out.println("Ошибка редактирования: " + e.getMessage());
        }
    }

    /**
     * Редактирование информации о городе
     */
    public static void editCity(){
        if (regionPointer == null){
            System.out.println("Регион не выбран");
        }

        previewList(regionPointer.cities, "Города");

        if (regionPointer.cities.all().isEmpty()){
            return;
        }

        System.out.print("Введите номер для редактирования: ");
        String item = scanner.nextLine();

        try{
            var index = Integer.parseInt(item);
            if (index <=0 || index > regionPointer.cities.all().size()){
                throw new Exception("Не существующий индекс");
            }
            index--;
            var city = regionPointer.cities.get(index);
            if (city != null){
                changeCity(city);
            }else{
                System.out.println("Не удалось определить город");
            }

        } catch (Exception e){
            System.out.println("Ошибка редактирования: " + e.getMessage());
        }
    }

    /**
     * Удаление города
     */
    private static void removeCity(){
        if (regionPointer == null){
            System.out.println("Регион не выбран");
        }

        previewList(regionPointer.cities, "Города");

        if (regionPointer.cities.all().isEmpty()){
            return;
        }

        System.out.print("Введите номер для удаления: ");
        String item = scanner.nextLine();

        try{
            var index = Integer.parseInt(item);

            if (index <=0 || index > regionPointer.cities.all().size()){
                throw new Exception("Не существующий индекс");
            }
            index--;
            var city = regionPointer.cities.get(index);
            if (city != null){
                var uuid = city.uuid;
                var name = city.name;

                System.out.println("Город "+ name + " будет удален, для подтверждения введите Y/y");
                var choise = scanner.nextLine();

                if (!choise.equalsIgnoreCase("y")){
                    System.out.println("Отмена операции удаления");
                    return;
                }

                regionPointer.cities.remove(city);
                if (regionPointer.cities.get(uuid) == null){
                    System.out.println("Город удален: " + name);
                }
            }else{
                System.out.println("Не удалось определить город");
            }

        } catch (Exception e){
            System.out.println("Ошибка удаления: " + e.getMessage());
        }
    }

    /**
     * Удаление региона
     */
    private static void removeRegion(){
        if (countryPointer == null){
            System.out.println("Страна не выбрана");
        }
        previewList(countryPointer.regions, "Регионы");
        if (countryPointer.regions.all().isEmpty()){
            return;
        }
        System.out.print("Введите номер для удаления: ");
        String item = scanner.nextLine();
        try{
            var index = Integer.parseInt(item);
            if (index <=0 || index > countryPointer.regions.all().size()){
                throw new Exception("Не существующий индекс");
            }

            index--;
            var region = countryPointer.regions.get(index);
            if (region != null){
                var uuid = region.uuid;
                var name = region.name;

                System.out.println("Регион "+ name + " будет удален, для подтверждения введите Y/y");
                var choise = scanner.nextLine();

                if (!choise.equalsIgnoreCase("y")){
                    System.out.println("Отмена операции удаления");
                    return;
                }

                countryPointer.regions.remove(region);
                if (countryPointer.regions.get(uuid) == null){
                    System.out.println("Регион удален: " + name);
                }
            }else{
                System.out.println("Не удалось определить регион");
            }

        } catch (Exception e){
            System.out.println("Ошибка удаления: " + e.getMessage());
        }
    }

    /**
     * Изменение Региона
     */
    private static void changeRegion(){
        System.out.print("Введите новое название: ");
        String item = scanner.nextLine();
        regionPointer.name = item;
        System.out.println("Новое название региона: " + item);
    }

    /**
     * Изменение СТраны
     */
    private static void changeCountry(){
        System.out.print("Введите новое название: ");
        String item = scanner.nextLine();
        countryPointer.name = item;
        System.out.println("Новое название страны: " + item);
    }

    private static void changeCity (City city){
        System.out.print("Введите новое название города: ");
        String newName = scanner.nextLine();
        var newPopulation = 0;
        while (true){
            System.out.print("Введите численность населения: ");
            try{
                newPopulation = scanner.nextInt();
                break;
            }catch (Exception e){
                System.out.println("Введено некорректное значение");
                scanner.nextLine();
            }
        }
       city.Edit(newName, newPopulation);
    }

    /**
     * Комплексное редактирование Страны
     */
    private static void editCountry(){
        previewList(world.countries, "Страны");
        if (world.countries.all().isEmpty()){
            return;
        }
        System.out.print("Введите номер для редактирования: ");
        String item = scanner.nextLine();
        try{
            var index = Integer.parseInt(item);
            if (index <=0 || index > world.countries.all().size()){
                throw new Exception("Не существующий индекс");
            }

            index--;
            var country = world.countries.get(index);
            if (country != null){
                countryPointer = country;
                regionActions();
            }else{
                System.out.println("Не удалось определить страну");
            }
        } catch (Exception e){
            System.out.println("Ошибка редактирования: " + e.getMessage());
        }
    }

    /**
     * Удаление Страны
     */
    private static void removeCountry() {
        previewList(world.countries, "Страны");
        if (world.countries.all().isEmpty()){
            return;
        }
        System.out.print("Введите номер для удаления: ");
        String item = scanner.nextLine();
        try{
            var index = Integer.parseInt(item);
            if (index <=0 || index > world.countries.all().size()){
                throw new Exception("Не существующий индекс");
            }
            index--;
            var country = world.countries.get(index);
            if (country != null){
                var uuid = country.uuid;
                var name = country.name;

                System.out.println("Страна "+ name + " будет удалена, для подтверждения введите Y/y");
                var choise = scanner.nextLine();

                if (!choise.equalsIgnoreCase("y")){
                    System.out.println("Отмена операции удаления");
                    return;
                }

                world.countries.remove(country);
                if (world.countries.get(uuid) == null){
                    System.out.println("Страна удалена: " + name);
                }
            }else{
                System.out.println("Не удалось определить страну");
            }
        } catch (Exception e){
            System.out.println("Ошибка удаления: " + e.getMessage());
        }
    }

    /**
     * Поиск стран
     */
    private static void deepSearch() {
        if (world.countries.ifEmpty()) {
            System.out.println("Нет данных");
            return;
        }

        var founders = new ArrayList<Founder>();
        System.out.print("Введите элемент для поиска: ");
        String item = scanner.nextLine();
        //Выполняем поиск среди всех Стран
        var founded_countries = world.countries.find(item);
        founded_countries.all().forEach( country -> founders.add(new Founder(country, SourceType.Country)));

        //Поиск среди Регионов всех Стран
        for (var country: world.countries.all()){
            var founded_regions = country.regions.find(item);
            founded_regions.all().forEach( region -> founders.add(new Founder(region, SourceType.Region, country.getName())));

            //Выполняем поиск среди Городов всех Регионов
            for (var region: country.regions.all()){
                var founded_cities = region.cities.find(item);
                founded_cities.all().forEach( city -> founders.add(new Founder(city, SourceType.City,
                       region.getName(), country.getName())));
            }
        }

        System.out.println("Результат поиска:");
        if (founders.isEmpty()) {
            System.out.println("Ничего не найдено");
        } else {
            var index = 0;
            for (var founder: founders){
                index++;
                System.out.print(String.format("%s. %s", index, founder.source.getName()));
                for (var level : founder.levels)
                {
                   System.out.print(String.format(" -> %s", level));
                }
                System.out.println();
            }
            System.out.print("Введите номер для входа в режим редактирования, иначе - Отмена: ");
            String choice = scanner.nextLine();
            try {
                var number = Integer.parseInt(choice);
                if (number <= 0 || number > founders.size()) {
                    throw new Exception("Неверный диарпазон");
                }
                var selected = founders.get(number-1);
                switch (selected.sourceType){
                    case Country -> {
                            countryPointer = (Country) selected.source;
                            regionActions();
                    }
                    case Region -> {
                        regionPointer = (Region) selected.source;
                        cityActions();
                    }
                    case City -> {
                        changeCity((City) selected.source);
                    }
                }
            }catch (Exception e) {
                System.out.println("Отмена");
            }
        }


    }


    private static void displayPreview(SourceType sourceType){
        switch (sourceType) {
            case Country -> previewList(world.countries, "Страны");
            case Region -> {
                if (countryPointer == null) {
                    break;
                }
                previewList(countryPointer.regions, "Регионы");
            }
            case City -> {
                if (regionPointer == null) {
                    break;
                }
                previewList(regionPointer.cities, "Города");
            }
        }
    }

    // Отображение списка
    private static void displayInfo(SourceType sourceType) {
        System.out.println();
        switch (sourceType) {
            case Country -> System.out.println(world.getInfo());
            case Region -> {
                if (countryPointer == null) {
                    break;
                }
                System.out.println(countryPointer.getInfo());
            }
            case City -> {
                if (regionPointer == null) {
                    break;
                }
                System.out.println(regionPointer.getInfo());
            }
        }
    }

    private static <T extends SourceName> void previewList (SourceList<T> sourceList, String sourceName){
        System.out.println();
        if (sourceList.ifEmpty()){
            System.out.println("Список пуст");
            return;
        }
        System.out.println(sourceName + ":");
        System.out.println(sourceList.pointer());
    }


}


