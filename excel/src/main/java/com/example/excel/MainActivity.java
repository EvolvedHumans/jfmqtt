package com.example.excel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.yangf.pub_libs.util.ExcelUtil;
import com.yangf.pub_libs.util.FileDaoUtil;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    /**
     * excle表格的后缀
     */
    //public static final String SUFFIX = ".xls";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ExcelUtil excelUtil = ExcelUtil.getInstance();
        excelUtil.create(Nihao.class);

        excelUtil.addCell(10,10,"你好世界");
        excelUtil.addCell(10,11,"你好世界");

        excelUtil.write(FileDaoUtil.getCacheFile(this,FileDaoUtil.getExcelFileName("扫码应用")));


    }

}
