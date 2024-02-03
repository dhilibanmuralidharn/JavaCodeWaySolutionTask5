import java.util.*;

class Course{
    String courseCode;
    String title;
    String description;
    int capacity;
    String schedule;
    int availableSlot;
    List<Student> studentsRegistered;

    public Course(String courseCode, String title, String description, int capacity, String schedule){
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.availableSlot = capacity;
        this.studentsRegistered = new ArrayList<>();
    }
}

class Student{
    int studentId;
    String name;
    List<Course> registeredCourses;

    public Student(int studentId, String name){
        this.studentId = studentId;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }
}
class CourseDatabase{
    List<Course> courses;
    public CourseDatabase(){
        this.courses = new ArrayList<>();
    }

    public void addCourse(Course course){
        courses.add(course);
    }
    public void removeCourse(Course course){
        courses.remove(course);
    }
    public void updateCourse(Course course){
        Scanner input = new Scanner(System.in);
        System.out.println("Update course: "+ course.title);

        System.out.println("Enter new title (press Enter to keep current): ");
        String newTitle = input.nextLine();
        if (!newTitle.isEmpty()){
            course.title = newTitle;
        }

        System.out.println("Enter new description (press Enter to keep current): ");
        String newDescription = input.nextLine();
        if (!newDescription.isEmpty()){
            course.description = newDescription;
        }
        System.out.println("Enter new Capacity (press Enter to keep current): ");
        String newCapacityStr = input.nextLine();
        if (!newCapacityStr.isEmpty()){
            try{
                int newCapacity = Integer.parseInt(newCapacityStr);
                course.capacity = newCapacity;
                course.availableSlot = newCapacity - course.studentsRegistered.size();
            }catch (NumberFormatException e){
                System.out.println("Invalid Capacity format. Capacity not update");
            }
        }
        System.out.println("Enter new Schedule (press Enter to keep current): ");
        String newSchedule = input.nextLine();
        if (!newSchedule.isEmpty()){
            course.schedule = newSchedule;
        }
        System.out.println("Course update successfully!!!");
    }
    public Course getCourse(String courseCode){
        for (Course course : courses){
            if (course.courseCode.equals(courseCode)){
                return course;
            }
        }
        return null;
    }
     public void displayAvailableCourses(){
        for (Course course: courses){
            System.out.println(course.courseCode + " - " + course.title + "(" + course.availableSlot + " slots available");
        }
    }
}

class StudentDatabase{
    List<Student> students;

    public StudentDatabase(){
        this.students = new ArrayList<>();
    }
    public void addStudent(Student student){
        students.add(student);
    }
    public void removeStudent(Student student){
        students.remove(student);
    }
    public void updateStudent(Student student){
        Scanner input = new Scanner(System.in);
        System.out.println("Updating Student: " + student.name);

        System.out.print("Enter new name (press Enter to keep current): ");
        String newName = input.nextLine();
        if (!newName.isEmpty()){
            student.name = newName;
        }
        System.out.println("Student updated successfully!!!");
    }
    public Student getStudent(int studentId){
        for (Student student: students){
            if (student.studentId == studentId){
                return student;
            }
        }
        return null;
    }
    public void resgiterCourse(Student student, Course course){
        if (course.availableSlot > 0){
            student.registeredCourses.add(course);
            course.studentsRegistered.add(student);
            course.availableSlot--;
            System.out.println("Course registration successful for " + student.name + " in " + course.title);
        }else {
            System.out.println("Course is full. Cannot register for " + student.name + " in " + course.title);
        }
    }
    public void dropCourse(Student student, Course course){
        student.registeredCourses.remove(course);
        course.studentsRegistered.remove(student);
        course.availableSlot++;
        System.out.println("Course dropped successfully for " + student.name + " in " + course.title);
    }
}
class Main{
    public static void main(String [] args){
        CourseDatabase courseDatabase = new CourseDatabase();

        Course course1 = new Course("CSE101", "Introduction to Computer Science", "Basic concepts of programming", 50, "Mon/Wed 10:00 AM - 11:30 AM");
        Course course2 = new Course("ENG201", "Advanced English Composition", "Improving writing skills", 30, "Tue/Thu 1:00 PM - 2:30 PM");
        Course course3 = new Course("MATH301", "Calculus III", "Advanced calculus topics", 40, "Mon/Wed/Fri 2:00 PM - 3:30 PM");

        courseDatabase.addCourse(course1);
        courseDatabase.addCourse(course2);
        courseDatabase.addCourse(course3);

        StudentDatabase studentDatabase = new StudentDatabase();
        Student student1 = new Student(1, "Dhilip");
        Student student2 = new Student(2, "Yogesh");
        Student student3 = new Student(3, "Venkat");
        Student student4 = new Student(4, "Edward");

        studentDatabase.addStudent(student1);
        studentDatabase.addStudent(student2);
        studentDatabase.addStudent(student3);
        studentDatabase.addStudent(student4);


        Scanner input = new Scanner(System.in);

        while (true){
            System.out.println("Main Menu: ");
            System.out.println("1. Display Available Courses");
            System.out.println("2. Register for a Course");
            System.out.println("3. Drop a Course");
            System.out.println("4. Exit!!!");

            System.out.print("Enter your choice: ");
            int choice = input.nextInt();

            switch (choice){
                case 1:
                    courseDatabase.displayAvailableCourses();
                    break;
                case 2:
                    System.out.println("Course Registration: ");
                    System.out.print("Enter student ID: ");
                    int studentId = input.nextInt();
                    Student studentForRegistration = studentDatabase.getStudent(studentId);

                    if(studentForRegistration != null){
                        System.out.println("Available Course: ");
                        courseDatabase.displayAvailableCourses();

                        System.out.print("Enter course code to register: ");
                        String newCourseCode = input.next();
                        Course courseForRegistration = courseDatabase.getCourse(newCourseCode);

                        if(courseForRegistration != null){
                            studentDatabase.resgiterCourse(studentForRegistration, courseForRegistration);
                        }else {
                            System.out.println("Invalid courseCode . Registration Failed");
                        }
                    }else {
                        System.out.println("Student Not Found. Registration Failed.");
                    }
                    break;
                case 3:
                    System.out.println("Course Drop: ");

                    System.out.print("Enter student Id: ");
                    int newStudentId = input.nextInt();
                    Student studentForDrop = studentDatabase.getStudent(newStudentId);

                    if(studentForDrop != null){
                        System.out.println("Registered Courses: ");
                        for (Course course : studentForDrop.registeredCourses){
                            System.out.println(course.courseCode + " - " + course.title);
                        }
                        System.out.println("Enter course code to drop: ");
                        String newCourseCode = input.next();
                        Course courseForDrop = courseDatabase.getCourse(newCourseCode);

                        if (courseForDrop != null){
                            studentDatabase.dropCourse(studentForDrop, courseForDrop);
                        }else {
                            System.out.println("Invalid course code or course not registered. Drop failed.");
                        }
                    } else {
                        System.out.println("Student not found.Drop failed.");
                    }
                    break;
                case 4:
                    System.out.println("Exiting program. GoodBye!!!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid Choice. Please enter a valid option.");
            }
        }
    }
}