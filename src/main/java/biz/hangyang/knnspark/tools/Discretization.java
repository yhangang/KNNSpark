/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.hangyang.knnspark.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 *
 * @author hangyang
 */
public class Discretization {

    static String srcFile = "/home/hangyang/jar/test.csv";
    static String destFile = "/home/hangyang/jar/result.txt";
    static int year = 7;
    static int dataStartCol = 2;

    public static void main(String[] args) throws IOException {
        Double[][] companyData;

        File file = new File(srcFile);
        InputStreamReader isr = new InputStreamReader(
                new FileInputStream(file), "GBK");
        BufferedReader br = new BufferedReader(isr);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destFile, true), "utf-8"));

        while ((companyData = loadNextCompanyData(year, dataStartCol, br)) != null) {
            String gene = discretization(companyData);
            bw.write(gene+"\n");
        }
        br.close();
        bw.close();
    }

    /**
     * 将公司原始数据离散化为01串
     *
     * @param companyData 存放公司原始数据
     * @return
     */
    public static String discretization(Double[][] companyData) {
        StringBuilder gene = new StringBuilder();
        for (int j = 0; j < companyData[0].length; j++) {
            for (int i = 1; i < companyData.length; i++) {
                if (companyData[i][j] >= companyData[i - 1][j]) {
                    gene.append("1");
                } else {
                    gene.append("0");
                }
            }
        }
        return gene.toString();
    }

    /**
     * 从csv文件顺序读入数据存到矩阵中，每次读year指定的行数
     * @param year 每次读入的行数
     * @param dataStartCol 有效数据开始的列数，前面是注解
     * @param br 字节流
     * @return
     * @throws IOException 
     */
    public static Double[][] loadNextCompanyData(int year, int dataStartCol, BufferedReader br) throws IOException {
        Double[][] companyData = new Double[year][];
        for (int i = 0; i < year; i++) {
            String line = br.readLine();
            if (line == null) {
                return null;
            }
            String[] str = line.split(",", -1);
            companyData[i] = new Double[str.length - dataStartCol];
            for (int j = dataStartCol; j < str.length; j++) {
                companyData[i][j - dataStartCol] = Double.valueOf(str[j]);
            }
        }
        return companyData;
    }

}
