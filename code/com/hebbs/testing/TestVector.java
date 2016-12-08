package com.hebbs.testing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import Jama.Matrix;

public class TestVector {

	public double[][] Inputdata(String fname){
		
		int row = 10,col = 35;
		double[][] data = new double[row][col];
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
	public Matrix randomError(Matrix mat, int num, PrintWriter out){
		
		Random random = new Random();
		ArrayList<Integer> randNum = new ArrayList<>();
		//int[] randNum = new int[num];
		Matrix m = mat.copy();
		out.print("Random At: ");
		for(int i=0;i<num;i++){
			int temp = random.nextInt((34-0)+1)+0;
			if(!randNum.contains(temp)){
				randNum.add(temp);
				out.print(temp+", ");
			}	
			else
				i=i-1;
		}	
		for(int i=0;i<m.getRowDimension();i++)
			for(int j=0;j<randNum.size();j++){
				
				int noise = (int)(Math.random()*10);
				//double temp = m.get(i, randNum.get(j))*((noise%2==0)?-1:0);
				//double temp = m.get(i, randNum.get(j))*(-1);
				double temp = m.get(i, randNum.get(j))*0;
				m.set(i, randNum.get(j), temp);
			}
		return m;
	}
	public Matrix calWeights(Matrix inputdata, Matrix outputdata){
		
		Matrix temp1 = inputdata.transpose();
		return temp1.times(outputdata);
	}
	public void displayInput(Matrix ip, Matrix op, int row, PrintWriter out){
		
		for(int i=0;i<35;i++){
			
			if(i%5==0){
			
				out.println();
				for(int j=0;j<5;j++){
					
					if(ip.get(row, i+j) == 1)
						out.print("#");
					else if(ip.get(row, i+j) == -1)
						out.print(" ");
					else if(ip.get(row, i+j) == 0)
						out.print("0");
				}
				out.print("\t");
			}
			if(op.get(row, i) == 1)
				out.print("#");
			else
				out.print(" ");
		}
	}
	public Matrix bias(Matrix m){
		
		Matrix bias = new Matrix(1,35);
		for(int i=0;i<35;i++){
			
			double temp = 0;
			for(int j=0;j<m.getRowDimension();j++){
				
				temp+=m.get(j, i);
			}
			bias.set(0, i, temp);
		}
		return bias;
	}
	public Matrix addBias(Matrix m, Matrix b){
		
		Matrix t = new Matrix(m.getRowDimension(),35);
		for(int i=0;i<m.getRowDimension();i++){
			
			Matrix temp = m.getMatrix(i, i, 0, 34).plus(b);
			t.setMatrix(i, i, 0, 34, temp);
		}
		return t;
	}
	public Matrix convertM(Matrix m){
		
		Matrix changedM = new Matrix(m.getRowDimension(),m.getColumnDimension());
		for(int i=0;i<m.getRowDimension();i++)
			for(int j=0;j<m.getColumnDimension();j++){
				
				if(m.get(i, j)>0.1)
					changedM.set(i, j, 1);
				else if(m.get(i, j)<-0.1)
					changedM.set(i, j, -1);
				else
					changedM.set(i, j, 0);
			}
		return changedM;
	}
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub

		TestVector tv = new TestVector();
		int[] row = {1,4,6,7,9};
		double[][] data = tv.Inputdata("input.txt");
		Matrix input = new Matrix(row.length,35);
		for(int i=0;i<row.length;i++)
			for(int j=0;j<35;j++)
				input.set(i, j, data[row[i]][j]);
		
		Matrix weight = tv.calWeights(input, input.copy());
		Matrix bias = tv.bias(input);
		PrintWriter out = new PrintWriter(new File("output.txt"));
		PrintWriter out1 = new PrintWriter(new File("output1.txt"));
		Map<Integer, Integer> map = new HashMap<>();
		for(int run = 0; run < 10000;run++){

			Matrix errorInput = tv.randomError(input, 17, out);
			
			//Testing the Error Input
			
			Matrix temp = errorInput.times(weight);
			Matrix errorOutput = tv.addBias(temp, bias);
			errorOutput = tv.convertM(errorOutput);
			boolean flag = false;
			int count=0;
			for(int i=0;i<row.length;i++){
				
				flag = true;
				out.print("\nErr Ip Output");
				tv.displayInput(errorInput, errorOutput, i, out);
				for(int j=0;j<35;j++){
					
					if(input.get(i, j) != errorOutput.get(i, j)){
						
						out.println("\nWRONG CLASSIFICATION");
						flag = false;
						out1.println("Classified "+row[i]+": False");
						break;
					}
				}
				if(flag){
					
					count++;
					out.println("\nClassified to: "+row[i]);
					out1.println("Classified "+row[i]+": True");
					if(map.containsKey(row[i])){
						
						map.put(row[i], map.get(row[i])+1);
					}
					else{
						
						map.put(row[i], 1);
					}
				}
			}
			out.println("Total Classified: "+count+"\n");
		}	
		out1.close();
		out.close();
		for(int i=0;i<row.length;i++){
			
			//System.out.println("Count of "+row[i]+":\t"+map.get(row[i]));
			System.out.println(map.get(row[i]));
		}
	}

}
