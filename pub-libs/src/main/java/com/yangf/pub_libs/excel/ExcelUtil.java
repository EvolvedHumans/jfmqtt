package com.yangf.pub_libs.excel;

import android.util.Log;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;

/**
 * @name： 杨帆
 * @Time： 2021年 01月 11日 10时 58分
 * @Data： Excel工具类
 * @JDK: VERSION_1_8
 * @Android_SDK: VERSION_8.0
 */
@Data
public class ExcelUtil <T> {

    private final static String TAG = "com.yangf.pub.libs.util";

    private HSSFWorkbook workbook; //表文件

    private HSSFSheet sheet; //Excel表

    private Map<Integer,HSSFRow> rowMap = new HashMap<>();

    public ExcelUtil() {
        workbook = new HSSFWorkbook();
    }

    private static volatile ExcelUtil excelUtil;

    public static ExcelUtil getInstance() {
        if (excelUtil == null) {
            synchronized (ExcelUtil.class) {
                //未初始化，则初始化instance变量
                if (excelUtil == null) {
                    excelUtil = new ExcelUtil();
                }
            }
        }
        return excelUtil;
    }

    //创建表
    public void create(Class<T> tClass) {
        try {
            boolean isExist = tClass.isAnnotationPresent(ExcelName.class);
            //如果该Object上有Excel的注解，则将注解打印出来
            if (isExist) {
                //拿到tClass上的注解实例
                ExcelName excel = (ExcelName) tClass.getAnnotation(ExcelName.class);
                if (excel != null && sheet == null) {
                    sheet = workbook.createSheet(excel.value());
                    Log.i("表名",excel.value());
                    sheet.setDefaultColumnWidth(20 * 256); //设置单元格宽度
                    sheet.setDefaultRowHeight((short) 300); //设置单元格高度
                }
            }
            //创建表头字段,获取私有成员上的注解
            Field[] fs = tClass.newInstance().getClass().getDeclaredFields();
            for (Field field:fs){
                Annotation[] annotations = field.getAnnotations();
                for(Annotation annotation:annotations){
                    //判断Annotation对象是否是我们需要的Excel类型对象
                    if(annotation instanceof ExcelHeaderName){
                        ExcelHeaderName excelHeaderName = (ExcelHeaderName)annotation;
                        Log.i("单元格所处位置",String.valueOf(excelHeaderName.id()));
                        Log.i("单元格表头",excelHeaderName.name());
                        addCell(0,excelHeaderName.id(),excelHeaderName.name());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 向指定单元格中填充数据源
     *
     * @param rownum 行数
     * @param column 列数
     * @param data 数据
     */
    public void addCell(int rownum,int column,Object data){
        if(sheet!=null){
            getHSSFRow(rownum).createCell(column).setCellValue(String.valueOf(data));
        }
    }

    /**
     * 从rowMap中取值，保证全部所取对象唯一
     *
     * @param rownum 行
     * @return 行的HSSFRow对象
     */
    public HSSFRow getHSSFRow(int rownum){
        if(rowMap.get(rownum)==null && sheet!=null){
            rowMap.put(rownum,sheet.createRow(rownum));
        }
        return rowMap.get(rownum);
    }

    /**
     * 将Excel表写入文件目录中
     *
     * @param fileName 文件名
     */
    public void write(File fileName) {
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(fileName);
            workbook.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
