import java.io.File;

public class MainFile {
    //private static final String filePath = "/home/artem/java/basejava/basejava";
    private static final String filePath = "C:\\Qt_prj\\jav\\basejava";

    private static void listTo(String str) {
        File file = new File(str);
        String[] strArr = file.list();

        for (String strList : strArr) {
            File file1 = new File(str + "\\" + strList);
            if (file1.isFile()) {
                System.out.println(str + "\\" + strList);
            } else if (file1.isDirectory()) {
                listTo(str + "\\" + strList);
            }
        }
    }

    public static void main(String[] args){
        File fileDir = new File(filePath);

        if (!fileDir.exists()) {
           System.out.println("File not found");
        }

        listTo(filePath);

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