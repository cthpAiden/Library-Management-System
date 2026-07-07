package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public  abstract class FileHelper<T>{
    private ArrayList<T> dataList;

    //constructor
    public FileHelper(ArrayList<T> dataList) {
        this.dataList = dataList;
    }

    //loadFromFile:nhận vào 1 đường đãn tới file,đọc file theo từng dòng
    public boolean loadFromFile(String url){
        // đưa url thành file
        File f = new File(url);
        // có khả năng file tào lao=> dùng try-catch

        try {
            FileReader fr = new FileReader(f);// đọc file
            BufferedReader reader = new BufferedReader(fr);//đọc từng dong
            String line = reader.readLine();
            while(line != null){
                //biến line thành object tương ứng cần lưu
                if (!line.trim().isEmpty()) {   // bỏ qua dòng trống
                    // Bọc riêng từng dòng: 1 dòng lỗi chỉ bị bỏ qua, không làm hỏng cả file.
                    try {
                        T t = handleLine(line);
                        if (t != null) {        // bỏ qua dòng không hợp lệ (handleLine trả null)
                            dataList.add(t);
                        }
                    } catch (Exception ex) {
                        System.out.println("Skipping bad line: " + line);
                    }
                }
                line =reader.readLine();
            }
            reader.close();// đóng file lại
            return true;

        } catch (Exception e) {
            System.out.println("Load from file error: " +e);
            return false;
        }


    }
    public abstract T handleLine(String line);

    public boolean saveFromFile(String url){
        File f = new File(url);
        try {
            // Dựng toàn bộ nội dung trước, chỉ mở file để ghi 1 lần. Nếu toString() có
            // lỗi thì file cũ chưa bị xoá trắng (FileOutputStream cắt file ngay khi mở).
            StringBuilder sb = new StringBuilder();
            for (T t : dataList) {
                sb.append(t.toString()).append("\n");
            }
            FileOutputStream fos = new FileOutputStream(f);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            writer.write(sb.toString());
            writer.close();//đóng fike lại
            return true;//return

        } catch (Exception e) {
            System.out.println("Save to file error: " +e);
            return false;
        }
    }
    //hmaf HandelLine : xử lí dòng
    //với mục đích  biến 1 dòng thành 1 obj( nhận vào 1 line -> obj)
    // mỗi 1 bài có 1  cách biến line thành obk khác nhau

    //mình cần  chỉ cho nó  cachs  biến 1 dòng thành 1 obj như  như nào
    // còn lại là hàm này làm



}
