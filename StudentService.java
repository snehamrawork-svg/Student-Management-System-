import java.io.*;
import java.util.*;

public class StudentService {
    private List<Student> students = new ArrayList<>();
    private final String filePath = "data/students.csv";

    public StudentService() {
        loadFromFile();
    }

    public void addStudent(Student s) {
        students.add(s);
        saveToFile();
        System.out.println("Student added successfully!");
    }

    public void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        for (Student s : students) {
            System.out.println(s);
        }
    }

    public void updateStudent(int id, String name, int age, String course) {
        for (Student s : students) {
            if (s.getId() == id) {
                s.setName(name);
                s.setAge(age);
                s.setCourse(course);
                saveToFile();
                System.out.println("Student updated!");
                return;
            }
        }
        System.out.println("Student not found!");
    }

    public void deleteStudent(int id) {
        Iterator<Student> iterator = students.iterator();

        while (iterator.hasNext()) {
            Student s = iterator.next();
            if (s.getId() == id) {
                iterator.remove();
                saveToFile();
                System.out.println("Student deleted!");
                return;
            }
        }
        System.out.println("Student not found!");
    }

    private void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("ID,Name,Age,Course\n");
            for (Student s : students) {
                bw.write(s.getId() + "," + s.getName() + "," + s.getAge() + "," + s.getCourse() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFromFile() {
        File file = new File(filePath);
        if (!file.exists())
            return;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // skip header

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                students.add(new Student(
                        Integer.parseInt(data[0]),
                        data[1],
                        Integer.parseInt(data[2]),
                        data[3]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}