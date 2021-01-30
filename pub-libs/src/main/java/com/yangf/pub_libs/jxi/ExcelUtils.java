package com.yangf.pub_libs.jxi;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import android.widget.Toast;

import com.yangf.pub_libs.Date;
import com.yangf.pub_libs.util.FileUtil;

import org.apache.poi.xslf.usermodel.tutorial.Step1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * Created by Xohn on 2018/7/10.
 */

public class ExcelUtils {
    private static WritableWorkbook wwb;
    private static File file;

    /**
     * 判断excel表是否存在
     *
     * FileUtil.getExcelFileName("售后扫码"+Date.timestamp())
     * @param context 上下文
     * @return false为不存在
     */
    public static void createFile(String name,Context context) {
        file = FileUtil.getCacheFile(context,name);
    }

    // 创建excel表.
    public static boolean createExcel( List<String> title,List<String> resource) {
        try {

            OutputStream os = new FileOutputStream(file);// 创建Excel工作表
            wwb = Workbook.createWorkbook(os);
            WritableSheet sheet = wwb.createSheet(Date.modernClock(), 0);// 添加第一个工作表并设置第一个Sheet的名字
            Label label;
            for (int i = 0; i < title.size(); i++) {
                label = new Label(i, 0, title.get(i)+"", getHeader());// Label(x,y,z) 代表单元格的第x+1列，第y+1行, 内容z
                sheet.addCell(label);// 将定义好的单元格添加到工作表中
            }

            // 当前行数
            int row = sheet.getRows();
            for (int i = 0; i < resource.size(); i++) {
                Label labell = new Label(i, row, resource.get(i)+"");
                sheet.addCell(labell);
            }
            wwb.write();
            wwb.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
        } catch (WriteException e) {
            e.printStackTrace();
        }
        return format;
    }
}
