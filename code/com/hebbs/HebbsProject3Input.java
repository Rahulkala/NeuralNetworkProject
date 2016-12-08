// Only with single input for the weight

package com.hebbs;

import java.io.*;

import Jama.Matrix;

public class HebbsProject3Input {

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
		HebbsProject3Input hp = new HebbsProject3Input();
		
		PrintWriter out = new PrintWriter(new File("output.txt"));
		
		// Input and Output data matrix
		double[][] data = hp.Inputdata("input.txt");
		
		// Round 2 : with only 2 input
		int row = 3;
		Matrix inputdata = new Matrix(row,35);
		int count=0;
		
		for(int i=0;i<10;i++){
			
			for(int j=i+1;j<10;j++){
				
				for(int j1=j+1;j1<10;j1++){

					count++;
					for(int k=0;k<35;k++){
						
						inputdata.set(0,k,data[i][k]);
						inputdata.set(1, k, data[j][k]);
						inputdata.set(2, k, data[j1][k]);
					}
					out.println("\n=========Input Data for Weight "+i+", "+j+", "+j1+"========");
					hp.printMatrix(inputdata,out);
					Matrix outputdata4W = inputdata.copy();
					
					// Weight Matrix
					Matrix weight = hp.calWeights(inputdata, outputdata4W);
					//out.println("\n====Weights at "+i+"=====");
					//hp.printMatrix(weight,out);
					
					//checking
					// New full matrix for check
					Matrix fulldata = new Matrix(data);
					Matrix temp = fulldata.times(weight);
					
					//out.println("==== OutPut Matrix ====");
					//hp.printMatrix(temp,out);
					boolean flag = true;
					for(int i1=0;i1<10;i1++){
						
						flag = true;
						for(int k1=0;k1<35;k1++){
							
							if(fulldata.get(i1, k1) != ((temp.get(i1, k1)>=0)?1:-1)){
							
								//out.print("( "+i1+" , "+j+" ) ");
								flag = false;
							}	
						}
						if(flag)
							out.println("\nCorrectly Classified: "+i1);
					}
				}
			}
		}
		out.close();
		System.out.println(count);
	}

}
