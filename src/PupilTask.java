import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static javafx.scene.input.KeyCode.T;

public class PupilTask {
    public static void main(String[] args) {
        Pupil pupil = new Pupil();
        ArrayList<Pupil> listOfPupils = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            listOfPupils.add(pupil.createNewPupil(i + 1));
        }
        System.out.println("Базовый список учеников для сортировки Stream API:");
        System.out.println(listOfPupils);
        System.out.println();

        // Используя Stream API:
        // 1. Разделить учеников на две группы: мальчиков и девочек. Результат: Map<Pupil.Gender, ArrayList<Pupil>>

        Map<Pupil.Gender, ArrayList<Pupil>> pupilsSortedByGender = listOfPupils.stream()
                .collect(groupingBy(Pupil::getGender, toCollection(ArrayList::new)));

        System.out.print("Список мальчиков и девочек:");
        System.out.println(pupilsSortedByGender);
        System.out.println();

        // 2. Найти средний возраст учеников
        double averageAge = listOfPupils.stream().mapToInt(Pupil::getAge).average().getAsDouble();

        System.out.printf("Средний возраст учеников в списке: %s лет\n", averageAge);
        System.out.println();

        // 3. Найти самого младшего ученика
        ArrayList<Pupil> listOfPupilsSortedByAge = listOfPupils.stream()
                .sorted((p1, p2) -> p1.getBirth().compareTo(p2.getBirth()))
                .collect(toCollection(ArrayList::new));

        System.out.print("Самый младший ученик/ученица в списке:");
        System.out.println(listOfPupilsSortedByAge.get(listOfPupilsSortedByAge.size() - 1));
        System.out.println();

        // 4. Найти самого старшего ученика
        System.out.print("Самый старший ученик/ученица в списке:");
        System.out.println(listOfPupilsSortedByAge.get(0));
        System.out.println();

        // 5. Собрать учеников в группы по году рождения
        ArrayList<Pupil> listOfPupilsWithSameBirthYear = listOfPupils.stream()
                .sorted(Comparator.comparing(Pupil::getBirth))
                .collect(toCollection(ArrayList::new));

        System.out.println("Список учеников, отсортированный по годам рождения:");
        System.out.println(listOfPupilsWithSameBirthYear);
        System.out.println();

        // 6. Убрать учеников с одинаковыми именами, имена и дату рождения оставшихся вывести в консоль

        ArrayList<Pupil> pupilsWithUniqueNamesOnly = listOfPupils.stream()
                .filter(filterPupils(Pupil::getName))
                .collect(toCollection(ArrayList::new));

        System.out.println("Список учеников/учениц с уникальными именами из базового списка:");
        System.out.println(pupilsWithUniqueNamesOnly.toString());
        System.out.println();

        // 7. Отсортировать по полу, потом по дате рождения, потом по имени (в обратном порядке), собрать в список (List)
        Map<Pupil.Gender, ArrayList<Pupil>> pupilsSortedBySexBirthNames = listOfPupils.stream()
                .sorted(Comparator.comparing(Pupil::getName).reversed())
                .sorted(Comparator.comparing(Pupil::getBirth))
                .collect(groupingBy(Pupil::getGender, toCollection(ArrayList::new)));

        System.out.print("Список учеников отсортированный по полу, дате рождения и имени (в обратном порядке):");

        for(Map.Entry<Pupil.Gender, ArrayList<Pupil>> map : pupilsSortedBySexBirthNames.entrySet()){

            System.out.print("\n" + map.getKey() + "S:");

            for(Pupil p : map.getValue()){
                System.out.print(p.toString());
            }
        }

        System.out.println();
        System.out.println();

        // 8. Вывести в косоль всех учеников в возрасте от N до M лет
        int n = 7;
        int m = 13;

        ArrayList<Pupil> listOfPupilsWithAge = listOfPupils.stream()
                .filter(p -> (p.getAge() >= n) && (p.getAge() <= m))
                .collect(toCollection(ArrayList::new));

        System.out.printf("Список учеников в возрасте от %s до %s лет:\n", n, m);
        System.out.println(listOfPupilsWithAge);
        System.out.println();

        // 9. Собрать в список всех учеников с именем=someName
        String firstNameInList = listOfPupils.stream().findFirst().get().getName();

        ArrayList<Pupil> pupilsWithSameNames = listOfPupils.stream()
                .filter(p -> firstNameInList.equals(p.getName()))
                .collect(toCollection(ArrayList::new));

        System.out.printf("Список учеников/учениц с именем %s:\n", firstNameInList);
        System.out.println(pupilsWithSameNames);
        System.out.println();

        // 10. Собрать Map<Pupil.Gender, Integer>, где Pupil.Gender - пол, Integer - суммарный возраст учеников данного пола

        Map<Pupil.Gender, Integer> newListOfPupils = listOfPupils.stream()
                .collect(Collectors.groupingBy(Pupil::getGender, Collectors.summingInt(Pupil::getAge)));

        System.out.println("Суммарный возраст учеников и учениц:");

        for(Map.Entry<Pupil.Gender, Integer> list : newListOfPupils.entrySet()){
            System.out.printf("%sS: %d\n", list.getKey(), list.getValue());
        }
    }

    private static <T> Predicate<T> filterPupils (Function<? super T, ?> key) {
        Set<Object> seen = new HashSet<>();
        return t -> seen.add(key.apply(t));
    }

}