package com.itmo.r3135;

import java.util.Objects;

public class Person {
        private String name; //Поле не может быть null, Строка не может быть пустой
        private java.time.LocalDateTime birthday; //Поле не может быть null
        private Color eyeColor; //Поле не может быть null
        private Color hairColor; //Поле не может быть null

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
