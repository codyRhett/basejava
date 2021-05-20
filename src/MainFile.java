import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args){
//        String filePath = ".\\.gitignore";
//        File file = new File(filePath);


        String filePath = ".\\";
        File fileDir = new File(filePath);

        String[] list = fileDir.list();
        if (list != null) {
            for(String str : list) {
                File file1 = new File(filePath + str);
                if (file1.isFile()) {
                    System.out.println("-----" + file1.getName());
                } else if (file1.isDirectory()) {
                    // Попали в новую директорию
                    System.out.println(file1.getName());
                    list()
                }

               //System.out.println(str);
            }
        }

//        try {
//            System.out.println(file.getCanonicalPath());
//        } catch (IOException e) {
//            throw new RuntimeException("Error", e);
//        }
//        File dir = new File(".\\src\\ru\\javawebinar");
//        System.out.println(dir.isDirectory());
//        String[] list = dir.list();
//        if (list != null) {
//            for (String name : list){
//                System.out.println(name);
//            }
//        }

//        FileInputStream fis = null;
//        try {
//            fis = new FileInputStream(filePath);
//            System.out.println(fis.read());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } finally {
//            if (fis != null) {
//                try {
//                    fis.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }


//        try(FileInputStream fis = new FileInputStream(filePath)) {
//            System.out.println(fis.read());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }
}