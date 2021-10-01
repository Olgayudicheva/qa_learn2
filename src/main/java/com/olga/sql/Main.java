package com.olga.sql;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Загрузка драйвера для mysql
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:41063/juser", "juser", "qwerty1234");
            //Начало работы с базой
            con.createStatement().executeUpdate("DROP TABLE Student");
            con.createStatement().executeUpdate("DROP TABLE `Group`");
            con.createStatement().executeUpdate("DROP TABLE `Curator`");

            con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS Student (id INT PRIMARY KEY AUTO_INCREMENT, fio VARCHAR(100), sex VARCHAR(10), id_group INT)");
            con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS `Group` (id INT PRIMARY KEY, name VARCHAR(100), id_curator INT)");
            con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS `Curator` (id INT PRIMARY KEY, fio VARCHAR(100))");

            con.createStatement().executeUpdate("INSERT INTO `Curator` (id, fio) VALUES(0, 'Горбачёв Герасим Платонович')");
            con.createStatement().executeUpdate("INSERT INTO `Curator` (id, fio) VALUES(1, 'Барановский Прохор Григорьевич')");
            con.createStatement().executeUpdate("INSERT INTO `Curator` (id, fio) VALUES(2, 'Журавлёва Нелли Борисовна')");
            con.createStatement().executeUpdate("INSERT INTO `Curator` (id, fio) VALUES(3, 'Кобзар Цара Львовна')");

            con.createStatement().executeUpdate("INSERT INTO `Group` (id, name, id_curator) VALUES(0, 'Группа А', 0)");
            con.createStatement().executeUpdate("INSERT INTO `Group` (id, name, id_curator) VALUES(1, 'Группа Б', 1)");
            con.createStatement().executeUpdate("INSERT INTO `Group` (id, name, id_curator) VALUES(2, 'Группа В', 2)");

            con.createStatement().executeUpdate("INSERT INTO `Student` (fio, sex, id_group) VALUES('Константинова Маргарита Макаровна', 'f',  0)");
            con.createStatement().executeUpdate("INSERT INTO `Student` (fio, sex, id_group) VALUES('Сомов Фёдор Денисович', 'm',  1)");
            con.createStatement().executeUpdate("INSERT INTO `Student` (fio, sex, id_group) VALUES('Борисова Кристина Александровна', 'f',  2)");
            con.createStatement().executeUpdate("INSERT INTO `Student` (fio, sex, id_group) VALUES('Данилова Лия Кирилловна', 'f',  0)");
            con.createStatement().executeUpdate("INSERT INTO `Student` (fio, sex, id_group) VALUES('Козлова Мария Данииловна', 'f',  0)");
            con.createStatement().executeUpdate("INSERT INTO `Student` (fio, sex, id_group) VALUES('Мухин Руслан Андреевич', 'm',  0)");
            con.createStatement().executeUpdate("INSERT INTO `Student` (fio, sex, id_group) VALUES('Коротков Леонид Маркович', 'm',  2)");
            con.createStatement().executeUpdate("INSERT INTO `Student` (fio, sex, id_group) VALUES('Осипова Мария Фёдоровна', 'f',  2)");
            con.createStatement().executeUpdate("INSERT INTO `Student` (fio, sex, id_group) VALUES('Черных Дарья Львовна', 'f',  2)");
            con.createStatement().executeUpdate("INSERT INTO `Student` (fio, sex, id_group) VALUES('Комарова Ульяна Никитична', 'f',  2)");
            con.createStatement().executeUpdate("INSERT INTO `Student` (fio, sex, id_group) VALUES('Устинов Тимофей Вадимович', 'm',  1)");
            con.createStatement().executeUpdate("INSERT INTO `Student` (fio, sex, id_group) VALUES('Афанасьева Ольга Ильинична', 'f',  1)");
            con.createStatement().executeUpdate("INSERT INTO `Student` (fio, sex, id_group) VALUES('Ткачева Василиса Алексеевна', 'f',  1)");
            con.createStatement().executeUpdate("INSERT INTO `Student` (fio, sex, id_group) VALUES('Кочетов Алексей Михайлович', 'm',  0)");
            con.createStatement().executeUpdate("INSERT INTO `Student` (fio, sex, id_group) VALUES('Назарова Арина Александровна', 'f',  0)");

            ResultSet resultSet1 = con.createStatement().executeQuery("SELECT Student.id, Student.fio, Student.sex, `Group`.`name`, Curator.fio as curator FROM Student\n" +
                    "JOIN `Group` on Student.id_group=`Group`.id\n" +
                    "JOIN Curator on Curator.id=`Group`.`id_curator`"+
                    "WHERE Student.sex='m'");
            int count = 0;
            System.out.println("Студенты:");
            while (resultSet1.next()) {
                int id = resultSet1.getInt(1);
                String fio = resultSet1.getString(2);
                String sex = resultSet1.getString(3);
                String groupName = resultSet1.getString(4);
                String curatorFio = resultSet1.getString(5);
                System.out.println(id+"|"+fio+"|"+sex+"|"+groupName+"|"+curatorFio);
                count++;
            }
            System.out.println("Общее количество: "+count);
            System.out.println("-----");

            resultSet1.close();

            System.out.println("Студентки:");
            ResultSet resultSet2 = con.createStatement().executeQuery("SELECT Student.id, Student.fio, Student.sex, `Group`.`name`, Curator.fio as curator FROM Student\n" +
                    "JOIN `Group` on Student.id_group=`Group`.id\n" +
                    "JOIN Curator on Curator.id=`Group`.`id_curator`\n" +
                    "WHERE Student.sex='f'");
            int count2 = 0;
            while (resultSet2.next()) {
                int id = resultSet2.getInt(1);
                String fio = resultSet2.getString(2);
                String sex = resultSet2.getString(3);
                String groupName = resultSet2.getString(4);
                String curatorFio = resultSet2.getString(5);
                System.out.println(id+"|"+fio+"|"+sex+"|"+groupName+"|"+curatorFio);
                count2++;
            }
            System.out.println("Общее количество: "+count2);
            System.out.println("-----");
            resultSet2.close();


            con.createStatement().executeUpdate("UPDATE `Group` SET id_curator=3 WHERE id=2");

            ResultSet resultSet3 = con.createStatement().executeQuery("SELECT `Group`.id, `Group`.`name`, Curator.fio as curator FROM `Group`\n" +
                    "JOIN Curator on Curator.id=`Group`.`id_curator`");
            System.out.println("Группы:");
            while (resultSet3.next()) {
                int id = resultSet3.getInt(1);
                String groupName = resultSet3.getString(2);
                String curatorFio = resultSet3.getString(3);
                System.out.println(id+"|"+groupName+"|"+curatorFio);
            }
            resultSet3.close();
            resultSet3 = con.createStatement().executeQuery("SELECT `Group`.id, `Group`.`name`, Curator.fio as curator FROM `Group`\n" +
                    "JOIN Curator on Curator.id=`Group`.`id_curator`");

            System.out.println("-----");
            System.out.println("-----");
            while (resultSet3.next()) {
                String groupName = resultSet3.getString(2);
                System.out.println(groupName);
                ResultSet resultSet4 = con.createStatement().executeQuery("SELECT * FROM Student where Student.id_group in (SELECT g.id FROM `Group` g WHERE g.name='"+groupName+"');");
                while (resultSet4.next()) {
                    int id = resultSet4.getInt(1);
                    String fio = resultSet4.getString(2);
                    String sex = resultSet4.getString(3);
                    System.out.println(id+"|"+fio+"|"+sex+"|"+groupName);
                }
                System.out.println("-----");
                resultSet4.close();
            }

            resultSet3.close();

            //Конец работы с базой
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
