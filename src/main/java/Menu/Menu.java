package Menu;


import static Menu.pupil.PupilMenu.ShowPupilMenu;
import static Menu.teacher.TeacherMenu.ShowTeacherMenu;

public class Menu {

    public void ShowMenu() {
        System.out.println("""
                Welcome to Simple School Database!\s
                
                Who are you?\s
                1. I am a pupil
                2. I am a teacher
                """);
    }

    public void MenuChoice(int choice) {

        switch (choice) {
            case 1:
                ShowPupilMenu();
                break;
            case 2:
                ShowTeacherMenu();
                break;
            default:
                break;
        }
    }

}
