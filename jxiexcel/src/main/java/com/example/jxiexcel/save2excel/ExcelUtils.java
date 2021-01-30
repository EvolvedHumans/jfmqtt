package com.example.jxiexcel.save2excel;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * Created by Xohn on 2018/7/10.
 */

public class ExcelUtils {
    //内存地址
    private static String root = Environment.getExternalStorageDirectory().getPath(); //根目录
    private static WritableWorkbook wwb;
    private static File file = null;
    private static String ExcelName = "LockExcel";

    // 创建excel表.
    public static void createExcel(Context context) {
        if(context == null) return;
        try {
            Log.d("路径",root);
            /*if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)&&getAvailableStorage()>1000000) {
                Toast.makeText(context, "SD卡不可用", Toast.LENGTH_LONG).show();
                return;
            }*/
            String[] title = { "Num", "RomID", "地址", "NbID", "时间", "经度", "纬度", "公司", "班组", "G经度", "G纬度"};

            //checkAndCreateFile(context);
            checkAndCreateFile();

            // 创建Excel工作表
            OutputStream os = new FileOutputStream(file);
            wwb = Workbook.createWorkbook(os);
            // 添加第一个工作表并设置第一个Sheet的名字
            WritableSheet sheet = wwb.createSheet("LockInfo", 0);
            Label label;
            for (int i = 0; i < title.length; i++) {
                // Label(x,y,z) 代表单元格的第x+1列，第y+1行, 内容z
                // 在Label对象的子对象中指明单元格的位置和内容
                label = new Label(i, 0, title[i], getHeader());
                // 将定义好的单元格添加到工作表中
                sheet.addCell(label);
            }

            // 写入数据
            wwb.write();
            // 关闭文件
            wwb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //将数据存入到Excel表中
    public static boolean writeToExcel(Object... args) {
        try {
            Workbook oldWwb = Workbook.getWorkbook(file);
            WritableWorkbook wwb = Workbook.createWorkbook(file, oldWwb);
            WritableSheet sheet = wwb.getSheet(0);
            // 当前行数
            int row = sheet.getRows();

            Label orderNum = new Label(0, row, row + "");
            Label lockRomId = new Label(1, row, args[0] + "");
            Label lockAddr = new Label(2, row, args[1] + "");
            Label nbid = new Label(3, row, args[2] + "");
            Label time = new Label(4, row, args[3] + "");
            Label lon = new Label(5, row, args[4] + "");
            Label lat = new Label(6, row, args[5] + "");
			Label company = new Label(7, row, args[6] + "");
            Label banzu = new Label(8, row, args[7] + "");
            Label glon = new Label(9, row, args[8] + "");
            Label glat = new Label(10, row, args[9] + "");

            sheet.addCell(orderNum);
            sheet.addCell(lockRomId);
            sheet.addCell(lockAddr);
            sheet.addCell(nbid);
            sheet.addCell(time);
            sheet.addCell(lon);
            sheet.addCell(lat);
			sheet.addCell(company);
            sheet.addCell(banzu);
            sheet.addCell(glon);
            sheet.addCell(glat);

            // 从内存中写入文件中,只能刷一次.
            wwb.write();
            wwb.close();
            //Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    public static void checkAndCreateFile(Context context){
//        if(file == null) {
//            File dir = new File(Objects.requireNonNull(context.getExternalFilesDir(null)).getPath()); //保存到对应包名下
//            if (!dir.exists()) {
//            }
//            file = new File(dir, ExcelName + ".xls");
//        }
//    }
    public static void checkAndCreateFile(){
        if(file == null) {
            File dir =  new File(root);
            if (!dir.exists()) {//exists() 判断是否存在文件
                dir.mkdirs();  //dir.mkdirs(); //创建文件夹，如果父目录不存在，连同父目录一起创建

            }
            file = new File(dir, ExcelName + ".xls");
        }
    }

//    public static boolean wwbExist(Context context){
//        if(context == null) return false;
//        File dir = new File(context.getExternalFilesDir(null).getPath());
//        File file = new File(dir, ExcelName + ".xls");
//
//        return file.exists();
//    }

    public static boolean wwbExist(){
        File dir = new File(root);
        File file = new File(dir, ExcelName + ".xls");
        Log.d("路径",dir.getAbsolutePath());
        Log.d("文件名",file.getAbsolutePath());
        Log.d("文件存在与否",String.valueOf(file.exists()));
        return file.exists(); //当文件存在时，File.exists()返回false
    }

//    public static void deleteFile(Context context){
//        if(context == null) return;
//
//        File dir = new File(context.getExternalFilesDir(null).getPath());
//        File file = new File(dir, ExcelName + ".xls");
//        if(file.exists()){
//            file.delete();
//        }
//    }
    public static void deleteFile(){
        File dir = new File(root);
        File file = new File(dir, ExcelName + ".xls");
        if(file.exists()){
            file.delete();
        }
    }

    public static void writeExcel(Context context, List<Order> exportOrder) throws Exception {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)&&getAvailableStorage()>1000000) {
            Toast.makeText(context, "SD卡不可用", Toast.LENGTH_LONG).show();
            return;
        }
        String[] title = { "Id", "RomID", "地址" };
        File file;
        File dir = new File(context.getExternalFilesDir(null).getPath());
        file = new File(dir, ExcelName + ".xls");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // 创建Excel工作表
        WritableWorkbook wwb;
        OutputStream os = new FileOutputStream(file);
        wwb = Workbook.createWorkbook(os);
        // 添加第一个工作表并设置第一个Sheet的名字
        WritableSheet sheet = wwb.createSheet("LockInfo", 0);
        Label label;
        for (int i = 0; i < title.length; i++) {
            // Label(x,y,z) 代表单元格的第x+1列，第y+1行, 内容z
            // 在Label对象的子对象中指明单元格的位置和内容
            label = new Label(i, 0, title[i], getHeader());
            // 将定义好的单元格添加到工作表中
            sheet.addCell(label);
        }

        for (int i = 0; i < exportOrder.size(); i++) {
            Order order = exportOrder.get(i);

            Label orderNum = new Label(0, i + 1, order.id);
            Label lockRomId = new Label(1, i + 1, order.lockRomId);
            Label lockAddr = new Label(2, i + 1, order.lockAddr);

            sheet.addCell(orderNum);
            sheet.addCell(lockRomId);
            sheet.addCell(lockAddr);
            Toast.makeText(context, "写入成功", Toast.LENGTH_LONG).show();

        }
        // 写入数据
        wwb.write();
        // 关闭文件
        wwb.close();
    }

    private static WritableCellFormat getHeader() {
        WritableFont font = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD);// 定义字体
        try {
            font.setColour(Colour.BLUE);// 蓝色字体
        } catch (WriteException e1) {
            e1.printStackTrace();
        }
        WritableCellFormat format = new WritableCellFormat(font);
        try {
            format.setAlignment(jxl.format.Alignment.CENTRE);// 左右居中
            format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 上下居中
            // format.setBorder(Border.ALL, BorderLineStyle.THIN,Colour.BLACK);// 黑色边框
            // format.setBackground(Colour.YELLOW);// 黄色背景
        } catch (WriteException e) {
            e.printStackTrace();
        }
        return format;
    }

    /** 获取SD可用容量 */
    private static long getAvailableStorage() {
        StatFs statFs = new StatFs(root);
        long blockSize = statFs.getBlockSize();
        long availableBlocks = statFs.getAvailableBlocks();
        long availableSize = blockSize * availableBlocks;
        // Formatter.formatFileSize(context, availableSize);
        return availableSize;
    }
}
