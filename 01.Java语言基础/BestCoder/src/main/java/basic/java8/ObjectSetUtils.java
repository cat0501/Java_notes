package basic.java8;

/**
 * @author Lemonade
 * @description
 * @updateTime 2023/6/30 11:19
 */
import org.apache.commons.collections4.CollectionUtils;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;
import java.util.List;

public class ObjectSetUtils {

    public static class Person {
        private String name;
        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        // Getter and Listter methods

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Person person = (Person) o;

            if (age != person.age) return false;
            return name != null ? name.equals(person.name) : person.name == null;
        }

        @Override
        public int hashCode() {
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + age;
            return result;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    public static <T> List<T> difference(List<T> set1, List<T> set2) {
        return new ArrayList<>(CollectionUtils.subtract(set1, set2));
    }

    public static void main(String[] args) {
        System.out.println(Timestamp.from(Instant.now()));

        List<Person> set1 = new ArrayList<>();
        set1.add(new Person("Alice", 25));
        set1.add(new Person("Bob", 30));
        set1.add(new Person("Charlie", 35));

        List<Person> set2 = new ArrayList<>();
        set2.add(new Person("Bob", 30));
        set2.add(new Person("Charlie", 35));
        set2.add(new Person("Dir", 25));

        List<Person> difference1 = difference(set1, set2);
        List<Person> difference2 = difference(set2, set1);
        System.out.println("Difference: " + difference1);
        System.out.println("Difference: " + difference2);
    }
}
