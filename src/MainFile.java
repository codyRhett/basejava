import java.io.File;

public class MainFile {
    private static final String filePath = "/home/artem/java/basejava/basejava";

    private static void listTo(String str) {
        File file = new File(filePath + "/" + str);
        String[] strArr = file.list();

        if (file.isFile()) {
            System.out.println(file.getName());
        } else if (file.isDirectory()) {
            //System.out.println("----------" + file.getName());

            listTo(file.getName());
        }


    }

    public static void main(String[] args){
//        String filePath = ".\\.gitignore";
//        File file = new File(filePath);
        File fileDir = new File(filePath);
        String[] list = fileDir.list();

        if (!fileDir.exists()) {
           System.out.println("File not found");
        }

        if (list != null) {
            for (String str : list) {
                listTo(str);
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