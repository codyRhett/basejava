import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class MainFile {
    private static String shift = "";
    private static String shift1 = "---";
    private static final List<String> listQueue = new LinkedList<>();

    private static void listTo(String str) {
        File file = new File(str);
        String[] strArr = file.list();

        if (strArr != null) {
            for (String strList : strArr) {
                File file1 = new File(str + "\\" + strList);

                if (file1.isFile()) {
                    shift = "";
                    System.out.print(shift1);
                    System.out.println(strList);
                } else if (file1.isDirectory()) {
                    System.out.print(shift);
                    shift += "---";
                    System.out.println(file1.getName());
                    listTo(str + "\\" + strList);
                    shift1 = shift;
                }
            }
        }
    }

    public static void main(String[] args){
        String filePath = "C:\\Qt_prj\\proba";
        File fileDir = new File(filePath);
        if (!fileDir.exists()) {
           System.out.println("File not found");
        }
        shift1 = "---";
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