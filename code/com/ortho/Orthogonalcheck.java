package com.ortho;

import java.io.BufferedReader;
import java.io.FileReader;

import Jama.Matrix;

public class Orthogonalcheck {

	public double[][] Inputdata(String fname){
		
		int row = 10,col = 35;
		double[][] data = new double[row][col];
		//Matrix data = new Matrix(row,col);
		String line;
		int i=0;
		try{
			
			BufferedReader bf = new BufferedReader(new FileReader(fname));
			while((line = bf.readLine())!=null){
				
				for(int j=0;j<col;j++){
					
					if(line.charAt(j)=='#'){
					
						data[i][j] = 1;
					}
					else
						data[i][j] = -1;
				}
				i++;
			}
			bf.close();
		}catch(Exception e){
		
			e.printStackTrace();
		}
		return data;
	}
	public double dotproduct(Matrix a, Matrix b){
		
		return a.times(b.transpose()).get(0, 0);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr = {1, 4, 6, 7, 9};
		Orthogonalcheck oc = new Orthogonalcheck();
		double[][] data = oc.Inputdata("input.txt");
		Matrix input = new Matrix(data);
		//int num = 10;
		//int count = 0;
		/*for(int i=0;i<input.getRowDimension();i++){
			
			for(int j=i+1;j<input.getRowDimension();j++){
		*/
		for(int i=0;i<arr.length;i++){

			for(int j=0;j<arr.length;j++){

				double dot = oc.dotproduct(input.getMatrix(arr[i], arr[i], 0, 34),input.getMatrix(arr[j], arr[j], 0, 34));
				System.out.print(dot+"\t");
			}
			System.out.println();
		}
		//System.out.println("Nearly Orthogonal["+(-1)*num+","+num+"] : "+count);
	}

}
