import org.apache.commons.math3.linear.*;

import java.util.ArrayList;

public class Arrays {
    public static void main(String[] args) {
        double[][] a = { { 0, 0, 0}, { 31, 0, 15}, {0, 41, 0} };
//        Matrix x = challenge3(a);
//        x.show();
//        System.out.println();
        Matrix x = challenge4_5(a);
        System.out.println("_______");
        x.show();

    }

    public static Matrix challenge2(double[][] arr){
        double[][] s = {{1, -3}, {1, -2}};
        double[] b = {arr[0][2] + arr[2][2], arr[1][0]};
        Matrix a = new Matrix(s);
        Matrix c = new Matrix(b);
        Matrix answ = a.solve(c);
        answ.show();
        return answ;
    }


    public static Matrix challenge3(double[][] arr){
        double sum = arr[1][1] = (arr[1][0] + arr[1][2])/2;
        arr[0][1] = 2*sum - arr[2][1];
        double[][] s = {{1, 1, 0, 0}, {0, 1, 1, 0}, {0, 0, 1, 1}, {1, 0, 0, 1}};
        double [] b = {(3*sum - arr[0][1]), (3*sum - arr[1][2]), (3*sum - arr[2][1]), (3*sum - arr[1][0])};

        RealMatrix matrix = MatrixUtils.createRealMatrix(s);
        RealVector vector = MatrixUtils.createRealVector(b);
        SingularValueDecomposition svd = new SingularValueDecomposition(matrix);
        DecompositionSolver ds = svd.getSolver();
        RealVector res = ds.solve(vector);
        arr[0][0] = res.getEntry(0);
        arr[0][2] = res.getEntry(1);
        arr[2][0] = res.getEntry(2);
        arr[2][2] = res.getEntry(3);
        return new Matrix(arr);
    }
    public static Matrix challenge4_5(double[][] arr){
    double [][] s =
            {       {1, 1, 1, 0, 0, 0, 0, 0},
                    {0, 0, 0, 1, 1, 0, 0, -1},
                    {0, 0, 0, 0, 0, 1, 1, -1},
                    {1, 0, 0, 1, 0, 1, 0, -1},
                    {0, 1, 0, 0, 1, 0, 0, -1},
                    {0, 0, 1, 0, 0, 0, 1, -1},
                    {1, 0, 0, 0, 1, 0, 1, -1},
                    {0, 0, 1, 0, 1, 1, 0, -1}
            };
        ArrayList<Object> vs = getV(arr);
        double x = (double)vs.get(0);
        double [][] answ = new double[3][8];
        ArrayList<Double> v = (ArrayList<Double>)vs.get(1);
        double [] b = {x, -1*v.get(0), -1*v.get(1), 0, -1*v.get(1), -1*v.get(0), 0, 0};
        RealMatrix matrix = MatrixUtils.createRealMatrix(s);
        RealVector vector = MatrixUtils.createRealVector(b);
        RealMatrix inv = new LUDecomposition(matrix).getSolver().getInverse();

        SingularValueDecomposition svd = new SingularValueDecomposition(inv);
        DecompositionSolver ds = svd.getSolver();
        RealVector res = ds.solve(vector);
        for(int j = 0; j < 3; j++){
            for(int i = 0; i < 8; i++){
                System.out.println(i);
                System.out.println(x);
                if((res.getEntry(i)<=0 || (res.getEntry(i) == Math.floor(res.getEntry(i)) && !Double.isInfinite(res.getEntry(i))))){
                    i = 0;
                    x++;
                    b = new double[]{x, -1*v.get(0), -1*v.get(1), 0, -1*v.get(1), -1*v.get(0), 0, 0};
                    matrix = MatrixUtils.createRealMatrix(s);
                    vector = MatrixUtils.createRealVector(b);
                    inv = new LUDecomposition(matrix).getSolver().getInverse();
                    svd = new SingularValueDecomposition(inv);
                    ds = svd.getSolver();
                    res = ds.solve(vector);
                }
                else {
                    x++;
                    answ[j] = b.clone();
                    break;
                }
            }
        }
        System.out.println("*"+res);

        return new Matrix(answ);
    }


    public static ArrayList<Object> getV(double[][] arr){
        double tmp = 0;
        ArrayList<Double> tmp1 = new ArrayList<>();
        ArrayList<Object> ret = new ArrayList<>();
        for(int i = 0; i < arr.length; i++){
            for(int j = 0; j < arr.length; j++){
                if(arr[i][j] > tmp)
                    tmp = arr[i][j];
                if(arr[i][j] != 0){
                    tmp1.add(arr[i][j]);
                }
            }
        }
        ret.add(tmp);
        ret.add(tmp1);
        return ret;

    }

}

