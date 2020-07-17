package com.baizhi;

import com.baizhi.entity.Student;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class PoiTest {


    @Test
    public void method() {
        //创建Excel文档
        Workbook workbook = new HSSFWorkbook();
        //创建工作表   参数：工作表名称(sheet1,sheet2...)
        //如果不指定工作表 默认按照：sheet1,sheet2...命名
        Sheet sheet = workbook.createSheet("学生表");
        //创建一行   参数：行下标（下标从0开始）
        Row row = sheet.createRow(0);
        //创建单元格   参数:单元格下标（下标从0开始）
        Cell cell = row.createCell(0);
        //给单元格设置内容
        cell.setCellValue("学号");
        //导出Excel文档
        try {
            workbook.write(new FileOutputStream(new File("D://Poi.xls")));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //导出
    @Test
    public void method1() {
        ArrayList<Student> students = new ArrayList<>();
        students.add(new Student("1", "小明", 18, new Date()));
        students.add(new Student("2", "小三", 19, new Date()));
        students.add(new Student("3", "小死", 18, new Date()));
        students.add(new Student("4", "小无", 20, new Date()));

        //创建Excel文档
        Workbook workbook = new HSSFWorkbook();

        //创建工作表   参数：工作表名称(sheet1,sheet2...)
        //如果不指定工作表 默认按照：sheet1,sheet2...命名
        Sheet sheet = workbook.createSheet("学生表");

        //设置字体样式
        Font font = workbook.createFont();
        font.setBold(true); //加粗
        font.setColor(Font.COLOR_RED);  //设置字体颜色
        font.setFontHeightInPoints((short) 24);  //设置字体大小
        font.setFontName("宋体");  //设置字体
        font.setItalic(true);  //设置斜体

        //创建字体样式对象
        CellStyle cellStyle1 = workbook.createCellStyle();
        cellStyle1.setFont(font);
        cellStyle1.setAlignment(HorizontalAlignment.CENTER);//设置字体居中

        //设置单元格宽度   参数：列索引，列宽
        sheet.setColumnWidth(3, 20 * 256);

        //创建标题行   参数：行下标（下标从0开始）
        Row row1 = sheet.createRow(0);

        //设置行高   参数：short类型的值
        row1.setHeight((short) 350);
        row1.createCell(0).setCellValue("学生信息");

        //合并列
        Cell cell1 = row1.createCell(0);
        //要合并的列      参数：行开始，行结束，列开时，列结束
        CellRangeAddress region = new CellRangeAddress(0, 0, 0, 3);
        sheet.addMergedRegion(region);
        cell1.setCellValue("学生信息表");

        //创建目录行   参数：行下标（下标从0开始）
        Row row2 = sheet.createRow(1);
        String[] title = {"学号", "姓名", "年龄", "生日"};

//        //合并行
//        Cell cell=row2.createCell(0);
//        CellRangeAddress region1=new CellRangeAddress(1, 5, 0, 0);
//        sheet.addMergedRegion(region1);
//        cell.setCellValue("用户");

        for (int i = 0; i < title.length; i++) {
            //创建单元格   参数:单元格下标（下标从0开始）
            Cell cell2 = row2.createCell(i);
            //给单元格设置内容
            cell2.setCellValue(title[i]);
            cell2.setCellStyle(cellStyle1);
        }

        //创建日期格式对象
        DataFormat dataFormat = workbook.createDataFormat();
        short format = dataFormat.getFormat("yyyy年MM月dd日");
        //创建样式对象
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(format);
        //处理数据行
        for (int i = 0; i < students.size(); i++) {
            //遍历一次创建一行
            Row row3 = sheet.createRow(i + 2);
            row3.createCell(0).setCellValue(students.get(i).getId());
            row3.createCell(1).setCellValue(students.get(i).getName());
            row3.createCell(2).setCellValue(students.get(i).getAge());
            //设置单元格日期格式
            Cell cell3 = row3.createCell(3);
            //添加日期样式
            cell3.setCellStyle(cellStyle);
            cell3.setCellValue(students.get(i).getBir());
        }
        //导出Excel文档
        try {
            workbook.write(new FileOutputStream(new File("D://Poi1.xls")));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //导入
    @Test
    public void method2() {

        try {
            //获取Excel文档
            Workbook workbook = new HSSFWorkbook(new FileInputStream(new File("D://Poi1.xls")));
            Sheet sheet = workbook.getSheet("学生表");
            for (int i = 2; i < sheet.getLastRowNum(); i++) {
                Student student = new Student();
                //获取行
                Row row = sheet.getRow(i);
                //获取Id
                student.setId(row.getCell(0).getStringCellValue());
                //获取name
                student.setName(row.getCell(1).getStringCellValue());
                //获取age
                double ages = row.getCell(2).getNumericCellValue();
                student.setAge((int) ages);
                //获取生日
                student.setBir(row.getCell(3).getDateCellValue());
                //调用一个插入数据库的方法
                System.out.println(student);
            }
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
