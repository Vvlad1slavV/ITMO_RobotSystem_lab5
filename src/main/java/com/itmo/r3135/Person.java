package com.itmo.r3135;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Класс Person.
 */
public class Person  {
        private String name; //Поле не может быть null, Строка не может быть пустой
        private java.time.LocalDateTime birthday; //Поле не может быть null
        private Color eyeColor; //Поле не может быть null
        private Color hairColor; //Поле не может быть null

        public Person(String name, LocalDateTime birthday, Color eyeColor, Color hairColor) {
                this.name = name;
                this.birthday = birthday;

                this.eyeColor = eyeColor;
                this.hairColor = hairColor;
        }

        public String getName() {
                return name;
        }

        public LocalDateTime getBirthday() {
                return birthday;
        }

        public Color getEyeColor() {
                return eyeColor;
        }

        public Color getHairColor() {
                return hairColor;
        }


        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Person person = (Person) o;
                return Objects.equals(name, person.name) &&
                        Objects.equals(birthday, person.birthday) &&
                        eyeColor == person.eyeColor &&
                        hairColor == person.hairColor;
        }

        @Override
        public int hashCode() {
                return Objects.hash(name, birthday, eyeColor, hairColor);
        }
}