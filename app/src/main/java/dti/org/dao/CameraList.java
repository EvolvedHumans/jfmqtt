package dti.org.dao;

import android.graphics.Bitmap;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 项目负责人： 杨帆
 * 包名：      dti.org.dao
 * 描述：      TODO 用来存储相片bitmap
 * 编译环境：  JDK-1_8、SDK-8.0
 * 创建时间：  2021年 02月 01日 12时 46分
 */
@Data
@AllArgsConstructor
public class CameraList {
    List<Bitmap> list;
}
