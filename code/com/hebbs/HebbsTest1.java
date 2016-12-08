// Only with single input for the weight

package com.hebbs;

import java.io.*;

import Jama.Matrix;

public class HebbsTest1 {

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
		HebbsTest1 hp = new HebbsTest1();
		
		PrintWriter out = new PrintWriter(new File("output.txt"));
		
		// Input and Output data matrix
		double[][] data = hp.Inputdata("input.txt");
		
		// Round 2 : with only 2 input
		int row = 6;
		Matrix inputdata = new Matrix(row,35);
		int count=0;
		
		for(int k=0;k<35;k++){
			
			inputdata.set(0,k,data[1][k]);
			inputdata.set(1, k, data[2][k]);
			inputdata.set(2, k, data[4][k]);
			inputdata.set(3, k, data[5][k]);
			inputdata.set(4, k, data[7][k]);
			inputdata.set(5, k, data[8][k]);
		}
		out.println("\n=====Input Data for Weight 1, 2, 4, 5, 7, 8====");
		//hp.printMatrix(inputdata,out);
		//Matrix outputdata4W = inputdata.copy();
		
		// Weight Matrix
		Matrix weight  = null;
		Matrix A = new Matrix(1,35);
		
		for(int i=0;i<6;i++){
			
			A = inputdata.getMatrix(i, i, 0, 34);
			if(weight == null)
				weight = A.transpose().times(A);
			else{
				
				weight = weight.plus(A.transpose().times(A));
			}
		}
		//Matrix weight = hp.calWeights(inputdata, outputdata4W);
		//out.println("\n====Weights at "+i+"=====");
		hp.printMatrix(weight,out);
		
		//checking
		// New full matrix for check
		Matrix fulldata = new Matrix(data);
		Matrix temp = fulldata.times(weight);
		
		//out.println("==== OutPut Matrix ====");
		//hp.printMatrix(temp,out);
		boolean flag = true;
		int maxCount = 0;
		for(int i1=0;i1<10;i1++){
		
			flag = true;
			for(int k1=0;k1<35;k1++){
				
				if(fulldata.get(i1, k1) != ((temp.get(i1, k1)>=0)?1:-1)){
				
					//out.print("( "+i1+" , "+j+" ) ");
					flag = false;
				}	
			}
			if(flag){
				maxCount++;
				out.println("\nCorrectly Classified: "+i1);
			}	
		}
		out.println("count: "+maxCount);
		
		out.close();
		System.out.println(count);
	}

}
