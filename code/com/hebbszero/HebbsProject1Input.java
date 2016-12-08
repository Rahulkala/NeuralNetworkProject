// Only with single input for the weight

package com.hebbszero;

import java.io.*;

import Jama.Matrix;

public class HebbsProject1Input {

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
		Matrix w = temp1.times(outputdata); 
		for(int i=0;i<w.getRowDimension();i++)
			w.set(i, i, 0);
		return w;
	}
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		HebbsProject1Input hp = new HebbsProject1Input();
		
		PrintWriter out = new PrintWriter(new File("output.txt"));
		
		// Input and Output data matrix
		double[][] data = hp.Inputdata("input.txt");
		
		// Round 1 : with only 1 input
		int row = 1;
		Matrix inputdata = new Matrix(row,35);
		
		for(int i=0;i<10;i++){
			
			for(int j=0;j<35;j++){
				
				inputdata.set(0, j, data[i][j]);
			}
			out.print("\nWeight from: "+i+" Correctly Classified: ");
			//hp.printMatrix(inputdata,out);
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
				for(int j=0;j<35;j++){
					
					if(fulldata.get(i1, j) != ((temp.get(i1, j)>=0)?1:-1)){
					
						//out.print("( "+i1+" , "+j+" ) ");
						flag = false;
					}	
				}
				if(flag)
					out.print(i1+", ");
			}
			
		}
		out.close();
	}

}
