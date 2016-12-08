// Only with single input for the weight

package com.hebbs;

import java.io.*;

import Jama.Matrix;

public class HebbsProjectInput {

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
	public void printMatrix(Matrix m,PrintWriter out){
		
		for(int i=0;i<m.getRowDimension();i++){
			
			out.print("[");
			for(int j=0;j<m.getColumnDimension();j++){
				
				out.print(m.get(i, j)+" ");
			}
			out.println("]");
		}
	}
	public void printMatrix(Matrix m){
		
		for(int i=0;i<m.getRowDimension();i++){
			
			System.out.print("[");
			for(int j=0;j<m.getColumnDimension();j++){
				
				System.out.print(m.get(i, j)+" ");
			}
			System.out.println("]");
		}
	}
	public Matrix calWeights(Matrix inputdata, Matrix outputdata){
		
		Matrix temp1 = inputdata.transpose();
		//System.out.println(temp1.getRowDimension());
		return temp1.times(outputdata);
	}
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		HebbsProjectInput hp = new HebbsProjectInput();
		
		PrintWriter out = new PrintWriter(new File("output.txt"));
		
		// Input and Output data matrix
		double[][] data = hp.Inputdata("input.txt");
		
		int row = 9;
		Matrix inputdata = new Matrix(row,35);
		int count=0;
		
		for(int i=0;i<10;i++)
		for(int j=i+1;j<10;j++)
		for(int j1=j+1;j1<10;j1++)
		for(int j2=j1+1;j2<10;j2++)
		for(int j3=j2+1;j3<10;j3++)
		for(int j4=j3+1;j4<10;j4++)
		for(int j5=j4+1;j5<10;j5++)
		for(int j6=j5+1;j6<10;j6++)
		for(int j7=j6+1;j7<10;j7++){

			count++;
			for(int k=0;k<35;k++){
				
				inputdata.set(0,k,data[i][k]);
				inputdata.set(1, k, data[j][k]);
				inputdata.set(2, k, data[j1][k]);
				inputdata.set(3, k, data[j2][k]);
				inputdata.set(4, k, data[j3][k]);
				inputdata.set(5, k, data[j4][k]);
				inputdata.set(6, k, data[j5][k]);
				inputdata.set(7, k, data[j6][k]);
				inputdata.set(8, k, data[j7][k]);
			}
			out.print("\nWeight From: "+i+", "+j+", "+j1+", "+j2+", "+j3+", "+j4+", "+j5+", "+j6+", "+j7+"  Correctly Classified: ");
			Matrix outputdata4W = inputdata.copy();
			Matrix weight = hp.calWeights(inputdata, outputdata4W);
			Matrix fulldata = new Matrix(data);
			Matrix temp = fulldata.times(weight);
			
			boolean flag = true;
			int maxCount = 0;
			for(int i1=0;i1<10;i1++){
			
				flag = true;
				for(int k1=0;k1<35;k1++){
					
					if(fulldata.get(i1, k1) != ((temp.get(i1, k1)>=0)?1:-1)){
					
						flag = false;
					}	
				}
				if(flag){
					maxCount++;
					out.print(i1+", ");
				}	
			}
			out.print(" count: "+maxCount);
		}
		out.close();
		System.out.println(count);
	}

}
