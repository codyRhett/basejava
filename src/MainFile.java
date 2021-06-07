import java.io.File;

public class MainFile {
    public static void main(String[] args){
        String filePath = ".\\";
        File fileDir = new File(filePath);
        if (!fileDir.exists()) {
           System.out.println("File not found");
        }

        listTo(filePath, "");
    }

    private static void listTo(String str, String shift) {
        File file = new File(str);
        String[] strArr = file.list();

        if (strArr != null) {
            for (String strList : strArr) {
                File file1 = new File(str + "\\" + strList);

                if (file1.isFile()) {
                    System.out.println(shift + "--|" + strList);
                } else if (file1.isDirectory()) {
                    System.out.println((shift + "--|" + file1.getName()));
                    listTo(str + "\\" + strList, shift + "--");
                }
            }
        }
    }
}