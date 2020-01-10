import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Random;

public class Pupil {

    enum Gender {
        MALE, FEMALE
    }

    private static String [] maleNames = {"Петя", "Коля", "Миша", "Вася", "Боря"};
    private static String [] femaleNames = {"Маша", "Даша", "Катя", "Лена", "Саша"};

    private int number; // уникальное значение для каждого ученика
    private String name;
    private Gender gender;
    private LocalDate birth;
    // TODO: добавить все необходимые методы

    Pupil () {}

    public Pupil(int number, String name, Gender gender, LocalDate birth) {
        this.number = number;
        this.name = name;
        this.gender = gender;
        this.birth = birth;
    }

    public int getAge() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        int currentYear = currentDateTime.getYear();
        int age = currentYear - this.getBirth().getYear();
        return age;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public Pupil createNewPupil (int id) {

        int newNumber = id;
        String newName;
        Gender newGender;
        LocalDate newBirth;
        Random rnd = new Random();

        int random = rnd.nextInt((2 - 1) + 1) + 1;

        if (random == 1) {
            newGender = Gender.MALE;
            newName = maleNames[(rnd.nextInt(maleNames.length))];
        }else {
            newGender = Gender.FEMALE;
            newName = femaleNames[(rnd.nextInt(maleNames.length))];
        }

        newBirth = LocalDate.of((rnd.nextInt((2014 - 2000) + 1) + 2000), (rnd.nextInt((12 - 1) + 1) + 1), (rnd.nextInt(28 - 1))+ 1);

        return new Pupil(newNumber, newName, newGender, newBirth);
    }

    @Override
    public String toString() {
        return "\n" + this.number + ". " + this.name + ", " + this.gender + ", " + "дата рождения: " + this.birth + " " + "(возраст: " + getAge() + " лет)";
    }

    @Override
    public boolean equals(Object obj) {
        Pupil p1 = (Pupil) obj;
        return this.getName().equals(p1.getName());
    }

//    public boolean equalPupils(String name) {
//        return this.getName().equals(name);
//    }
}