import java.util.Objects;
import java.util.OptionalInt;

public class Main {

    public static class Person {

        protected final String name;
        protected final String surname;
        private int age;
        private String address = null;

        // Оставил конструкторы, вдруг будет смысл использовать Person без builder
        //----------------------------------------------------------------------
        public Person(String name, String surname) {
            this.name = name;
            this.surname = surname;
        }

        public Person(String name, String surname, int age) {
            this.name = name;
            this.surname = surname;
            this.age = age;
        }
        //----------------------------------------------------------------------

        public Person(String name, String surname, int age, String address) {
            if (name == null) {
                throw new IllegalStateException("Не хватает обязательных полей, имя не указанно, name: " + name);
            } else {
                this.name = name;
            }

            if (surname == null) {
                throw new IllegalStateException("Не хватает обязательных полей, фамилия не указанна, " +
                        "surname: " + surname);
            } else {
                this.surname = surname;
            }

            this.age = age;
            this.address = address;
        }

        public boolean hasAge() {
            if (getAge().hashCode() == 0) {
                return false;
            } else {
                return true;
            }
        }

        public boolean hasAddress() {
            if (this.address == null) {
                return false;
            } else {
                return true;
            }
        }

        public String getName() {
            return this.name;
        }

        public String getSurname() {
            return this.surname;
        }

        public OptionalInt getAge() {
            return OptionalInt.of(this.age);
        }

        public String getAddress() {
            if (hasAddress()) {
                return this.address;
            } else {
                return "Адрес не известен";
            }
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void happyBirthday() {
            if (getAge().hashCode() != 0) {
                this.age += 1;
            }
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", surname='" + surname + '\'' +
                    ", age=" + age +
                    ", address='" + address + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return age == person.age && Objects.equals(name, person.name) && Objects.equals(surname, person.surname)
                    && Objects.equals(address, person.address);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, surname, age, address);
        }

        public PersonBuilder newChildBuilder() {
            return new PersonBuilder()
                    .setSurname(surname)
                    .setAge(10)           // установил 10 лет
                    .setAddress(address);
        }
    }

    public interface IPersonBuilder {
        Person build();
    }

    public static class PersonBuilder implements IPersonBuilder {

        private String name;
        private String surname;
        private int age;
        private String address;

        public PersonBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public PersonBuilder setSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public PersonBuilder setAge(int age) {
            if (age <= 0) {
                throw new IllegalArgumentException("Возраст не может быть отрицательным или нулевым, вы ввели: " + age);
            } else {
                this.age = age;
                return this;
            }
        }

        public PersonBuilder setAddress(String address) {
            this.address = address;
            return this;
        }

        @Override
        public Person build() {
            return new Person(name, surname, age, address);
        }
    }

    public static void main(String[] args) {

        Person mom = new PersonBuilder()
                .setName("Анна")
                .setSurname("Вольф")
                .setAge(31)
                .setAddress("Сидней")
                .build();
        System.out.println(mom);
        Person son = mom.newChildBuilder()
                .setName("Антошка")
                .build();
        System.out.println("У " + mom + " есть сын, " + son);
        System.out.println();


        System.out.println("----------------------------sister----------------------------");
        Person sister = new PersonBuilder()
                .setName("Вика")
                .setSurname("Вольф")
                .build();

        System.out.println("sister: " + sister);
        System.out.println("sister hasAge: " + sister.hasAge());
        System.out.println("sister getAddress: " + sister.getAddress());
        System.out.println("sister hasAddress: " + sister.hasAddress());
        sister.setAddress("New York");
        System.out.println("sister setAddress hasAddress: " + sister.hasAddress());
        System.out.println("sister getAddress: " + sister.getAddress());

        System.out.println("---------------------------brother----------------------------");
        Person brother = new PersonBuilder()
                .setName("Евгений")
                .setAge(16)
                .setSurname("Вольф")
                .setAddress("Сидней")
                .build();
        System.out.println("brother: " + brother);
        System.out.println("brother hasAge: " + brother.hasAge());
        System.out.println("brother getAge: " + brother.getAge().getAsInt());
        brother.happyBirthday();
        System.out.println("brother happyBirthday getAge: " + brother.getAge().getAsInt());
        System.out.println("brother hasAddress: " + brother.hasAddress());
        System.out.println("brother getAddress: " + brother.getAddress());
        System.out.println();


        System.out.println("---------------------------exception---------------------------");
        try {
            // Не хватает обяхательных полей
            new PersonBuilder().build();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        try {
            // Возраст недопустимый
            new PersonBuilder().setAge(-100).build();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
