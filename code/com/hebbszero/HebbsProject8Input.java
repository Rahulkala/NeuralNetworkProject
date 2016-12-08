// Only with single input for the weight

package com.hebbszero;

import java.io.*;

import Jama.Matrix;

public class HebbsProject8Input {

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
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		HebbsProject8Input hp = new HebbsProject8Input();
		
		PrintWriter out = new PrintWriter(new File("output.txt"));
		
		// Input and Output data matrix
		double[][] data = hp.Inputdata("input.txt");
		
		// Round 2 : with only 2 input
		int row = 8;
		Matrix inputdata = new Matrix(row,35);
		int count=0;
		
		for(int i=0;i<10;i++)
		for(int j=i+1;j<10;j++)
		for(int j1=j+1;j1<10;j1++)
		for(int j2=j1+1;j2<10;j2++)
		for(int j3=j2+1;j3<10;j3++)
		for(int j4=j3+1;j4<10;j4++)
		for(int j5=j4+1;j5<10;j5++)
		for(int j6=j5+1;j6<10;j6++){

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
			}
			out.print("\nWeight from: "+i+", "+j+", "+j1+", "+j2+", "+j3+", "+j4+", "+j5+", "+j6+" Correctly Classified: ");
			//hp.printMatrix(inputdata,out);
			Matrix outputdata4W = inputdata.copy();
			
			// Weight Matrix
			Matrix weight = hp.calWeights(inputdata, outputdata4W);
			Matrix bias = hp.bias(inputdata);
			//out.println("\n====Weights at "+i+"=====");
			//hp.printMatrix(weight,out);
			
			//checking
			// New full matrix for check
			Matrix fulldata = new Matrix(data);
			Matrix temp = hp.addBias(fulldata.times(weight),bias);
			temp = hp.convertM(temp);
			
			//out.println("==== OutPut Matrix ====");
			//hp.printMatrix(temp,out);
			boolean flag = true;
			int maxCount = 0;
			for(int i1=0;i1<10;i1++){
			
				flag = true;
				for(int k1=0;k1<35;k1++){
					
					if(fulldata.get(i1, k1) != temp.get(i1, k1)){
					
						//out.print("( "+i1+" , "+j+" ) ");
						flag = false;
					}	
				}
				if(flag){
					maxCount++;
					out.print(i1+", ");
				}	
			}
			out.print("count: "+maxCount);
		}
		out.close();
		System.out.println(count);
	}

}
